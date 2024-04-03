package com.camellia.squirrelyouxuan.home.controller;

import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author fuyunjia
 * @Date 2023-11-20 14:35
 */
@Api(tags = "商品分类")
@RestController
@RequestMapping("api/home")
public class CategoryApiController {

    @Resource
    private ProductFeignClient productFeignClient;

    @ApiOperation(value = "获取分类信息")
    @GetMapping("category")
    public Result index() {
        return Result.ok(productFeignClient.findAllCategoryList());
    }

    /**
     * 批量获取分类信息
     *
     * @return Result
     * @Author fuyunjia
     * @Date 2023-11-20 14:35
     * @Version 1.0
     */

}
