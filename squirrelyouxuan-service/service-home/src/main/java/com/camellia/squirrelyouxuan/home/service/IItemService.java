package com.camellia.squirrelyouxuan.home.service;

import java.util.Map;

/**
 * @Author fuyunjia
 * @Date 2023-11-21 14:42
 */
public interface IItemService {

    /**
     * 获取sku详细信息
     * @param skuId
     * @param userId
     * @return
     */
    Map<String, Object> item(Long skuId, Long userId);
}
