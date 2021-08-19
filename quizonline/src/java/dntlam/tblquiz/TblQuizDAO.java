/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblquiz;

import dntlam.utiles.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sasuk
 */
public class TblQuizDAO implements Serializable {

    private List<TblQuizDTO> historyList;

    public TblQuizDTO createQuiz(TblQuizDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblQuizDTO result = null;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO UserTakeQuiz (examId,dateCreate,studentId,status,total) "
                        + "VALUES (?,?,?,?,0)";

                stm = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setInt(1, dto.getExamId());
                Date dateCreate = new Date();
                stm.setTimestamp(2, new Timestamp(dateCreate.getTime()));
                stm.setString(3, dto.getStudentId());
                stm.setInt(4, dto.getStatus());

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
            if (rs != null) {
                rs.close();
            }

        }
        return result;
    }

    public boolean updateQuiz(TblQuizDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "UPDATE UserTakeQuiz "
                        + "SET status=?, total=? "
                        + "WHERE id=?";
                stm = con.prepareStatement(query);

                stm.setInt(1, dto.getStatus());
                stm.setFloat(2, dto.getTotal());
                stm.setInt(3, dto.getId());
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

    public TblQuizDTO findQuizById(int quizId, String email, int status) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblQuizDTO dto = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id, examId, dateCreate "
                        + "FROM UserTakeQuiz "
                        + "WHERE studentId=? "
                        + "AND id=? "
                        + "AND status=? ";

                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setInt(2, quizId);
                stm.setInt(3, status);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int examId = rs.getInt("examId");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    dto = new TblQuizDTO(id, email, dateCreate, examId, status);
                    dto.setMilisecon(dateCreate.getTime());

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
        return dto;
    }

    public void getHistoryFromUser(String email, String txtSearchName, String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String querySubject = ("0".equals(subjectId))?"":"AND E.subjectId =? ";
                String sql = "SELECT U.id,U.examId,U.dateCreate,U.total ,E.name as examName,S.id as subjectId "
                        + "FROM UserTakeQuiz U, ExamInSubject E, Subject S "
                        + "WHERE status=1"
                        + "AND studentId = ? "
                        + "AND U.examId = E.id "
                        + "AND E.name like ? "
                        + querySubject
                        + "AND E.subjectId = S.id "
                        + "ORDER BY U.dateCreate DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, "%"+txtSearchName+"%");
                if (!"".equals(querySubject)){
                    stm.setString(3,subjectId);
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int examId = rs.getInt("examId");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    Float total = rs.getFloat("total");
                    String examName = rs.getString("examName");
                    String subjId = rs.getString("subjectId");
                    TblQuizDTO dto = new TblQuizDTO();
                    dto.setId(id);
                    dto.setExamId(examId);
                    dto.setDateCreate(dateCreate);
                    dto.setTotal(total);
                    dto.setExamName(examName);
                    dto.setSubjectId(subjId);
                    if (historyList == null) {
                        historyList = new ArrayList<>();
                    }
                    historyList.add(dto);

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

    public List<TblQuizDTO> getHistoryList() {
        return historyList;
    }

}
