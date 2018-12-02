package com.cgx.smart4j.framework.annotation;

import java.lang.annotation.*;

/********
 * 切面注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectJ {
    Class<? extends Annotation> value();
}
