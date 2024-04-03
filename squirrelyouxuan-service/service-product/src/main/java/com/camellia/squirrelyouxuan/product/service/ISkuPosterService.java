package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.SkuPoster;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface ISkuPosterService extends IService<SkuPoster> {

    /**
     * 根据商品id查询商品海报列表
     * @param id
     * @return
     */
    List<SkuPoster> getPosterListBySkuId(Long id);
}
