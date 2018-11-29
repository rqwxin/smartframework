package com.cgx.smart4j.framework;

import com.cgx.smart4j.framework.helper.BeanHelper;
import com.cgx.smart4j.framework.helper.ClassHelper;
import com.cgx.smart4j.framework.helper.ControllerHelper;
import com.cgx.smart4j.framework.helper.IOCHelper;
import com.cgx.smart4j.framework.utils.ClassUtil;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-29 18:08
 **/
public final class HelperLoader {

    public static void init(){
        Class<?> classList[] = {
                ClassHelper.class,
                BeanHelper.class,
                IOCHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cla:classList
             ) {
            ClassUtil.loadClass(cla.getName());
        }
    }
}
