/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblquestion;

import dntlam.utiles.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import tblquestioninquiz.TblQuestionInQuizDTO;

/**
 *
 * @author sasuk
 */
public class TblQuestionDAO implements Serializable {

    final int QUESTION_PER_PAGE = 10;
    private List<TblQuestionDTO> listQuestion;
    private List<TblQuestionInQuizDTO> listQuestionInQuiz;
    private Map<Integer,Integer> listCorrectAns;
    private int countQuestion;
    
    public TblQuestionDTO createQuestion(TblQuestionDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblQuestionDTO result = null;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO Question (questionTitle,subjectId,correctAnswer,status,dateCreate,userCreate) "
                        + "VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, dto.getQuestionTitle());
                stm.setString(2, dto.getSubjectId());
                stm.setInt(3, dto.getCorrectAnswer());
                stm.setInt(4, dto.getStatus());
                Date date = new Date();
                stm.setTimestamp(5, new Timestamp(date.getTime()));
                stm.setString(6, dto.getUserCreate());
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        dto.setId(rs.getInt(1));
                        result = dto;
                    }
                }

            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return result;
    }

    public boolean updateQuestion(TblQuestionDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "UPDATE Question "
                        + "SET questionTitle=?,subjectId=?,correctAnswer=?,status=?,dateUpdate=?,userUpdate=? "
                        + "WHERE id=?";
                stm = con.prepareStatement(query);
                stm.setString(1, dto.getQuestionTitle());
                stm.setString(2, dto.getSubjectId());
                stm.setInt(3, dto.getCorrectAnswer());
                stm.setInt(4, dto.getStatus());
                Date date = new Date();
                stm.setTimestamp(5, new Timestamp(date.getTime()));
                stm.setString(6, dto.getUserUpdate());
                stm.setInt(7, dto.getId());
                int row = stm.executeUpdate();
                if (row > 0) {
                    result = true;
                }

            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return result;
    }

    public void findAllQuestion(Parameter param) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id, questionTitle, subjectId, correctAnswer, dateCreate "
                        + "FROM Question "
                        + "WHERE status = ? "
                        + "AND questionTitle like ? "
                        + "AND subjectId = ? "
                        + "ORDER BY dateCreate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";

                stm = con.prepareStatement(sql);
                int count = 0;
                stm.setInt(1, param.getStatus());
                stm.setString(2, "%" + param.getTitle() + "%");
                stm.setString(3, param.getSubjectId());
                stm.setInt(4, (param.getPage() - 1) * QUESTION_PER_PAGE);
                stm.setInt(5, QUESTION_PER_PAGE);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionId = rs.getInt("id");
                    String questionTitle = rs.getString("questionTitle");
                    String subjectId = rs.getString("subjectId");
                    int correctAnswer = rs.getInt("correctAnswer");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    TblQuestionDTO dto = new TblQuestionDTO(questionId, questionTitle, subjectId, correctAnswer, param.getStatus(), dateCreate, null, null, null);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void countQuestion(Parameter param) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {

                String sql = "SELECT COUNT(id) "
                        + "FROM Question "
                        + "WHERE status = ? "
                        + "AND questionTitle LIKE ? "
                        + "AND subjectId = ? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, param.getStatus());
                stm.setString(2, "%" + param.getTitle() + "%");
                stm.setString(3, param.getSubjectId());
                rs = stm.executeQuery();
                if (rs.next()) {
                    this.countQuestion = rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public TblQuestionDTO findQuestionById(int id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblQuestionDTO result = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id, questionTitle, subjectId, correctAnswer, status "
                        + "FROM Question "
                        + "WHERE id=? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int questionId = rs.getInt("id");
                    String questionTitle = rs.getString("questionTitle");
                    String subjectId = rs.getString("subjectId");
                    int correctAnswer = rs.getInt("correctAnswer");
                    int status = rs.getInt("status");
                    result = new TblQuestionDTO(questionId, questionTitle, subjectId, correctAnswer, status, null, null, null, null);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean deleteQuestion(int[] ids, String subjectId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "UPDATE Question "
                        + "SET status=0 "
                        + "WHERE id IN ids";
                String idList = "";
                for (int i = 0; i < ids.length; i++) {
                    idList += ids[i];
                    if (i < ids.length - 1) {
                        idList += ",";
                    }
                }
                query = query.replace("ids", "(" + idList + ")");
                stm = con.prepareStatement(query);
                int row = stm.executeUpdate();
                if (row > 0) {

                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public void findQuestionIdByExamId(int examId, String subjectID, int quizId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT TOP (SELECT numQuest FROM ExamInSubject WHERE id=?) Q.id, Q.correctAnswer "
                        + "FROM Question Q "
                        + "WHERE Q.subjectId = ? "
                        + "AND Q.status=1 "
                        + "ORDER BY NEWID()";
                stm = con.prepareStatement(sql);
                stm.setInt(1, examId);
                stm.setString(2, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionId = rs.getInt("id");
                    int correctAnswert = rs.getInt("correctAnswer");
                    TblQuestionInQuizDTO dto = new TblQuestionInQuizDTO(questionId, quizId, correctAnswert);
                    if (listQuestionInQuiz == null) {
                        listQuestionInQuiz = new ArrayList<>();
                    }
                    listQuestionInQuiz.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    public List<TblQuestionDTO> getListQuestion() {
        return listQuestion;
    }

    public int getCountQuestion() {
        return countQuestion;
    }

    public List<TblQuestionInQuizDTO> getListQuestionInQuiz() {
        return listQuestionInQuiz;
    }
    

    public void findAllQuestionByExamId(int examId, int page) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT Q.id, Q.questionTitle "
                        + "FROM Question Q, QuestionInQuiz P "
                        + "WHERE P.quizId=? "
                        + "AND Q.id = P.questionId "
                        + "ORDER BY Q.dateCreate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";

                stm = con.prepareStatement(sql);
                stm.setInt(1, examId);
                stm.setInt(2, (page - 1) * QUESTION_PER_PAGE);
                stm.setInt(3, QUESTION_PER_PAGE);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionId = rs.getInt("id");
                    String questionTitle = rs.getString("questionTitle");
                    TblQuestionDTO dto = new TblQuestionDTO();
                    dto.setQuestionTitle(questionTitle);
                    dto.setId(questionId);
                    if (listQuestion == null) {
                        listQuestion = new ArrayList<>();
                    }
                    listQuestion.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void getResultFromIds(Set<Integer> ids) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String idList = "";
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id,correctAnswer "
                        + "FROM Question "
                        + "WHERE id IN ids";
                Iterator<Integer> iter = ids.iterator();
                do{
                    idList += iter.next();
                    if (iter.hasNext()){
                        idList+=",";
                    }
                }
                while (iter.hasNext());
                sql = sql.replace("ids", "("+idList+")");
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int correctAnswer = rs.getInt("correctAnswer");
                    int id = rs.getInt("id");
                    if (listCorrectAns == null) {
                        listCorrectAns = new HashMap<>();
                    }
                    listCorrectAns.put(id,correctAnswer);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public Map<Integer, Integer> getListCorrectAns() {
        return listCorrectAns;
    }

    
    
    
}
