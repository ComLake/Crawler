package com.company.hungry_worm;

import com.company.fileSample.JPG;
import org.openqa.selenium.net.UrlChecker;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CrackCrack implements Runnable {
    private String link;
    private File fileLocation;

    public CrackCrack(String link, File fileLocation) {
        this.link = link;
        this.fileLocation = fileLocation;
    }

    @Override
    public void run() {
        readMetaDataJPGOnline();
    }
    private void readMetaDataJPGOnline(){
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            JPG.readMetaData(inputStream,null);
            byte [] block = new byte[4*1024];
            while (true){
                int n = inputStream.read(block);
                if (n<=0){
                    break;
                }
                buffer.write(block,0,n);

            }
            inputStream.close();
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void readHTML(){
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            byte [] block = new byte[4*1024];
            while (true){
                int n = inputStream.read(block);
                if (n<=0){
                    break;
                }
                buffer.write(block,0,n);

            }
            inputStream.close();
            System.out.println(buffer.toString("utf-8"));
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void down_2(){
        try {
            // file target
            fileLocation.getParentFile().mkdirs();
            fileLocation.createNewFile();
            FileOutputStream outputStream
                    = new FileOutputStream(fileLocation, false);
            // file source
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            long total = connection.getContentLengthLong();
            InputStream inputStream = connection.getInputStream();
            // Save file
            long totalSaved = 0;
            byte[] b = new byte[10*1024];
            int count = inputStream.read(b);
            while (count != -1) {
                outputStream.write(b, 0, count);
                totalSaved += count;
                int percent = (int) (totalSaved * 100 / total);
                System.out.println(percent);
                count = inputStream.read(b);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void down_1(){
        try {
            URL url = new URL(link);
            HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
            double fileSize = (double)httpConnection.getContentLengthLong();
            BufferedInputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream fileOS = new FileOutputStream(this.fileLocation);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOS,10*1024);
            byte[] buffer = new byte[10*1024];
            double downloaded = 0.00;
            int read = 0;
            double percentDownload = 0.00;
            while ((read = inputStream.read(buffer,0,10*1024))>=0){
                outputStream.write(buffer,0,read);
                downloaded += read;
                percentDownload = (downloaded*100)/fileSize;
                String percent = String.format("%.4f",percentDownload);
                System.out.println("Downloaded" + percent + "% of the file..");
            }
            outputStream.close();
            inputStream.close();
            System.out.println("Downloaded completely");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
