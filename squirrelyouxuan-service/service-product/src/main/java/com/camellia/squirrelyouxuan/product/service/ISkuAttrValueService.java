package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.SkuAttrValue;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface ISkuAttrValueService extends IService<SkuAttrValue> {

    /**
     * 根据id查询商品属性信息列表
     * @param id
     * @return
     */
    List<SkuAttrValue> getAttrValueListBySkuId(Long id);

}
