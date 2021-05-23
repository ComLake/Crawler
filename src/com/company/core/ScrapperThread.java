package com.company.core;

import com.company.config.ConfigurationManager;
import com.company.helper.Helper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ScrapperThread extends Thread {
    public static String GITHUB_API_BASE_URL = "https://api.github.com/";
    public static String KAGGLE_API_BASE_URL = "https://www.kaggle.com/";
    private static String KAGGLE_API_SEARCH = "search?q=";
    private static String GITHUB_API_SEARCH_REPOSITORIES = "search/repositories?q=";
    public static String GITHUB_REPOS = "repos/";
    private String pathDriver = "C:\\selenium\\chromedriver.exe";
    private static String KAGGLE_DATASET = "+in%3Adatasets";
    public static String GITHUB_ZIP_DOWNLOAD = "zipball/master";
    public static String KAGGLE_ZIP_DOWNLOAD = "/download";
    private String target;
    private String keySeek;
    private ArrayList<String> sources;

    public ScrapperThread(String target, String keySeek) {
        this.target = target;
        this.keySeek = keySeek;
    }

    @Override
    public void run() {
        sources = new ArrayList<>();
        switch (target) {
            case "kaggle":
                String k_direct = KAGGLE_API_BASE_URL + KAGGLE_API_SEARCH + keySeek + KAGGLE_DATASET;
                System.setProperty("webdriver.chrome.driver", pathDriver);
                WebDriver webDriver = new ChromeDriver();
                webDriver.manage().window().maximize();
                webDriver.manage().deleteAllCookies();
                webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
                webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                webDriver.get(k_direct);
                List<WebElement> links = null;
                try {
                    links = webDriver.findElements(By.tagName("a")); //This will store all the link WebElements into a list
                    int count = 0;
                    for(WebElement ele: links)
                    {
                        String url = ele.getAttribute("href"); //To get the link you can use getAttribute() method with "href" as an argument
                        StringBuilder kaggleDownloader = new StringBuilder();
                        kaggleDownloader.append(url);
                        kaggleDownloader.append(KAGGLE_ZIP_DOWNLOAD);
                        String result = kaggleDownloader.toString();
                        if (count<5){
                            sources.add(result);
                            ++count;
                        }
                    }
                    Thread.sleep(3000);
                    webDriver.close();
                    webDriver.quit();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String link:sources) {
                    System.out.println(link);
                }
                ConfigurationManager configurationManagerK = ConfigurationManager.getInstance();
                configurationManagerK.setTopic(keySeek);
                configurationManagerK.addMoreItems(sources);
                configurationManagerK.downloadToWorkPlace();
                System.out.println("********************************");
                configurationManagerK.openSources();
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
                    ConfigurationManager configurationManager = ConfigurationManager.getInstance();
                    configurationManager.setTopic(keySeek);
                    configurationManager.addMoreItems(sources);
                    configurationManager.downloadToWorkPlace();
                    System.out.println("********************************");
                    configurationManager.openSources();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
