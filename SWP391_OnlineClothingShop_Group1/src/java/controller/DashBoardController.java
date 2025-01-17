/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DBContext.OrderDAO;
import DBContext.ProductDAO;
import DBContext.UserDAO;
import entity.Order;
import entity.Product;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author BEAN
 */
//@WebServlet(name = "DashboardController", urlPatterns = {"/DashBoard"})
public class DashBoardController extends HttpServlet {

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
        HttpSession ss = request.getSession();
        UserDAO udao = new UserDAO();
        ProductDAO pdao = new ProductDAO();
        OrderDAO odao = new OrderDAO();
        ArrayList<Order> olist = new ArrayList<>();
        try {
            Users u = (Users) ss.getAttribute("user");
            String role = udao.getRoleByUserName(u.getUserName());
            if (role.equals("Admin")) {
                olist = odao.getAllOrders();

            } else if (role.equals("seller")) {
                ArrayList<Product> plist = pdao.getProductBySellerName(u.getUserName());
                olist = odao.getOdByListProduct(plist);

            } else {
                response.sendRedirect("home");

            }
            request.setAttribute("totalCus", udao.getTotalUser());
            request.setAttribute("totalPro", pdao.getTotalProduct());
            request.setAttribute("totalOrders", odao.getTotalOrders());
            request.setAttribute("olist", olist);
            request.getRequestDispatcher("Dashboard.jsp").forward(request, response);
        } catch (Exception e) {
        }
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
