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
        String login = getInitParameter("login");
        String password = getInitParameter("pass");
        String username = getInitParameter("userName");
        if (user.equals(login) && pass.equals(password)) {
           
            isAdmin = true;
            Customer admin = new Customer(0, username, "--", "--", "--", "--");
            request.getSession().setAttribute("user", admin);
        } else {
            try {
                int id = 0;
                DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.embedded));
                try {
                    id = Integer.parseInt(pass);
                } catch (NumberFormatException ex) {
                    throw new DAOException("Password must be numeric.");
                }

                Customer customer = dao.Login(user, id);
                if (customer != null) {
                    request.getSession().setAttribute("user", customer);

                } else {
                    throw new DAOException("Your account or password is incorrect.");
                }

            } catch (DAOException ex) {
                msg = ex.getMessage();
                request.setAttribute("message", msg);
                request.getRequestDispatcher("errorView.jsp").forward(request, response);
                Logger.getLogger("LoginController").log(Level.SEVERE, "Action en erreur", ex);

            }
        }

        if (isAdmin) {
            //Forward to admin jsp
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } else {
            //Forward to client jsp
            response.sendRedirect(request.getContextPath() + "/customer.jsp");

            //request.getRequestDispatcher("customer.jsp").forward(request, response);
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
