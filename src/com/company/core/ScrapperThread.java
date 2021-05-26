package com.company.core;

import com.company.config.ConfigurationManager;
import com.company.helper.Helper;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ScrapperThread extends Thread {
    public static String GITHUB_API_BASE_URL = "https://api.github.com/";
    public static String KAGGLE_API_BASE_URL = "https://www.kaggle.com/api/v1/datasets/";
    private static String KAGGLE_API_SEARCH = "list?group=public&sortBy=hottest&size=all&filetype=all&license=all&search=";
    private static String GITHUB_API_SEARCH_REPOSITORIES = "search/repositories?q=";
    public static String GITHUB_REPOS = "repos/";
    private static String KAGGLE_PAGE = "&page=1";
    private String pathDriver = "C:\\selenium\\chromedriver.exe";
    public static String GITHUB_ZIP_DOWNLOAD = "zipball/master";
    public static String KAGGLE_ZIP_DOWNLOAD = "download/";
    public static String defaultKName = "thanhjeff";
    public static String defaultKPass = "2cd30ce68497dcab0c97efce84937a49";
    private String target;
    private String keySeek;
    private ArrayList<String> sources;

    public ScrapperThread(String target, String keySeek) {
        this.target = target;
        this.keySeek = keySeek;
        this.sources = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            initialResources();
            sleep(1000);
            train();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void initialResources(){
        switch (target) {
            case "kaggle":
                String k_direct = KAGGLE_API_BASE_URL + KAGGLE_API_SEARCH + keySeek + KAGGLE_PAGE;
                try {
                    URL direct = new URL(k_direct);
                    HttpURLConnection urlConnection = null;
                    urlConnection = (HttpURLConnection)direct.openConnection();
                    urlConnection.setRequestMethod("GET");
                    String auth = defaultKName+":"+defaultKPass;
                    byte[] encodeAuth = Base64.encodeBase64(
                            auth.getBytes(StandardCharsets.ISO_8859_1)
                    );
                    String authHeaderValue = "Basic "+new String(encodeAuth);
                    urlConnection.setRequestProperty("User-Agent","Mozilla/5.0");
                    urlConnection.setRequestProperty("Authorization",authHeaderValue);
                    int responseCode = urlConnection.getResponseCode();
                    System.out.println("\nSending 'GET' Request to URL"+k_direct);
                    System.out.println("Response code :"+responseCode);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while((inputLine = in.readLine())!=null){
                        response.append(inputLine+"\n");
                    }
                    in.close();
                    System.out.println("Result of JSON Object reading response");
                    System.out.println("----------------------------------------");
                    JSONArray kaggleArray = new JSONArray(response.toString());
                    for (int i = 0; i < kaggleArray.length(); i++) {
                        JSONObject kaggleObject = (JSONObject) kaggleArray.get(i);
                        if (i<2){
                            String dataDownload = kaggleObject.getString("ref");
                            StringBuffer kaggleDownload = new StringBuffer();
                            kaggleDownload.append(KAGGLE_API_BASE_URL);
                            kaggleDownload.append(KAGGLE_ZIP_DOWNLOAD);
                            kaggleDownload.append(dataDownload);
                            sources.add(kaggleDownload.toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "github":
                String url = GITHUB_API_BASE_URL + GITHUB_API_SEARCH_REPOSITORIES + keySeek;
                try {
                    URL direct = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) direct.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    int respond = httpURLConnection.getResponseCode();
                    System.out.println("\n Sending 'GET' request for URL " + url);
                    System.out.println("Response code : " + respond);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream())
                    );
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = reader.readLine()) != null) {
                        response.append(inputLine + "\n");
                    }
                    reader.close();
                    System.out.println("Result of JSON object reading response ");
                    System.out.println("----------------------------------------");
                    JSONObject myResponse = new JSONObject(response.toString());
                    JSONArray array = myResponse.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        String linkSource = object.getString("archive_url").replace("{archive_format}{/ref}",
                                GITHUB_ZIP_DOWNLOAD);
                        System.out.println(linkSource);
                        if (i < 5) {
                            sources.add(linkSource);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public synchronized void train(){
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        configurationManager.setTopic(keySeek);
        configurationManager.addMoreItems(sources);
        configurationManager.downloadToWorkPlace();
        System.out.println("********************************");
        configurationManager.openSources();
    }
}
