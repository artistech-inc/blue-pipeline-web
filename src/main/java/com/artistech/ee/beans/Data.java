/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.beans;

import java.io.File;

/**
 *
 * @author matta
 */
public class Data extends DataBase {
    
    public static final String CAMR_DATA_DIR = "camr_out";
    public static final String ELISA_DIR = "elisa_out";

    public Data(String key) {
        super(key);
    }

    public String getCamrOut() {
        return getPipelineDir() + File.separator + CAMR_DATA_DIR;
    }

    public String[] getCamrFiles() {
        File dir = new File(getCamrOut());
        if (dir.exists()) {
            return dir.list();
        }
        return new String[]{};
    }

    public String getElisa() {
        return getPipelineDir() + File.separator + ELISA_DIR;
    }

    public String[] getElisaFiles() {
        File f = new File(getPipelineDir());
        if (f.exists()) {
            return f.list();
        }
        return new String[]{};
    }
}
