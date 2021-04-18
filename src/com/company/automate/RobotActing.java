package com.company.automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotActing {
    public static void main(String[]args) throws AWTException, InterruptedException {
        System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
        WebDriver webDriver = new EdgeDriver();
        webDriver.get("https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F%3Fhl%3Den-GB&ss=1&scc=1&ltmpl=default&ltmplcache=2&hl=en-GB&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        webDriver.findElement(By.xpath("//input[@name='identifier']")).click();
        Robot robot = new Robot();
        Thread.sleep(3000);
        robot.keyPress(KeyEvent.VK_F);
        robot.keyPress(KeyEvent.VK_I);
        robot.keyPress(KeyEvent.VK_L);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_Y);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyPress(KeyEvent.VK_I);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyPress(KeyEvent.VK_2);
        robot.keyPress(KeyEvent.VK_3);
        Thread.sleep(3000);
        //webDriver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
        webDriver.findElement(By.id("identifierNext")).click();
        Thread.sleep(3000);
        webDriver.quit();
    }
}
