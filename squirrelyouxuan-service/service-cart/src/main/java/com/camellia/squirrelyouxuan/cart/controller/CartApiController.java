package com.camellia.squirrelyouxuan.cart.controller;

import com.camellia.squirrelyouxuan.cart.service.ICartInfoService;
import com.camellia.squirrelyouxuan.common.auth.AuthContextHolder;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.model.order.CartInfo;
import com.camellia.squirrelyouxuan.vo.order.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2023-11-22 11:56
 */
@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private ICartInfoService cartInfoService;




    /**
     * 根据skuId选中
     * @param skuId
     * @param isChecked
     * @return
     */
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,
                            @PathVariable Integer isChecked) {
        //获取userId
        Long userId = AuthContextHolder.getUserId();
        //调用方法
        cartInfoService.checkCart(userId,skuId,isChecked);
        return Result.ok(null);
    }

    /**
     * 全选购物车
     * @param isChecked
     * @return
     */
    @GetMapping("checkAllCart/{isChecked}")
    public Result checkAllCart(@PathVariable Integer isChecked) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.checkAllCart(userId,isChecked);
        return Result.ok(null);
    }

    /**
     * 批量选中
     * @param skuIdList
     * @param isChecked
     * @return
     */
    @PostMapping("batchCheckCart/{isChecked}")
    public Result batchCheckCart(@RequestBody List<Long> skuIdList,
                                 @PathVariable Integer isChecked) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.batchCheckCart(skuIdList,userId,isChecked);
        return Result.ok(null);
    }

    /**
     * 购物车列表
     * @return
     */
    @GetMapping("cartList")
    public Result cartList() {
        // 获取userId
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        return Result.ok(cartInfoList);
    }

    /**
     * 添加商品到购物车
     */
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        // 获取当前登录用户Id
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.addToCart(userId,skuId,skuNum);
        return Result.ok(null);
    }

    /**
     * 根据skuId删除购物车
     * @param skuId
     * @return
     */
    @DeleteMapping("deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId) {
        Long userId = AuthContextHolder.getUserId();
        cartInfoService.deleteCart(skuId,userId);
        return Result.ok(null);
    }


    /**
     * 获取当前用户购物车选中购物项
     * 根据用户Id 查询购物车列表
     *
     * @param userId
     * @return
     */
    @GetMapping("inner/getCartCheckedList/{userId}")
    public List<CartInfo> getCartCheckedList(@PathVariable("userId") Long userId) {
        return cartInfoService.getCartCheckedList(userId);
    }

    /**
     * 查询购物车页面
     * @return
     */
    @GetMapping("getCartPagesInfo")
    public Result CartList() {
        // 获取用户Id
        Long userId = AuthContextHolder.getUserId();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);

        OrderConfirmVo orderTradeVo = cartInfoService.findCartPagesInfo(cartInfoList);

        return Result.ok(orderTradeVo);
    }



}
