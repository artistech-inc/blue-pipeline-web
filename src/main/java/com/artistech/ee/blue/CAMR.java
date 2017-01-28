/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.blue;

import com.artistech.ee.beans.Data;
import com.artistech.ee.beans.DataManager;
import com.artistech.utils.ExternalProcess;
import com.artistech.utils.StreamGobbler;
import java.io.File;
import java.io.IOException;
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
        String camr_path = getInitParameter("path");

        Part pipeline_id_part = request.getPart("pipeline_id");
        String pipeline_id = IOUtils.toString(pipeline_id_part.getInputStream(), "UTF-8");
        Data data = DataManager.getData(pipeline_id);
        String input_directory = data.getInput();
        File f = new File(input_directory);
        String camr_out = data.getCamrOut();
//        data.setCamrOut(camr_out);
        File output_dir = new File(camr_out);
        FileUtils.copyDirectory(new File(input_directory), output_dir);
//        output_dir.mkdirs();

        File[] copied_input_files = output_dir.listFiles();
        //TODO: this should not be in a for loop
        for (File txt : copied_input_files) {
            //TODO: need to know "$FILE_LIST", "$INPUT_SGM", "$ENIE_OUTP"
            ProcessBuilder pb = new ProcessBuilder("./tok-pipeline.sh", txt.getAbsolutePath());
            pb.directory(new File(camr_path));
            //catch output...
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            StreamGobbler sg = new StreamGobbler(proc.getInputStream());
            sg.start();
            ExternalProcess ex_proc = new ExternalProcess(sg, proc);
            data.setProc(ex_proc);
//            try {
//                proc.waitFor();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(CAMR.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
//        Part part = request.getPart("step");
//        String target = IOUtils.toString(part.getInputStream(), "UTF-8");

        // displays done.jsp page after upload finished
        data.setPipelineIndex(data.getPipelineIndex() + 1);
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
        return "Short description";
    }// </editor-fold>

}
