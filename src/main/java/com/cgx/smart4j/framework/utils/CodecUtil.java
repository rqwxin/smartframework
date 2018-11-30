package com.cgx.smart4j.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-30 10:21
 **/
public class CodecUtil {
    /**
     * 将URL编码
     */
    public static String encodeURL(String source){
        String target;
        try {
            target = URLEncoder.encode(source,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        return target;
    }

    /**
     * 将URL解码
     */
    public static String dencodeURL(String source){
        String target;
        try {
            target = URLDecoder.decode(source,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        return target;
    }
}
