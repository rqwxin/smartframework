package com.cgx.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



/**********
 * @program: smartframework
 * @description: 反射工具类，实例化对象，调用指定方法，设置成员变量
 * @author: cgx
 * @create: 2018-11-29 16:30
 **/
public class ReflectionUtil {
private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
    /************
     * 实例化对象
     * @param clas
     * @return
     */
    public static Object newInstance(Class<?> clas){
        Object instance = null;
        try {
             instance = clas.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**********\
     * 反射调用方法
     * @param obj 目标对象
     * @param method 被调用的方法
     * @param args 方法参数
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        } catch (Exception e) {
            logger.error("方法反射：{},类:{}",method.getName(),obj);
            e.printStackTrace();
        }
        return result;
    }

    /****************
     * 设置成员变量的值
     * @param obj 目标对象
     * @param field 成员变量
     * @param value 值
     */
    public static void setField(Object obj, Field field,Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
