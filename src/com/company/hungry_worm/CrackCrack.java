package com.company.hungry_worm;

import com.company.fileSample.JPG;
import com.company.fileSample.ZIP;
import org.openqa.selenium.net.UrlChecker;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CrackCrack implements Runnable {
    private String link;
    private File fileLocation;

    public CrackCrack(String link, File fileLocation) {
        this.link = link;
        this.fileLocation = fileLocation;
    }

    @Override
    public void run() {
        ZIP zipFile = new ZIP(fileLocation.getAbsolutePath());
        zipFile.readZip();
    }

}
