package com.camellia.squirrelyouxuan.cart.service.impl;

import com.camellia.squirrelyouxuan.cart.service.ICartInfoService;
import com.camellia.squirrelyouxuan.cart.utils.CartUtil;
import com.camellia.squirrelyouxuan.common.exception.SquirrelYouXuanException;
import com.camellia.squirrelyouxuan.common.redisconst.RedisConst;
import com.camellia.squirrelyouxuan.common.result.ResultCodeEnum;
import com.camellia.squirrelyouxuan.enums.SkuType;
import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import com.camellia.squirrelyouxuan.model.order.CartInfo;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.vo.order.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author fuyunjia
 * @Date 2023-11-22 11:51
 */
@Service
public class CartInfoServiceImpl implements ICartInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private  ProductFeignClient productFeignClient;


    /**
     * 根据skuId选中
     * @param userId
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(Long userId, Long skuId, Integer isChecked) {
        //获取redis的key
        String cartKey =  CartUtil.getCartKey(userId);
        //cartKey获取field-value
        BoundHashOperations<String,String,CartInfo> boundHashOperations =
                redisTemplate.boundHashOps(cartKey);
        //根据field（skuId）获取value（CartInfo）
        CartInfo cartInfo = boundHashOperations.get(skuId.toString());
        if(cartInfo != null) {
            cartInfo.setIsChecked(isChecked);
            //更新
            boundHashOperations.put(skuId.toString(),cartInfo);
            //设置key过期时间
            this.setCartKeyExpire(cartKey);
        }
    }

    /**
     * 全选购物车
     * @param userId
     * @param isChecked
     */
    @Override
    public void checkAllCart(Long userId, Integer isChecked) {
        String cartKey = CartUtil.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> boundHashOperations =
                redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = boundHashOperations.values();
        cartInfoList.forEach(cartInfo -> {
            cartInfo.setIsChecked(isChecked);
            boundHashOperations.put(cartInfo.getSkuId().toString(),cartInfo);
        });
        this.setCartKeyExpire(cartKey);
    }

    /**
     * 批量选中
     * @param skuIdList
     * @param userId
     * @param isChecked
     */
    @Override
    public void batchCheckCart(List<Long> skuIdList,
                               Long userId,
                               Integer isChecked) {
        String cartKey = CartUtil.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> boundHashOperations =
                redisTemplate.boundHashOps(cartKey);
        skuIdList.forEach(skuId -> {
            CartInfo cartInfo = boundHashOperations.get(skuId.toString());
            cartInfo.setIsChecked(isChecked);
            boundHashOperations.put(cartInfo.getSkuId().toString(),cartInfo);
        });
        this.setCartKeyExpire(cartKey);
    }

    /**
     * 购物车页面及订单信息
     * @param cartInfoList
     * @return
     */
    @Override
    public OrderConfirmVo findCartPagesInfo(List<CartInfo> cartInfoList) {
        //计算金额
        BigDecimal originalTotalAmount = cartInfoList.stream()
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .map(cartInfo -> cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderConfirmVo orderTradeVo = new OrderConfirmVo();
        orderTradeVo.setOriginalTotalAmount(originalTotalAmount);
        orderTradeVo.setCartInfoList(cartInfoList);

        return orderTradeVo;
    }

    /**
     * 根据skuId删除购物车
     * @param skuId
     * @param userId
     */
    @Override
    public void deleteCart(Long skuId, Long userId) {
        BoundHashOperations<String,String,CartInfo> hashOperations =
                redisTemplate.boundHashOps(CartUtil.getCartKey(userId));
        if(hashOperations.hasKey(skuId.toString())) {
            hashOperations.delete(skuId.toString());
        }
    }



    /**
     * 添加商品到购物车
     * 添加内容：当前登录用户id，skuId，商品数量
     * @param userId
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long userId, Long skuId, Integer skuNum) {
        // 1. 将购物车数据存在redis中，使用hash数据结构
        String cartKey = CartUtil.getCartKey(userId);
        BoundHashOperations<String,String, CartInfo> hashOperations =
                redisTemplate.boundHashOps(cartKey);
        //2 根据第一步查询出来的结果，得到是skuId + skuNum关系
        CartInfo cartInfo = null;
        //目的：判断是否是第一次添加这个商品到购物车
        // 进行判断，判断结果里面，是否有skuId
        if(hashOperations.hasKey(skuId.toString())) {
            //3 如果结果里面包含skuId，不是第一次添加
            //3.1 根据skuId，获取对应数量，更新数量
            cartInfo = hashOperations.get(skuId.toString());
            //把购物车存在商品之前数量获取数量，在进行数量更新操作
            Integer currentSkuNum = cartInfo.getSkuNum() + skuNum;

            // 这里计算完后的数量最小也要是1,这样商品存在购物车才有存在的意义，如果数量为0,前端直接发请求从购物车中删除该商品
            // 做预防而已，数量0时，理论上不可能来到后端这里
            if(currentSkuNum < 1) {
                return;
            }

            //更新cartInfo对象
            cartInfo.setSkuNum(currentSkuNum);

            //判断商品数量不能大于限购数量
            Integer perLimit = cartInfo.getPerLimit();
            if(currentSkuNum > perLimit) {
                throw new SquirrelYouXuanException(ResultCodeEnum.SKU_LIMIT_ERROR);
            }

            //更新其他值
            // 默认设置结算
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            //4 如果结果里面没有skuId，就是第一次添加
            //4.1 直接添加
            skuNum = 1;

            //远程调用根据skuId获取skuInfo
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            if(skuInfo == null) {
                throw new SquirrelYouXuanException(ResultCodeEnum.DATA_ERROR);
            }

            //封装cartInfo对象
            cartInfo = new CartInfo();
            cartInfo.setSkuId(skuId);
            cartInfo.setCategoryId(skuInfo.getCategoryId());
            cartInfo.setSkuType(skuInfo.getSkuType());
            cartInfo.setIsNewPerson(skuInfo.getIsNewPerson());
            cartInfo.setUserId(userId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuType(SkuType.COMMON.getCode());
            cartInfo.setPerLimit(skuInfo.getPerLimit());
            cartInfo.setImgUrl(skuInfo.getImgUrl());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setStatus(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

        //5 更新redis缓存
        hashOperations.put(skuId.toString(),cartInfo);

        //6 设置有效时间
        this.setCartKeyExpire(cartKey);

    }

    /**
     * 设置key 过期时间
     * @param key
     */
    private void setCartKeyExpire(String key) {
        redisTemplate.expire(key, RedisConst.USER_CART_EXPIRE, TimeUnit.MINUTES);
    }

    /**
     * 获取当前用户购物车选中购物项
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getCartCheckedList(Long userId) {

        String cartKey = CartUtil.getCartKey(userId);

        BoundHashOperations<String,String,CartInfo> boundHashOperations =
                redisTemplate.boundHashOps(cartKey);
        List<CartInfo> cartInfoList = boundHashOperations.values();
        //isChecked = 1购物项选中
        List<CartInfo> cartInfoListNew = cartInfoList.stream()
                .filter(cartInfo -> {
                    return cartInfo.getIsChecked().intValue() == 1;
                }).collect(Collectors.toList());
        return cartInfoListNew;
    }

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getCartList(Long userId) {
        //判断userId
        List<CartInfo> cartInfoList = new ArrayList<>();
        if(StringUtils.isEmpty(userId)) {
            return cartInfoList;
        }
        // 从redis获取购物车数据
        String cartKey = CartUtil.getCartKey(userId);
        BoundHashOperations<String,String,CartInfo> boundHashOperations =
                redisTemplate.boundHashOps(cartKey);

        cartInfoList = boundHashOperations.values();
        if(!CollectionUtils.isEmpty(cartInfoList)) {
            // 根据商品添加时间，降序
            cartInfoList.sort(new Comparator<CartInfo>() {
                @Override
                public int compare(CartInfo o1, CartInfo o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });
        }
        return cartInfoList;
    }

    /**
     * 根据userId删除选中购物车记录
     * @param userId
     */
    @Override
    public void deleteCartChecked(Long userId) {
        //根据userid查询选中购物车记录
        List<CartInfo> cartInfoList = this.getCartCheckedList(userId);

        //查询list数据处理，得到skuId集合
        List<Long> skuIdList = cartInfoList.stream().map(item -> item.getSkuId()).collect(Collectors.toList());

        //构建redis的key值
        // hash类型 key filed-value
        String cartKey = CartUtil.getCartKey(userId);
        //根据key查询filed-value结构
        BoundHashOperations<String,String,CartInfo> hashOperations =
                redisTemplate.boundHashOps(cartKey);

        //根据filed（skuId）删除redis数据
        skuIdList.forEach(skuId -> {
            hashOperations.delete(skuId.toString());
        });
    }



}
