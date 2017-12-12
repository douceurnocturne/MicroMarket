/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import model.Order;
import model.Product;

/**
 *
 * @author Ehsan
 */
@WebServlet(name = "UpdateOrder", urlPatterns = {"/UpdateOrder"})
public class UpdateOrder extends HttpServlet {

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
        
        DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.server));
        String message = "";
        int ordernumber = 0;
        int productid = 0 ;
        Customer c;
        Order order;
        String action = request.getParameter("action");
        String ornumber = request.getParameter("ordernumber");
        action = (action == null) ? "" : action;
        c = (Customer) request.getSession().getAttribute("user");
        List<String> result = dao.getProductCodes();
        request.setAttribute("productCodeslist", result);
        try {
            ordernumber = Integer.parseInt(ornumber);
            switch (action) {
                case "get":

                    order = dao.getCustomerOrderByid(c, ordernumber);
                    request.setAttribute("order", order);
                    break;

                case "update":
                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        int quantity = Integer.parseInt(request.getParameter("quantity"));
                        float shipping_cost = Float.parseFloat(request.getParameter("shipping_cost"));
                        java.sql.Date sale_date = new java.sql.Date(format.parse(request.getParameter("sale_date")).getTime());
                        java.sql.Date shipping_date = new java.sql.Date(format.parse(request.getParameter("shipping_date")).getTime());
                        String freight = request.getParameter("freight");
                        String proid = request.getParameter("productid");
                        productid = Integer.parseInt(proid);
                        Product p = dao.getProductByid(productid);

                        order = new Order(ordernumber, c, p, quantity, shipping_cost,
                                sale_date, shipping_date, freight);
                        dao.UpdateOrder(order);
                        message = "Your order has been successfully updated";
                        break;
                    } catch (ParseException ex) {
                        message = ex.getMessage();
                    }
            }
        } catch (NumberFormatException ex) {
            message = "We are sorry there was a probleme with the order: "+ex.getMessage();
        } catch (DAOException ex) {
            message = "We are sorry there was a probleme with the order: "+ex.getMessage();
            Logger.getLogger(UpdateOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("UpdateOrder.jsp").forward(request, response);

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
