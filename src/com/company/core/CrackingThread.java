package com.company.core;

import com.company.utils.FirstDocument;

import java.io.File;

public class CrackingThread extends Thread {
    private File fileTarget;
    private String sourceLink;
    private String keySeek;
    @Override
    public void run() {
        FirstDocument firstDocument = new FirstDocument();
        firstDocument.setTopic(keySeek);
        firstDocument.analysis(fileTarget,sourceLink);
        TransportThread transportThread = new TransportThread(firstDocument);
        transportThread.run();
    }

    public void setFileTarget(File fileTarget) {
        this.fileTarget = fileTarget;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public void setKeySeek(String keySeek) {
        this.keySeek = keySeek;
    }
}
