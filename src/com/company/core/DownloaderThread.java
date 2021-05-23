package com.company.core;

import com.company.config.ConfigurationManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloaderThread extends Thread{
    private static final int BUFFER_SIZE = 4096;
    private String link;
    private String path;

    public DownloaderThread(String link, String path) {
        this.link = link;
        this.path = path;
    }

    @Override
    public void run() {
        drop(path);
    }
    public void drop(String path){
        try {
            File file = new File(path);
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            double fileSize = (double) urlConnection.getContentLengthLong();
            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            FileOutputStream fileOS = new FileOutputStream(file);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOS,BUFFER_SIZE);
            byte[]buffer = new byte[BUFFER_SIZE];
            double downloaded = 0.00;
            int percentageDownloaded;
            int read;
            while ((read = inputStream.read(buffer,0,BUFFER_SIZE))!=-1){
                outputStream.write(buffer,0,read);
                downloaded += read;
                percentageDownloaded = (int)((downloaded*100L)/fileSize);
                System.out.println("Downloaded " + percentageDownloaded + "% of the file..");
            }
            outputStream.close();
            inputStream.close();
            ConfigurationManager configurationManager = ConfigurationManager.getInstance();
            configurationManager.storageReport(path,link);
            System.out.println("Downloaded!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
