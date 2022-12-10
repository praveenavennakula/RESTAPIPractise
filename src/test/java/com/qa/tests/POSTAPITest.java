package com.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class POSTAPITest extends TestBase {
    TestBase testBase;
    String serviceUrl;
    String apiUrl;
    String url;
    RestClient restClient;
    @BeforeMethod
    public void setUp(){
        testBase=new TestBase();
        serviceUrl = prop.getProperty("ServiceURL");
        apiUrl = prop.getProperty("APIURL");
        url = serviceUrl + apiUrl;
    }

    @Test
    public void POSTAPITestWithHeaders() throws IOException, ParseException {
        restClient=new RestClient();
        HashMap<String,String> headerMap=new HashMap<String,String>();
        headerMap.put("Content-Type", "application/json");
        Users users=new Users("morpheus", "leader");
        //jackson API
        ObjectMapper objectMapper=new ObjectMapper();
        //java Object to JSON file Conversion
        objectMapper.writeValue(new File("C:\\Users\\prade\\Praveena\\RESTAPIPractise\\src\\main\\java\\com\\qa\\data\\users.json"),users);

        //java Object to JSON in String
        String usersReqJsonString=objectMapper.writeValueAsString(users);
        System.out.println("Users Request JSON String is:->"+usersReqJsonString);

        //Call post method from restclient.java which executes post request with url,request payload and headers
        CloseableHttpResponse closeableHttpResponse=restClient.post(url,usersReqJsonString,headerMap);
        System.out.println(closeableHttpResponse);

        //1.Status code
        int statusCode=closeableHttpResponse.getCode();
        Assert.assertEquals(statusCode,testBase.RESPONSE_STATUS_CODE_201);

        //2.JSON String
        String responseString= EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        System.out.println("Response String is:->"+responseString);

        //string to JSON Object
        JSONObject responseJson=new JSONObject(responseString);
        System.out.println("JSON response is"+responseJson);

        //JSON String to Java Object
        Users usersResObj=objectMapper.readValue(responseString,Users.class);
        System.out.println(usersResObj);
        Assert.assertTrue(users.getName().equals(usersResObj.getName()));
        Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));

        System.out.println(users.getName().equals(usersResObj.getName()));
        System.out.println(users.getJob().equals(usersResObj.getJob()));
        System.out.println(usersResObj.getId());
        System.out.println(usersResObj.getCreatedAt());



    }
}
