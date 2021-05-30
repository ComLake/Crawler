package com.company.cooporate;

import com.company.file_config.ImageFile;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import static com.company.utils.Annotation.ULAKE_UPLOAD_FILE;

public class UploadFrameWork extends Thread {
    private boolean isExit;
    private String token;
    private File file;

    public UploadFrameWork(File file) {
        isExit = false;
        this.file = file;
    }

    @Override
    public void run() {
        while (!isExit) {
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
                closeFrameWork();
                System.out.println("[" + this.getClass().getName() + "]:" + this.isAlive());
            }
        }
    }

    public void closeFrameWork() {
        isExit = true;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
