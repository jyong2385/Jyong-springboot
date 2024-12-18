package com.jyong.springboot.service.elasticsearch;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author jyong
 * @Date 2024/5/2 17:35
 * @desc
 */

@Service
public class ESearchServiceImpl implements ESearchService {

    private final Logger logger = LoggerFactory.getLogger(ESearchServiceImpl.class);

    @Autowired
    protected RestHighLevelClient restHighLevelClient;


    /**
     * @return
     */
    @Override
    public List<SearchHit> search(SearchRequest searchRequest) {
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if (search != null && search.getHits() != null && search.getHits().getHits().length > 0) {
                return Arrays.asList(search.getHits().getHits());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<SearchHit> search(String index, SearchSourceBuilder searchSourceBuilder) {
        try {
            logger.info("search service ,,index=" + index + " ,dsl=\n" + searchSourceBuilder.toString());
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(index);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if (search != null && search.getHits() != null && search.getHits().getHits().length > 0) {
                return Arrays.asList(search.getHits().getHits());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<SearchHit> search(String index, SearchSourceBuilder searchSourceBuilder, List<String> highlightFields, String preTag, String postTag) {
        try {
            logger.info("search service ,,index=" + index + " ,dsl=\n" + searchSourceBuilder.toString());

            //设置高亮字段 高亮标签
            if(CollectionUtils.isNotEmpty(highlightFields)){
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                highlightFields.forEach(e->highlightBuilder.field(e).preTags(preTag).postTags(postTag));
                searchSourceBuilder.highlighter(highlightBuilder);
            }

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(index);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if (search != null && search.getHits() != null && search.getHits().getHits().length > 0) {
                return Arrays.asList(search.getHits().getHits());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return Collections.emptyList();
    }

    public Boolean save(String index, Map<String, Object> source) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            IndexRequest indexRequest = new IndexRequest(index);
            indexRequest.create(true);
            indexRequest.source(source);
            bulkRequest.add(indexRequest);
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return bulk.hasFailures();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(String index, String id, Map<String, Object> doc) {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(index);
            updateRequest.id(id);
            updateRequest.doc(doc);
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return update.forcedRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean createTemplate(String name, Map<String, Object> mapping) {
        try {

            PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest(name);
            putIndexTemplateRequest.create(true);
            putIndexTemplateRequest.source(mapping);
            AcknowledgedResponse response = restHighLevelClient.indices().putTemplate(putIndexTemplateRequest, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param countRequest
     * @return
     */
    @Override
    public long count(CountRequest countRequest) {
        try {
            CountResponse count = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            if (count != null) {
                return count.getCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
