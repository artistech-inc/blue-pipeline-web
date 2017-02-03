/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.blue;

import com.artistech.ee.beans.Data;
import com.artistech.ee.beans.DataManager;
import com.artistech.utils.ExternalProcess;
import com.artistech.utils.StreamGobbler;
import com.artistech.utils.StreamGobblerWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
 * Run the CAMR/TOK step.
 *
 * @author matta
 */
public class CAMR extends HttpServlet {

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
        final String camr_path = getInitParameter("path");

        Part pipeline_id_part = request.getPart("pipeline_id");
        String pipeline_id = IOUtils.toString(pipeline_id_part.getInputStream(), "UTF-8");
        final Data data = (Data) DataManager.getData(pipeline_id);
        String input_directory = data.getInput();
        String camr_out = data.getCamrOut();
        File output_dir = new File(camr_out);
        FileUtils.copyDirectory(new File(input_directory), output_dir);

        final File[] copied_input_files = output_dir.listFiles();

//        PipedInputStream in = new PipedInputStream();
//        final PipedOutputStream out = new PipedOutputStream(in);
        final StreamGobblerWrapper sg = new StreamGobblerWrapper(null);
        sg.start();

//        final OutputStreamWriter bos = new OutputStreamWriter(out);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (File txt : copied_input_files) {
                    try {
                        ProcessBuilder pb = new ProcessBuilder("./tok-pipeline.sh", txt.getAbsolutePath());
                        pb.directory(new File(camr_path));
                        //catch output...
                        pb.redirectErrorStream(true);
                        Process proc = pb.start();
                        OutputStream os = new FileOutputStream(new File(data.getConsoleFile()), true);
                        StreamGobbler sg2 = new StreamGobbler(proc.getInputStream(), os);
                        sg2.start();
                        sg.setWrapped(sg2);
                        try {
                            proc.waitFor();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CAMR.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        bos.write(sg2.getUpdateText());
//                        bos.write(System.lineSeparator());
//                        bos.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(CAMR.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Run TOK Step";
    }// </editor-fold>

}
