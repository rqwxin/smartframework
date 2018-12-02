package com.cgx.smart4j.modules.aspect;

import com.cgx.smart4j.framework.annotation.Transaction;
import com.cgx.smart4j.framework.proxy.AspectjProxy;
import com.cgx.smart4j.framework.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-12-02 21:19
 **/
public class TransactionAspect extends AspectjProxy {
    private static final Logger logger = LoggerFactory.getLogger(TransactionAspect.class);

    /***********
     * 本地线程标识，保证同一个线程相关业务逻辑只执行一次
     */
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Method targetMethod = proxyChain.getTargetMethod();
            Object result =null;
        if (targetMethod!=null&&targetMethod.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try {
             logger.debug("---------------开始事务----------------");
             result = proxyChain.doProxyChain();
             logger.debug("----------------提交事务----------------");

            }catch (Exception e){
                logger.debug("----------------回滚事务----------------");
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result =  proxyChain.doProxyChain();
        }
        return result;
    }
}
