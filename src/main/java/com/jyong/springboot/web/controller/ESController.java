package com.jyong.springboot.web.controller;

import cn.hutool.core.util.StrUtil;
import com.jyong.springboot.entity.Person;
import com.jyong.springboot.enums.PersonIndexTemplateEnum;
import com.jyong.springboot.service.elasticsearch.ESearchService;
import com.jyong.springboot.web.Result;
import com.jyong.springboot.web.vo.IndexTemplateRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author jyong
 * @Date 2024/5/2 17:41
 * @desc
 */

@RestController
@RequestMapping(value = "/es")
public class ESController {

    private final String index = "es-data-jyong";

    @Autowired
    private ESearchService eSearchService;

    @GetMapping("/matchQuery.json")
    @ResponseBody
    public Result<List<Person>> matchQuery(@RequestParam(required = false) String names,@RequestParam(required = false) String name ,@RequestParam(required = false) Integer age) {
        Result<List<Person>> result = new Result<>();
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setNames(Arrays.asList(names.split(",")));
        SearchSourceBuilder searchSourceBuilder = personSearchSourceBuilder(person, 0, 10);
        List<SearchHit> searchHits = eSearchService.search(index, searchSourceBuilder);
        List<Person> personList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Person p = new Person();
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            p.setName((String) sourceAsMap.get(PersonIndexTemplateEnum.NAME.getEsDataKey()));
            p.setAge((Integer) sourceAsMap.get(PersonIndexTemplateEnum.AGE.getEsDataKey()));
            p.setAddress((String) sourceAsMap.get(PersonIndexTemplateEnum.ADDRESS.getEsDataKey()));
            personList.add(p);
        }
        result.setData(personList);
        result.setSuccess(true);
        return result;
    }

    @PostMapping("/matchCount.json")
    @ResponseBody
    public Result<Long> countQuery(@RequestBody Person person) {

        Result<Long> result = new Result<>();
        CountRequest countRequest = new CountRequest();
        countRequest.indices(index);

        SearchSourceBuilder searchSourceBuilder = personSearchSourceBuilder(person, 0, 0);
        countRequest.source(searchSourceBuilder);
        System.out.println("统计条数DSL：" + searchSourceBuilder);
        long count = eSearchService.count(countRequest);
        result.setData(count);
        return result;
    }

    @PostMapping("/createTemplate.json")
    @ResponseBody
    public Result<Object> createTemplate(@RequestBody IndexTemplateRequest indexTemplateRequest) {
        Result<Object> result = new Result<>();
        Boolean success = eSearchService.createTemplate(indexTemplateRequest.getName(), indexTemplateRequest.getMapping());
        result.setData(success);
        return result;
    }


    @PostMapping("/save.json")
    @ResponseBody
    public Result<Object> save(@RequestBody List<Person> persons) {
        List<Map<String, Object>> data = buildDataMap(persons);
        for (Map<String, Object> datum : data) {
            eSearchService.save(index, datum);
        }
        Result<Object> objectResult = new Result<>();
        objectResult.setSuccess(true);
        objectResult.setData(data);
        return objectResult;
    }


    public SearchRequest buildPersonSearchRequest() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
        return searchRequest;
    }

    private Map<String, Object> buildMapping() {
        Map<String, Object> mapping = new HashMap<>();
        //存储字段
        Map<String, Map<String, String>> fields = new HashMap<>();
        Map<String, String> fieldConfig = new HashMap<>();
        fieldConfig.put("type", "text");
        fields.put("address", fieldConfig);

        mapping.put("properties", fields);
        mapping.put("index_patterns", "jyong-*");
        return mapping;
    }

    public SearchSourceBuilder personSearchSourceBuilder(Person person, int page, int size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        searchSourceBuilder.query(buildBoolQueryBuilder(person));

        if (size > 0) {
            searchSourceBuilder.size(size);
            searchSourceBuilder.from(page);
        }

        System.out.println(searchSourceBuilder.toString());
        return searchSourceBuilder;
    }

    public List<Map<String, Object>> buildDataMap(List<Person> personList) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Person person : personList) {
            Map<String, Object> data = new HashMap<>();
            if (StrUtil.isNotBlank(person.getName())) {
                data.put(PersonIndexTemplateEnum.NAME.getEsDataKey(), person.getName());
            }
            if (person.getAge() > 0) {
                data.put(PersonIndexTemplateEnum.AGE.getEsDataKey(), person.getAge());
            }

            if (StrUtil.isNotBlank(person.getAddress())) {
                data.put(PersonIndexTemplateEnum.ADDRESS.getEsDataKey(), person.getAddress());
            }

            list.add(data);
        }
        return list;
    }


    /**
     * term和match的区别：
     * 1.对于keyword字段的区别：match 不携带.keyword查询=like    携带.keyword=term
     *
     *
     * 对于keyword字段查询时注意：
     * 使用es查询方式有2中：
     * 1. term精准匹配查询，查询必须要携带.keyword。
     * 2. 使用match查询时，不携带：.keyword则是模糊配置相当于like %x%。 携带.keyword：相当于精准匹配==term
     * @param person
     * @return
     */
    public BoolQueryBuilder buildBoolQueryBuilder(Person person) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if (StrUtil.isNotBlank(person.getName())) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(PersonIndexTemplateEnum.NAME.getEsDataKey(), person.getName());
            boolQueryBuilder.must(matchQueryBuilder);
        }

        if (StrUtil.isNotBlank(person.getAddress())) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(PersonIndexTemplateEnum.ADDRESS.getEsDataKey(), person.getAddress());
            boolQueryBuilder.must(matchQueryBuilder);
        }

        if (person.getAge() !=null && person.getAge() > 0) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(PersonIndexTemplateEnum.AGE.getEsDataKey(), person.getAge());
            boolQueryBuilder.must(matchQueryBuilder);
        }

        //批量查询，names in (a,b,c)
        if(!CollectionUtils.isEmpty(person.getNames())){
            TermsQueryBuilder termsQueryBuilder = new TermsQueryBuilder(PersonIndexTemplateEnum.NAME.getEsDataKey(true), person.getNames());
            boolQueryBuilder.must(termsQueryBuilder);
        }

        return boolQueryBuilder;
    }
}
