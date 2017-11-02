/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class ClientFormLogin {

    public static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();

//        HttpGet httpget = new HttpGet("http://19.16.91.111:9016/apihub/login");

//        HttpResponse response = httpclient.execute(httpget);
//        HttpEntity entity = response.getEntity();

//        System.out.println("Login form get: " + response.getStatusLine());
//        if (entity != null) {
//            entity.consumeContent();
//        }
//        System.out.println("Initial set of cookies:");
//        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
//        if (cookies.isEmpty()) {
//            System.out.println("None");
//        } else {
//            for (int i = 0; i < cookies.size(); i++) {
//                System.out.println("- " + cookies.get(i).toString());
//            }
//        }

        HttpPost httpost = new HttpPost("http://19.16.91.111:9016/apihub/login");
//        HttpResponse response = httpclient.execute(httpost);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//        nvps.add(new BasicNameValuePair("IDToken1", "username"));
//        nvps.add(new BasicNameValuePair("IDToken2", "password"));
        
        nvps.add(new BasicNameValuePair("username","sysadmin"));
        nvps.add(new BasicNameValuePair("password","sgs#ywxt"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httpost);
        HttpEntity entity =  null;

        entity = response.getEntity();
        String result = EntityUtils.toString(entity); 
		System.out.println(result);
        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
            entity.consumeContent();
        }

//        System.out.println("Post logon cookies:");
//        cookies = httpclient.getCookieStore().getCookies();
//        if (cookies.isEmpty()) {
//            System.out.println("None");
//        } else {
//            for (int i = 0; i < cookies.size(); i++) {
//                System.out.println("- " + cookies.get(i).toString());
//            }
//        }

        // When HttpClient instance is no longer needed, 
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();        
    }
}
