package com.company.core;

import javax.print.attribute.standard.Fidelity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;

public class CrackingThread extends Thread {
    private ZipEntry element;
    private File destiny;
    private int length;
    private byte[]bytes;
    @Override
    public void run() {
        System.out.println("Cracking " + destiny.getAbsolutePath());
    }

    public void parse() {
    }

    public ZipEntry getElement() {
        return element;
    }

    public void setElement(ZipEntry element) {
        this.element = element;
    }

    public File getDestiny() {
        return destiny;
    }

    public void setDestiny(File destiny) {
        this.destiny = destiny;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
