package com.company.cooporate;

import com.company.config.ConfigurationManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.company.utils.Annotation.ULAKE_REFRESH_TOKEN;

public class RefreshFrameWork extends Thread {
    private boolean isExit;
    private String refreshToken;
    private String token;

    public RefreshFrameWork() {
        isExit = false;
    }

    @Override
    public void run() {
        while (!isExit) {
            System.out.println("++++++++++++++++++++++RefreshToken+++++++++++++++++++++++++");
            try {
                URL direct = new URL(ULAKE_REFRESH_TOKEN);
                HttpURLConnection urlConnection = (HttpURLConnection) direct.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer " +
                        token);
                urlConnection.setDoOutput(true);
                String jsonInputString = "{\n" +
                        "  \"refreshToken\": \"" + refreshToken + "\"\n" +
                        "}";
                StringBuffer response = null;
                try (OutputStream outputStream = urlConnection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("UTF-8");
                    outputStream.write(input, 0, input.length);
                }
                System.out.println(urlConnection.getResponseCode());
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "UTF-8"
                ))) {
                    response = new StringBuffer();
                    String responseLine = null;
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }
                JSONObject jsonObject = new JSONObject(response.toString());
                token = jsonObject.getString("token");
                refreshToken = jsonObject.getString("refreshToken");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                ConfigurationManager manager = ConfigurationManager.getInstance();
                manager.setTokenULake(token);
                manager.setRefreshTokenULake(refreshToken);
                closeFrameWork();
            }
        }
    }

    public void closeFrameWork() {
        isExit = true;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
