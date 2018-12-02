package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.annotation.AspectJ;
import com.cgx.smart4j.framework.annotation.Service;
import com.cgx.smart4j.framework.annotation.Transaction;
import com.cgx.smart4j.framework.proxy.AspectjProxy;
import com.cgx.smart4j.framework.proxy.Proxy;
import com.cgx.smart4j.framework.proxy.ProxyManager;
import com.cgx.smart4j.modules.aspect.TransactionAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-12-02 15:03
 **/
public final class AopHelper {
    private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);

    /*****************
     * 获取代理类及目标类集合的映射关系
     * 进一步获取目标类与代理对象列表映射关系
     * 进而遍历这个映射关系，从中获取目标类与代理对象列表
     * 调用ProxyManager.creatProxy方法获取代理对象，
     * 调用beanhelper.setBean 方法，将代理对象重新放入BEAN_MAP中
     */
    static {
        Map<Class<?>, Set<Class<?>>> proxyMap = null;
        try {
            proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetProxy :targetMap.entrySet()){
                Class<?> targetClass = targetProxy.getKey();
                List<Proxy> proxyList = targetProxy.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                Object o = BeanHelper.getBeanMap().get(targetClass);
                System.out.println("代理前："+o.toString());
                //设置targetClass 映射对象为代理
                BeanHelper.setBean(targetClass,proxy);
                Object o2 = BeanHelper.getBeanMap().get(targetClass);
                System.out.println("代理后"+o2.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /************
     * 封装所有带有AspectJ注解的类
     * @param aspectJ
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(AspectJ aspectJ){
        Set<Class<?>> classSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspectJ.value();
        if (annotation!=null && !annotation.equals(AspectJ.class)){
            Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(annotation);
            classSet.addAll(classSetByAnnotation);
        }
        return classSet;
    }

    /**************
     * 建立代理类(切面类)与代理目标类映射关系
     * 1、先获取AspectjProxy 所有子类
     * 2、遍历所有子类，再获取AspectJ 注解的class
     * 如ControllerAspect 为代理类，再根据其@AspectJ(Controller.class) ，查找所有带有@Controller 的类
     * @return
     * @throws Throwable
     */
    public static Map<Class<?>,Set<Class<?>>> createProxyMap()throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return  proxyMap;
    }

    /************
     * 添加aspect 注解的代理类
     * @param proxyMap
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyclassSet = ClassHelper.getClassSetBySuper(AspectjProxy.class);
        for (Class proxyClass : proxyclassSet) {
            if(proxyClass.isAnnotationPresent(AspectJ.class)){
                AspectJ aspectJ = (AspectJ) proxyClass.getAnnotation(AspectJ.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspectJ);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    /************
     * 添加Transaction 注解的代理类
     * @param proxyMap
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> classSetByAnnotation = ClassHelper.getServiceClassSet();
        proxyMap.put(TransactionAspect.class,classSetByAnnotation);
    }

    /***************
     *目标类与代理对象列表之间的映射关系
     * 如customerController 目标类，代理对象为ControllerAspect
     * @return
     * @throws Throwable
     */
    public  static  Map<Class<?>,List<Proxy>> createTargetMap( Map<Class<?>,Set<Class<?>>> proxyMap ) throws Exception{
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>,Set<Class<?>>> proxyEntity :proxyMap.entrySet() ){
            Class<?> proxyClass = proxyEntity.getKey();
            Set<Class<?>> targetClassSet = proxyEntity.getValue();
            for (Class<?>  targetClass:
                    targetClassSet ) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return  targetMap;
    }
}
