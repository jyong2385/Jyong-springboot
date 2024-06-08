package com.jyong.springboot.service.designpattern.factory.impl;

import com.jyong.springboot.service.designpattern.factory.ProcessFactory;
import com.jyong.springboot.service.designpattern.factory.ProcessService;
import com.jyong.springboot.enums.ProcessEnumType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @Author jyong
 * @Date 2023/10/18 19:33
 * @desc
 */

@Service
public class ProcessServiceBImpl implements ProcessService, InitializingBean {

    @Override
    public void process() {
        System.out.println("B method is running ....");
    }

    @Override
    public ProcessEnumType getType() {
        return ProcessEnumType.PROCESS_B;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ProcessFactory.registerProcessHandler(this);
    }
}
