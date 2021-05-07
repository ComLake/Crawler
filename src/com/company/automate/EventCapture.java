package com.company.automate;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.internal.EclipseInterface;

import java.io.File;
import java.util.Set;

public class EventCapture implements WebDriverEventListener {
    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        //get parent window handle
        String mainWindow = driver.getWindowHandle();
        //open new tab in same browser in order to go to the downloads site
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open()");
        //get all windows handles
        Set<String>allWindowHandles = driver.getWindowHandles();
        for (String winHandle:allWindowHandles) {
            if (!winHandle.equals(mainWindow)){
                driver.switchTo().window(winHandle);
            }
        }
        //navigate to the download site
        driver.get("chrome://downloads/");
        JavascriptExecutor next_js = (JavascriptExecutor)driver;
        double percentage = (double) 0;
        while(percentage!= 100){
            try{
                percentage = (Long)next_js.executeScript("return document" +
                        ".querySelector('downloads-manager')" +
                        ".shadowRoot.querySelector('#downloadsList downloads-item')" +
                        ".shadowRoot.querySelector('#progress').value");
                System.out.println(percentage);
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        String fileName = (String)next_js.executeScript("return document" +
                ".querySelector('downloads-manager')" +
                ".shadowRoot.querySelector('#downloadsList downloads-item')" +
                ".shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("Details of Downloaded Files");
        System.out.println("Downloaded File Name: " + fileName);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        System.out.println("Those current downloaded file in here");
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {

    }
}
