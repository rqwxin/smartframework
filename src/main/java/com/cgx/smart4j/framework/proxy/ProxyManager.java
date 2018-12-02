package com.cgx.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**********
 * @program: smartframework
 * @description: 代理管理器
 * 创建所有代理对象，输入一个目标类和一组proxy.
 * 由切面类来调用
 * @author: cgx
 * @create: 2018-12-01 16:28
 **/
public class ProxyManager {

    public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList){
        Object o = Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,o,method,objects,methodProxy,proxyList).doProxyChain();
            }
        });
        return (T) o;
    }

}
