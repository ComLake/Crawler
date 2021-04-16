package com.company.automate;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/***Xpath=//tagname[@Attribute='Value']***/
public class RuntimeBot {
    public static void main(String[]args) throws InterruptedException {
        String url="";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which you want dig in ?");
        url = scanner.nextLine();
        System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
        WebDriver webDriver = new EdgeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        switch (url){
            case "facebook":
                webDriver.get("https://www.facebook.com/login.php");
                webDriver.findElement(By.xpath("//input[@id='email']")).sendKeys("+84836918988");
                Thread.sleep(10000);
//        webDriver.findElement(By.xpath("//input[@id='pass']")).sendKeys("debovorrestcoman");
//        Thread.sleep(10000);
                webDriver.findElement(By.className("_xkt")).click();
                break;
            case "amazon":
                webDriver.get("https://www.amazon.com/");
                webDriver.findElement(By.id("twotabsearchtextbox")).sendKeys("neko");
                Thread.sleep(4000);
                webDriver.findElement(By.xpath("//input[@id='nav-search-submit-button']")).click();
                Thread.sleep(4000);
                webDriver.findElement(By.linkText("Popular Brands")).click();
                webDriver.navigate().to("https://www.amazon.com/Kawaii-Changing-Sensitive-Ceramic-Novelty/dp/B09279GQCR/ref=sr_1_10?dchild=1&keywords=neko+custom&qid=1618473102&sr=8-10");
                Thread.sleep(4000);
                webDriver.navigate().back();
                webDriver.quit();
                break;
            case "twitter":
                webDriver.get("https://twitter.com/");
                webDriver.findElement(By.linkText("Sign up")).click();
                Thread.sleep(4000);
                webDriver.findElement(By.xpath("//input[@name='name']")).sendKeys("Neko_好き");
                Thread.sleep(4000);
                webDriver.findElement(By.xpath("//span[contains(text(),'Use email instead')]")).click();
                Thread.sleep(4000);
                webDriver.findElement(By.xpath("//input[@name='email']")).sendKeys("jeffnews22@gmail.com");
                break;
            case "yahoo":
                webDriver.get("https://login.yahoo.com/");
                //something likes id
                webDriver.findElement(By.cssSelector("#login-username")).sendKeys("Thiet Nguyen");
                Thread.sleep(4000);
                webDriver.findElement(By.cssSelector("#mbr-forgot-link")).click();
                break;
            case "youtube":
                webDriver.get("https://www.youtube.com/");
                webDriver.findElement(By.partialLinkText("Trending")).click();
//                webDriver.findElement(By.cssSelector("#search")).sendKeys("lofi");
//                Thread.sleep(4000);
//                webDriver.findElement(By.cssSelector("#search-icon-legacy")).click();
                break;
            case "ebay":
                webDriver.get("https://www.ebay.com/");
                JavascriptExecutor js = (JavascriptExecutor)webDriver;
                webDriver.findElement(By.cssSelector("#gh-ac")).sendKeys("cat cosplay");
                Thread.sleep(4000);
                webDriver.findElement(By.cssSelector("#gh-btn")).click();
//                for (int i = 0; i < 10; i++) {
                    Thread.sleep(4000);
                    js.executeScript("window.scrollBy(0,500)");
//                }
                Thread.sleep(4000);
                webDriver.findElement(By.xpath("//span[contains(text(),'CAT')]")).click();
                Thread.sleep(4000);
                Alert alert  = webDriver.switchTo().alert();
//                String alertMessage = webDriver.switchTo().alert().getText();
//                System.out.println(alertMessage);
//                Thread.sleep(3000);
//                alert.accept();
                break;
            default:
                break;
        }
    }
}
