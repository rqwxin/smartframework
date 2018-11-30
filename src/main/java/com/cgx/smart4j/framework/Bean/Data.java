package com.cgx.smart4j.framework.Bean;

/**********
 * @program: smartframework
 * @description: 数据对象
 * @author: cgx
 * @create: 2018-11-30 09:29
 **/
public class Data {
    /********
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
