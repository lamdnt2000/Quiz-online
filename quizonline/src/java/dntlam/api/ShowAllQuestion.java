/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.api;

import com.google.gson.Gson;
import dntlam.tblquestion.Parameter;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquestion.TblQuestionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "ShowAllQuestion", urlPatterns = {"/api/ShowAllQuestion"})
public class ShowAllQuestion extends HttpServlet {

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
        String page = request.getParameter("page");
        int status = 1;
        int currentPage = 1;
        try {
            try {
                status = Integer.parseInt(statusQuestion);
                currentPage = Integer.parseInt(page);
            } catch (NumberFormatException ex) {
                log("ShowAllQuestion_NumberFormatException: " + ex.getMessage());
            }
            Parameter param = new Parameter(searchTitle, status, subjectId, currentPage);
            TblQuestionDAO dao = new TblQuestionDAO();
            dao.findAllQuestion(param);
            List<TblQuestionDTO> listQuestion = dao.getListQuestion();
            String json = new Gson().toJson(listQuestion);
            out.println(json);
            /* TODO output your page here. You may use following sample code. */
        } catch (NamingException ex) {
            log("ShowAllQuestion_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("ShowAllQuestion_SQLException: " + ex.getMessage());
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
