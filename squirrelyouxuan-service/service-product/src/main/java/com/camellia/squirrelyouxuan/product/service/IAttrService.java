package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface IAttrService extends IService<Attr> {

    /**
     * 根据属性分组id 获取属性列表
     * @param attrGroupId
     * @return
     */
    List<Attr> findByAttrGroupId(Long attrGroupId);

}
