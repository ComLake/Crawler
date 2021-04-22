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
        FileAnalysis analysis = new FileAnalysis("C:\\Users\\User\\Downloads\\archive");
        analysis.writeTextFile();
    }
}
