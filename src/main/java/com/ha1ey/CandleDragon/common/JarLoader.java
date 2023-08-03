package com.ha1ey.CandleDragon.common;


import com.ha1ey.CandleDragon.core.PluginImpl;
import com.ha1ey.CandleDragon.core.RegistersImpl;
import com.ha1ey.CandleDragon.plugin.PluginManager;
import com.ha1ey.CandleDragon.plugin.Register;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarLoader {

    public static void loadJar() {
        try {
            String filePath = System.getProperty("user.dir") + File.separator + "plugin";
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            List<File> files = findFiles(dir);
            for (File file : files) {
                if (file.getName().endsWith(".jar")) {
                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> entries = jarFile.entries();
                    MyURLClassLoader classLoader = new MyURLClassLoader(file.getPath());
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String entryName = jarEntry.getName();
                        if (!entryName.contains("hutool") && entryName.endsWith(".class")) {
                            String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                            try {
                                Class<?> clazz = classLoader.loadClass(className);
                                if (PluginManager.class.isAssignableFrom(clazz)) {
                                    Method method = clazz.getMethod("registerPlugin", Register.class);
                                    method.invoke(clazz.newInstance(), new RegistersImpl());
                                    break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    jarFile.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<File> findFiles(File dir) {
        List<File> fileList = new ArrayList<>();
        if (dir != null) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    fileList.addAll(findFiles(file));
                } else if (file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
                    fileList.add(file);
                }

            }
        }
        return fileList;
    }


}
