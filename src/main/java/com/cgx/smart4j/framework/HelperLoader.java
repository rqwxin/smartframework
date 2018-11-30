package com.cgx.smart4j.framework;

import com.cgx.smart4j.framework.helper.BeanHelper;
import com.cgx.smart4j.framework.helper.ClassHelper;
import com.cgx.smart4j.framework.helper.ControllerHelper;
import com.cgx.smart4j.framework.helper.IOCHelper;
import com.cgx.smart4j.framework.utils.ClassUtil;
import com.cgx.smart4j.framework.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-29 18:08
 **/
public final class HelperLoader {

    private static final Logger logger = LoggerFactory.getLogger(HelperLoader.class);

    public static void init(){
        logger.info("初始化HelperLoader");
        Class<?> classList[] = {
                ClassHelper.class,
                BeanHelper.class,
                IOCHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cla:classList
             ) {
            ClassUtil.loadClass(cla.getName());
            ReflectionUtil.newInstance(cla);
        }
    }
}
