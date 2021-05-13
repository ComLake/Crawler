package com.company.config;

import com.company.core.DropInBagThread;
import com.company.core.OpenSourceThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorkPlaceManager {
    private static WorkPlaceManager workPlaceManager;
    private final String path = "D:\\save\\sources\\";
    private List<String> sources = new ArrayList<>();
    private List<File> zipTarget = new ArrayList<>();
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
    public void storageReport(String fishFile){
        File file = new File(fishFile);
        if (file.exists()){
            zipTarget.add(file);
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
        for (File zipFile:zipTarget) {
            System.out.println("** Unzipping "+zipFile.getName()+" ...");
            OpenSourceThread openSource = new OpenSourceThread();
            openSource.setTargetZFile(zipFile);
            openSource.run();
        }
    }
}