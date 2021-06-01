package com.company.config.manager;

import com.company.config.utils.EmbeddedFile;
import com.company.uploader.UnpackingThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.company.config.utils.Annotation.*;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private final String path = "D:\\save\\sources\\";
    private List<String>websites = new ArrayList<>();
    private List<String> sources = new ArrayList<>();
    private List<EmbeddedFile> zipTarget = new ArrayList<>();
    private String topic;
    private String tokenULake;
    private String refreshTokenULake;
    public ConfigurationManager() {
    }
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    public void addMoreItems(ArrayList<String> items) {
        if (items.size() != 0) {
            for (int i = 0; i < items.size(); i++) {
                sources.add(items.get(i));
            }
        }
    }
    public void storageReport(String fishFile,String linkSource){
        File file = new File(fishFile);
        if (file.exists()){
            zipTarget.add(new EmbeddedFile(linkSource,file));
        }
    }
    public synchronized void openSources(){
        for (EmbeddedFile zipFile:zipTarget) {
            System.out.println("** Unzipping "+zipFile.getFile().getName()+" ...");
            UnpackingThread openSource = new UnpackingThread();
            openSource.setTargetZFile(zipFile);
            openSource.setKeySearch(topic);
            openSource.run();
        }
    }
    public void addWebsitesTarget(List<String>mainDirects){
        if (mainDirects.size()!=0){
            for (String website:mainDirects) {
                websites.add(website);
            }
        }
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getTopic() {
        return topic;
    }

    public String getTokenULake() {
        return tokenULake;
    }

    public void setTokenULake(String tokenULake) {
        this.tokenULake = tokenULake;
    }

    public String getRefreshTokenULake() {
        return refreshTokenULake;
    }

    public void setRefreshTokenULake(String refreshTokenULake) {
        this.refreshTokenULake = refreshTokenULake;
    }
}