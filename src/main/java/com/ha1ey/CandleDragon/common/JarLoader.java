package com.ha1ey.CandleDragon.common;


import com.ha1ey.CandleDragon.core.RegistersImpl;
import com.ha1ey.CandleDragon.plugin.Register;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarLoader {

    public static void loadJar() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            String filePath = System.getProperty("user.dir") + File.separator + "plugin";
            Path dirPath = Paths.get(filePath);
            if (!Files.exists(dirPath)) {
                Files.createDirectory(dirPath);
            }
            List<File> files = findFiles(dirPath);
            for (File file : files) {
                String fileMd5 = CommonUtils.calculateMD5(file.getPath());
                if (!CommonUtils.pluginFileHashList.contains(fileMd5)) {
                    executorService.submit(() -> {
                        try {
                            JarFile jarFile = new JarFile(file);
                            Enumeration<JarEntry> entries = jarFile.entries();
                            MyURLClassLoader myURLClassLoader = new MyURLClassLoader(file.getPath());
                            while (entries.hasMoreElements()) {
                                JarEntry jarEntry = entries.nextElement();
                                String entryName = jarEntry.getName();
                                if (entryName.endsWith("PluginRegister.class")) {
                                    String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                                    Class<?> clazz = myURLClassLoader.loadClass(className);
                                    Method method = clazz.getMethod("registerPlugin", Register.class);
                                    method.invoke(clazz.newInstance(), new RegistersImpl());
                                    CommonUtils.pluginFileHashList.add(fileMd5);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                }
            }
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<File> findFiles(Path dir) throws IOException {
        List<File> jarFiles = new ArrayList<>();
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
                if (filePath.toString().endsWith(".jar")) {
                    jarFiles.add(filePath.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return jarFiles;
    }


}