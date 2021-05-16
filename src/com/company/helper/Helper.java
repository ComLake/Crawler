package com.company.helper;

import com.company.model.ZIP;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class Helper {
    public static void attackDownloadingFile(WebDriver driver){
        System.out.println("**いただきます !**");
        //get parent window handle
        String mainWindow = driver.getWindowHandle();
        //open new tab in same browser in order to go to the downloads site
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open()");
        //get all windows handles
        Set<String> allWindowHandles = driver.getWindowHandles();
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
        String fileName = (String) next_js.executeScript("return document" +
                ".querySelector('downloads-manager')" +
                ".shadowRoot.querySelector('#downloadsList downloads-item')" +
                ".shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("Details of Downloaded Files");
        System.out.println("Downloaded File Name: " + fileName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
        driver.quit();
        ZIP zip = new ZIP("C:\\Users\\User\\Downloads\\"+fileName);
        zip.readZip();
//        zip.unZip("C:\\Users\\User\\Downloads\\"+fileName.replace("."));
    }
}
