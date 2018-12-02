package com.cgx.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**********
 * @program: smartframework
 * @description: 代理链
 * @author: cgx
 * @create: 2018-12-01 15:13
 **/
public class ProxyChain {
    /***
     * 被代理class
     */
    private final Class<?> targetClass;

    /***
     * 被代理实例对象
     */
    private final Object targetObject;
    /*********
     * 被代理方法
     */
    private final Method targetMethod;

    /*********
     *     * 被代理方法的参数，可空
     */
    private final Object[] objectParams;

    /*********
     * 代理工具类
     */
    private  final MethodProxy methodProxy;
    /*****
     *代理列表
     */
    private List<Proxy> proxyList = new LinkedList<>();

    private int proxyIndex = 0;

    /***********
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param objectParams
     * @param methodProxy
     */
    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] objectParams, MethodProxy methodProxy,List<Proxy> proxyList ) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.objectParams = objectParams;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getObjectParams() {
        return objectParams;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    /****************
     * 执行责任链
     * @return
     * @throws Throwable
     */
    public Object doProxyChain()throws Throwable{
        //
        Object result = null;
        if(proxyIndex<proxyList.size()){
            result = proxyList.get(proxyIndex++).doProxy(this);
        }else{
           result = methodProxy.invokeSuper(targetObject,objectParams);
        }
        return  result;
    }

}
