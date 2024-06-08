package com.jyong.springboot.service.designpattern.factory;

import org.springframework.stereotype.Component;

/**
 * @Author jyong
 * @Date 2023/10/18 19:34
 * @desc
 */


@Component
public class ExecuteProcess {

    public void process(String type){
        ProcessService processHandler = ProcessFactory.getProcessHandler(type);
        processHandler.process();
    }


}
