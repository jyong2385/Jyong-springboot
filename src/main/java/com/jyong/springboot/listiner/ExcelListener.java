package com.jyong.springboot.listiner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jyong
 * @date 2024/8/11 10:20
 * @description 自定义监听器，用于处理导入的数据
 */
public class ExcelListener extends AnalysisEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelListener.class);

    private List<List<String>> data = new ArrayList<>();


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {

        if (o instanceof List<?>) {
            List<String> rowData = (List<String>) o;
            data.add(rowData);
        }
    }

    /**
     * 后置处理，在这里添加导出完毕后的其他处理逻辑
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("数据导出完成，共导出：" + data.size() + "行数据");
    }
}
