package com.camellia.squirrelyouxuan.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.product.service.ISkuInfoService;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoQueryVo;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
@Api(value = "SkuInfo管理", tags = "商品Sku管理")
@RestController
@RequestMapping(value="/admin/product/skuInfo")
public class SkuInfoController {

    @Autowired
    private ISkuInfoService skuInfoService;

    @ApiOperation(value = "获取sku分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<SkuInfo>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "skuInfoQueryVo", value = "查询对象", required = false)
            SkuInfoQueryVo skuInfoQueryVo) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPage(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * sku商品添加方法
     * @param skuInfoVo
     * @return
     */
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation("获取sku信息")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.deleteBySkuId(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("商品审核")
    @GetMapping("check/{skuId}/{status}")
    public Result check(@PathVariable Long skuId,
                        @PathVariable Integer status) {
        skuInfoService.check(skuId,status);
        return Result.ok(null);
    }

    /**
     * 商品上下架
     * @param skuId
     * @param status
     * @return
     */
    @ApiOperation("商品上下架")
    @GetMapping("publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId,
                          @PathVariable Integer status) {
        skuInfoService.publish(skuId,status);
        return Result.ok(null);
    }

    /**
     * 新人专享
     * @param skuId
     * @param status
     * @return
     */
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@PathVariable("skuId") Long skuId,
                              @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok(null);
    }


}

