package com.company.model;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadWithLink {
    private static final int BUFFER_SIZE = 4096;

    public static void readHTML(String link) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
            byte[] block = new byte[4 * 1024];
            while (true) {
                int n = inputStream.read(block);
                if (n <= 0) {
                    break;
                }
                buffer.write(block, 0, n);

            }
            inputStream.close();
            System.out.println(buffer.toString("utf-8"));
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download_method2(File fileLocation, String link) {
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
            byte[] b = new byte[10 * 1024];
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

    public static void download_method1(File fileLocation, String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            double fileSize = (double) httpConnection.getContentLengthLong();
            InputStream inputStream = httpConnection.getInputStream();
            FileOutputStream fileOS = new FileOutputStream(fileLocation);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOS, 1024);
            byte[] buffer = new byte[1024];
            double downloaded = 0.00;
            int read = 0;
            double percentDownload = 0.00;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
                downloaded += read;
                percentDownload = (downloaded * 100) / fileSize;
                String percent = String.format("%.4f", percentDownload);
                System.out.println(read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            System.out.println("Downloaded completely");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void download_method3(String fileURL, String saveDir) {
        try {
            URL url = new URL(fileURL);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fOutStream = new FileOutputStream(saveDir);
            fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fOutStream.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
