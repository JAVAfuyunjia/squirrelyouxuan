package com.camellia.squirrelyouxuan.search.controller;

import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.model.search.SkuEs;
import com.camellia.squirrelyouxuan.search.service.ISkuService;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import com.camellia.squirrelyouxuan.vo.search.SkuEsQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品搜索列表接口
 * </p>
 *
 * @Author fuyunjia
 * @Date 2023-11-10 10:48
 */
@RestController
@RequestMapping("api/search/sku")
public class SkuApiController {

    @Autowired
    private ISkuService skuService;


    @ApiOperation(value = "更新商品销量")
    @PostMapping("inner/updateSale")
    public void updateSale(@RequestBody SkuStockLockVo skuStockLockVo){
        skuService.updateSale(skuStockLockVo);
    };

    @ApiOperation(value = "上架商品")
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperGoods(@PathVariable("skuId") Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    @ApiOperation(value = "下架商品")
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerGoods(@PathVariable("skuId") Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }

    /**
     * 获取爆款商品
     * @return
     */
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }

    /**
     * 更新商品热度
     * @param skuId
     * @return
     */
    @GetMapping("inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId) {
        skuService.incrHotScore(skuId);
        return true;
    }

    /**
     * 查询分类商品
     * @param page
     * @param limit
     * @param skuEsQueryVo
     * @return
     */
    @GetMapping("{page}/{limit}")
    public Result listSku(@PathVariable Integer page,
                          @PathVariable Integer limit,
                          SkuEsQueryVo skuEsQueryVo) {
        // 创建pageable对象，0代表第一页
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<SkuEs> pageModel = skuService.search(pageable,skuEsQueryVo);
        return Result.ok(pageModel);
    }

}
