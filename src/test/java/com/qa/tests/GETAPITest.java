package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import netscape.javascript.JSObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GETAPITest extends TestBase {
    TestBase testBase;
    String serviceUrl;
    String apiUrl;
    String url;
    RestClient restClient;


    @BeforeMethod
    public void setUp() {
        testBase = new TestBase();
        serviceUrl = prop.getProperty("ServiceURL");
        apiUrl = prop.getProperty("APIURL");
        url = serviceUrl + apiUrl;


    }

    @Test(priority = 1)
    public void getAPITestWithoutHeaders() throws IOException, ParseException {
        restClient = new RestClient();
        CloseableHttpResponse closeableHttpResponse = restClient.get(url);
        //a. Status Code:
        int statusCode = closeableHttpResponse.getCode();
        System.out.println("Status Code--->" + statusCode);
        System.out.println(closeableHttpResponse.getCode() + " " + closeableHttpResponse.getReasonPhrase());
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. Json String:
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---> " + responseJson);

        //single value assertion:
        //per_page:
        String perPageValue = TestUtil.getValueByJpath(responseJson, "/per_page");
        System.out.println("value of per page is-->" + perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue), 6);

        //total
        String total = TestUtil.getValueByJpath(responseJson, "/total");
        System.out.println("value of total is-->" + total);
        Assert.assertEquals(Integer.parseInt(total), 12);

        //get the value from JSON ARRAY:
        String lastName = TestUtil.getValueByJpath(responseJson, "/data[0]/first_name");
        String id = TestUtil.getValueByJpath(responseJson, "/data[0]/id");
        String avatar = TestUtil.getValueByJpath(responseJson, "/data[0]/avatar");
        String firstName = TestUtil.getValueByJpath(responseJson, "/data[0]/first_name");

        System.out.println(lastName);
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(firstName);

        //c. All Headers
        Header[] headersArray = closeableHttpResponse.getHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());

        }
        System.out.println("Headers Array-->" + allHeaders);

    }

    @Test(priority = 2)
    public void getAPITestWithHeaders() throws IOException, ParseException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

    //		headerMap.put("username", "test@amazon.com");
    //		headerMap.put("password", "test213");
    //		headerMap.put("Auth Token", "12345");

        CloseableHttpResponse closeableHttpResponse = restClient.get(url, headerMap);
        //a. Status Code:
        int statusCode = closeableHttpResponse.getCode();
        System.out.println("Status Code--->" + statusCode);
        System.out.println(closeableHttpResponse.getCode() + " " + closeableHttpResponse.getReasonPhrase());
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        //b. Json String:
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API---> " + responseJson);

        //single value assertion:
        //per_page:
        String perPageValue = TestUtil.getValueByJpath(responseJson, "/per_page");
        System.out.println("value of per page is-->" + perPageValue);
        Assert.assertEquals(Integer.parseInt(perPageValue), 6);

        //total
        String total = TestUtil.getValueByJpath(responseJson, "/total");
        System.out.println("value of total is-->" + total);
        Assert.assertEquals(Integer.parseInt(total), 12);

        //get the value from JSON ARRAY:
        String lastName = TestUtil.getValueByJpath(responseJson, "/data[0]/first_name");
        String id = TestUtil.getValueByJpath(responseJson, "/data[0]/id");
        String avatar = TestUtil.getValueByJpath(responseJson, "/data[0]/avatar");
        String firstName = TestUtil.getValueByJpath(responseJson, "/data[0]/first_name");

        System.out.println(lastName);
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(firstName);

        //c. All Headers
        Header[] headersArray = closeableHttpResponse.getHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());

        }
        System.out.println("Headers Array-->" + allHeaders);

    }
}
