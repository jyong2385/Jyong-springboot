package com.jyong.springboot.config;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jyong.springboot.listiner.ExcelListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jyong
 * @date 2024/8/11 10:11
 * @description
 */

@Configuration
public class EasyExcelConfig {

//    @Value("${easyexcel.max-read-rows}")
//    private String maxRows;
//
//    @Value("${excel.read.useDefaultListener}")
//    private String useDefaultListener;
//
//    @Value("${excel.read.readCacheSize}")
//    private int readCacheSize;

    /**
     * 创建excel事件监听器
     */
    @Bean
    public AnalysisEventListener analysisEventListener() {
        return new ExcelListener();
    }

    /**
     * 创建easyexcel实例
     */
    @Bean
    public EasyExcel easyExcel() {
        return new EasyExcel();
    }


}
