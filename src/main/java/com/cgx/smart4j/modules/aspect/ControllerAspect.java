package com.cgx.smart4j.modules.aspect;

import com.cgx.smart4j.framework.annotation.AspectJ;
import com.cgx.smart4j.framework.annotation.Controller;
import com.cgx.smart4j.framework.proxy.AspectjProxy;
import com.cgx.smart4j.framework.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**********
 * @program: smartframework
 * @description:  拦截 controller 所有方法
 * @author: cgx
 * @create: 2018-12-02 14:33
 **/
@AspectJ(Controller.class)
public class ControllerAspect  extends AspectjProxy {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private Long begin;
    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] args) throws Throwable {
        logger.debug("---------------before-----------------------");
        logger.debug("class:{},Method:{}",targetClass.getSimpleName(),targetMethod.getName());
       begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> targetClass, Method targetMethod, Object[] args) throws Throwable {
        logger.debug("---------------after-----------------------");
        logger.debug("class:{},Method:{}",targetClass.getSimpleName(),targetMethod.getName());
        logger.debug("time:{}",System.currentTimeMillis()-begin);
    }


}
