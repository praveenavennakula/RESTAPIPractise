package com.qa.client;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {
//1. GET Method without Headers:
    public CloseableHttpResponse get(String url) throws IOException {
        CloseableHttpClient httpClient=HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);//http get request
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        return closeableHttpResponse;



    }


    //2. GET Method with Headers:
    public CloseableHttpResponse get(String url, HashMap<String,String> hashMap) throws IOException {
        CloseableHttpClient httpClient=HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);//http get request
        for(Map.Entry<String,String> entry:hashMap.entrySet()){
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        return closeableHttpResponse;



    }
}
