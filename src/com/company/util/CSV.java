package com.company.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSV {
    private String path;
    private BufferedReader buffer;
    public CSV(String path) {
        this.path = path;
        try {
            buffer = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public void readColumn(int i){
        String line = "";
        while (true){
            try {
                while (((line = buffer.readLine())!=null)){
                    String []values = line.split(",");
                    System.out.println(values[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void readAll(){
        String line = "";
        while (true){
            try {
                while (((line = buffer.readLine())!=null)){
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
