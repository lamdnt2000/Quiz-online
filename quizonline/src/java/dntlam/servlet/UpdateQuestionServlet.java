package dntlam.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import dntlam.responsestatus.ResponseMessage;
import dntlam.tblChoice.TblChoiceDAO;
import dntlam.tblChoice.TblChoiceDTO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquestion.TblQuestionDTO;
import dntlam.utiles.DBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
@WebServlet(urlPatterns = {"/admin/UpdateQuestionServlet"})
public class UpdateQuestionServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String questionId = request.getParameter("questionId");
        String questionTitle = request.getParameter("txtQuestionTitle");
        String subjectId = request.getParameter("subjectId");
        String answer = request.getParameter("answer");
        String questionStatus = request.getParameter("status");
        String option1 = request.getParameter("txtOption1");
        String option2 = request.getParameter("txtOption2");
        String option3 = request.getParameter("txtOption3");
        String option4 = request.getParameter("txtOption4");
        List<String> options = new ArrayList<>(Arrays.asList(option1, option2, option3, option4));

        int correctAns = 1;
        int id = 0;
        int questStatus = "on".equals(questionStatus)?1:0;
        int status = 0;
        String msg = "Update question fail";
        boolean flag = true;
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                id = Integer.parseInt(questionId);
                correctAns = Integer.parseInt(answer);
            } catch (NumberFormatException ex) {
                log("UpdateQuestionServlet_NumberFormatException:" + ex.getMessage());
                flag = false;
            }
            String email = DBHelper.getCurrentUser(request);
            if (flag) {
                TblQuestionDAO questDao = new TblQuestionDAO();
                TblQuestionDTO questDto = new TblQuestionDTO(id, questionTitle, subjectId, correctAns, questStatus, null, null, null, email);
                boolean result = questDao.updateQuestion(questDto);
                if (result) {
                    TblChoiceDAO choiceDao = new TblChoiceDAO();
                    for (int i = 0; i < 4; i++) {
                        TblChoiceDTO choiceDto = new TblChoiceDTO(questDto.getId(), i + 1, options.get(i));
                        choiceDao.updateChoice(choiceDto);
                    }
                    status = 1;
                    msg = "Update question success";

                }
            }

        } catch (SQLException ex) {
            log("UpdateQuestionServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateQuestionServlet_NamingException:" + ex.getMessage());
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
