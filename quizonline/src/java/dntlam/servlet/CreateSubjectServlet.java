/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import com.google.gson.Gson;
import dntlam.responsestatus.ResponseMessage;
import dntlam.tblsubject.TblSubjectDAO;
import dntlam.tblsubject.TblSubjectDTO;
import dntlam.utiles.AccessUtils;
import static dntlam.utiles.AccessUtils.ADMIN_ROLE;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CreateSubjectServlet", urlPatterns = {"/admin/CreateSubjectServlet"})
public class CreateSubjectServlet extends HttpServlet {

    final String LABEL_URL = "CreateSubject";

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
        String subjectId = request.getParameter("txtSubjectId");
        String subjectName = request.getParameter("txtSubjectName");
        String message = "Create new subject fail";
        int status = 0;

        try {
            /* TODO output your page here. You may use following sample code. */
            boolean access = AccessUtils.checkAccessRole(request, ADMIN_ROLE);
            if (access) {
                TblSubjectDAO dao = new TblSubjectDAO();
                TblSubjectDTO dto = new TblSubjectDTO(subjectId, subjectName, null);
                boolean result = dao.createSubject(dto);

                if (result) {
                    status = 1;
                    message = "Create new subject success";
                }
            }
        } catch (SQLException ex) {
            log("CreateSubjectServlet_SQLException:" + ex.getMessage());
            if (ex.getMessage().contains("duplicate")) {
                message = "Duplicated subject id try again.";
            }
        } catch (NamingException ex) {
            log("CreateSubjectServlet_NamingException:" + ex.getMessage());

        } finally {
            ResponseMessage resMessage = new ResponseMessage(status, message);
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
