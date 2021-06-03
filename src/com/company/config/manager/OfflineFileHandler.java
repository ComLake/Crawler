package com.company.config.manager;

import com.company.config.utils.EmbeddedFile;
import com.company.uploader.UploadHandler;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OfflineFileHandler {
    private boolean isExit;
    private EmbeddedFile targetZFile;

    public OfflineFileHandler(EmbeddedFile targetZFile) {
        this.targetZFile = targetZFile;
    }

    public OfflineFileHandler() {
        isExit = false;
    }

    public void unpacking(String token){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!isExit){
                    try {
                        File targetFile = targetZFile.getFile();
                        String source = targetFile.getAbsolutePath();
                        String des = source.replace(".zip","");
                        File file = new File(des);
                        if (!file.exists()){
                            file.mkdirs();
                        }
                        byte []bytes = new byte[1024];
                        FileInputStream fInputStream = new FileInputStream(targetFile);
                        ZipInputStream zipInputStream = new ZipInputStream(fInputStream);
                        ZipEntry entry ;
                        while ((entry = zipInputStream.getNextEntry()) !=null){
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(des);
                            String finalPath = buffer.append(File.separator+entry.getName()).toString();
                            File newBie = new File(finalPath);
                            if (entry.isDirectory()){
                                if (!newBie.isDirectory()&&!newBie.mkdirs()){
                                    throw new IOException("Failed to create directory"+newBie);
                                }
                            }else {
                                File parent = newBie.getParentFile();
                                if (!parent.isDirectory()&&!parent.mkdirs()){
                                    throw new IOException("Failed to create directory"+parent);
                                }
                                FileOutputStream fOS = new FileOutputStream(newBie);
                                int length;
                                while ((length = zipInputStream.read(bytes))!=-1){
                                    fOS.write(bytes,0,length);
                                }
                                fOS.close();
                            }
                            new UploadHandler().upload(newBie,token);
                        }
                        zipInputStream.closeEntry();
                        zipInputStream.close();
                        System.out.println("Successfully unzipped");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        onStop();
                    }
                }
            }
        };
        runnable.run();
    }
    public void onStop(){
        isExit = true;
    }
}
