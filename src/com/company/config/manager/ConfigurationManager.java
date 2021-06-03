package com.company.config.manager;

import com.company.config.utils.EmbeddedFile;

import java.util.ArrayList;

public class ConfigurationManager{
    private static ConfigurationManager configurationManager;
    private ArrayList<EmbeddedFile> zipTarget = new ArrayList<>();
    private String topic;
    private String token;
    private OfflineFileHandler offlineFileHandler;
    public ConfigurationManager() {
    }
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    public void unPack(){
        for (EmbeddedFile zipFile:zipTarget) {
            System.out.println("** Unzipping "+zipFile.getFile().getName()+" ...");
            offlineFileHandler = new OfflineFileHandler(zipFile);
            offlineFileHandler.unpacking(token);
        }
    }
    public void collectEncodeFiles(ArrayList<EmbeddedFile>embeddedFiles){
        if (embeddedFiles!=null){
            for (EmbeddedFile embeddedFile:embeddedFiles) {
                zipTarget.add(embeddedFile);
            }
        }
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getTopic() {
        return topic;
    }

    public void setToken(String token) {
        this.token = token;
    }
}