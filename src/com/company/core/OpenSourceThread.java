package com.company.core;

import com.company.utils.CrawlerFormData;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OpenSourceThread extends Thread{
    private CrawlerFormData targetZFile;
    @Override
    public void run() {
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
                CrackingThread cracking = new CrackingThread();
                cracking.setFileTarget(newBie);
                cracking.setSourceLink(targetZFile.getLinkSources());
                cracking.start();
            }
            zipInputStream.closeEntry();
            zipInputStream.close();
            System.out.println("Successfully unzipped");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CrawlerFormData getTargetZFile() {
        return targetZFile;
    }

    public void setTargetZFile(CrawlerFormData targetZFile) {
        this.targetZFile = targetZFile;
    }
}
