package com.ha1ey.CandleDragon.common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class MyURLClassLoader extends ClassLoader{
    public URLClassLoader classLoader;


    public MyURLClassLoader(String JarName) {
        try{
            File file = new File(JarName);
            URL url = file.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
            this.classLoader = urlClassLoader;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Class<?> loadClass(String className) {
        Class<?> clazz = null;
        try {
            Method method = this.classLoader.getClass().getDeclaredMethod("findClass", String.class);
            method.setAccessible(true);
            clazz = (Class<?>) method.invoke(classLoader,className);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clazz;
    }



}
