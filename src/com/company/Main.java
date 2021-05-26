package com.company;

import com.company.config.ConfigurationManager;
import com.company.core.ScrapperThread;
import com.company.crawler.WebsiteScrapper;
import com.company.model.DownloadWithLink;
import com.company.model.ZIP;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> nameThatWebsites = new ArrayList<>();
        System.out.println("                           (o)(o)\n" +
                "                          /     \\\n" +
                "                         /       |\n" +
                "                        /   \\  * |\n" +
                "          ________     /    /\\__/\n" +
                "  _      /        \\   /    /\n" +
                " / \\    /  ____    \\_/    /\n" +
                "//\\ \\  /  /    \\         /\n" +
                "V  \\ \\/  /      \\       /\n" +
                "    \\___/        \\_____/");

        System.out.println("------------------------");
        System.out.println("Give me a name");
        String keyword = new Scanner(System.in).nextLine();
        System.out.println("How many website you wanna scrap ? ");
        int n = new Scanner(System.in).nextInt();
        System.out.println("Name of those..");
        for (int i = 0; i < n; i++) {
            String target = new Scanner(System.in).nextLine();
            nameThatWebsites.add(target);
        }
        System.out.println("\n-----------------------------------------");
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        configurationManager.setTopic(keyword);
        configurationManager.addWebsitesTarget(nameThatWebsites);
        System.out.println("Begin scrapped");
        configurationManager.scrapped();
        System.out.println("End scrapped");
        System.out.println("Begin download");
        configurationManager.downloadToWorkPlace();
        System.out.println("********************************");
        System.out.println("End download");
        System.out.println("Begin unpack");
        configurationManager.openSources();
        System.out.println("End unpack");
    }
}
