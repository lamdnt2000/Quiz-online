/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.api;

import com.google.gson.Gson;
import dntlam.tblquestion.Parameter;
import dntlam.tblquestion.TblQuestionDAO;
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
@WebServlet(name = "GetDataForPaging", urlPatterns = {"/api/GetDataForPaging"})
public class GetDataForPaging extends HttpServlet {

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
        String searchTitle = request.getParameter("txtSearchTitle");
        String subjectId = request.getParameter("subjectId");
        String statusQuestion = request.getParameter("status");

        int status = 1;
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                status = Integer.parseInt(statusQuestion);
            } catch (NumberFormatException ex) {
                log("GetDataForPaging_NumberFormatException: " + ex.getMessage());
            }
            Parameter param = new Parameter(searchTitle, status, subjectId, 0);

            TblQuestionDAO dao = new TblQuestionDAO();
            dao.countQuestion(param);
            int totalQuestion = dao.getCountQuestion();

            Hashtable hash = new Hashtable<>();
            hash.put("title", searchTitle);
            hash.put("subjectId", subjectId);
            hash.put("status", status);
            hash.put("totalQuestion", totalQuestion);
            String json = new Gson().toJson(hash);
            out.println(json);

        } catch (NamingException ex) {
            log("GetDataForPaging_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("GetDataForPaging_SQLException: " + ex.getMessage());
        } finally {
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
