/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.beans;

import java.io.File;

/**
 * Data bean class for the blue pipeline.
 *
 * @author matta
 */
public class Data extends DataBase {

    public static final String CAMR_DATA_DIR = "camr_out";
    public static final String ELISA_DIR = "elisa_out";

    /**
     * Constructor.
     *
     * @param key
     */
    public Data(String key) {
        super(key);
    }

    /**
     * Get the CAMR output directory.
     *
     * @return
     */
    public String getCamrOut() {
        return getPipelineDir() + File.separator + CAMR_DATA_DIR;
    }

    /**
     * Get the CAMR output files.
     *
     * @return
     */
    public String[] getCamrFiles() {
        File dir = new File(getCamrOut());
        if (dir.exists()) {
            return dir.list();
        }
        return new String[]{};
    }

    /**
     * Get the ELISA output directory.
     *
     * @return
     */
    public String getElisa() {
        return getPipelineDir() + File.separator + ELISA_DIR;
    }

    /**
     * Get the ELISA output files.
     * @return 
     */
    public String[] getElisaFiles() {
        File f = new File(getPipelineDir());
        if (f.exists()) {
            return f.list();
        }
        return new String[]{};
    }
}
