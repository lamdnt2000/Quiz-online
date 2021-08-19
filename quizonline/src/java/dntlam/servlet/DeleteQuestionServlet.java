/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import com.google.gson.Gson;
import dntlam.tblExam.TblExamDAO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.utiles.AccessUtils;
import static dntlam.utiles.AccessUtils.ADMIN_ROLE;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Hashtable;
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
@WebServlet(name = "DeleteQuestionServlet", urlPatterns = {"/admin/DeleteQuestionServlet"})
public class DeleteQuestionServlet extends HttpServlet {

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
        String[] listDelete = request.getParameterValues("questionId[]");
        String subjectId = request.getParameter("subjectId");
        int[] ids = new int[10];
        boolean flag = true;
        int statusDelete = 0;
        String msg = "Delete question fail";

        try {
            /* TODO output your page here. You may use following sample code. */
            boolean access = AccessUtils.checkAccessRole(request, ADMIN_ROLE);
            if (access) {
                try {
                    if (listDelete == null) {
                        msg = "Select at least one question to delete";
                        flag = false;
                    } else {
                        for (int i = 0; i < listDelete.length; i++) {
                            ids[i] = Integer.parseInt(listDelete[i]);
                        }
                    }
                } catch (NumberFormatException ex) {
                    log("DeleteQuestionServlet_NumberFormatException: " + ex.getMessage());
                    flag = false;
                }
                if (flag) {
                    TblExamDAO examDAO = new TblExamDAO();
                    if (examDAO.countMaxNumQuest(subjectId) <= listDelete.length) {
                        msg = "The question left not enough for exam";
                    } else {
                        TblQuestionDAO dao = new TblQuestionDAO();
                        boolean result = dao.deleteQuestion(ids, subjectId);
                        if (result) {
                            statusDelete = 1;
                            msg = "Delete success";
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log("DeleteQuestionServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("DeleteQuestionServlet_NamingException: " + ex.getMessage());
        } finally {
            Hashtable hash = new Hashtable<>();
            hash.put("status", statusDelete);
            hash.put("message", msg);
            String json = new Gson().toJson(hash);
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
