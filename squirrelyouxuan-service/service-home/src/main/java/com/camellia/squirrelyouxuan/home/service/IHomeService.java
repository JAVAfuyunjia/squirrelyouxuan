package com.camellia.squirrelyouxuan.home.service;

import java.util.Map;

/**
 * @Author fuyunjia
 * @Date 2023-11-19 16:28
 */
public interface IHomeService {
    /**
     * 获取主页数据
     * @param userId
     * @return
     */
    Map<String, Object> homeData(Long userId);

}
