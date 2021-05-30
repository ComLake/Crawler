package com.company.core;

import com.company.config.ConfigurationManager;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.company.utils.Annotation.defaultKName;
import static com.company.utils.Annotation.defaultKPass;

public class DownloaderThread extends Thread{
    private static final int BUFFER_SIZE = 4096;
    private String link;
    private String path;
    private boolean isExit;

    public DownloaderThread(String link, String path) {
        this.link = link;
        this.path = path;
        isExit = false;
    }

    @Override
    public void run() {
        drop(path);
    }
    public void drop(String path){
        while (!isExit){
            try {
                File file = new File(path);
                if (!file.exists()){
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    String auth = defaultKName+":"+defaultKPass;
                    byte[] encodeAuth = Base64.encodeBase64(
                            auth.getBytes(StandardCharsets.ISO_8859_1)
                    );
                    String authHeaderValue = "Basic "+new String(encodeAuth);
                    urlConnection.setRequestProperty("User-Agent","Mozilla/5.0");
                    urlConnection.setRequestProperty("Authorization",authHeaderValue);
                    int responseCode = urlConnection.getResponseCode();
                    System.out.println("\nSending 'GET' Request to URL"+url);
                    System.out.println("Response code :"+responseCode);
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
                }else {
                    System.out.println("file is already exist");
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                closeThread();
                System.out.println("["+this.getName()+"]:"+this.isAlive());
            }
        }
    }
    public void closeThread(){
        isExit = true;
    }
}
