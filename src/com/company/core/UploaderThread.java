package com.company.core;

import com.company.utils.SimpleDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class UploaderThread extends Thread {
    private URL direct;
    private SimpleDocument document;

    public UploaderThread(SimpleDocument document) {
        this.document = document;
        init();
    }

    private void init() {
        try {
            direct = new URL("https://jsonplaceholder.typicode.com/posts");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("filename", document.getFileName());
        params.put("source", document.getSource());
        params.put("topic", document.getTopic());
        params.put("language", document.getLanguage());
        params.put("description", document.getDescription());
        params.put("mimeType", document.getMimeType());
        params.put("size", String.format("%,d bytes", document.getSize()));
        StringBuilder postData = new StringBuilder();
        try {
            for (Map.Entry<String,Object>param : params.entrySet()) {
                if (postData.length()!=0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            HttpURLConnection urlConnection = (HttpURLConnection)direct.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(postDataBytes);
            BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(urlConnection.
                    getInputStream(),"UTF-8"));
            String inputLine;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine())!=null){
                response.append(inputLine+"\n");
            }
            bufferedInput.close();
            System.out.println(response.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
