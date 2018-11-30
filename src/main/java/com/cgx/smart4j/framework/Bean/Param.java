package com.cgx.smart4j.framework.Bean;

import java.util.Map;

/**********
 * @program: smartframework
 * @description: 请求参数对象
 * @author: cgx
 * @create: 2018-11-30 09:22
 **/
public class Param {
    private Map<String ,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public  long getLong(String name){
        return (long) paramMap.get(name);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
