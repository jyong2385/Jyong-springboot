package com.jyong.springboot.service.designpattern.factory;

import com.jyong.springboot.enums.ProcessEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author jyong
 * @Date 2023/10/18 19:24
 * @desc
 */

public class ProcessFactory {

    private static Map<ProcessEnumType,ProcessService> handler = new HashMap<>();

    public static void  registerProcessHandler(ProcessService handlerService){
        handler.put(handlerService.getType(),handlerService);
    }


    public static ProcessService getProcessHandler(String type){
     return handler.get(ProcessEnumType.getEnumType(type));
    }

}
