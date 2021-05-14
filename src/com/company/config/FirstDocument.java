package com.company.config;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

public class FirstDocument {
    private File fileSource;
    private String metadata;
    private String content;
    private String type;

    public FirstDocument(File fileSource) {
        this.fileSource = fileSource;
        type = targetFormat();
    }
    public File getFileSource() {
        return fileSource;
    }
    public String targetFormat(){
        String fileName = fileSource.getName();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    public synchronized void analysis(){
        StringBuffer metadataConvert = new StringBuffer();
        StringBuffer contentConvert = new StringBuffer();
        if (type.equals("svg")||type.equals("png")||type.equals("jpg")){
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(fileSource);
                for (Directory directory: metadata.getDirectories()) {
                    for (Tag tag:directory.getTags()) {
                        metadataConvert.append(tag);
                    }
                }
                this.metadata = metadataConvert.toString();
            } catch (ImageProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("**************Metadata***************");
        System.out.println(metadata);
        System.out.println("**************Content****************");
        System.out.println(content);
    }
}
