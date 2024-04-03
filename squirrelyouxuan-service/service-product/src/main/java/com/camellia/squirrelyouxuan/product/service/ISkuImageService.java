package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.SkuImage;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface ISkuImageService extends IService<SkuImage> {

    /**
     * 根据商品Id查询商品图片列表
     * @param id
     * @return
     */
    List<SkuImage> getImageListBySkuId(Long id);
}
