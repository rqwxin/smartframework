package com.cgx.smart4j.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-30 10:55
 **/
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER =new ObjectMapper();

    /**
     * 将POJO转换为JSON
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        return json;
    }

    /**
     * 将JSON转为POJO
     */
    public static <T> T fromJson(String json,Class<T> type){
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        return pojo;

    }
}
