package com.company.core;

import com.company.utils.FirstDocument;

import java.io.File;

public class CrackingThread extends Thread {
    private File fileTarget;
    private String sourceLink;
    @Override
    public void run() {
        FirstDocument firstDocument = new FirstDocument();
        firstDocument.analysis(fileTarget,sourceLink);
    }

    public void setFileTarget(File fileTarget) {
        this.fileTarget = fileTarget;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }
}
