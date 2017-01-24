/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.web;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Used in tomcat and jetty.
 * 
 * This class used to manage pipeline builders.
 *
 * @author matta
 */
public class DataManager {

    private static final TreeMap<String, Data> DATAS = new TreeMap<>();
    private static String dataPath;

    private String pipeline_id;

    public static void setDataPath(String value) {
        dataPath = value;
    }

    public static String getDataPath() {
        return dataPath;
    }
    
    public synchronized String[] getStoredData() {
        ArrayList<Data> ret = new ArrayList<>(DATAS.values());
        ArrayList<String> stored = new ArrayList<>();
        File f = new File(getDataPath());
        if (f.exists()) {
            for(File file : f.listFiles()) {
                String id = file.getName();
                stored.add(id);
            }
        }
        for(Data data : ret) {
            if(stored.contains(data.getKey())) {
                stored.remove(data.getKey());
            }
        }
        for(String key : stored) {
            Data d = new Data(key);
            d.setPipelineDir(getDataPath());
            ret.add(d);
            DATAS.put(key, d);
        }
        ArrayList<String> ids = new ArrayList<>();
        for(Data data : ret) {
            ids.add(data.getKey());
        }
        return ids.toArray(new String[]{});
    }

    /**
     * Get all registered Data objects.
     * @return 
     */    
    public synchronized ArrayList<Data> getAllData() {
        ArrayList<Data> ret = new ArrayList<>(DATAS.values());
        return ret;
    }
    
    /**
     * Get the Data object with the specified id.
     * @param id
     * @return 
     */
    public static synchronized Data getData(String id) {
        if (id != null && DATAS.containsKey(id)) {
            Data data = DATAS.get(id);
            data.updateLastUse();
            return data;
        } else {
            return null;
        }
    }

    /**
     * Set the Data object with the specified id.
     * @param id
     * @param data 
     */
    public static synchronized void setData(String id, Data data) {
        if (id != null) {
            DATAS.put(id, data);
        }
    }

    /**
     * Check if the Data with the specified object exists.
     * @param id
     * @return 
     */
    public static synchronized boolean containsData(String id) {
        return id != null ? DATAS.containsKey(id) : false;

    }

    /**
     * Get the algolink id.
     * @return 
     */
    public String getPipeline_id() {
        return pipeline_id;
    }

    /**
     * Set the algolink id.
     * @param value 
     */
    public void setPipeline_id(String value) {
        pipeline_id = value;
    }

    /**
     * Get the Data associated with the current algolink id.
     * @return 
     */
    public Data getData() {
        if (pipeline_id != null && DataManager.containsData(pipeline_id)) {
            DataManager.getData(pipeline_id).updateLastUse();
            return DataManager.getData(pipeline_id);
        } else {
            return null;
        }
    }

    /**
     * Set the Data with the current algolink id.
     * @param value 
     */
    public void setData(Data value) {
        if (pipeline_id != null) {
            setData(pipeline_id, value);
        }
    }

    /**
     * Remove Data with the specified id.
     * @param id 
     */
    public static void removeData(String id) {
        DATAS.remove(id);
    }
}
