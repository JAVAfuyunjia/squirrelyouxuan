package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoQueryVo;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoVo;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface ISkuInfoService extends IService<SkuInfo> {

    /**
     *   获取sku分页列表
     * @param pageParam
     * @param skuInfoQueryVo
     * @return
     */
    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    /**
     * 添加sku商品
     * @param skuInfoVo
     */
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 根据Id获取skuinfo
     * @param id
     * @return
     */
    SkuInfoVo getSkuInfo(Long id);

    /**
     * 修改商品
     * @param skuInfoVo
     */
    void updateSkuInfo(SkuInfoVo skuInfoVo);

    /**
     * 商品审核
     * @param skuId
     * @param status
     */
    void check(Long skuId, Integer status);

    /**
     * 商品上下架
     * @param skuId
     * @param status
     */
    void publish(Long skuId, Integer status);

    /**
     * 新人专享
     * @param skuId
     * @param status
     */
    void isNewPerson(Long skuId, Integer status);
    /**
     * 删除skuInfo
     * @param id
     */
    void deleteBySkuId(Long id);


    /**
     * 通过skuIdList获取skuList
     * @param skuIdList
     * @return
     */
    List<SkuInfo> getSkuInfoList(List<Long> skuIdList);

    /**
     * 关键字获取商品列表
     * @param keyword
     * @return
     */
    List<SkuInfo> findSkuInfoByKeyword(String keyword);


    /**
     * 获取新人专享
     * @return
     */
    List<SkuInfo> findNewPersonSkuInfoList();

    /**
     * 验证和锁定库存
     * @param skuStockLockVoList
     * @param orderNo
     * @return
     */
    Boolean checkAndLock(List<SkuStockLockVo> skuStockLockVoList, String orderNo);

    /**
     * 扣减库存成功，更新订单状态
     * @param orderNo
     */
    void minusStock(String orderNo);
}
