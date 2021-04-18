package com.company.fileSample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIP {
    public static void readZip(String path){
        try {
            FileInputStream fIS = new FileInputStream(path);
            ZipInputStream zIS = new ZipInputStream(fIS);
            ZipEntry zipEntry;
            while ((zipEntry = zIS.getNextEntry())!=null){
                System.out.println(zipEntry.getName());
                zIS.closeEntry();
            }
            zIS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
