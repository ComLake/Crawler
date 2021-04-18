package com.company.automate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AnnotationAttack {
    public String baseUrl = "https://www.youtube.com/";
    public String driverPath = "C:\\selenium\\msedgedriver.exe";
    public WebDriver webDriver;
    @BeforeTest
    public void launchBrowser(){
        System.out.println("Launching Edge Browser");
        System.setProperty("webdriver.edge.driver",driverPath);
        webDriver = new EdgeDriver();
        webDriver.get(baseUrl);
    }
    @Test
    public void verifyYoutubeTitle(){
        String expected = "YouTube";// "Explore - YouTube";
        String getTitle = webDriver.getTitle();
        Assert.assertEquals(getTitle,expected);
    }
    @AfterTest
    public void terminateBrowser(){
        webDriver.close();
    }
}
