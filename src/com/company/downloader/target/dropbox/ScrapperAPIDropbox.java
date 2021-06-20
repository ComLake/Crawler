package com.company.downloader.target.dropbox;

import com.dropbox.core.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Locale;

public class ScrapperAPIDropbox {
    private static final String clientId = "emrh04gy5dgeeee";//clientId
    private static final String callBackUrl = "https://api.dropboxapi.com/oauth2/token";//The url defined in WSO2
    private static final String authorizeUrl = "https://www.dropbox.com/oauth2/authorize";
    String authorizationRedirect = getAuthGrantType(callBackUrl);

    private static String getAuthGrantType(String callbackURL) {
        return authorizeUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + callbackURL + "&scope=openid";
    }

    public void useBearerToken(String bearerToken) {
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.dropboxapi.com/2/auth/token/from_oauth1");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String response = out.toString();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
