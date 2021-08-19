  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import dntlam.tblExam.TblExamDAO;
import dntlam.tblExam.TblExamDTO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquiz.TblQuizDAO;
import dntlam.tblquiz.TblQuizDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "EndQuizServlet", urlPatterns = {"/EndQuizServlet"})
public class EndQuizServlet extends HttpServlet {

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
        String pk = request.getParameter("id");
        String[] quizId = request.getParameterValues("quizId");
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < quizId.length; i++) {
            String ans = request.getParameter(quizId[i]);
            if (ans != null) {
                answer.add(request.getParameter(quizId[i]));
            } else {
                answer.add("0");
            }
        }
        Map<Integer, Integer> result = new HashMap<>();
        int quizPk = 0;
        boolean flag = true;
        String url = "error";
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                quizPk = Integer.parseInt(pk);
                for (int i = 0; i < quizId.length; i++) {
                    int id = Integer.parseInt(quizId[i]);
                    int ans = Integer.parseInt(answer.get(i));
                    result.put(id, ans);
                }
            } catch (NumberFormatException ex) {
                log("EndQuizServlet_NumberFormatException: " + ex.getMessage());
                flag = false;
            }
            if (flag) {
                TblExamDAO examDAO = new TblExamDAO();
                TblExamDTO examDTO = examDAO.findExamByQuizId(quizPk);
                if (examDTO != null) {
                    int totalQuest = examDTO.getNumQuest();
                    TblQuestionDAO questDAO = new TblQuestionDAO();
                    questDAO.getResultFromIds(result.keySet());
                    Map<Integer,Integer> correctList = questDAO.getListCorrectAns();
                    int countCorrectAns = 0;
                    for (Integer id : result.keySet()) {
                        if (result.get(id)==correctList.get(id)) {
                            countCorrectAns++;
                        }
                        
                    }

                    float total = (float)(Math.round((float)countCorrectAns/totalQuest*1000))/1000;
                    TblQuizDAO dao = new TblQuizDAO();
                    TblQuizDTO dto = new TblQuizDTO();
                    dto.setId(quizPk);
                    dto.setStatus(1);
                    dto.setTotal(total*10);
                    boolean check = dao.updateQuiz(dto);
                    if (check) {
                        HttpSession session = request.getSession(false);
                        session.removeAttribute("QUIZDOING");
                        url = "ShowHistory";
                    }
                }

            }
        } catch (NamingException ex) {
            log("EndQuizServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("EndQuizServlet_SQLException: " + ex.getMessage());
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
