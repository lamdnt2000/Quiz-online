/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import com.google.gson.Gson;
import dntlam.responsestatus.ResponseMessage;
import dntlam.tblExam.TblExamDAO;
import dntlam.tblExam.TblExamDTO;
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
@WebServlet(name = "CreateExamServlet", urlPatterns = {"/admin/CreateExamServlet"})
public class CreateExamServlet extends HttpServlet {

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
        String examName = request.getParameter("txtExamName");
        String subjectId = request.getParameter("subjectId");
        String timeDoExam = request.getParameter("timeDoExam");
        String numQuest = request.getParameter("numQuest");
        int time = 0;
        int numberQuestion = 0;
        int status = 0;
        String msg = "Create new Exam fail";
        boolean flag = true;
        try {
            boolean access = AccessUtils.checkAccessRole(request, ADMIN_ROLE);
            /* TODO output your page here. You may use following sample code. */
            if (access) {
                try {
                    numberQuestion = Integer.parseInt(numQuest);
                    time = Integer.parseInt(timeDoExam);
                } catch (NumberFormatException ex) {
                    log("CreateExamServlet_NumberFormatException:" + ex.getMessage());
                    flag = false;
                }
                if (flag) {
                    TblExamDAO dao = new TblExamDAO();
                    TblExamDTO dto = new TblExamDTO(0, null, time, numberQuestion, subjectId, examName);
                    dto = dao.createExam(dto);
                    if (dto != null) {
                        status = 1;
                        msg = "Create new Exam success";
                    }
                }
            }
        } catch (SQLException ex) {
            log("CreateExamServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateExamServlet_NamingException:" + ex.getMessage());
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
