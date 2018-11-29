package com.cgx.smart4j.framework.Bean;

import java.lang.reflect.Method;

/**********
 * @program: smartframework
 * @description: controller Action请求处理信息
 * @author: cgx
 * @create: 2018-11-29 17:40
 **/
public class CtrlHandle {
    /*******
     * controller class
     */
    private Class<?> controllerClass;

    /*********
     * action 方法
     */
    private Method actionMethod;

    public CtrlHandle(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(Method actionMethod) {
        this.actionMethod = actionMethod;
    }
}
