package com.company.raw;

import java.io.File;

public class FileAnalysis {
    private String pathFile;

    public FileAnalysis(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
    public void writeTextFile(){
        File file = new File(pathFile);
        if (file.isDirectory()){
            System.out.println("This is directory");
        }else if (file.isFile()){
            System.out.println("This is file");
        }
    }
}
