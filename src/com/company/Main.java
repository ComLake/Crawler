package com.company;

import com.company.downloader.target.BoxCrawler;

public class Main {
    public static void main(String[] args) {
//        List<EmbeddedFile>savedFiles = new ArrayList<>();
//        List<String> nameThatWebsites = new ArrayList<>();
//        System.out.println("***ULAKE PROJECT COOPERATION***");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("------------------------");
//        System.out.println("Welcome to Worm Crawler");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Authentication authentication = Authentication.getInstance();
//        System.out.println("Do you have any account?[Y/N]");
//        String exitsAccount = (new Scanner(System.in)).nextLine();
//        if (exitsAccount.equals("Y")){
//            authentication.login();
//        }else if (exitsAccount.equals("N")){
//            authentication.register();
//        }else {
//            System.out.println("Access denied");
//        }
//        String token = authentication.getToken();
//        System.out.println("Authenticated!");
//        System.out.println("*********************************************************");
//        System.out.println("What do you wanna search ?");
//        String keyword = new Scanner(System.in).nextLine();
//        System.out.println("How many website you wanna scrap ? ");
//        int n = new Scanner(System.in).nextInt();
//        System.out.println("Name of those..");
//        for (int i = 0; i < n; i++) {
//            String target = new Scanner(System.in).nextLine();
//            nameThatWebsites.add(target);
//        }
//        ScrapperCenter scrapperManager = ScrapperCenter.getInstance();
//        scrapperManager.setTopic(keyword);
//        for (String target : nameThatWebsites) {
//            scrapperManager.scrapper(target);
//        }
//        scrapperManager.downloader();
//        System.out.println("End download");
//        System.out.println("Begin unpack");
//        ConfigurationManager configManager = ConfigurationManager.getInstance();
//        configManager.setToken(token);
//        configManager.collectEncodeFiles(scrapperManager.getZipTarget());
//        configManager.unPack();
//        System.out.println("End unpack");
        new BoxCrawler().search();
    }
}
