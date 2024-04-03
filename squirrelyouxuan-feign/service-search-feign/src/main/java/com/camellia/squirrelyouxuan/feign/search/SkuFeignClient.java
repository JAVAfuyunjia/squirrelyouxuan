package com.camellia.squirrelyouxuan.feign.search;

import com.camellia.squirrelyouxuan.model.search.SkuEs;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "service-search")
public interface SkuFeignClient {

    /**
     * 更新商品热度
     * @param skuId
     * @return
     */
    @GetMapping("/api/search/sku/inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId);

    /**
     * 获取热销产品
     * @return
     */
    @GetMapping("/api/search/sku/inner/findHotSkuList")
    public List<SkuEs> findHotSkuList();

    /**
     *  更新产品销量
     * @return
     */
    @PostMapping("/api/search/sku/inner/updateSale")
    public void updateSale(@RequestBody SkuStockLockVo skuStockLockVo);
}
