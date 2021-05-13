package com.company.core;

import com.company.config.WorkPlaceManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScrapperThread extends Thread {
    private static String GITHUB_API_BASE_URL ="https://api.github.com/";
    private static String KAGGLE_API_BASE_URL = "https://www.kaggle.com/";
    private static String KAGGLE_API_SEARCH = "search?q=";
    private static String GITHUB_API_SEARCH_REPOSITORIES="search/repositories?q=";
    private static String GITHUB_REPOS = "repos/";
    private static String GITHUB_ZIP_DOWNLOAD = "zipball/master";
    private String target;
    private String keySeek;
    private ArrayList<String>sources;

    public ScrapperThread(String target, String keySeek) {
        this.target = target;
        this.keySeek = keySeek;
    }

    @Override
    public void run() {
        sources = new ArrayList<>();
        switch (target){
            case "kaggle":
                break;
            case "github":
                String url = GITHUB_API_BASE_URL + GITHUB_API_SEARCH_REPOSITORIES + keySeek;
                try {
                    URL direct = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)direct.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0");
                    int respond = httpURLConnection.getResponseCode();
                    System.out.println("\n Sending 'GET' request for URL "+url);
                    System.out.println("Response code : "+respond);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream())
                    );
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = reader.readLine())!=null){
                        response.append(inputLine +"\n");
                    }
                    reader.close();
                    System.out.println("Result of JSON object reading response ");
                    System.out.println("----------------------------------------");
                    JSONObject myResponse = new JSONObject(response.toString());
                    JSONArray array = myResponse.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = (JSONObject)array.get(i);
                        String linkSource = object.getString("archive_url").replace("{archive_format}{/ref}",
                                GITHUB_ZIP_DOWNLOAD);
                        System.out.println(linkSource);
                        if (i<5){
                            sources.add(linkSource);
                        }
                    }
                    WorkPlaceManager workPlaceManager = WorkPlaceManager.getInstance();
                    workPlaceManager.addMoreItems(sources);
                    workPlaceManager.downloadToWorkPlace();
                    System.out.println("********************************");
                    workPlaceManager.openSources();
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
