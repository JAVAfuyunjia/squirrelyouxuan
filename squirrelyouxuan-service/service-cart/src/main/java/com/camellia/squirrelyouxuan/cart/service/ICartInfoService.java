package com.camellia.squirrelyouxuan.cart.service;

import com.camellia.squirrelyouxuan.model.order.CartInfo;
import com.camellia.squirrelyouxuan.vo.order.OrderConfirmVo;

import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2023-11-22 11:50
 */

public interface ICartInfoService {
    /**
     * 添加商品到购物车
     * @param userId
     * @param skuId
     * @param skuNum
     */
    void addToCart(Long userId, Long skuId, Integer skuNum);

    /**
     * 根据skuId删除购物车
     * @param skuId
     * @param userId
     */
    void deleteCart(Long skuId, Long userId);



    /**
     * 批量删除购物车 多个skuId
     * @param userId
     * @return
     */
    List<CartInfo> getCartCheckedList(Long userId);

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    List<CartInfo> getCartList(Long userId);

    /**
     * 根据skuId选中购物项
     * @param userId
     * @param skuId
     * @param isChecked
     */
    void checkCart(Long userId, Long skuId, Integer isChecked);

    /**
     * 全选购物车
     * @param userId
     * @param isChecked
     */
    void checkAllCart(Long userId, Integer isChecked);

    /**
     * 批量选中
     * @param skuIdList
     * @param userId
     * @param isChecked
     */
    void batchCheckCart(List<Long> skuIdList, Long userId, Integer isChecked);

    /**
     * 购物车页面及订单信息
     * @param cartInfoList
     * @return
     */
    OrderConfirmVo findCartPagesInfo(List<CartInfo> cartInfoList);

    /**
     * 根据userId删除选中购物车记录
     * @param userId
     */
    void deleteCartChecked(Long userId);

}
