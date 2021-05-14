package com.company.core;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.print.attribute.standard.Fidelity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;

public class CrackingThread extends Thread {
    private File target;
    private String metadata;
    private String content;
    @Override
    public void run() {
    }
    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }

}
