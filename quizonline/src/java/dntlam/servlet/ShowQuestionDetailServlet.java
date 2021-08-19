/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.servlet;

import dntlam.tblChoice.TblChoiceDAO;
import dntlam.tblChoice.TblChoiceDTO;
import dntlam.tblquestion.TblQuestionDAO;
import dntlam.tblquestion.TblQuestionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "ShowQuestionDetailServlet", urlPatterns = {"/admin/ShowQuestionDetailServlet"})
public class ShowQuestionDetailServlet extends HttpServlet {

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
        String id = request.getParameter("id");
        
        int questionId = 0;
        String url = "ShowQuestion";
        boolean status = false;
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                questionId = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                log("ShowQuestionDetailServlet_NumberFormatException: " + ex.getMessage());
            }
            TblQuestionDAO questionDao = new TblQuestionDAO();
            TblQuestionDTO question = questionDao.findQuestionById(questionId);
            if (question!=null){
                TblChoiceDAO choiceDao = new TblChoiceDAO();
                choiceDao.findChoiceById(questionId);
                List<TblChoiceDTO> listChoice = choiceDao.getListChoice();
                request.setAttribute("QUESTIONDETAIL", question);
                request.setAttribute("CHOICEDETAIL", listChoice);
                status = true;
                url = "questiondetail.jsp";
            }
        } catch (NamingException ex) {
            log("ShowQuestionDetailServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("ShowQuestionDetailServlet_SQLException: " + ex.getMessage());
        } finally {
            if (status){
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            else{
                response.sendRedirect(url);
            }
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
