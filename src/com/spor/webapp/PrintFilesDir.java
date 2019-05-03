package com.spor.webapp;

import java.io.File;

public class PrintFilesDir {
    public static void main(String[] args) {
        File projectDir = new File(System.getProperty("user.dir"));
        System.out.println(projectDir);
        printFile(projectDir, "");
    }

    public static void printFile(File directory, String indent) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + "[" + file.getName() + "]");
                    printFile(file, indent + "\t");
                } else if (file.isFile()) {
                    System.out.println(indent + file.getName());
                }
            }
        }
    }
}
