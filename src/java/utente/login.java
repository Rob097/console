/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utente;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static utente.EncryptDecryptString.ATT_NAME;
import static utente.EncryptDecryptString.encrypt;
import static varie.costanti.CART_COOKIE_MAX_AGE;
import static varie.costanti.PASS;
import static varie.costanti.USER;

/**
 *
 * @author Roberto97
 */
public class login extends HttpServlet {

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
        //request.setCharacterEncoding("UTF-8");

        String email = "", password = "";
        Boolean ricordami = false;

        if (request.getParameter("email") != null) {
            email = request.getParameter("email");
        }
        if (request.getParameter("password") != null) {
            password = request.getParameter("password");
        }
        if (request.getParameter("ricordami") != null) {
            ricordami = true;
        }
        
        if (email.equals(USER) && password.equals(PASS)) {

            request.getSession().setAttribute(encrypt(ATT_NAME), encrypt(PASS));

            if (ricordami == true) {
                
                Cookie logged = new Cookie(encrypt(ATT_NAME), encrypt(PASS));
                logged.setPath(request.getContextPath());
                logged.setMaxAge(CART_COOKIE_MAX_AGE);
                response.addCookie(logged);
            }
            response.sendRedirect("dashboard.jsp");

        }else{   
            request.getSession().setAttribute(ATT_NAME, false);
            response.sendRedirect("login.jsp");            
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
        //request.setCharacterEncoding("UTF-8");
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
        //request.setCharacterEncoding("UTF-8");
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
