/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.DataSourceFactory;

/**
 *
 * @author Ehsan
 */
@WebServlet(name = "GraphicsController", urlPatterns = {"/GraphicsController"})
public class GraphicsController extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");

        java.sql.Date start_date = null;
        java.sql.Date end_date = null;
        DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.server));
        String graphic_type = request.getParameter("graphic");
        graphic_type = (graphic_type == null) ? "" : graphic_type;
        String sdate = request.getParameter("start_date");
        String edate = request.getParameter("end_date");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            start_date = new java.sql.Date(format.parse(sdate).getTime());
            end_date = new java.sql.Date(format.parse(edate).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(GraphicsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (graphic_type) {
            case "codes":
                
                try (PrintWriter out = response.getWriter()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    out.println(gson.toJson(dao.GetBenefitsByProductCodesAndDate(start_date, end_date)));
                }
                break;
            case "geo":
                try (PrintWriter out = response.getWriter()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    out.println(gson.toJson(dao.GetBenefitsByState(start_date, end_date)));
                }
                break;
            case "client":
                try (PrintWriter out = response.getWriter()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    out.println(gson.toJson(dao.GetBenefitsByCustomerAndDate(start_date, end_date)));
                }
                break;
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
