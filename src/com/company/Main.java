package com.company;

import com.company.fileSample.CSV;
import com.company.fileSample.DCM;
import com.company.fileSample.JPG;
import com.company.fileSample.ZIP;
import com.company.hungry_worm.Automation;
import com.company.hungry_worm.CrackCrack;
import com.company.raw.FileAnalysis;
import org.openqa.selenium.io.Zip;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        CSV csv = new CSV("C:\\Users\\User\\Downloads\\metadata.csv");
//        csv.readAll();
//        DCM dcm = new DCM();
//       JPG.readMetaData(null,"C:\\Users\\User\\Downloads\\audio_default.png");
//       Automation automation = new Automation();
//       automation.crawlFile();
        new FileAnalysis("C:\\Users\\User\\Downloads\\data.def","C:\\Users\\User\\Downloads\\def2.txt");
    }
}
