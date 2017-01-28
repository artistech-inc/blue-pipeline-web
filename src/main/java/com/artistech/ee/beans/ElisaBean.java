/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artistech.ee.beans;

/**
 *
 * @author matta
 */
public class ElisaBean {

    public String[] getModels() {
        return new String[]{
//            "ExpectD",
//            "CRF",
            "DNN"};
    }

    public String[] getOutputFormats() {
        return new String[]{"KnowledgeGraph", "EvalTab"};
    }
}
