/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DeleteForceException;
import dao.PermisosDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.User;

/**
 *
 * @author oscar
 */
@WebServlet(name = "ExampleUsers", urlPatterns = {"/ExampleUsers"})
public class ExampleUsers extends HttpServlet {

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
            throws ServletException, IOException{
        ObjectMapper map = new ObjectMapper();
        UserDAO dao = new UserDAO();
        PermisosDAO pDao = new PermisosDAO();
        
        response.getWriter().print(map.writeValueAsString(dao.getAllUser()));
        
        dao.addUser(new User("temp","temp"));
        
        response.getWriter().print("<br>ADD USER <br>");
        response.getWriter().print(map.writeValueAsString(dao.getAllUser()));
        
        response.getWriter().print("<br> CHECK USER <br>");
        response.getWriter().print(pDao.checkUser(new User("carlos","carlos")));
        
        response.getWriter().print("<br> UPDATE USER <br>");
        response.getWriter().print(dao.updateUser(new User("carlos","carlitos")));
        response.getWriter().print(map.writeValueAsString(dao.getAllUser()));
        
        response.getWriter().print("<br> DEL USER <br>");
        try {
            response.getWriter().print(dao.delUser(new User("carlos","carlitos")));
             
        } catch (DeleteForceException ex) {
            Logger.getLogger(ExampleUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.getWriter().print(map.writeValueAsString(dao.getAllUser()));
        try {
            response.getWriter().print(dao.delUser(new User("juan","")));
             
        } catch (DeleteForceException ex) {
            Logger.getLogger(ExampleUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.getWriter().print("<br> DEL FORCE USER <br>");
            response.getWriter().print(dao.delUserForce(new User("juan","")));
        response.getWriter().print(map.writeValueAsString(dao.getAllUser()));
        
        
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
