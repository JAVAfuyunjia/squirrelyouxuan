package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.AttrGroup;
import com.camellia.squirrelyouxuan.vo.product.AttrGroupQueryVo;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface IAttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> selectPage(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo);

     List<AttrGroup> findAllList();
}
