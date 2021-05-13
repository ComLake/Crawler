package com.company.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadWithLink {
    public static void readHTML(String link){
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
    public static void download_method2(File fileLocation, String link){
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
    public static void download_method1(File fileLocation,String link){
        try {
            URL url = new URL(link);
            HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
            double fileSize = (double)httpConnection.getContentLengthLong();
            BufferedInputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream fileOS = new FileOutputStream(fileLocation);
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
