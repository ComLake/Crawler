package com.company.config.manager;

import com.company.config.utils.EmbeddedFile;
import com.company.uploader.UploadingFileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ConfigurationManager{
    private static ConfigurationManager configurationManager;
    private ArrayList<EmbeddedFile> zipTarget = new ArrayList<>();
    private String topic;
    private String token;
    private UnpackZippedFile unpackZippedFile;
    public ConfigurationManager() {
    }
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    public void unPack(){
        for (EmbeddedFile zipFile:zipTarget) {
            System.out.println("** Unzipping "+zipFile.getFile().getName()+" ...");
            unpackZippedFile = new UnpackZippedFile();
            unpackZippedFile.unpacking(zipFile,token);
        }
    }
    public void collectEncodeFiles(ArrayList<EmbeddedFile>embeddedFiles){
        if (embeddedFiles!=null){
            for (EmbeddedFile embeddedFile:embeddedFiles) {
                zipTarget.add(embeddedFile);
            }
        }
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getTopic() {
        return topic;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UnpackZippedFile{
        private boolean isExit;

        public UnpackZippedFile() {
            isExit = false;
        }

        public void unpacking(EmbeddedFile targetZFile,String token){
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
                                new UploadingFileHandler().upload(newBie,token);
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
}