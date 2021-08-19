/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.api;

import com.google.gson.Gson;
import dntlam.tblChoice.TblChoiceDAO;
import dntlam.tblChoice.TblChoiceDTO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquestion.TblQuestionDTO;
import dntlam.utiles.DBHelper;
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
@WebServlet(name = "ShowQuestionByQuizId", urlPatterns = {"/api/ShowQuestionByQuizId"})
public class ShowQuestionByQuizId extends HttpServlet {

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
        String quizId = request.getParameter("quizId");
        String page = request.getParameter("page");
        int id = 0;
        boolean flag = true;
        int currentPage = 1;
        String json = "{}";
        try {
            try {
                id = Integer.parseInt(quizId);
                currentPage = Integer.parseInt(page);
            } catch (NumberFormatException ex) {
                log("ShowQuestionByQuizId_NumberFormatException: " + ex.getMessage());
                flag = false;
            }
            if (flag) {
                
                TblQuestionDAO dao = new TblQuestionDAO();
                dao.findAllQuestionByExamId(id,currentPage);
                List<TblQuestionDTO> listQuestion = dao.getListQuestion();
                
                for (TblQuestionDTO dto : listQuestion){
                    TblChoiceDAO choiceDAO = new TblChoiceDAO();
                    choiceDAO.findChoiceById(dto.getId());
                    List<TblChoiceDTO> listChoice = choiceDAO.getListChoice();
                    dto.setListChoice(listChoice);
                }
                json = new Gson().toJson(listQuestion);
                out.println(json);
            }
            /* TODO output your page here. You may use following sample code. */
        } catch (NamingException ex) {
            log("ShowQuestionByQuizId_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("ShowQuestionByQuizId_SQLException: " + ex.getMessage());
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
