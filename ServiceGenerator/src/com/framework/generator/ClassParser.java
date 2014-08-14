package com.framework.generator;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author nelson.yang
 */
public final class ClassParser {

    public List<Class> parse(final String[] packageNames) {
        List<Class> clazzList = new ArrayList<Class>(240);
        List<String> classNameList = new ArrayList<String>(240);
        if (packageNames != null) {
            String packagePath;
            File file;
            URL url;
            String filePath;
            Enumeration<URL> eUrl;
            try {
               ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                for (String packageName : packageNames) {
                   packagePath = packageName.replaceAll("\\.", "/");
                   eUrl = classLoader.getResources(packagePath);
                    while (eUrl.hasMoreElements()) {
                        url = eUrl.nextElement();
                        filePath = url.getFile();
                        if (filePath.indexOf("src/test/") == -1 && filePath.indexOf("src/main/") == -1) {
                            file = new File(url.getPath());
                            this.parseFile(clazzList,classNameList, file, packagePath, url,classLoader);
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("parse 没有输出指定的包路径");
        }
        return clazzList;
    }

    private void parseFile(List<Class> clazzList ,List<String> classNameList, File file, String packagePath, URL url,ClassLoader classLoader) throws ClassNotFoundException {
        File[] subFiles;
        if (file.isDirectory()) {
            subFiles = file.listFiles();
            for (File subFile : subFiles) {
                this.parseFile(clazzList,classNameList, subFile, packagePath, url,classLoader);
            }
        } else if (file.getPath().contains(".class")) {
            this.findClass(clazzList,classNameList, file, packagePath,classLoader);
        } else if (file.getPath().contains(".jar")) {
            this.findClassInJar(clazzList,classNameList, url,classLoader);
        }
    }

    private void findClassInJar(List<Class> clazzList ,List<String> classNameList, URL url,ClassLoader classLoader) throws ClassNotFoundException {

        JarFile jarFile = null;
        try {
            //  jarFile = new JarFile(file);
            jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> jarEntryEnum = jarFile.entries();
            JarEntry jarEntry;
            String className;
            while (jarEntryEnum.hasMoreElements()) {
                jarEntry = jarEntryEnum.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    className = jarEntry.getName().replaceAll("/", ".").substring(0, jarEntry.getName().length() - 6);
                    if (!classNameList.contains(className)) {
                        classNameList.add(className);
                         clazzList.add(classLoader.loadClass(className));
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void findClass(List<Class> clazzList ,List<String> classNameList, File file, String packagePath,ClassLoader classLoader) throws ClassNotFoundException {
        String absolutePath = file.getAbsolutePath().replaceAll("\\\\", "/");
        int index = absolutePath.indexOf(packagePath);
        String className = absolutePath.substring(index);
        className = className.replaceAll("/", ".");
        className = className.substring(0, className.length() - 6);
        if (!classNameList.contains(className)) {
            classNameList.add(className);
            clazzList.add(classLoader.loadClass(className));
        }
    }
}
