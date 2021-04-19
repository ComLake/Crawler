package com.company.fileSample;

import org.openqa.selenium.io.Zip;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIP {
    private String path;
    public ZIP(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void readZip(){
        try {
            FileInputStream fIS = new FileInputStream(path);
            ZipInputStream zIS = new ZipInputStream(fIS);
            ZipEntry zipEntry;
            while ((zipEntry = zIS.getNextEntry())!=null){
                System.out.println(fileFormat(zipEntry.getName()));
                zIS.closeEntry();
            }
            zIS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String fileFormat(String name){
        return name.substring(name.lastIndexOf(".")+1);
    }
    public void unZipFile(){
        try {
            String pathDir = path.replace("."+fileFormat(fileFormat(path)),"");
            File desDirectory = new File(pathDir);
            if (!desDirectory.exists()){
                desDirectory.mkdir();
            }
            byte []buffer = new byte[2048];
            FileInputStream fIS = new FileInputStream(path);
            ZipInputStream zIS = new ZipInputStream(fIS);
            ZipEntry zipEntry;
            while ((zipEntry = zIS.getNextEntry())!=null){
                String filePath = pathDir + File.separator + zipEntry.getName();
                System.out.println("Uzipping..."+filePath);
                if (!zipEntry.isDirectory()){
                    FileOutputStream fOS = new FileOutputStream(filePath);
                    int len;
                    while ((len = zIS.read(buffer))>0){
                        fOS.write(buffer,0,len);
                    }
                    fOS.close();
                }else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zIS.closeEntry();
                zIS.close();
                System.out.println("Unzipping successfully.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
