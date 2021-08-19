/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import com.google.gson.Gson;
import dntlam.responsestatus.ResponseMessage;
import dntlam.tblChoice.TblChoiceDAO;
import dntlam.tblChoice.TblChoiceDTO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquestion.TblQuestionDTO;
import dntlam.utiles.AccessUtils;
import static dntlam.utiles.AccessUtils.ADMIN_ROLE;
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
@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/admin/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {

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
        String questionTitle = request.getParameter("txtQuestionTitle");
        String subjectId = request.getParameter("subjectId");
        String answer = request.getParameter("answer");
        String option1 = request.getParameter("txtOption1");
        String option2 = request.getParameter("txtOption2");
        String option3 = request.getParameter("txtOption3");
        String option4 = request.getParameter("txtOption4");
        List<String> options = new ArrayList<>(Arrays.asList(option1, option2, option3, option4));

        int correctAns = 1;
        int status = 0;
        String msg = "Create new question fail";
        try {
            boolean access = AccessUtils.checkAccessRole(request, ADMIN_ROLE);
            if (access) {
                try {
                    correctAns = Integer.parseInt(answer);
                } catch (NumberFormatException ex) {
                    log("CreateQuestionServlet_NumberFormatException:" + ex.getMessage());
                }
                String email = DBHelper.getCurrentUser(request);
                TblQuestionDAO questDao = new TblQuestionDAO();
                TblQuestionDTO questDto = new TblQuestionDTO(0, questionTitle, subjectId, correctAns, 1, null, null, email, null);
                questDto = questDao.createQuestion(questDto);
                if (questDto != null) {
                    TblChoiceDAO choiceDao = new TblChoiceDAO();
                    for (int i = 0; i < 4; i++) {
                        TblChoiceDTO choiceDto = new TblChoiceDTO(questDto.getId(), i + 1, options.get(i));
                        choiceDao.createChoice(choiceDto);
                    }
//                flag = true;
                    status = 1;
                    msg = "Create new question success";

                }
            }

        } catch (SQLException ex) {
            log("CreateQuestionServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateQuestionServlet_NamingException:" + ex.getMessage());
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
