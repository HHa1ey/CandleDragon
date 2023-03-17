package com.ha1ey.CandleDragon.tools;

import com.ha1ey.CandleDragon.core.RegistersImpl;
import com.ha1ey.CandleDragon.plugin.Registers;

import java.io.File;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarLoader extends ClassLoader {

    private final static String PLUGIN_PATH = "./plugin";

    public void mainLoader() {
        try {
            File directory = new File(PLUGIN_PATH);
            File[] jars = directory.listFiles();
            if (jars != null) {
                for (File file : jars) {
                    String fPath = file.getPath();
                    // 只加载jar
                    if (fPath.endsWith(".jar")) {
                        File file1 = new File(fPath);
                        JarURLClassLoader loader = new JarURLClassLoader(file1.toURI().toURL());
                        Class<?> clazz = loader.loadClass("com.ha1ey.CandleDragon.plugin.register.PluginRegister");
                        Method method = clazz.getMethod("registerPlugin", Registers.class);
                        method.invoke(clazz.newInstance(), new RegistersImpl());
                    }
                }
            }
        } catch (Throwable ignored) {

        }

    }

    public static class JarURLClassLoader {
        private URL jar;
        private URLClassLoader classLoader;

        public JarURLClassLoader(URL jar) {
            this.jar = jar;
            classLoader = new URLClassLoader(new URL[]{jar});
        }

        private Class<?> loadClass(String name) {
            try {
                return classLoader.loadClass(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }


        public Set<Class> loadClass(Class<?> superClass, String basePackage) {
            JarFile jarFile;
            try {
                // 将jar文件的URL转换为JarFile对象
                jarFile = ((JarURLConnection) jar.openConnection()).getJarFile();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return loadClassFromJar(superClass, basePackage, jarFile);
        }

        private Set<Class> loadClassFromJar(Class<?> superClass, String basePackage, JarFile jar) {
            Set<Class> classes = new HashSet<>();
            String pkgPath = basePackage.replace(".", "/");
            Enumeration<JarEntry> entries = jar.entries();
            Class<?> clazz;
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.charAt(0) == '/') {
                    entryName = entryName.substring(1);
                }
                if (jarEntry.isDirectory() || !entryName.startsWith(pkgPath) || !entryName.endsWith(".class")) {
                    continue;
                }
                String className = entryName.substring(0, entryName.length() - 6);
                clazz = loadClass(className.replace("/", "."));
                if (clazz != null && !clazz.isInterface() && superClass.isAssignableFrom(clazz)) {
                    classes.add(clazz);
                }
            }
            return classes;
        }
    }
}
