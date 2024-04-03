package com.camellia.squirrelyouxuan.search.service;

import com.camellia.squirrelyouxuan.model.search.SkuEs;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import com.camellia.squirrelyouxuan.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2023-11-10 10:50
 */
public interface ISkuService {

    /**
     * 上架商品，将商品信息保存在ES中
     * @param skuId
     */
    void upperSku(Long skuId);

    /**
     * 下架商品，将商品从ES中删除
     * @param skuId
     */
    void lowerSku(Long skuId);

    /**
     * 删除sku
     * @param skuId
     */
    void deleteSku(Long skuId);

    /**
     *获取爆款商品
     * @return
     */
    List<SkuEs> findHotSkuList();

    /**
     * 更新商品热度
     * @param skuId
     */
    void incrHotScore(Long skuId);


    /**
     * 搜索商品
     * @param pageable
     * @param skuEsQueryVo
     * @return
     */
    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo);

    /**
     * 更新销量
     * @param skuStockLockVo
     */
    void updateSale(SkuStockLockVo skuStockLockVo);
}
