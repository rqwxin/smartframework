package com.cgx.smart4j.framework.proxy;

/**********
 * @program: smartframework
 * @description: 代理接口
 * @author: cgx
 * @create: 2018-12-01 15:10
 **/
public interface Proxy {
    /***********
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
