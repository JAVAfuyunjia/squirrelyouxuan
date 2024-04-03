package com.camellia.squirrelyouxuan.product.api;

import com.camellia.squirrelyouxuan.model.product.Category;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.product.service.ICategoryService;
import com.camellia.squirrelyouxuan.product.service.ISkuInfoService;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoVo;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2023-11-10 13:10
 */
@RestController
@RequestMapping("api/product")
public class ProductInnerController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ISkuInfoService skuInfoService;
    @ApiOperation(value = "inner:根据分类id获取分类信息")
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @ApiOperation(value = "inner:根据skuId获取sku信息")
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId) {
        return skuInfoService.getById(skuId);
    }

    @ApiOperation(value = "inner:根据skuId列表获取sku信息列表")
    @PostMapping("inner/getSkuInfoList")
    public List<SkuInfo> getSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.getSkuInfoList(skuIdList);
    }

    @ApiOperation(value = "inner:根据关键字获取sku列表")
    @PostMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }

    @ApiOperation(value = "inner:根据categoryId列表获取category信息列表")
    @PostMapping("inner/getCategoryInfoList")
    public List<Category> getCategoryInfoList(@RequestBody List<Long> categoryIdList) {
        return categoryService.getCategoryInfoList(categoryIdList);
    }

    @ApiOperation("inner:获取所有分类")
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList() {
        List<Category> categoryList = categoryService.list();
        return categoryList;
    }

    /**
     * 获取新人专享商品
     * @return
     */
    @ApiOperation("inner:获取新人专享商品")
    @GetMapping("inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList() {
        List<SkuInfo> list = skuInfoService.findNewPersonSkuInfoList();
        return list;
    }

    /**
     * 根据skuId获取skuInfoVo信息
     * @param skuId
     * @return
     */

    @ApiOperation("inner:skuInfoVo信息")
    @GetMapping("inner/getSkuInfoVo/{skuId}")
    public SkuInfoVo getSkuInfoVo(@PathVariable Long skuId) {
        return skuInfoService.getSkuInfo(skuId);
    }

    /**
     * 验证和锁定库存
     * @param skuStockLockVoList
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "锁定库存")
    @PostMapping("inner/checkAndLock/{orderNo}")
    public Boolean checkAndLock(@RequestBody List<SkuStockLockVo> skuStockLockVoList,
                                @PathVariable String orderNo) {
        return skuInfoService.checkAndLock(skuStockLockVoList,orderNo);
    }

    /**
     * 获取商品数量
     */
    @ApiOperation(value = "获取商品数量")
    @GetMapping("inner/getSkuCount")
    public Integer getSkuCount() {
        return skuInfoService.count();
    }


}
