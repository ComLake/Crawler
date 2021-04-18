package com.company;

import com.company.fileSample.CSV;
import com.company.fileSample.DCM;
import com.company.fileSample.JPG;
import com.company.hungry_worm.CrackCrack;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        JPG jpg = new JPG("C:\\Users\\User\\Downloads\\love_text.png","C:\\Users\\User\\Downloads\\love_text.jpg");
//        JPG.readMetaData(null,"D:\\javaTransfer\\downloadedModel\\audio_default.png");
//        DCM dcm = new DCM();
//        CSV csv = new CSV("C:\\Users\\User\\Downloads\\TechCrunchcontinentalUSA.csv");
//        csv.readAll();
        String link = "https://photo-zmp3.zadn.vn/audio_default.png";
////        Scanner scanner = new Scanner(System.in);
////        link = scanner.nextLine();
//        File file = new File("D:\\javaTransfer\\downloadedModel\\audio_default.png");
        CrackCrack crackCrack = new CrackCrack(link,null);
        crackCrack.run();
    }
}
