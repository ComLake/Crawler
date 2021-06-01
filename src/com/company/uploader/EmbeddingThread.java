package com.company.uploader;

import com.company.config.utils.SimpleDocument;

import java.io.File;

public class EmbeddingThread extends Thread {
    private File fileTarget;
    private String sourceLink;
    private String keySeek;
    private boolean isExit;

    public EmbeddingThread() {
        isExit = false;
    }

    @Override
    public void run() {
        while (!isExit){
            SimpleDocument simpleDocument = new SimpleDocument();
            simpleDocument.setTopic(keySeek);
            simpleDocument.analysis(fileTarget,sourceLink);
            UploaderThread uploaderThread = new UploaderThread(simpleDocument);
            uploaderThread.run();
            if (!uploaderThread.isAlive()){
                closeThread();
                System.out.println("["+this.getClass().getName()+"]:"+this.isAlive());
            }
        }
    }
    public void closeThread(){
        isExit = true;
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
