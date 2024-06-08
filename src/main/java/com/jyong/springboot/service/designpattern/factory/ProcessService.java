package com.jyong.springboot.service.designpattern.factory;

import com.jyong.springboot.enums.ProcessEnumType;

/**
 * @Author jyong
 * @Date 2023/10/18 19:25
 * @desc
 */

public interface ProcessService {

    void process();

    ProcessEnumType getType();

}
