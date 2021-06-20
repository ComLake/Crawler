package com.company.downloader.target.box;


import com.box.sdk.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoxCrawler {
    private static final String USER_ID = "16373928354";
    private static final int MAX_DEPTH = 1;
    private static final int MAX_CACHE_ENTRIES = 100;
    private static BoxDeveloperEditionAPIConnection api;
    public void searchDemo(){
        Reader reader = null;
        try {
            reader = new FileReader("src/com/company/lib/config.json");
            BoxConfig config = BoxConfig.readFrom(reader);
            BoxDeveloperEditionAPIConnection api = BoxDeveloperEditionAPIConnection.getAppEnterpriseConnection(config);
            BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
            System.out.format("Welcome, %s!\n\n",userInfo.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void search(){
        // Turn off logging to prevent polluting the output.
        Logger.getLogger("com.box.sdk").setLevel(Level.SEVERE);
        //It is a best practice to use an access token cache to prevent unneeded requests to Box for access tokens.
        //For production applications it is recommended to use a distributed cache like Memcached or Redis, and to
        //implement IAccessTokenCache to store and retrieve access tokens appropriately for your environment.
        IAccessTokenCache accessTokenCache = new InMemoryLRUAccessTokenCache(MAX_CACHE_ENTRIES);
        try {
            Reader reader = new FileReader("src/com/company/lib/config.json");
            BoxConfig config = BoxConfig.readFrom(reader);
            api = BoxDeveloperEditionAPIConnection.getAppUserConnection(USER_ID,
                    config,
                    accessTokenCache);
            BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
            System.out.format("Welcome, %s!\n\n",userInfo.getName());
            BoxSearch boxSearch = new BoxSearch(api);
            searchForTopic(boxSearch);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchForTopic(BoxSearch boxSearch) {
        System.out.println("Please enter the topic");
        String topic = (new Scanner(System.in)).nextLine();
        /**
         * Use this class to specify all the different search parameters that you may want to use.
         * Examples include: type, contentType, folderId's, metadata filters, created, updated.
         * Also shows how to incrementally crawl result sets.
         */
        BoxSearchParameters boxSearchParam = new BoxSearchParameters();
        //searchByDescription(boxSearchParam,boxSearch,topic);
        ownerIdFilterExample(boxSearchParam,boxSearch,topic);
    }
    private static void crawlerSearchResult(BoxSearchParameters boxSearchParam,BoxSearch boxSearch,String topic){
        //Set up result Partial Object
        PartialCollection<BoxItem.Info>searchResult;
        //Starting point of the result set
        long offset = 0;
        //Number of results that would be pulled back
        long limit = 1000;
        //Storing the full size of the results
        long fullSizeOfResult = 0;

        while (offset <= fullSizeOfResult){
            searchResult = boxSearch.searchRange(offset,limit,boxSearchParam);
            fullSizeOfResult = searchResult.fullSize();
            System.out.println("offset: "+offset+ " of fullSizeOfResult: "+fullSizeOfResult);
            printSearchResults(searchResult,topic);
            offset+=limit;
        }
    }
    private static void printSearchResults(PartialCollection<BoxItem.Info>searchResult,String topic){
        //Crawl the folder
        System.out.println("--=Results fullResultSize: "+ searchResult.fullSize() + "==--");
        for (BoxItem.Info info:searchResult) {
            System.out.println("File Found: "+info.getID()+" : "+info.getName()+", Owner: "+info.getOwnedBy().getID());
            System.out.println("***Download Progress***");
            downloadFile(info.getID());
        }
        System.out.println("");

    }
    private static void downloadFile(String id){
        BoxFile file = new BoxFile(api, id);
        BoxFile.Info info = file.getInfo();

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(new File("D:\\save\\sources\\"+info.getName()+info.getType().replace("file","")));
        file.download(stream, new ProgressListener() {
            public void onProgressChanged(long numBytes, long totalBytes) {
                double percentComplete = numBytes / totalBytes;
                System.out.println(percentComplete);
            }
        });
        stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void searchByDescription(BoxSearchParameters boxSearchParam, BoxSearch boxSearch,String topic){
        System.out.println("************Search by Description************");
        List<String>contentTypes = new ArrayList<>();
        contentTypes.add("");
        boxSearchParam.clearParameters();
        boxSearchParam.setContentTypes(contentTypes);
        boxSearchParam.setQuery(topic);
        crawlerSearchResult(boxSearchParam,boxSearch,topic);
    }
    private static void ownerIdFilterExample(BoxSearchParameters boxSearchParam, BoxSearch boxSearch,String topic){
        System.out.println("***********Owner Id's Filter Search***********");
        List<String>ownerUserIds = new ArrayList<>();
        ownerUserIds.add(USER_ID);
        boxSearchParam.clearParameters();
        boxSearchParam.setQuery(topic);
        boxSearchParam.setOwnerUserIds(ownerUserIds);
        crawlerSearchResult(boxSearchParam,boxSearch,topic);
    }
    private static void downloadZipFilter(PartialCollection<BoxItem.Info>searchResult,String topic){
        ArrayList<BoxZipItem>items = new ArrayList<>();
        for (BoxItem.Info info:searchResult) {
            BoxZipItem boxZipItem = new BoxZipItem(info.getType(),info.getID());
            items.add(boxZipItem);
        }
        BoxZip zip = new BoxZip(api);
        try {
            FileOutputStream fileOS = new FileOutputStream(new File("D:\\save\\sources"+"\\"+topic+".zip"));
            BoxZipDownloadStatus zipDownloadStatus = zip.download(topic,items,fileOS);
            fileOS.close();
            if(zipDownloadStatus.getState()== BoxZipDownloadStatus.State.SUCCEEDED){
                System.out.println("Download Successfully");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
