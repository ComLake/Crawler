package com.company.downloader.target.dropbox;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;

public class DropBoxCrawler {
    private static final String ACCESS_TOKEN = "TyPf_XQoz_AAAAAAAAAAAZ4qLLuKENU9BGjUjkMKDlidV4A7tgb0sREa5qBY48vx";
    public void checkAccount(){
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/crawler_csv").build();
        DbxClientV2 client = new DbxClientV2(config,ACCESS_TOKEN);
        try {
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());
            searchAndDownloadMachine(client);
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
    public void searchAndDownloadMachine(DbxClientV2 client) throws DbxException {
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
//                System.out.println(metadata.getPathLower());
                if (metadata.getPathLower().contains("mew")){
                    download(client,metadata.getPathLower().replace("/",""),metadata);
                }
            }
            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
    }
    public void download(DbxClientV2 client,String filename, Metadata pathMetadata){
        String path = "D:\\save\\sources";
        File file = new File(path, filename);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            //Metadata pathMetadata = client.files().getMetadata("/octopus_1.gif");
            client.files().download(pathMetadata.getPathLower()).download(outputStream);
            System.out.println("METADATA"+  pathMetadata.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DownloadErrorException e) {
            e.printStackTrace();
        } catch (GetMetadataErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }

    }
}
