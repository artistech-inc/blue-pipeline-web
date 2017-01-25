/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElisaClient {

    private final static Logger LOGGER = Logger.getLogger(ElisaClient.class.getName());

    private final String uri;

    public ElisaClient(String base) {
        uri = base;
    }

    public String ie(String content, String lang, String model, String output_format) {
        String ret = "";
        try {

            URL url = new URL(uri + "/supported_language/" + lang + "/ie?model=" + model + "&output_format=" + output_format);
//            URL url = new URL(uri + "/supported_language/en/ie?model=DNN&output_format=KnowledgeGraph");
            HttpPostUtil post = new HttpPostUtil(url);
            HashMap<String, String> nvc = new HashMap<>();
            nvc.put("text", content);
            ret = post.HttpPOSTMultipart(nvc, true);
            LOGGER.log(Level.FINER, ret);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ElisaClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    private String get(String fn) {
        try {

            URL url = new URL(uri + "/" + fn);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                LOGGER.log(Level.FINER, output);
                sb.append(output).append(System.lineSeparator());
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, null, e);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, null, e);
        }
        return "";
    }

    public String getStatus() {
        return get("status");
    }

    public String getSupportedLanguage() {
        return get("supported_language/");
    }

    public String getModel(String modelId) {
        return get("supported_language/" + modelId);
    }

//    public static void main(String[] args) {
//        ElisaClient ec = new ElisaClient();
//        String ret = ec.extract("Japan began the defence of their Asian Cup title with a lucky 2-1 win against Syria in a Group C championship match on Friday .");
//        System.out.println(ret);
//    }
}
