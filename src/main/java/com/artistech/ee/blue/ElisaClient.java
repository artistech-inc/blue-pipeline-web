/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.blue;

import com.artistech.utils.HttpPostUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Web client for accessing the ELISA service.
 *
 * @author matta
 */
public class ElisaClient {

    private final static Logger LOGGER = Logger.getLogger(ElisaClient.class.getName());

    private final String uri;

    /**
     * Constructor.
     *
     * @param base
     */
    public ElisaClient(String base) {
        uri = base;
    }

    /**
     * Extract data from a string.
     *
     * @param content
     * @param lang
     * @param model
     * @param output_format
     * @return
     */
    public String ie(String content, String lang, String model, String output_format) {
        String ret = "";
        try {

            URL url = new URL(uri + "/supported_language/" + lang + "/ie?model=" + model + "&output_format=" + output_format);
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

    /**
     * Function for a 'get' command.
     *
     * @param fn
     * @return
     */
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

    /**
     * Get the status.
     *
     * @return
     */
    public String getStatus() {
        return get("status");
    }

    /**
     * Get supported languages.
     *
     * @return
     */
    public String getSupportedLanguage() {
        return get("supported_language/");
    }

    /**
     * Get the model.
     *
     * @param modelId
     * @return
     */
    public String getModel(String modelId) {
        return get("supported_language/" + modelId);
    }
}
