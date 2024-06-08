package com.jyong.springboot.service.designpattern.strategy;

import com.jyong.springboot.enums.ProcessEnumType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author jyong
 * @Date 2023/10/16 20:38
 * @desc
 */

@Component
public class ProcessMain {

    @Autowired
    private ApplicationContext applicationContext;

    public void process(String type){
        String type3 = ProcessEnumType.getType(type);
        assert type3 != null;
        CommonProcess commonProcess = applicationContext.getBean(type3, CommonProcess.class);
        commonProcess.process();
    }

}
