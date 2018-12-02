package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.annotation.Controller;
import com.cgx.smart4j.framework.constant.ConfigConstant;
import com.cgx.smart4j.framework.utils.ClassUtil;
import com.cgx.smart4j.framework.utils.PropertiesUtil;
import  com.cgx.smart4j.framework.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**********
 * @program: smartframework
 * @description:类操作助手类
 * @author: cgx
 * @create: 2018-11-29 16:14
 **/
public class ClassHelper {
    private  static  final Logger logger = LoggerFactory.getLogger(ClassHelper.class);
    private static Set<Class<?>> CLASS_SET = null;
    static {
        String packageName = PropertiesUtil.getProperty(ConfigConstant.BASE_PACKAGE);
        logger.info("类加载路径:"+packageName);
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
                logger.info("service class:"+cl.getSimpleName());
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
                logger.info("controller class:"+cl.getSimpleName());
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
        classSet.addAll(getServiceClassSet());
        return  classSet;
    }

    /*************
     * 获取某父类下的所有子类
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class cl :
                CLASS_SET) {
            //cl 是否是superClass的实现
            if(superClass.isAssignableFrom(cl)&&!superClass.equals(cl)){
                classSet.add(cl);
            }
        }
        return classSet;
    }

    /**********
     * 获取指定注解的类
     * @param annotation
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotation){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class cl :CLASS_SET) {
            if(cl.isAnnotationPresent(annotation)){
                classSet.add(cl);
            }
        }
        return classSet;
    }
}
