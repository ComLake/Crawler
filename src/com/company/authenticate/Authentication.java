package com.company.authenticate;

import com.company.config.manager.ConfigurationManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.config.utils.Annotation.*;

public class Authentication {
    private static Authentication authentication;
    private String token;
    private String refreshToken;

    public static Authentication getInstance(){
        if (authentication==null){
            authentication = new Authentication();
        }
        return authentication;
    }
    public void login(){
        HandleInformation handleInformation = new HandleInformation();
        handleInformation.loginAccount();
    }
    public void register(){
        HandleInformation handleInformation = new HandleInformation();
        handleInformation.createAccount();
        System.out.println("--------------------Verify account--------------------");
        handleInformation.loginAccount();
    }
    public void refreshToken(){
        HandleInformation handleInformation = new HandleInformation();
        handleInformation.setToken(token);
        handleInformation.setRefreshToken(refreshToken);
        handleInformation.refreshToken();
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public static class HandleInformation{
        private boolean isExit;
        private String token;
        private String refreshToken;

        private void createAccount(){
            isExit = false;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (!isExit){
                        System.out.println("Register access ...");
                        System.out.println("----------------------------------");
                        System.out.println("Your username : ");
                        String username = (new Scanner(System.in)).nextLine();
                        System.out.println("Your password : ");
                        String password = (new Scanner((System.in))).nextLine();
                        System.out.println("Email address : ");
                        String email = (new Scanner(System.in)).nextLine();
                        ArrayList<String> roles = new ArrayList<>();
                        System.out.println("+++++++++++++++++++++++++++++++++");
                        System.out.println("Which role you wanna be in ULake?[student,professor,anonymous,..]");
                        System.out.println("Press x to cancel");
                        String role;
                        do {
                            role = (new Scanner(System.in)).nextLine();
                            roles.add(role);
                        } while (!role.equals("x"));
                        roles.remove("x");
                        URL direct = null;
                        try {
                            direct = new URL(ULAKE_REGISTER);
                            HttpURLConnection urlConnection = (HttpURLConnection) direct.openConnection();
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                            urlConnection.setRequestProperty("Accept", "application/json");
                            urlConnection.setDoOutput(true);
                            StringBuilder roleBuilder = new StringBuilder();
                            if (roles.size() > 0) {
                                roleBuilder.append("    \"" + roles.get(0) + "\"\n");
                            }
                            for (int i = 1; i < roles.size(); i++) {
                                roleBuilder.append(",");
                                roleBuilder.append("    \"" + roles.get(i) + "\"\n");
                            }
                            String jsonInputString = "{\n" +
                                    "  \"username\": \"" + username + "\",\n" +
                                    "  \"email\": \"" + email + "\",\n" +
                                    "  \"role\": [\n" +
                                    roleBuilder.toString() +
                                    "  ],\n" +
                                    "  \"password\": \"" + password + "\"\n" +
                                    "}";
                            try (OutputStream outputStream = urlConnection.getOutputStream()) {
                                byte[] input = jsonInputString.getBytes("UTF-8");
                                outputStream.write(input, 0, input.length);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                    urlConnection.getInputStream(), "UTF-8"
                            ))) {
                                StringBuffer response = new StringBuffer();
                                String responseLine = null;
                                while ((responseLine = bufferedReader.readLine()) != null) {
                                    response.append(responseLine.trim());
                                }
                                System.out.println(response.toString());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stop();
                    }
                }
            };
            runnable.run();
        }
        private void loginAccount(){
            isExit = false;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (!isExit){
                        try {
                            Thread.sleep(1000);
                            System.out.println("-------------Sign in--------------");
                            System.out.println("\n**************username**************");
                            String username = (new Scanner(System.in)).nextLine();
                            System.out.println("\n**************password**************");
                            String password = (new Scanner(System.in)).nextLine();
                            URL direct = new URL(ULAKE_SIGN_IN);
                            HttpURLConnection urlConnection = (HttpURLConnection) direct.openConnection();
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                            urlConnection.setRequestProperty("Accept", "application/json");
                            urlConnection.setDoOutput(true);
                            String jsonInputString = "{\n" +
                                    "  \"username\": \"" + username + "\",\n" +
                                    "  \"password\": \"" + password + "\"\n" +
                                    "}";
                            StringBuffer response = null;
                            try (OutputStream outputStream = urlConnection.getOutputStream()) {
                                byte[] input = jsonInputString.getBytes("UTF-8");
                                outputStream.write(input, 0, input.length);
                            }
                            if (urlConnection.getResponseCode()!=200){
                                System.out.println("Invalid access");
                            }else {
                                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                        urlConnection.getInputStream(),"UTF-8"
                                ))){
                                    response = new StringBuffer();
                                    String responseLine = null;
                                    while ((responseLine = bufferedReader.readLine())!=null){
                                        response.append(responseLine.trim());
                                    }
                                    System.out.println(response.toString());
                                }
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String refreshToken = jsonObject.getString("refreshToken");
                                String token = jsonObject.getString("accessToken");
                                Authentication authentication = Authentication.getInstance();
                                authentication.setToken(token);
                                authentication.setRefreshToken(refreshToken);
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        stop();
                    }
                }
            };
            runnable.run();
        }
        private void refreshToken(){
            isExit = false;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (!isExit){
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
                            Authentication authentication = Authentication.getInstance();
                            authentication.setToken(token);
                            authentication.setRefreshToken(refreshToken);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        stop();
                    }
                }
            };
            runnable.run();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public void stop(){
            isExit = true;
        }
    }
}
