package com.company.uploader;

import com.company.config.utils.EmbeddedFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnpackingThread extends Thread{
    private String keySearch;
    private EmbeddedFile targetZFile;
    private boolean isExit;

    public UnpackingThread() {
        isExit = false;
    }

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
                    EmbeddingThread cracking = new EmbeddingThread();
                    cracking.setKeySeek(keySearch);
                    cracking.setFileTarget(newBie);
                    cracking.setSourceLink(targetZFile.getLinkSources());
                    cracking.run();
                }
                zipInputStream.closeEntry();
                zipInputStream.close();
                System.out.println("Successfully unzipped");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                closeThread();
                System.out.println("["+this.getClass().getName()+"]:"+this.isAlive());
            }
        }
    }
    public void closeThread(){
        isExit = true;
    }
    public EmbeddedFile getTargetZFile() {
        return targetZFile;
    }

    public void setTargetZFile(EmbeddedFile targetZFile) {
        this.targetZFile = targetZFile;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

}
