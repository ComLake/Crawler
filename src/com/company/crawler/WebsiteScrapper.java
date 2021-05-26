package com.company.crawler;

//import com.company.automate.EventCapture;
import com.company.helper.Helper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WebsiteScrapper {
    private static String GITHUB_API_BASE_URL ="https://api.github.com/";
    private static String KAGGLE_API_BASE_URL = "https://www.kaggle.com/";
    private static String KAGGLE_API_SEARCH = "search?q=";
    private static String GITHUB_API_SEARCH_REPOSITORIES="search/repositories?q=";
    private static String GITHUB_REPOS = "repos/";
    private static String GITHUB_ZIP_DOWNLOAD = "zipball/master";
    private String pathDriver = "C:\\selenium\\chromedriver.exe";
    private ArrayList<String>downloadUrl;

    public WebsiteScrapper() {
        downloadUrl = new ArrayList<>();
    }
    public void dynamicGETRequestK(){
//        try {
//            HttpGet request = new HttpGet("https://www.kaggle.com/api/v1/datasets/list?group=public&sortBy=hottest&size=all&filetype=all&license=all&search=cat&page=1");
//            String auth = "thanhjeff"+":"+"2cd30ce68497dcab0c97efce84937a49";
//            byte[]encodeAuth = Base64.encodeBase64(
//                    auth.getBytes(StandardCharsets.ISO_8859_1)
//            );
//            String authHeader = "Basic "+ new String(encodeAuth);
//            request.setHeader(HttpHeaders.AUTHORIZATION,authHeader);
//
//            HttpClient client = HttpClientBuilder.create().build();
//            HttpResponse response = client.execute(request);
//            int statusCode = response.getStatusLine().getStatusCode();
//            System.out.println(statusCode);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    public void dynamicPOSTRequest(String url){
        try {
            URL direct = new URL(url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("data","Thiet");
            params.put("age","22");
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object>param : params.entrySet()) {
                if (postData.length()!=0) {
                    postData.append('&');
                }
                    postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            HttpURLConnection urlConnection = (HttpURLConnection)direct.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(postDataBytes);
            BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(urlConnection.
                    getInputStream(),"UTF-8"));
            String inputLine;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine())!=null){
                response.append(inputLine+"\n");
            }
            bufferedInput.close();
            System.out.println(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ChromeOptions setUpCustomizeBrowser(){
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String,String> ePref = new HashMap<String, String>();
        String downloadFolder = "C:\\Users\\User\\Downloads";
        ePref.put("download.default_directory",downloadFolder);
        chromeOptions.setExperimentalOption("prefs",ePref);
        return chromeOptions;
    }
    public void dynamicGETRequest(){
        try {
//            System.out.println("Please enter the key set ");
//            String search = (new Scanner(System.in).nextLine());
            String url = "https://www.kaggle.com/api/v1/datasets/download/crawford/cat-dataset";
            URL direct = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection)direct.openConnection();
            urlConnection.setRequestMethod("GET");
            String auth = "thanhjeff"+":"+"2cd30ce68497dcab0c97efce84937a49";
            byte[] encodeAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.ISO_8859_1)
            );
            String authHeaderValue = "Basic "+new String(encodeAuth);
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0");
            urlConnection.setRequestProperty("Authorization",authHeaderValue);
            int responseCode = urlConnection.getResponseCode();
            System.out.println("\nSending 'GET' Request to URL"+url);
            System.out.println("Response code :"+responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine())!=null){
                response.append(inputLine+"\n");
            }
            in.close();
            System.out.println("Result of JSON Object reading response");
            System.out.println(response.toString());
//            JSONObject myResponse = new JSONObject(response.toString());
//            JSONArray array = myResponse.getJSONArray("items");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject object = (JSONObject)array.get(i);
//                String target = object.getString("archive_url").replace("{archive_format}{/ref}",GITHUB_ZIP_DOWNLOAD);
//                System.out.println(target);
//                downloadUrl.add(target);
//            }
//            String urlDownload = downloadUrl.get(0);
//            System.out.println("Downloading the zip file"+ urlDownload +" illegally..");
//            System.setProperty("webdriver.chrome.driver",pathDriver);
//            WebDriver webDriver = new ChromeDriver(setUpCustomizeBrowser());
//            EventFiringWebDriver eventDriver = new EventFiringWebDriver(webDriver);
//            EventCapture listener = new EventCapture();
//            eventDriver.register(listener);
//            eventDriver.manage().window().maximize();
//            eventDriver.manage().deleteAllCookies();
//            eventDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
//            eventDriver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//            eventDriver.get(urlDownload);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void crawl(){
        String url = "";
        String searchable = "";
        System.out.println("Which website you wanna get the dataset ?");
        url = new Scanner(System.in).nextLine();
        switch (url){
            case "kaggle":
                System.out.println("What do you wanna search ?");
                searchable = new Scanner(System.in).nextLine();
                System.setProperty("webdriver.chrome.driver",pathDriver);
                WebDriver webDriver = new ChromeDriver(setUpCustomizeBrowser());
                webDriver.manage().window().maximize();
                webDriver.manage().deleteAllCookies();
                webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
                webDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
                String keySearch=searchable;
                webDriver.get("https://www.kaggle.com/");
                loginMachine(webDriver,"https://www.kaggle.com/");
                try {
                    webDriver.findElement(By.xpath("//input[@aria-label='Search']")).click();
                    Thread.sleep(1000);
                    webDriver.findElement(By.xpath("//input[@data-testid='searchInputBarInputElement']")).sendKeys(keySearch);
                    Thread.sleep(1000);
                    webDriver.findElement(By.xpath("//button[@aria-label='search']")).click();
                    Thread.sleep(1000);
                    webDriver.findElement(By.xpath("//p[contains(text(),'Datasets')]")).click();
                    Thread.sleep(1000);
                    webDriver.findElement(By.xpath("/html/body/main/div[1]/div/div[4]/div[2]/div/div/div[2]/div[2]/div[2]/a[1]/li/div/div[2]/div[2]/h6")).click();
                    Thread.sleep(1000);
                    webDriver.findElement(By.xpath("/html/body/main/div[1]/div/div[5]/div[3]/div[1]/div/div/div[2]/div[2]/div[1]/div[2]/a")).click();
                    Helper.attackDownloadingFile(webDriver);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "github":
                dynamicGETRequest();
                break;
            default:
                break;
        }
    }
    public void loginMachine(WebDriver webDriver,String website){
        switch (website){
            case "https://www.kaggle.com/":
                try {
                    webDriver.navigate().to("https://www.kaggle.com/account/login?phase=startSignInTab&returnUrl=%2F");
                    Thread.sleep(1000);
                    String currentUrl = webDriver.getCurrentUrl();
                    if (!currentUrl.equals("https://www.kaggle.com/#")){
                        webDriver.navigate().to("https://www.kaggle.com/account/login?phase=emailSignIn&returnUrl=%2F");
                        Thread.sleep(1000);
                        webDriver.findElement(By.xpath("//input[@name='email']")).sendKeys("jeffnews22@gmail.com");
                        Thread.sleep(1000);
                        webDriver.findElement(By.xpath("//input[@name='password']")).sendKeys("1234567");
                        Thread.sleep(1000);
                        webDriver.findElement(By.xpath("/html/body/main/div[1]/div[1]/div/form/div[2]/div[3]/button")).click();
                    }else {
                        System.out.println("Already signed in.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
