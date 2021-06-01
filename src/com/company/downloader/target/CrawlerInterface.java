package com.company.downloader.target;

import com.company.config.utils.EmbeddedFile;

import java.util.ArrayList;

public interface CrawlerInterface {
    void storageReport(String file,String link);
    void updateSources(ArrayList<String>sourcesLink);
}
