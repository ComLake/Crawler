package com.company.hungry_worm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Automation {
    private String pathDriver = "C:\\selenium\\msedgedriver.exe";
    public void crawlFile(){
        String url = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which website you wanna get the dataset ?");
        url = scanner.nextLine();
        System.setProperty("webdriver.edge.driver",pathDriver);
        WebDriver webDriver = new EdgeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        webDriver.get("https://www.kaggle.com/");
        loginMachine(webDriver,"https://www.kaggle.com/");
        String keySearch="";
        switch (url){
            case "xray":
                keySearch = "xray";
                break;
            case "ct_scan":
                keySearch = "ct scan";
                break;
            default:
                break;
        }
        try {
            webDriver.findElement(By.xpath("//input[@aria-label='Search']")).click();
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("//input[@data-testid='searchInputBarInputElement']")).sendKeys(keySearch);
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("//button[@aria-label='search']")).click();
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("//p[contains(text(),'Datasets')]")).click();
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("/html/body/main/div[1]/div/div[4]/div[2]/div/div/div[2]/div[2]/div[2]/a[1]/li/div/div[2]/div[2]/h6")).click();
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("/html/body/main/div[1]/div/div[5]/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div[2]/a")).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void loginMachine(WebDriver webDriver,String website){
        switch (website){
            case "https://www.kaggle.com/":
                try {
                    webDriver.navigate().to("https://www.kaggle.com/account/login?phase=startSignInTab&returnUrl=%2F");
                    Thread.sleep(1000);
                    String currentUrl = webDriver.getCurrentUrl();
                    if (!currentUrl.equals("https://www.kaggle.com/#")){
                        webDriver.navigate().to("https://www.kaggle.com/account/login?phase=emailSignIn&returnUrl=%2F");
                        Thread.sleep(1000);
                        webDriver.findElement(By.xpath("//input[@name='email']")).sendKeys("jeffnews22@gmail.com");
                        Thread.sleep(1000);
                        webDriver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234567");
                        Thread.sleep(1000);
                        webDriver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/form/div[2]/div[3]/button")).click();
                    }else {
                        System.out.println("Already signed in.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
