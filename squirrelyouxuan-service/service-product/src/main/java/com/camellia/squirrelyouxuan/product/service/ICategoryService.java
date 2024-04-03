package com.camellia.squirrelyouxuan.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.product.Category;
import com.camellia.squirrelyouxuan.vo.product.CategoryQueryVo;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 查询商品分类列表
     * @param pageParam
     * @param categoryQueryVo
     * @return
     */
    IPage<Category> selectPage(Page<Category> pageParam, CategoryQueryVo categoryQueryVo);

    /**
     * 查询所有商品分类
     * @return
     */
    List<Category> findAllList();

    /**
     * 通过categoryIdList获取categoryList
     * @param categoryIdList
     * @return
     */
    List<Category> getCategoryInfoList(List<Long> categoryIdList);
}
