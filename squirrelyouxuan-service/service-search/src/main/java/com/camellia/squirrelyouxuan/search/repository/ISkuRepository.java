package com.camellia.squirrelyouxuan.search.repository;

import com.camellia.squirrelyouxuan.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author fuyunjia
 * @Date 2023-11-10 10:54
 */
public interface ISkuRepository extends ElasticsearchRepository<SkuEs, Long> {

    /**
     * 获取爆款商品
     * @param pageable
     * @return
     */
    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageable);

    /**
     * 根据分类ID查询ES中的商品
     * @param categoryId
     * @param pageable
     * @return
     */
    Page<SkuEs> findByCategoryId(Long categoryId,Pageable pageable);

    /**
     * 根据关键字仓库ID查询ES中的商品
     * @param keyword
     * @param pageable
     * @return
     */
    Page<SkuEs> findByKeyword(String keyword,Pageable pageable);
}
