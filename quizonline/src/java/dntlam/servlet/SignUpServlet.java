/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import com.google.gson.Gson;
import dntlam.responsestatus.ResponseMessage;
import dntlam.tblmember.TblMemberDAO;
import dntlam.tblmember.TblMemberDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String fullName = request.getParameter("txtFullName");
        int status = 0;
        String msg = "Create new Member fail";
        try {
            /* TODO output your page here. You may use following sample code. */
            TblMemberDTO dto = new TblMemberDTO(email, password, fullName, "student", "New");
            TblMemberDAO dao = new TblMemberDAO();
            boolean result = dao.createUser(dto);
            if (result) {
                status = 1;
                msg = "Create new Member success";
            }
        } catch (SQLException ex) {
            log("SignUpServlet_SQLException:" + ex.getMessage());
            if (ex.getMessage().contains("duplicate")) {
                msg = "Email already exist try other";
            }
        } catch (NamingException ex) {
            log("SignUpServlet_NamingException:" + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            log("SignUpServlet_NoSuchAlgorithmException:" + ex.getMessage());
        } finally {
            ResponseMessage resMessage = new ResponseMessage(status, msg);
            String json = new Gson().toJson(resMessage);
            out.println(json);
            out.close();
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
