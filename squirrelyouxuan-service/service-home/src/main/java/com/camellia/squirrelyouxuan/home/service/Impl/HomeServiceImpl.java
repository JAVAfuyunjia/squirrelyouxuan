package com.camellia.squirrelyouxuan.home.service.Impl;

import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import com.camellia.squirrelyouxuan.feign.search.SkuFeignClient;
import com.camellia.squirrelyouxuan.feign.user.UserFeignClient;
import com.camellia.squirrelyouxuan.home.service.IHomeService;
import com.camellia.squirrelyouxuan.model.product.Category;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.model.search.SkuEs;
import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fuyunjia
 * @Date 2023-11-19 16:29
 */
@Service
public class HomeServiceImpl implements IHomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    /**
     * 首页数据显示接口
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> homeData(Long userId) {

        Map<String,Object> result = new HashMap<>();
        //1 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取需要数据
        UserAddressVo userAddressVo =
                userFeignClient.getUserAddressByUserId(userId);
        result.put("userAddressVo",userAddressVo);

        //2 获取所有分类
        // 远程调用service-product模块接口
        List<Category> categoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList",categoryList);

        //3 获取新人专享商品
        // 远程调用service-product模块接口
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList",newPersonSkuInfoList);

        //4 获取爆款商品
        // 远程调用service-search模块接口
        // hotscore 热门评分降序排序
        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        result.put("hotSkuList",hotSkuList);

        //5 封装获取数据到map集合，返回
        return result;
    }
}
