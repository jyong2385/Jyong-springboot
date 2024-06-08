package com.jyong.springboot.service.elasticsearch;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;

/**
 * @Author jyong
 * @Date 2024/5/2 17:34
 * @desc
 */

public interface ESearchService {


    /**
     * 原始搜索
     *
     * @param searchRequest
     * @return
     */
    List<SearchHit> search(SearchRequest searchRequest);


    long count(CountRequest countRequest);

    /**
     * 通用搜索接口
     *
     * @param index
     * @param searchSourceBuilder
     * @return
     */
    List<SearchHit> search(String index, SearchSourceBuilder searchSourceBuilder);

    Boolean createTemplate(String name, Map<String, Object> mapping);


    Boolean save(String index, Map<String, Object> source);

    Boolean update(String index, String id, Map<String, Object> doc);
}
