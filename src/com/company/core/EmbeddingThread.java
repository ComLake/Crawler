package com.company.core;

import com.company.utils.SimpleDocument;

import java.io.File;

public class EmbeddingThread extends Thread {
    private File fileTarget;
    private String sourceLink;
    private String keySeek;
    @Override
    public void run() {
        SimpleDocument simpleDocument = new SimpleDocument();
        simpleDocument.setTopic(keySeek);
        simpleDocument.analysis(fileTarget,sourceLink);
        UploaderThread uploaderThread = new UploaderThread(simpleDocument);
        uploaderThread.run();
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
