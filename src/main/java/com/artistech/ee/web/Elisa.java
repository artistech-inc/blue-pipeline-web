/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author matta
 */
public class Elisa extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String url = "supported_language/en/ie?model=DNN&output_format=KnowledgeGraph";
        String url = getInitParameter("url");
        final String lang = getInitParameter("lang");

        Part pipeline_id_part = request.getPart("pipeline_id");
        String pipeline_id = IOUtils.toString(pipeline_id_part.getInputStream(), "UTF-8");
        Part output_format_part = request.getPart("outputFormat");
        final String output_format = IOUtils.toString(output_format_part.getInputStream(), "UTF-8");
        Part model_part = request.getPart("model");
        final String model = IOUtils.toString(model_part.getInputStream(), "UTF-8");
        Data data = DataManager.getData(pipeline_id);
        String[] input_files = data.getCamrFiles();
        String tok = null;
        for (String file : input_files) {
            if (file.endsWith(".tok")) {
                tok = file;
                break;
            }
        }
        if (tok != null) {
            final ElisaClient ec = new ElisaClient(url);
            File f = new File(data.getCamrOut() + File.separator + tok);
            final List<String> readLines = FileUtils.readLines(f, "UTF-8");

            PipedInputStream in = new PipedInputStream();
            final PipedOutputStream out = new PipedOutputStream(in);
            StreamGobbler sg = new StreamGobbler(in);
            sg.start();

            final OutputStreamWriter bos = new OutputStreamWriter(out);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String line : readLines) {
                        try {
                            String res = ec.ie(line, lang, model, output_format);
                            bos.write(res + System.lineSeparator());
                            bos.flush();
                        } catch (IOException ex) {
                            Logger.getLogger(Elisa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            t.start();
            ExternalProcess ex_proc = new ExternalProcess(sg, t);
            data.setProc(ex_proc);

            getServletContext().getRequestDispatcher("/watchProcess.jsp").forward(
                    request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
