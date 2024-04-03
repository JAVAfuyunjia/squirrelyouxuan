package com.camellia.squirrelyouxuan.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
@Repository
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    /**
     * 解锁库存
     * @param skuId
     * @param skuNum
     */
    void unlockStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    /**
     * 验证库存
     * @param skuId
     * @param skuNum
     * @return
     */
    SkuInfo checkStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    /**
     * 锁定库存:update
     * @param skuId
     * @param skuNum
     * @return
     */
    Integer lockStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    /**
     * 减库存
     * @param skuId
     * @param skuNum
     */
    void minusStock(Long skuId, Integer skuNum);
}
