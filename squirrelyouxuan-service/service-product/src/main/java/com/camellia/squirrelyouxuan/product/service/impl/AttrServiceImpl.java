package com.camellia.squirrelyouxuan.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.camellia.squirrelyouxuan.model.product.Attr;
import com.camellia.squirrelyouxuan.product.mapper.AttrMapper;
import com.camellia.squirrelyouxuan.product.service.IAttrService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-07
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements IAttrService {

    /**
     * 根据属性分组id 获取属性列表
     * @param attrGroupId
     * @return
     */
    @Override
    public List<Attr> findByAttrGroupId(Long attrGroupId) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getAttrGroupId,attrGroupId);
        List<Attr> attrList = baseMapper.selectList(wrapper);
        return attrList;
    }
}
