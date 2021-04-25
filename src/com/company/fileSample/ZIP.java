package com.company.fileSample;

import org.openqa.selenium.io.Zip;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.*;

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

    public void readZip() {
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

    public String fileFormat(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public void createFolder(String path) {
        File des = new File(path);
        if (!des.exists()) {
            des.mkdir();
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
