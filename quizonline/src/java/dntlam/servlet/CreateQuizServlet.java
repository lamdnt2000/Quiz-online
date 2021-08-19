/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import dntlam.tblmember.TblMemberDTO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquiz.TblQuizDAO;
import dntlam.tblquiz.TblQuizDTO;
import dntlam.utiles.AccessUtils;
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
import javax.servlet.http.HttpSession;
import tblquestioninquiz.TblQuestionInQuizDAO;
import tblquestioninquiz.TblQuestionInQuizDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "CreateQuizServlet", urlPatterns = {"/CreateQuizServlet"})
public class CreateQuizServlet extends HttpServlet {
    private static final String URL_SHOWEXAM = "ShowExam";
    private static final String URL_DO_QUIZ = "DoQuiz";
    
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
        PrintWriter out = response.getWriter();
        String examId = request.getParameter("examId");
        String subjectId = request.getParameter("subjectId");
        int id = 0;
        boolean flag = true;
        String url = URL_SHOWEXAM+"?subjectId=" + subjectId;
        try {
            boolean access = AccessUtils.checkAccessRole(request, AccessUtils.STUDENT_ROLE);
            if (access) {
                HttpSession session = request.getSession(false);
                Integer currentQuiz = (Integer) session.getAttribute("QUIZDOING");
                if (currentQuiz!=null){
                    url = URL_DO_QUIZ+"?quizId=" + currentQuiz;
                    flag= false;
                }
                try {
                    id = Integer.parseInt(examId);
                } catch (NumberFormatException ex) {
                    log("CreateQuizServlet_NumberFormatException:" + ex.getMessage());
                    flag = false;
                }
                if (flag) {
                    TblMemberDTO member = (TblMemberDTO) session.getAttribute("RESULTLOGIN");
                    TblQuizDAO quizDAO = new TblQuizDAO();
                    TblQuizDTO dto = new TblQuizDTO(0, member.getEmail(), null, id, 0);
                    dto = quizDAO.createQuiz(dto);
                    if (dto != null) {
                        TblQuestionDAO quesDAO = new TblQuestionDAO();
                        quesDAO.findQuestionIdByExamId(id,subjectId,dto.getId());
                        List<TblQuestionInQuizDTO> listQuestionInQuiz = quesDAO.getListQuestionInQuiz();
                        TblQuestionInQuizDAO questInQuizDAO = new TblQuestionInQuizDAO();
                        boolean result = questInQuizDAO.createQuiz(listQuestionInQuiz);
                        if (result) {
                            url = URL_DO_QUIZ+"?quizId=" + dto.getId();
                            session.setAttribute("QUIZDOING",dto.getId());
                        }

                    }

                }
            }
        } catch (SQLException ex) {
            log("CreateQuizServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateQuizServlet_NamingException:" + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
