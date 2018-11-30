package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.Bean.CtrlHandle;
import com.cgx.smart4j.framework.Bean.Request;
import com.cgx.smart4j.framework.annotation.Action;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**********
 * @program: smartframework
 * @description: 解析controller 类 action 注解
 * 1、匹配带有Action注解的方法
 * 2、将Action 里的请求内容存放到 actionMap，key 就是request 对象，参数为获取请求方法与请求路径,value 为 被Action注解的方法
 * @author: cgx
 * @create: 2018-11-29 17:48
 **/
public class ControllerHelper {

    private static Map<Request,CtrlHandle> actionMap = new ConcurrentHashMap<>();
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        //遍历controller
        if (CollectionUtils.isNotEmpty(controllerClassSet)){
            for (Class<?> cla :
                    controllerClassSet) {
                Method[] methods = cla.getDeclaredMethods();
                if (methods!=null&&methods.length>0){
                     //遍历controller 类的方法
                    for (Method method :
                            methods) {
                        //匹配带有Action注解的方法
                        if (method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证url action 请求格式:get(post):/路径
                            if (mapping.matches("\\w+:/\\w*")){
                                String[] split = mapping.split(":");
                                if (split!=null&&split.length==2){
                                    //获取请求方法与请求路径
                                    String requestMethod = split[0];
                                    String requestPath = split[1];
                                    //封装请求方法
                                    Request request = new Request(requestMethod,requestPath);
                                    //封装处理方法
                                    CtrlHandle ctrlHandle = new CtrlHandle(cla,method);
                                    actionMap.put(request,ctrlHandle);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static CtrlHandle getHandler(String requestMethod,String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return actionMap.get(request);
    }
}
