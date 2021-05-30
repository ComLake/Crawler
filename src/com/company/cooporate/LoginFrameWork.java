package com.company.cooporate;

import com.company.Main;
import com.company.config.ConfigurationManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static com.company.utils.Annotation.ULAKE_REGISTER;
import static com.company.utils.Annotation.ULAKE_SIGN_IN;

public class LoginFrameWork extends Thread {
    private boolean isExit;
    private String accountExist;

    public LoginFrameWork() {
        isExit = false;
    }
    @Override
    public void run() {
        while (!isExit){
            try {
                if (accountExist.equals("Y")) {
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
                        ConfigurationManager manager = ConfigurationManager.getInstance();
                        manager.setTokenULake(token);
                        manager.setRefreshTokenULake(refreshToken);
                    }

                } else if (accountExist.equals("N")) {
                    Thread.sleep(1000);
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
                    URL direct = new URL(ULAKE_REGISTER);
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
                    }
                } else {
                    System.out.println("Access denied!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                stopThread();
                System.out.println("["+this.getClass().getName()+"]:"+this.isAlive());
            }
        }
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public String getAccountExist() {
        return accountExist;
    }

    public void setAccountExist(String accountExist) {
        this.accountExist = accountExist;
    }

    public void stopThread(){
        isExit = true;
    }
}
