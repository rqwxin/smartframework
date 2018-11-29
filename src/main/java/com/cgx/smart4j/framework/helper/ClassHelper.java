package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.annotation.Controller;
import com.cgx.smart4j.framework.constant.ConfigConstant;
import com.cgx.smart4j.framework.utils.ClassUtil;
import com.cgx.smart4j.framework.utils.PropertiesUtil;
import  com.cgx.smart4j.framework.annotation.Service;

import java.util.HashSet;
import java.util.Set;

/**********
 * @program: smartframework
 * @description:类操作助手类
 * @author: cgx
 * @create: 2018-11-29 16:14
 **/
public class ClassHelper {

    private static Set<Class<?>> CLASS_SET = null;
    static {
        String packageName = PropertiesUtil.getProperty(ConfigConstant.BASE_PACKAGE);
        CLASS_SET = ClassUtil.getClassSet(packageName);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /************
     * 获取应用下@Service 的类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> serviceSet = new HashSet<>();
        for (Class<?> cl:  CLASS_SET ) {
            if (cl.isAnnotationPresent(Service.class)){
                serviceSet.add(cl);
            }
        }
        return  serviceSet;
    }

    /************
     * 获取应用下@Controller 的类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cl:  CLASS_SET ) {
            if (cl.isAnnotationPresent(Controller.class)){
                classSet.add(cl);
            }
        }
        return  classSet;
    }

    /************
     * 获取应用下@Controller,@Service 的类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getControllerClassSet());
        return  classSet;
    }
}
