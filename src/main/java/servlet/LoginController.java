/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Customer;
import model.DAO;
import model.DAOException;
import model.DataSourceFactory;

/**
 *
 * @author Ehsan
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String msg = "";
        boolean isAdmin = false;
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        if (user.equals("admin") && pass.equals("admin")) {
            msg = "Bonjour Admin";
            isAdmin = true;
        } else {
            try {
                int id = 0;
                DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.embedded));
                try {
                    id = Integer.parseInt(pass);
                } catch (NumberFormatException ex) {
                    id = -1;
                    msg = ex.getMessage();
                    Logger.getLogger("LoginController").log(Level.SEVERE, "Action en erreur", ex);
                }
                try {
                    Customer customer = dao.Login(user, id);
                    msg = String.format("Bonjour %s", customer.GetName());
                    request.setAttribute("customerID", customer.GetID());
                } catch (NullPointerException ex) {
                    msg = ex.getMessage();
                    Logger.getLogger("LoginController").log(Level.SEVERE, "Action en erreur", ex);
                }

            } catch (DAOException ex) {
                msg = ex.getMessage();
                System.out.println(ex.getMessage());
                Logger.getLogger("LoginController").log(Level.SEVERE, "Action en erreur", ex);

            }
        }

        request.setAttribute("message", msg);

        if (isAdmin) {
            //Send to admin jsp
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } else {
            //Send to client jsp
            request.getRequestDispatcher("customer.jsp").forward(request, response);
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
