package com.cgx.smart4j.framework.helper;

import com.cgx.smart4j.framework.constant.ConfigConstant;
import com.cgx.smart4j.framework.utils.PropertiesUtil;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-29 15:37
 **/
public class ConfigHelper {

    public static String getJdbcDriver(){
        return PropertiesUtil.getProperty(ConfigConstant.JDBC_DRIVER);
    }
}
