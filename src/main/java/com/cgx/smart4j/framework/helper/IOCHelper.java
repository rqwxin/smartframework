package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.annotation.Inject;
import com.cgx.smart4j.framework.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**********
 * @program: smartframework
 * @description:控制反转类
 * 1、从BeanHelper 类中获取所有bean映射map
 * 2、取出bean实例和类，通过反射类来获取bean类中带有注入注解(@Inject)的成员变量
 * 3、通过ReflectionUtil.setField来修改成员变量的值
 * @author: cgx
 * @create: 2018-11-29 17:00
 **/
public final  class IOCHelper {

    static {
        //获取bean 映射关系map
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap!=null&&!beanMap.isEmpty()){
          for(Map.Entry<Class<?>, Object> bean: beanMap.entrySet()){
              //循环map,取bean的实例和类
              Class<?> beanClass = bean.getKey();
              Object beanInstance = bean.getValue();
              Field[] fields = beanClass.getDeclaredFields();
              if (ArrayUtils.isNotEmpty(fields)){
                  //遍历beanclass 里的成员变量
                  for (Field field :  fields) {
                          //是否带有Inject注解
                      if (field.isAnnotationPresent(Inject.class)) {
                          //取成员的类型
                          Class<?> type = field.getType();
                          Object fieldBean = beanMap.get(type);
                          if (fieldBean!=null){
                              //反射设置成员变量
                              ReflectionUtil.setField(beanInstance,field,fieldBean);
                          }
                      }
                  }
              }
          }

        }
    }
}
