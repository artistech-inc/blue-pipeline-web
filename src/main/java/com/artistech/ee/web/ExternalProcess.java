/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.web;

/**
 *
 * @author matta
 */
public class ExternalProcess {

    private final StreamGobbler sg;
    private final Process proc;

    public ExternalProcess(StreamGobbler sg, Process proc) {
        this.sg = sg;
        this.proc = proc;
    }

    public Process getProcess() {
        return proc;
    }

    public StreamGobbler getGobbler() {
        return sg;
    }
}
