package com.company.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScrapperTool {
    private static String pathDriver = "C:\\selenium\\chromedriver.exe";
    public static void main(String[]args){
        System.setProperty("webdriver.chrome.driver", pathDriver);
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        webDriver.get("https://product.rakuten.co.jp/product/-/b336eccff569970579770a810680d544/item/");
        List<WebElement>list=null;
        list = webDriver.findElements(By.className("quickViewIteminfo"));
        for (WebElement element:list) {
           String outLink = element.findElement(By.tagName("a")).getAttribute("href");
            System.out.println(outLink);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.close();
    }

}
