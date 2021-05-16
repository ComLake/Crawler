package com.company.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FirstDocument {
    private long size;
    private String fileName;
    private String source;
    private String topic;
    private String language;
    private String description;
    private String mimeType;

    public FirstDocument() {
    }

    public long getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSource() {
        return source;
    }

    public String getTopic() {
        return topic;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return "FirstDocument{" +
                "size=" + size +
                ", fileName='" + fileName + '\'' +
                ", source='" + source + '\'' +
                ", topic='" + topic + '\'' +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }

    public String fileFormat(){return fileName.substring(fileName.lastIndexOf(".")+1);}
    public synchronized void analysis(File target, String source){
        this.fileName = target.getName();
        mimeType = fileFormat();
        this.source = source;
        topic = "This is topic of the file";
        description = "This is description of the file";
        language = "National language";
        Path fileLocation = Paths.get(target.getAbsolutePath());
        try {
            BasicFileAttributes attr = Files.readAttributes(fileLocation,BasicFileAttributes.class);
            this.size = attr.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = toString();
        System.out.println(result);
    }
}
