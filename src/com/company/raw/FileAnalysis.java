package com.company.raw;

import java.io.*;
import java.lang.reflect.Field;
import java.util.zip.*;

public class FileAnalysis {
    private String pathFile;
    private String desFile;
    public FileAnalysis(String pathFile, String desFile) {
        this.pathFile = pathFile;
        this.desFile = desFile;
        init();
    }

    public void init() {
        String status = statusFile();
        switch (status) {
            case "This is directory":
                break;
            case "This is file":
                unzipFileDEF();
                break;
            case "File is not found":
                System.out.println("Need to be checked");
                break;
            default:
                break;
        }
    }

    public String getDesFile() {
        return desFile;
    }

    public void setDesFile(String desFile) {
        this.desFile = desFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String statusFile() {
        File file = new File(pathFile);
        if (file.isDirectory()) {
            return "This is directory";
        } else if (file.isFile()) {
            return "This is file";
        } else {
            return "File is not found";
        }
    }

    public void writeFR() {
        try {
            FileReader reader = new FileReader(pathFile);
            FileWriter writer = new FileWriter(desFile);
            int count;
            while ((count = reader.read()) != -1) {
                writer.write((char) count);
            }
            writer.write("By Thiet");
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFR() {
        try {
            FileReader reader = new FileReader(pathFile);
            int count;
            while ((count = reader.read()) != -1) {
                System.out.print((char) count);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFOS() {
        File file = new File(desFile);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            FileInputStream inputStream = new FileInputStream(pathFile);
            int cout;
            byte[] b = new byte[2048];
            while ((cout = inputStream.read()) != -1) {
                outputStream.write((char) cout);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFIS() {
        try {
            File file = new File(pathFile);
            FileInputStream inputStream = new FileInputStream(file);
            int count;
            while ((count = inputStream.read()) != -1) {
                System.out.print((char) count);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readBufferIS() {
        try {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            BufferedInputStream bufferIS = new BufferedInputStream(fileInputStream);
            byte []b = new byte[1024];
            int length = bufferIS.read(b);
            System.out.println(new String(b,0,length));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeBufferOS(){
        File file = new File(desFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferOS = new BufferedOutputStream(fileOutputStream);
            byte []b = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            BufferedInputStream bufferIS = new BufferedInputStream(fileInputStream);
            int length = bufferIS.read(b);
            bufferOS.write(b,0,length);
            bufferOS.close();
            bufferIS.close();
            fileOutputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void zipFileDEF(){
        File file = new File(pathFile);
        try {
            FileInputStream fIS = new FileInputStream(file);
            DeflaterInputStream dIS = new DeflaterInputStream(fIS);
            FileOutputStream fOS = new FileOutputStream(new File(desFile));
            int code;
            while ((code = dIS.read())!=-1){
                fOS.write(code);
            }
            fOS.close();
            dIS.close();
            fIS.close();
            System.out.println("Finished zipping !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void unzipFileDEF(){
        try {
            FileInputStream fIS = new FileInputStream(new File(pathFile));
            FileOutputStream fOS = new FileOutputStream(new File(desFile));
            InflaterInputStream inflaterIS = new InflaterInputStream(fIS);
            int count;
            while ((count = inflaterIS.read())!=-1){
                fOS.write(count);
            }
            fOS.close();
            fOS.close();
            fIS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
