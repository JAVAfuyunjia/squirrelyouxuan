package com.camellia.squirrelyouxuan.search.service.Impl;

import com.camellia.squirrelyouxuan.common.redisconst.RedisConst;
import com.camellia.squirrelyouxuan.enums.SkuType;
import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import com.camellia.squirrelyouxuan.model.product.Category;
import com.camellia.squirrelyouxuan.model.product.SkuInfo;
import com.camellia.squirrelyouxuan.model.search.SkuEs;
import com.camellia.squirrelyouxuan.search.repository.ISkuRepository;
import com.camellia.squirrelyouxuan.search.service.ISkuService;
import com.camellia.squirrelyouxuan.vo.product.SkuStockLockVo;
import com.camellia.squirrelyouxuan.vo.search.SkuEsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @Author fuyunjia
 * @Date 2023-11-10 10:51
 */
@Service
public class SkuServiceImpl implements ISkuService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ISkuRepository skuRepository;


    @Override
    public void upperSku(Long skuId) {

        // 远程调用，根据skuId获取相关信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo == null) {
            return;
        }
        // 远程调用，根据category获取相关信息
        Category category = productFeignClient.getCategory(skuInfo.getCategoryId());

        SkuEs skuEs = new SkuEs();
        // 封装分类信息
        if (category != null) {
            skuEs.setCategoryId(category.getId());
            skuEs.setCategoryName(category.getName());
        }
        // 封装sku信息部分
        skuEs.setId(skuInfo.getId());
        skuEs.setKeyword(skuInfo.getSkuName()+","+skuEs.getCategoryName());
        skuEs.setIsNewPerson(skuInfo.getIsNewPerson());
        skuEs.setImgUrl(skuInfo.getImgUrl());
        skuEs.setTitle(skuInfo.getSkuName());
        if(skuInfo.getSkuType().equals(SkuType.COMMON.getCode())) {
            skuEs.setSkuType(0);
            skuEs.setPrice(skuInfo.getPrice().doubleValue());
            skuEs.setStock(skuInfo.getStock());
            skuEs.setSale(skuInfo.getSale());
            skuEs.setPerLimit(skuInfo.getPerLimit());
        } else {
            //TODO 待完善-秒杀商品看看以后有没有时间做
        }
        // 添加信息到ES中
        skuRepository.save(skuEs);
    }

    @Override
    public void lowerSku(Long skuId) {
        this.deleteSku(skuId);
    }

    @Override
    public void deleteSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    /**
     *  更新产品销量
     * @param skuStockLockVo
     */
    @Override
    public void updateSale(SkuStockLockVo skuStockLockVo) {
        Long skuId = skuStockLockVo.getSkuId();
        Integer saleNum = skuStockLockVo.getSkuNum();
        // 更加skuId更新skuEs的内容
        Optional<SkuEs> optional = skuRepository.findById(skuId);
        SkuEs skuEs = optional.get();
        skuEs.setSale(saleNum+skuEs.getSale());
        skuEs.setStock(skuEs.getStock()-saleNum);
        skuRepository.save(skuEs);

    }

    /**
     * 根据分类在ES中查询所有的sku
     * @param pageable
     * @param skuEsQueryVo
     * @return
     */
    @Override
    public Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo) {


        Page<SkuEs> pageModel = null;
        //2 调用SkuRepository方法，根据springData命名规则定义方法，进行条件查询
        //  判断keyword是否为空，如果为空 ，根据仓库id + 分类id查询
        String keyword = skuEsQueryVo.getKeyword();
        if(StringUtils.isEmpty(keyword)) {
            pageModel =
                    skuRepository
                            .findByCategoryId(
                                    skuEsQueryVo.getCategoryId(),
                                    pageable);
        } else {
            // 如果keyword不为空根据仓库id + keyword进行查询
            pageModel = skuRepository
                    .findByKeyword(
                            skuEsQueryVo.getKeyword(),
                            pageable);
        }
        return pageModel;
    }

    /**
     * 更新商品热度
     * @param skuId
     */
    @Override
    public void incrHotScore(Long skuId) {
        // 设置增幅阈值,redis保存数据，每次+1
        Double hotScore = redisTemplate.opsForZSet().incrementScore(RedisConst.HOR_SCORE, "skuId:" + skuId, 1);
        //规则
        if(hotScore%10==0) {
            //更新es
            Optional<SkuEs> optional = skuRepository.findById(skuId);
            SkuEs skuEs = optional.get();
            skuEs.setHotScore(Math.round(hotScore));
            skuRepository.save(skuEs);
        }
    }

    /**
     * 获取爆款商品
     * @return
     */
    @Override
    public List<SkuEs> findHotSkuList() {
        //find  read  get开头
        //关联条件关键字
        // 0代表第一页
        Pageable pageable = PageRequest.of(0,10);
        Page<SkuEs> pageModel = skuRepository.findByOrderByHotScoreDesc(pageable);
        List<SkuEs> skuEsList = pageModel.getContent();
        return skuEsList;
    }

}
