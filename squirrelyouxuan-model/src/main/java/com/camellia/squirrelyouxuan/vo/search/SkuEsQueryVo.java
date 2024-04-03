package com.camellia.squirrelyouxuan.vo.search;

import lombok.Data;

/**
 *  封装查询条件
 */

@Data
public class SkuEsQueryVo {

    /**
     * 三级分类id
     */
    private Long categoryId;;

    /**
     * 检索的关键字
     */
    private String keyword;

}
