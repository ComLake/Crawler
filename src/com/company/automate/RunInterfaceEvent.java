package com.company.automate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class RunInterfaceEvent {
    public static void main(String[]args) throws InterruptedException {
        System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
        WebDriver webDriver = new EdgeDriver();
        JavascriptExecutor jvExecutor = (JavascriptExecutor)webDriver;
        EventFiringWebDriver eventHandle = new EventFiringWebDriver(webDriver);
        EventCapture capture = new EventCapture();
        //Register with EventFiringWebDriver
        //Register method allows to register our implementation of listener
        eventHandle.register(capture);
        //navigating the web youtube
        eventHandle.navigate().to("https://www.youtube.com/");
        jvExecutor.executeScript("window.scrollBy(0,400)");
        Thread.sleep(3000);
        eventHandle.findElement(By.xpath("//yt-formatted-string[contains(text(),'Chill-out music')]")).click();
        //navigate to webpage "https://www.youtube.com/watch?v=x8VYWazR5mE"
        eventHandle.navigate().to("https://www.youtube.com/watch?v=x8VYWazR5mE");
        Thread.sleep(10000);
        eventHandle.navigate().back();
        eventHandle.quit();
        //unregister allow to detach
        eventHandle.unregister(capture);
        System.out.println("End Listener");
    }
}
