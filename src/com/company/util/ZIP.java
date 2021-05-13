package com.company.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class ZIP {
    private List<String>fileList;
    private String path;

    public ZIP(String path) {
        this.path = path;
        fileList = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void readZip() {
        byte[]buffer = new byte[1024];
        try {
            FileInputStream fIS = new FileInputStream(path);
            ZipInputStream zIS = new ZipInputStream(fIS);
            ZipEntry zipEntry;
            while ((zipEntry = zIS.getNextEntry()) != null) {
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

    public static String fileFormat(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public void createFolder(String path) {
        File des = new File(path);
        if (!des.exists()) {
            des.mkdir();
        }
    }
    public String generateZipEntry(String file){
        return file.substring(path.length(),file.length());
    }
    public void generateFileList(File file){
        //add file
        if (file.isFile()){
            fileList.add(generateZipEntry(file.getAbsoluteFile().toString()));
        }
        if (file.isDirectory()){
            String[] subNote = file.list();
            for (String fileName: subNote) {
                generateFileList(new File(file,fileName));
            }
        }
    }
    public void zipFolder(String des){
        generateFileList(new File(path));
        try {
            FileOutputStream fileOS = new FileOutputStream(des);
            ZipOutputStream zipOS = new ZipOutputStream(fileOS);
            System.out.println("Zipping into "+des+".....");
            for (String file : this.fileList) {
                System.out.println("Adding "+ file +".....");
                ZipEntry zipEntry = new ZipEntry(file);
                zipOS.putNextEntry(zipEntry);
                System.out.println(path+File.separator+file);
                FileInputStream fileIS = new FileInputStream(path+File.separator+file);
                int len;
                while ((len = fileIS.read())!=-1){
                    zipOS.write(len);
                }
                fileIS.close();
            }
            zipOS.closeEntry();
            zipOS.close();
            fileOS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void unZipFileText(String input) {
        try {
            String pathDir = path.replace("." + fileFormat(fileFormat(path)), "");
            System.out.println(pathDir);
            createFolder(pathDir);
            byte [] inputB = input.getBytes();
            System.out.println(input.length());
            //Compress the bytes
            byte [] output = new byte[100];
            Deflater deflater = new Deflater();
            deflater.setInput(inputB);
            deflater.finish();
            int compressDataLength = deflater.deflate(output);
            //Decompress the bytes
            Inflater inflater = new Inflater();
            inflater.setInput(output,0,output.length);
            byte [] result = new byte[1024];
            int resultLength = inflater.inflate(result);
            inflater.end();
            String outPut = new String(result,0,resultLength);
            System.out.println(outPut);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }
    public void zipFile(String des){
        File file = new File(path);
        if (!file.exists()){
            System.out.println("File is not exist");
            return;
        }
        try {
            FileOutputStream fileOS = new FileOutputStream(des);
            ZipOutputStream zipOS = new ZipOutputStream(fileOS);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOS.putNextEntry(zipEntry);
            FileInputStream fileIS = new FileInputStream(file);
            int len;
            while ((len = fileIS.read())!=-1){
                zipOS.write(len);
            }
            zipOS.closeEntry();
            zipOS.close();
            fileIS.close();
            System.out.println("Finish zipping");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void unZip(String des){
        File file = new File(des);
        if (!file.exists()){
            file.mkdir();
        }
        try {
            ZipInputStream zipIS = new ZipInputStream(new FileInputStream(path));
            ZipEntry zipEntry = zipIS.getNextEntry();
            while (zipEntry!=null){
                String fileName = zipEntry.getName();
                File newFile = new File(des + File.separator + fileName);
                System.out.println("Unzipping "+newFile.getAbsoluteFile() +".....");
                new File(newFile.getParent()).mkdir();
                FileOutputStream fileOS = new FileOutputStream(newFile);
                int len;
                while ((len=zipIS.read())!=-1){
                    fileOS.write(len);
                }
                fileOS.close();
                zipEntry = zipIS.getNextEntry();
            }
            zipIS.closeEntry();
            zipIS.close();
            System.out.println("Successfully unzipped");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void zipFileText(String input){
        try {
            byte []inputGet = input.getBytes("UTF-8");
            byte []result = new byte[100];
            Deflater deflater = new Deflater();
            deflater.setInput(inputGet);
            deflater.finish();
            int lengthCompress = deflater.deflate(result);
            System.out.println(new String(result,0,lengthCompress)+"*");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
