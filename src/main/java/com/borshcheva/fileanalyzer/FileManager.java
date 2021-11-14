package com.borshcheva.fileanalizer;

import java.io.*;

public class FileManager {

    public static void main(String[] args) throws IOException {
        String path = "c:\\tasks";
        int countOfFiles = countFiles(path);
        int countOfDirectory = countDirs(path);
        System.out.println(countOfFiles);
        System.out.println(countOfDirectory);
        copy(path, "c:\\tasks1");
        move(path, "c:\\tasks2");
    }

    public static int countFiles(String path) {
        int count = 0;

        File pathToFile = new File(path);
        File[] files = pathToFile.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    count++;
                } else {
                    count += countFiles(files[i].getAbsolutePath());
                }
            }
        }
        return count;
    }

    public static int countDirs(String path) {
        int count = 0;

        File pathToFile = new File(path);
        File[] files = pathToFile.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    count++;
                    count += countDirs(files[i].getAbsolutePath());
                }
            }
        }
        return count;
    }

    public static void copy(String from, String to) throws IOException {
        File pathToFileSrc = new File(from);
        File pathToFileDist = new File(to);
        if (pathToFileSrc.isFile()) {
            copyFile(from, to);
        } else {
            copyDir(pathToFileSrc, pathToFileDist);
        }
    }

    static void copyFile(String from, String to) throws IOException {
        FileInputStream inputStream = new FileInputStream(from);
        FileOutputStream outputStream = new FileOutputStream(to);

        byte[] array = new byte[1024];
        int read;

        while ((read = inputStream.read(array)) > 0) {
            outputStream.write(array, 0, read);
        }
        inputStream.close();
        outputStream.close();
    }

    public static void copyDir(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDir(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream inputStream = new FileInputStream(sourceLocation);
            OutputStream outputStream = new FileOutputStream(targetLocation);


            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            inputStream.close();
            outputStream.close();
        }
    }

    public static void move(String from, String to) throws IOException {
        copy(from, to);
        deleteDirectory(from);
    }

    static void deleteDirectory(String path) {
        File pathFile = new File(path);
        File[] files = pathFile.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else {
                file.deleteOnExit();
                deleteDirectory(file.getAbsolutePath());
            }
        }
        pathFile.delete();
    }

}




