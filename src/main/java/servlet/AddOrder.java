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
@WebServlet(name = "AddOrder", urlPatterns = {"/AddOrder"})
public class AddOrder extends HttpServlet {

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

        DAO dao = new DAO(DataSourceFactory.getDataSource());
        if (request.getParameter("ordernumber_input") != null) {
            try {

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                int ordernumber = Integer.parseInt(request.getParameter("ordernumber_input"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                float shipping_cost = Float.parseFloat(request.getParameter("shipping_cost"));
                java.sql.Date sale_date = new java.sql.Date(format.parse(request.getParameter("sale_date")).getTime());
                java.sql.Date shipping_date = new java.sql.Date(format.parse(request.getParameter("shipping_date")).getTime());
                String freight = request.getParameter("freight");
                Customer c;
                c = (Customer) request.getSession().getAttribute("user");
                int productid = Integer.parseInt(request.getParameter("product"));
                Product p =dao.getProductByid(productid);

                Order order = new Order(ordernumber, c, p, quantity, shipping_cost,
                        sale_date,shipping_date, freight);
                
                int res = dao.AddOrder(order);
                if (res == 1) {
                    request.setAttribute("message", "Done, order Number:"+ordernumber+" has been placed");
                }

            } catch (ParseException ex) {
                Logger.getLogger(AddOrder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DAOException ex) {
                request.setAttribute("message", "Sorry your order couldn't be placed :"+ex.getMessage());
                Logger.getLogger(AddOrder.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        List<String> result = dao.getProductCodes();
        request.setAttribute("productCodeslist", result);
        request.getRequestDispatcher("AddOrder.jsp").forward(request, response);

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
