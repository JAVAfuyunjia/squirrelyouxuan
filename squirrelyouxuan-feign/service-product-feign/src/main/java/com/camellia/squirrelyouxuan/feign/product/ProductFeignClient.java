package com.camellia.squirrelyouxuan.feign.product;

import com.camellia.squirrelyouxuan.model.product.Category;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoVo;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2023-11-10 13:27
 */

@FeignClient(value = "service-product")
public interface ProductFeignClient {


    /**
     * 获取新人专享商品
     * @return
     */
    @GetMapping("/api/product/inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList();

    /**
     * 根据id获取分类信息
     * @param categoryId
     * @return
     */
    @GetMapping("api/product/inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId);

    /**
     * 根据id获取sku信息
     * @param skuId
     * @return
     */
    @GetMapping("api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);

    /**
     * 关键字获取sku列表
     * @param keyword
     * @return
     */
    @PostMapping("api/product/inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable String keyword);

    /**
     * 根据skuId列表获取sku信息列表
     * @param skuIdList
     * @return
     */
    @PostMapping ("api/product/inner/getSkuInfoList")
    public List<SkuInfo> getSkuInfoList(@RequestBody List<Long> skuIdList);

    /**
     * 通过categoryIdList获取categoryList
     * @param categoryIdList
     * @return
     */
    @PostMapping("api/product/inner/getCategoryInfoList")
    public List<Category> getCategoryInfoList(@RequestBody List<Long> categoryIdList);


    /**
     * 所有分类
     * @return
     */
    @GetMapping("/api/product/inner/findAllCategoryList")
    public List<Category> findAllCategoryList();

    /**
     * 根据skuId获取sku信息
     * @param skuId
     * @return
     */
    @GetMapping("/api/product/inner/getSkuInfoVo/{skuId}")
    public SkuInfoVo getSkuInfoVo(@PathVariable Long skuId);


    /**
     * 获取sku数量
     * @return
     */
    @GetMapping("/api/product/inner/getSkuCount")
    public Integer getSkuCount();

    /**
     * 验证和锁定库存
     * @param skuStockLockVoList
     * @param orderNo
     * @return
     */
    @PostMapping("/api/product/inner/checkAndLock/{orderNo}")
    public Boolean checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList,
                                @PathVariable("orderNo") String orderNo);
}
