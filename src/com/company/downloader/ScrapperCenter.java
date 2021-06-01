package com.company.downloader;

import com.company.config.utils.EmbeddedFile;
import com.company.downloader.target.Crawler;
import com.company.downloader.target.GithubCrawler;
import com.company.downloader.target.KaggleCrawler;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.company.config.utils.Annotation.*;

public class ScrapperCenter {
    private String topic;
    private final String path = "D:\\save\\sources\\";
    private List<String> sources = new ArrayList<>();
    private List<EmbeddedFile> zipTarget = new ArrayList<>();
    private static ScrapperCenter scrapperCenter;
    private Crawler gitHubCrawler = new GithubCrawler();
    private Crawler kaggleCrawler = new KaggleCrawler();

    public void addMoreItems(ArrayList<String> items) {
        if (items.size() != 0) {
            for (int i = 0; i < items.size(); i++) {
                sources.add(items.get(i));
            }
        }
    }
    public static ScrapperCenter getInstance(){
        if (scrapperCenter==null){
            scrapperCenter = new ScrapperCenter();
        }
        return scrapperCenter;
    }
    public void scrapper(String website){
        if (website.equals("github")){
            gitHubCrawler.setKeySeek(topic);
            gitHubCrawler.scrapper();
        }else if(website.equals("kaggle")){
            kaggleCrawler.setKeySeek(topic);
            kaggleCrawler.scrapper();
        }else {
            System.out.println("website has not been supported");
        }
    }
    public void downloader(){
        for (String link:sources) {
            if (link.contains("kaggle")){
                StringBuffer linkBased = new StringBuffer();
                StringBuffer downloadPath = new StringBuffer();
                StringBuffer texture = new StringBuffer();
                linkBased.append(KAGGLE_API_BASE_URL);
                downloadPath.append(KAGGLE_ZIP_DOWNLOAD);
                texture.append("/");
                String nameTheZip = link.replaceAll(linkBased.toString(),"");
                nameTheZip = nameTheZip.replaceAll(downloadPath.toString(),"");
                nameTheZip = nameTheZip.replaceAll(texture.toString(),"");
                StringBuffer buffer = new StringBuffer();
                buffer.append(nameTheZip);
                buffer.append(".zip");
                StringBuffer destiny = new StringBuffer();
                destiny.append(path);
                destiny.append(buffer);
                System.out.println("Downloading.. "+buffer + " to "+destiny);
                kaggleCrawler.download(link,destiny.toString());
            }else if(link.contains("github")){
                StringBuffer linkBased = new StringBuffer();
                StringBuffer downloadPath = new StringBuffer();
                StringBuffer texture = new StringBuffer();
                linkBased.append(GITHUB_API_BASE_URL+GITHUB_REPOS);
                downloadPath.append("/"+GITHUB_ZIP_DOWNLOAD);
                texture.append("/");
                String nameTheZip = link.replaceAll(linkBased.toString(),"");
                nameTheZip = nameTheZip.replaceAll(downloadPath.toString(),"");
                nameTheZip = nameTheZip.replaceAll(texture.toString(),"");
                StringBuffer buffer = new StringBuffer();
                buffer.append(nameTheZip);
                buffer.append(".zip");
                StringBuffer destiny = new StringBuffer();
                destiny.append(path);
                destiny.append(buffer);
                System.out.println("Downloading.. "+buffer + " to "+destiny);
                gitHubCrawler.download(link,destiny.toString());
            }else {
                sources.remove(link);
            }
        }
        for (EmbeddedFile embeddedFile:zipTarget) {
            System.out.println(embeddedFile.getFile().getName());
        }
    }
    public void storageReport(String fishFile,String linkSource){
        File file = new File(fishFile);
        if (file.exists()){
            zipTarget.add(new EmbeddedFile(linkSource,file));
        }
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
