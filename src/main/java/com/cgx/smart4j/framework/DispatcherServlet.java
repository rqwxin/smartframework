package com.cgx.smart4j.framework;

import com.cgx.smart4j.framework.Bean.CtrlHandle;
import com.cgx.smart4j.framework.Bean.Data;
import com.cgx.smart4j.framework.Bean.Param;
import com.cgx.smart4j.framework.Bean.View;
import com.cgx.smart4j.framework.constant.ConfigConstant;
import com.cgx.smart4j.framework.helper.BeanHelper;
import com.cgx.smart4j.framework.helper.ControllerHelper;
import com.cgx.smart4j.framework.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "dispatcherServlet",urlPatterns ="/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(HelperLoader.class);
    /******
     * 初始化
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("DispatcherServlet 初始化");
        //初始化helper类
        HelperLoader.init();
        //获取servletContext 对象(用于注册Servlet)
        ServletContext servletContext = config.getServletContext();
        //注册jsp 的servelt
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(PropertiesUtil.getProperty(ConfigConstant.JSP_APTH)+"*");
        //注册处理静态资源的默认servelt
        ServletRegistration aDefault = servletContext.getServletRegistration("default");
        aDefault.addMapping(PropertiesUtil.getProperty(ConfigConstant.ASSET_PATH)+"*");
    }

    /****************
     * 接收http请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //获取Action 处理器
        CtrlHandle handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null){
            //获取controller 类和其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }

            String reqStr = StreamUtil.getString(req.getInputStream());
            String body = CodecUtil.dencodeURL(reqStr);
            if (body!=null&&!"".equals(body)){
                String[] params = body.split("&");
                if (params!=null){
                    for (String param :
                            params) {
                        String[] array = param.split("&");
                        if (array!=null&& array.length==2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            //调用action 方法
            Method actionMethod = handler.getActionMethod();
            Object result = null;
            if(param.getParamMap().size()==0){
             result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            }else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod,param);
            }

            //处理action 方法返回值
            if(result instanceof View){
//                返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if (path!=null){
                    if (path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    }else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()){
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            req.setAttribute(key,value);
                        }
                        req.getRequestDispatcher(PropertiesUtil.getProperty(ConfigConstant.JSP_APTH)+path).forward(req, resp);
                    }
                }else if (result instanceof Data){
                    //返回json数据
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if (model!= null){
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        PrintWriter writer = resp.getWriter();
                        String json = JsonUtil.toJson(data);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }else {
            resp.sendRedirect(req.getContextPath()+requestPath);

        }
    }


}
