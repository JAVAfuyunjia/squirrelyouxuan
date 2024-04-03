package com.camellia.squirrelyouxuan.home.service.Impl;

import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import com.camellia.squirrelyouxuan.feign.search.SkuFeignClient;
import com.camellia.squirrelyouxuan.home.service.IItemService;
import com.camellia.squirrelyouxuan.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author fuyunjia
 * @Date 2023-11-21 14:43
 */
@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ProductFeignClient productFeignClient;


    @Autowired
    private SkuFeignClient skuFeignClient;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 获取sku详细信息
     * @param skuId
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> item(Long skuId, Long userId) {
        Map<String, Object> result = new HashMap<>();

        // skuId查询SkuInfoVo
        CompletableFuture<SkuInfoVo> skuInfocompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    // 远程调用获取sku对应数据
                    SkuInfoVo skuInfoVo = productFeignClient.getSkuInfoVo(skuId);
                    result.put("skuInfoVo", skuInfoVo);
                    return skuInfoVo;
                },threadPoolExecutor);

        //更新商品热度
        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
            //远程调用更新热度
            skuFeignClient.incrHotScore(skuId);
        },threadPoolExecutor);

        //任务组合
        CompletableFuture.allOf(
                skuInfocompletableFuture,
                hotCompletableFuture
        ).join();


        return result;
    }
}
