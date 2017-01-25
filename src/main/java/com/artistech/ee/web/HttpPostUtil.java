/*
 * Copyright 2015 ArtisTech, Inc.
 */
package com.artistech.ee.web;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author matta
 */
public class HttpPostUtil {

    private java.net.URI uri;

    public HttpPostUtil(java.net.URL url) {
        try {
            uri = new java.net.URI(url.toString());
        } catch (URISyntaxException ex) {
            Logger.getLogger(HttpPostUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String HttpPOSTMultipart(HashMap<String, String> nvc, boolean getResponse) {
        String retVal = "";
        try {
            //http://stackoverflow.com/questions/1378920/how-can-i-make-a-multipart-form-data-post-request-using-java
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(uri);

            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
            for (String key : nvc.keySet()) {
                reqEntity.addPart(key, new StringBody(nvc.get(key), ContentType.TEXT_PLAIN));
            }
            httppost.setEntity(reqEntity.build());

            HttpResponse response = httpclient.execute(httppost);
            if (getResponse) {
                HttpEntity resEntity = response.getEntity();
                StringWriter writer = new StringWriter();
                org.apache.commons.io.IOUtils.copy(resEntity.getContent(), writer, "utf-8");
                retVal = writer.toString();
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpPostUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
}
