package com.cgx.smart4j.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**********
 * @program: java-high-level
 * @description:  类加载器，加载class 文件或jar里的class
 * @author: cgx
 * @create: 2018-11-29 11:13
 **/
public class ClassUtil {

    /***********
     * 获取当前线程的类加载器
     */
    public static ClassLoader getClassLoader(){
        return  Thread.currentThread().getContextClassLoader();
    }

    /****************
     *反射获取class
     * @param classPathName 类的全路径如 java.lang.String
     * @return
     */
    public static Class<?> loadClass(String classPathName){
        Class<?> cla = null;
        try {
            cla = Class.forName(classPathName,false,getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  cla;
    }

    /*******************
     * 获取指定包名下的所有class 文件,包括jar里的class
     * @param packageName
     * @return
     */
    public  static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> resources = getClassLoader().getResources(packageName.replace(".","/"));
            if (resources!=null){
                while (resources.hasMoreElements()){
                    URL url = resources.nextElement();
                    if (url != null) {
                        String protocol = url.getProtocol();
                        if ("file".equals(protocol)){
                            String packagePath = url.getPath().replaceAll("%20", " ");
                            System.out.println(packagePath);
                            addClass(classSet,packagePath,packageName);
                        }else if("jar".equals(protocol)){
                            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                            JarFile jarFile = jarURLConnection.getJarFile();
                            Enumeration<JarEntry> entries = jarFile.entries();
                            while (entries.hasMoreElements()){
                                JarEntry jarEntry = entries.nextElement();
                                String jarEntryName = jarEntry.getName();
                                if (jarEntryName.endsWith(".class")){
                                     jarEntryName = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                     doAddClass(classSet,jarEntryName);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    /***********
     *
     * @param classSet class 集合
     * @param packagePath
     * @param packageName
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                boolean aClass = pathname.isFile() && pathname.getName().endsWith("class") || pathname.isDirectory();
                return aClass;
            }
        });
        for (File file :files) {
            String name = file.getName();
            if (file.isFile()){
                String className = name.substring(0,name.lastIndexOf("."));
                className = packageName+"."+className;
                System.out.println("classname:"+className);
                doAddClass(classSet,className);
            }else if (file.isDirectory()){
                String subPackagePath = packagePath+"/"+name;
                String subPackageName = packageName+"."+name;
                System.out.println("classPath:"+subPackagePath);
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String packageName) {
        Class<?> aClass = loadClass(packageName);
        classSet.add(aClass);
    }

    public static void main(String[] args) {
        getClassSet("org.jboss.netty");
    }
}
