package com.cgx.smart4j.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**********
 * @program: smartframework
 * @description: 流工具类
 * @author: cgx
 * @create: 2018-11-30 10:21
 **/
public class StreamUtil {
    /**
     * 从输入流中获取字符串
     * @param is
     * @return
     */
    public static String getString(InputStream is){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
