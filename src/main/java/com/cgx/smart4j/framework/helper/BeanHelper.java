package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**********
 * @program: smartframework
 * @description: bean管理容器，保存实例化后的bean 对象
 * @author: cgx
 * @create: 2018-11-29 16:50
 **/
public class BeanHelper {

    private  static  final Logger logger = LoggerFactory.getLogger(BeanHelper.class);
    /***************
     * bean映射(存放bean类与bean实例的映射关系)
     */
    private static Map<Class<?>,Object> BEAN_MAP = new ConcurrentHashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> cl :beanClassSet){
            logger.info("实例化bean:"+cl.getSimpleName());
            Object o = ReflectionUtil.newInstance(cl);
            BEAN_MAP.put(cl,o);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cl){
        boolean b = BEAN_MAP.containsKey(cl);
        if (!b){
            throw new RuntimeException("can not get bean by class:"+cl);
        }
        return (T) BEAN_MAP.get(cl);
    }

    /*******
     * 设置Bean实例
     * @param cla
     * @param o
     */
    public static void setBean(Class<?> cla,Object o){
        BEAN_MAP.put(cla,o);
    }
}
