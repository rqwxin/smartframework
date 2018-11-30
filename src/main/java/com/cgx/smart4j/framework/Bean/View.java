package com.cgx.smart4j.framework.Bean;

import java.util.HashMap;
import java.util.Map;

/**********
 * @program: smartframework
 * @description: 视图对象，返回jsp页面
 * @author: cgx
 * @create: 2018-11-30 09:24
 **/
public class View {
    /*******
     * 视图路径
     */
    private String  path;

    /***********
     * 模型数据
     */
    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }
    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
