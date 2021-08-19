/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.api;

import com.google.gson.Gson;
import dntlam.tblmember.TblMemberDTO;
import dntlam.tblquiz.TblQuizDAO;
import dntlam.tblquiz.TblQuizDTO;
import dntlam.utiles.AccessUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "GetDataForQuiz", urlPatterns = {"/api/GetDataForQuiz"})
public class GetDataForQuiz extends HttpServlet {

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
        String examId = request.getParameter("quizId");
        String json = "{}";
        int id = 0;
        boolean flag = true;
        try {
            boolean access = AccessUtils.checkAccessRole(request, AccessUtils.STUDENT_ROLE);
            if (access) {
                try {
                    id = Integer.parseInt(examId);
                } catch (NumberFormatException ex) {
                    log("GetDataForQuiz_NamingException:" + ex.getMessage());
                    flag = false;
                }
                if (flag) {
                    HttpSession session = request.getSession(false);
                    TblMemberDTO member = (TblMemberDTO) session.getAttribute("RESULTLOGIN");
                    TblQuizDAO dao = new TblQuizDAO();
                    
                    TblQuizDTO dto = dao.findQuizById(id,member.getEmail(), 0);
                    if (dto != null) {
                        json = new Gson().toJson(dto);
                    }
                    out.write(json);
                }
            }
        } catch (NamingException ex) {
            log("GetDataForQuiz_NamingException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("GetDataForQuiz_SQLException:" + ex.getMessage());
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
