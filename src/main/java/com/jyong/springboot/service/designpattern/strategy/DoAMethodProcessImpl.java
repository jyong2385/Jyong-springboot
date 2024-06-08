package com.jyong.springboot.service.designpattern.strategy;

import com.jyong.springboot.enums.ProcessEnumType;
import org.springframework.stereotype.Service;

/**
 * @Author jyong
 * @Date 2023/10/16 20:35
 * @desc
 */

@Service("PROCESS_A")
public class DoAMethodProcessImpl implements CommonProcess{

    @Override
    public void process() {
        System.out.println("do DoAmethodProcessImpl running ...");
    }

    @Override
    public String getType() {
        return ProcessEnumType.PROCESS_A.name();
    }
}
