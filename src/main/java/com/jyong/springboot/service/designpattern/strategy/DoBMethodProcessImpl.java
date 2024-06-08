package com.jyong.springboot.service.designpattern.strategy;

import com.jyong.springboot.enums.ProcessEnumType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author jyong
 * @Date 2023/10/16 20:35
 * @desc
 */

@Component
@Service("PROCESS_B")
public class DoBMethodProcessImpl implements CommonProcess{

    @Override
    public void process() {
        System.out.println("do DoBMethodProcessImpl running");
    }

    @Override
    public String getType() {
        return ProcessEnumType.PROCESS_B.name();
    }
}
