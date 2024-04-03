package com.camellia.squirrelyouxuan.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.squirrelyouxuan.model.order.OrderInfo;
import com.camellia.squirrelyouxuan.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-25
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 获取订单统计
     * @return
     */
    List<OrderCountVo> getOrderCount();
}
