package com.afecioru.mjia.intro;

import java.io.File;
import java.io.FileFilter;

public class HiddenFiles {
    @SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
    private static File[] legacyJavaWay() {
        System.out.println("------[Legacy Java]---------");
        return new File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isHidden();
            }
        });
    }

    private static File[] modernJavaWay() {
        System.out.println("------[Modern Java]---------");
        return new File(".").listFiles(File::isHidden);
    }

    private static void printFileNames(File[] files) {
        for (var file : files) {
            System.out.println(file.getName());
        }
    }

    public static void main(String[] args) {
        printFileNames(legacyJavaWay());
        printFileNames(modernJavaWay());
    }
}
