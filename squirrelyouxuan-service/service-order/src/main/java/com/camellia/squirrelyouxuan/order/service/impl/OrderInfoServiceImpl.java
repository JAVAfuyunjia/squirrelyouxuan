package com.camellia.squirrelyouxuan.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.camellia.squirrelyouxuan.common.auth.AuthContextHolder;
import com.camellia.squirrelyouxuan.common.exception.SquirrelYouXuanException;
import com.camellia.squirrelyouxuan.common.redisconst.RedisConst;
import com.camellia.squirrelyouxuan.common.result.ResultCodeEnum;
import com.camellia.squirrelyouxuan.enums.OrderStatus;
import com.camellia.squirrelyouxuan.enums.SkuType;
import com.camellia.squirrelyouxuan.feign.cart.CartFeignClient;
import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import com.camellia.squirrelyouxuan.feign.user.UserFeignClient;
import com.camellia.squirrelyouxuan.model.order.CartInfo;
import com.camellia.squirrelyouxuan.model.order.OrderInfo;
import com.camellia.squirrelyouxuan.model.order.OrderItem;
import com.camellia.squirrelyouxuan.mq.constant.MqConst;
import com.camellia.squirrelyouxuan.mq.service.RabbitService;
import com.camellia.squirrelyouxuan.order.mapper.OrderInfoMapper;
import com.camellia.squirrelyouxuan.order.mapper.OrderItemMapper;
import com.camellia.squirrelyouxuan.order.service.IOrderInfoService;
import com.camellia.squirrelyouxuan.vo.order.*;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-25
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {


    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;


    /**
     * 确认订单
     *
     * @return
     */
    @Override
    public OrderConfirmVo confirmOrder() {
        //获取用户id
        Long userId = AuthContextHolder.getUserId();

        //获取用户收货地址信息
        UserAddressVo userAddressVo =
                userFeignClient.getUserAddressByUserId(userId);

        //获取购物车里面选中的商品
        List<CartInfo> cartInfoList = cartFeignClient.getCartCheckedList(userId);

        //唯一标识订单(生成预订单编号)
        String orderNo = System.currentTimeMillis() + "";
        redisTemplate.opsForValue().set(RedisConst.ORDER_REPEAT + orderNo, orderNo,
                24, TimeUnit.HOURS);

        //计算原始金额
        BigDecimal originalTotalAmount = cartInfoList.stream()
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .map(cartInfo -> cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //封装其他值
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        orderConfirmVo.setCartInfoList(cartInfoList);
        orderConfirmVo.setOriginalTotalAmount(originalTotalAmount);
        orderConfirmVo.setUserAddressVo(userAddressVo);
        orderConfirmVo.setOrderNo(orderNo);

        return orderConfirmVo;
    }

    /**
     * 生成订单
     *
     * @param orderParamVo
     * @return
     */
    @Override
    public Long submitOrder(OrderSubmitVo orderParamVo) {
        //第一步 设置给哪个用户生成订单  设置orderParamVo的userId
        Long userId = AuthContextHolder.getUserId();
        orderParamVo.setUserId(userId);

        //第二步 订单不能重复提交，重复提交验证
        // 通过redis + lua脚本进行判断
        //// lua脚本保证原子性操作
        //1 获取传递过来的订单 orderNo
        String orderNo = orderParamVo.getOrderNo();
        if (StringUtils.isEmpty(orderNo)) {
            throw new SquirrelYouXuanException(ResultCodeEnum.ILLEGAL_REQUEST);
        }

        //2 拿着orderNo 到 redis进行查询，
        String script = "if(redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";
        //3 如果redis有相同orderNo，表示正常提交订单，把redis的orderNo删除

        Boolean flag = (Boolean) redisTemplate
                .execute(new DefaultRedisScript(script, Boolean.class),
                        Arrays.asList(RedisConst.ORDER_REPEAT + orderNo), orderNo);
        //4 如果redis没有相同orderNo，表示重复提交了，不能再往后进行
        if (!flag) {
            throw new SquirrelYouXuanException(ResultCodeEnum.REPEAT_SUBMIT);
        }

        //第三步 验证库存 并且 锁定库存
        // 比如仓库有10个西红柿，我想买2个西红柿
        // ** 验证库存，查询仓库里面是是否有充足西红柿
        // ** 库存充足，库存锁定 2锁定（目前没有真正减库存）
        //1、远程调用service-cart模块，获取当前用户购物车商品（选中的购物项）
        List<CartInfo> cartInfoList =
                cartFeignClient.getCartCheckedList(userId);

        //2、购物车有很多商品，处理普通类型商品
        List<CartInfo> commonSkuList = cartInfoList.stream()
                .filter(cartInfo -> cartInfo.getSkuType().equals(SkuType.COMMON.getCode()))
                .collect(Collectors.toList());

        //3、把获取购物车里面普通类型商品list集合，
        // List<CartInfo>转换List<SkuStockLockVo>
        if (!CollectionUtils.isEmpty(commonSkuList)) {
            List<SkuStockLockVo> commonStockLockVoList = commonSkuList.stream().map(item -> {
                SkuStockLockVo skuStockLockVo = new SkuStockLockVo();
                skuStockLockVo.setSkuId(item.getSkuId());
                skuStockLockVo.setSkuNum(item.getSkuNum());
                return skuStockLockVo;
            }).collect(Collectors.toList());

            //4、远程调用service-product模块实现锁定商品
            //// 验证库存并锁定库存，保证具备原子性
            Boolean isLockSuccess =
                    productFeignClient.checkAndLock(commonStockLockVoList, orderNo);
            //库存锁定失败
            if (!isLockSuccess) {
                throw new SquirrelYouXuanException(ResultCodeEnum.ORDER_STOCK_FALL);
            }
        }

        //第四步 下单过程
        //1 向两张表添加数据
        // order_info 和 order_item
        Long orderId = this.saveOrder(orderParamVo, cartInfoList);

        //下单完成，删除购物车记录
        //发送mq消息
        rabbitService.sendMessage(MqConst.EXCHANGE_ORDER_DIRECT,
                MqConst.ROUTING_DELETE_CART, orderParamVo.getUserId());

        //第五步 返回订单id
        return orderId;
    }


    /**
     * 向两张表添加数据
     * order_info 和 order_item
     *
     * @param orderParamVo
     * @param cartInfoList
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public Long saveOrder(OrderSubmitVo orderParamVo,
                          List<CartInfo> cartInfoList) {


        if (CollectionUtils.isEmpty(cartInfoList)) {
            throw new SquirrelYouXuanException(ResultCodeEnum.DATA_ERROR);
        }

        //查询用户收货地址信息
        Long userId = AuthContextHolder.getUserId();
        UserAddressVo userAddressVo =
                userFeignClient.getUserAddressByUserId(userId);
        if (userAddressVo == null) {
            throw new SquirrelYouXuanException(ResultCodeEnum.DATA_ERROR);
        }

        // 封装订单项数据
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            OrderItem orderItem = getOrderItem(orderParamVo, cartInfo);
            orderItemList.add(orderItem);
        }

        //封装订单OrderInfo数据
        OrderInfo orderInfo = new OrderInfo();
        //用户id
        orderInfo.setUserId(userId);
        //订单号 唯一标识
        orderInfo.setOrderNo(orderParamVo.getOrderNo());
        //订单状态，生成成功未支付
        orderInfo.setOrderStatus(OrderStatus.UNPAID);

        orderInfo.setReceiverName(orderParamVo.getReceiverName());
        orderInfo.setReceiverPhone(orderParamVo.getReceiverPhone());
        orderInfo.setReceiverAddress(userAddressVo.getAddressDetail());

        //计算订单金额
        BigDecimal originalTotalAmount = this.computeTotalAmount(cartInfoList);
        //计算订单金额
        orderInfo.setOriginalTotalAmount(originalTotalAmount);

        //添加数据到订单基本信息表
        baseMapper.insert(orderInfo);

        //添加订单里面订单项
        orderItemList.forEach(orderItem -> {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        });

        //订单id
        return orderInfo.getId();
    }

    private static OrderItem getOrderItem(OrderSubmitVo orderParamVo, CartInfo cartInfo) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(null);
        orderItem.setCategoryId(cartInfo.getCategoryId());

        if (cartInfo.getSkuType().equals(SkuType.COMMON.getCode())) {
            orderItem.setSkuType(SkuType.COMMON);
        }

        orderItem.setSkuId(cartInfo.getSkuId());
        orderItem.setSkuName(cartInfo.getSkuName());
        orderItem.setSkuPrice(cartInfo.getCartPrice());
        orderItem.setImgUrl(cartInfo.getImgUrl());
        orderItem.setSkuNum(cartInfo.getSkuNum());

        //总金额
        BigDecimal skuTotalAmount =
                orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum()));

        orderItem.setTotalAmount(skuTotalAmount);
        return orderItem;
    }

    // 计算总金额
    private BigDecimal computeTotalAmount(List<CartInfo> cartInfoList) {
        BigDecimal total = new BigDecimal(0);
        for (CartInfo cartInfo : cartInfoList) {
            BigDecimal itemTotal = cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum()));
            total = total.add(itemTotal);
        }
        return total;
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo getOrderInfoById(Long orderId) {
        //根据orderId查询订单基本信息
        OrderInfo orderInfo = baseMapper.selectById(orderId);

        //根据orderId查询订单所有订单项list列表
        List<OrderItem> orderItemList = orderItemMapper.selectList(
                new LambdaQueryWrapper<OrderItem>()
                        .eq(OrderItem::getOrderId, orderInfo.getId())
        );

        //查询所有订单项封装到每个订单对象里面
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    /**
     * 通过订单号获取订单详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = baseMapper.selectOne(
                new LambdaQueryWrapper<OrderInfo>()
                        .eq(OrderInfo::getOrderNo, orderNo)
        );
        return orderInfo;
    }

    /**
     * 订单支付成功，更新订单状态，扣减库存
     *
     * @param orderNo
     */
    @Override
    public void orderPay(String orderNo) {
        //查询订单状态是否已经修改完成了支付状态
        OrderInfo orderInfo = this.getOrderInfoByOrderNo(orderNo);
        if (orderInfo == null || orderInfo.getOrderStatus() != OrderStatus.UNPAID) {
            return;
        }
        //更新状态
        this.updateOrderStatus(orderInfo.getId());

        //扣减库存
        rabbitService.sendMessage(MqConst.EXCHANGE_ORDER_DIRECT,
                MqConst.ROUTING_MINUS_STOCK,
                orderNo);
    }

    /**
     * 更新状态
     */

    private void updateOrderStatus(Long id) {
        OrderInfo orderInfo = baseMapper.selectById(id);
        orderInfo.setOrderStatus(OrderStatus.WAITING_DELEVER);
        baseMapper.updateById(orderInfo);
    }

    /**
     * 订单查询
     *
     * @param pageParam
     * @param orderUserQueryVo
     * @return
     */
    @Override
    public IPage<OrderInfo> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam,
                                                     OrderUserQueryVo orderUserQueryVo) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, orderUserQueryVo.getUserId());
        wrapper.eq(OrderInfo::getOrderStatus, orderUserQueryVo.getOrderStatus());
        IPage<OrderInfo> pageModel = baseMapper.selectPage(pageParam, wrapper);

        //获取每个订单，把每个订单里面订单项查询封装
        List<OrderInfo> orderInfoList = pageModel.getRecords();
        for (OrderInfo orderInfo : orderInfoList) {
            //根据订单id查询里面所有订单项列表
            List<OrderItem> orderItemList = orderItemMapper.selectList(
                    new LambdaQueryWrapper<OrderItem>()
                            .eq(OrderItem::getOrderId, orderInfo.getId())
            );
            //把订单项集合封装到每个订单里面
            orderInfo.setOrderItemList(orderItemList);
            //封装订单状态名称
            orderInfo.getParam().put("orderStatusName", orderInfo.getOrderStatus().getComment());
        }
        return pageModel;
    }

    /**
     * 后台订单查询
     *
     * @param pageParam
     * @param orderInfoVo
     * @return
     */
    @Override
    public IPage<OrderInfo> selectPageOrder(Page<OrderInfo> pageParam, OrderInfoVo orderInfoVo) {
        String orderNo = orderInfoVo.getOrderNo();
        OrderStatus orderStatus = orderInfoVo.getOrderStatus();
        String receiverName = orderInfoVo.getReceiverName();
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(orderNo)) {
            wrapper.eq(OrderInfo::getOrderNo, orderNo);
            Page<OrderInfo> pageModel = baseMapper.selectPage(pageParam, wrapper);
            extractedOrderStatus(pageModel);
            return pageModel;
        }
        if (orderStatus != null) {
            wrapper.eq(OrderInfo::getOrderStatus, orderStatus);
            Page<OrderInfo> pageModel = baseMapper.selectPage(pageParam, wrapper);
            extractedOrderStatus(pageModel);
            return pageModel;
        }

        if (!StringUtils.isEmpty(receiverName)) {
            wrapper.like(OrderInfo::getReceiverName, receiverName);
        }
        IPage<OrderInfo> pageModel = baseMapper.selectPage(pageParam, wrapper);

        extractedOrderStatus(pageModel);

        return pageModel;
    }

    /**
     * 将订单状态枚举类转换为订单名
     *
     * @param pageModel
     */
    private void extractedOrderStatus(IPage<OrderInfo> pageModel) {
        List<OrderInfo> orderInfoList = pageModel.getRecords();
        for (OrderInfo orderInfo : orderInfoList) {
            String comment = orderInfo.getOrderStatus().getComment();
            orderInfo.getParam().put("orderStatusName", comment);
        }
    }

    /**
     * 获取订单统计数据
     * @return
     */
    @Override
    public List<OrderCountVo> orderCountForChart() {
        return orderInfoMapper.getOrderCount();
    }

    /**
     * 根据订单id查询订单详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public OrderInfo getByOrderId(String orderNo) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderNo, orderNo);

        return baseMapper.selectOne(wrapper);


    }
}
