package com.company.utils;

import java.io.File;

public class CrawlerFormData {
    private String linkSources;
    private File file;

    public CrawlerFormData(String linkSources, File file) {
        this.linkSources = linkSources;
        this.file = file;
    }

    public String getLinkSources() {
        return linkSources;
    }

    public void setLinkSources(String linkSources) {
        this.linkSources = linkSources;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
