package com.cgx.smart4j.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-29 15:30
 **/
public class PropertiesUtil {

    private static Properties props = null;

    private static File configFile = null;

    private static long fileLastModified = 0L;

    private static void init() {
        props = new Properties();
        URL url = PropertiesUtil.class.getClassLoader().getResource("/");
        File file = new File(url.getPath());
        File[] files = file.listFiles();
        for (File f : files){
            configFile = f;
            props.putAll(load(configFile));
        }
    }

    private static void load() {
        try {
            props.load(new FileInputStream(configFile));
            fileLastModified = configFile.lastModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map load(File cfgFile){
        Properties loadMap =new Properties();
        try {
            loadMap.load(new FileInputStream(cfgFile));
            fileLastModified = configFile.lastModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadMap;
    }
    public static Properties getConfigInfoProps() {
        return props;
    }

    public static String getProperty(String key) {
        if ((configFile == null) || (props == null)){
            init();
        }
        if (configFile.lastModified() > fileLastModified){
            load();
        }
        String propertyValue=props.getProperty(key);
        return propertyValue;
    }
}
