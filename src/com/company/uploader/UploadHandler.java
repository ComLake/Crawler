package com.company.uploader;

import com.company.config.manager.ConfigurationManager;
import okhttp3.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.company.config.utils.Annotation.ULAKE_UPLOAD_FILE;
import com.company.config.manager.ConfigurationManager.UnpackZippedFile;
public class UploadHandler {
    private boolean isExit;
    public UploadHandler() {
        isExit = false;
    }
    private void onStop(){
        isExit = true;
    }


    public void upload(File file, String token) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!isExit){
                    try {
                        URL direct = new URL(ULAKE_UPLOAD_FILE);
                        OkHttpClient client = new OkHttpClient.Builder().build();
                        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("file", file.getAbsolutePath(),
                                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                                file))
                                .build();
                        Request request = new Request.Builder().
                                url(direct).
                                method("POST", body).
                                addHeader("Authorization", "Bearer "+token)
                                .build();
                        Response response = client.newCall(request).execute();
                        System.out.println(response.toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        onStop();
                    }
                }
            }
        };
        runnable.run();
    }
    private void uploadTest(File document){
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("filename", document.getName());
        params.put("size", String.format("%,d bytes", document.getUsableSpace()));
        StringBuilder postData = new StringBuilder();
        try {
            URL direct = new URL("https://jsonplaceholder.typicode.com/posts");
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
    private void urlHttpConnectionPost(URL direct, File fileSend) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) direct.openConnection();
            String boundary = Long.toHexString(System.currentTimeMillis());
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", "Bearer " +
                    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGlldCIsImlhdCI6MTYyMjM0MjczMCwiZXhwIjoxNjIyNDI5MTMwfQ.H5sNFzeEIc6Wu6SyLlnVNpoeKgIIHqxo2BgGO4jy-PDJmMFNuaDkb5jRPWV7btoTBpG15EWlPV1-FTK8pYxgHw");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----" + boundary);
            connection.setRequestProperty("Content-Length", "" + fileSend.length());
            //Just for generating some unique random value
            String CRLF = "\r\n";//Line separator required by multipart/form-data
            try (
                    OutputStream outputStream = connection.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"), true);
            ) {
                writer.append("----" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"file\";" +
                        " filename=\"" + fileSend.getName() + "\"").append(CRLF);
                writer.append("Content-Type: text/plain").append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(fileSend.toPath(), outputStream);
                outputStream.flush();
                writer.append(CRLF).flush();
                writer.append("----" + boundary).append(CRLF).flush();
            }
            BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(connection.
                    getInputStream(), "UTF-8"));
            String inputLine;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            bufferedInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshToken(String token, String refreshToken) {

    }
}
