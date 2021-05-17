package com.company.config;

import com.company.core.DropInBagThread;
import com.company.core.OpenSourceThread;
import com.company.utils.CrawlerFormData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorkPlaceManager {
    private static WorkPlaceManager workPlaceManager;
    private final String path = "D:\\save\\sources\\";
    private List<String> sources = new ArrayList<>();
    private List<CrawlerFormData> zipTarget = new ArrayList<>();
    private String topic;
    public WorkPlaceManager() {
    }
    public static WorkPlaceManager getInstance() {
        if (workPlaceManager == null) {
            workPlaceManager = new WorkPlaceManager();
        }
        return workPlaceManager;
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
            zipTarget.add(new CrawlerFormData(linkSource,file));
        }
    }
    public void downloadToWorkPlace() {
        for (String link : sources) {
            String nameTheZip = link.replaceAll("https://api.github.com/repos","");
            nameTheZip = nameTheZip.replaceAll("/zipball/master","");
            nameTheZip = nameTheZip.replaceAll("/","");
            StringBuffer buffer = new StringBuffer();
            buffer.append(nameTheZip);
            buffer.append(".zip");
            StringBuffer destiny = new StringBuffer();
            destiny.append(path);
            destiny.append(buffer);
            System.out.println("Downloading.. "+buffer + " to "+destiny);
            DropInBagThread dropInBag = new DropInBagThread(link,destiny.toString());
            dropInBag.run();
        }
    }
    public void openSources(){
        for (CrawlerFormData zipFile:zipTarget) {
            System.out.println("** Unzipping "+zipFile.getFile().getName()+" ...");
            OpenSourceThread openSource = new OpenSourceThread();
            openSource.setTargetZFile(zipFile);
            openSource.setKeySearch(topic);
            openSource.run();
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getTopic() {
        return topic;
    }
}