/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.beans;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matta
 */
public class Data extends DataBase {
    
    public static final String INPUT_DIR = "input";
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

    @Override
    public String[] getKeys() {
        ArrayList<String> keys = new ArrayList<>(Arrays.asList(super.getKeys()));
        Field[] fields = Data.class.getFields();
        for(Field f : fields) {
            int modifiers = f.getModifiers();
            if((modifiers & (Modifier.STATIC | Modifier.FINAL)) ==
                    (Modifier.STATIC | Modifier.FINAL)) {
                try {
                    keys.add(f.get(null).toString());
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return keys.toArray(new String[]{});
    }
}
