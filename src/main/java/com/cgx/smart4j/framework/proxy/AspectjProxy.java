package com.cgx.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**********
 * @program: smartframework
 * @description: 切面代理
 * @author: cgx
 * @create: 2018-12-02 14:11
 **/
public abstract class AspectjProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectjProxy.class);
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        logger.info("doProxy");
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] args = proxyChain.getObjectParams();
        begin();
        Object result = null;
        try {
            if (intercept(targetClass, targetMethod, args)){
                before(targetClass, targetMethod, args);
                 result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, args);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logger.error("doProxy error:"+e.getMessage());
            error(targetClass, targetMethod, args);
        }finally {
            end();
        }
        return result;
    }
    public void begin(){

    }

    public void end(){

    }

    /************
     *
     * @param targetClass
     * @param targetMethod
     * @param args
     * @throws Throwable
     */
    public void before(Class<?> targetClass,Method targetMethod,Object args[])throws   Throwable{

    }
    public void after(Class<?> targetClass,Method targetMethod,Object args[])throws   Throwable{

    }
    public void error(Class<?> targetClass,Method targetMethod,Object args[])throws   Throwable{

    }

    public boolean intercept(Class<?> targetClass,Method targetMethod,Object args[])throws   Throwable{
        return true;
    }

}
