/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblExam;

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
public class TblExamDAO implements Serializable{
    List<TblExamDTO> listExam;
    public TblExamDTO createExam(TblExamDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        TblExamDTO result = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO ExamInSubject (dateCreate,timeDoExam,subjectId,name,numQuest) "
                        + "VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
                Date dateCreate = new Date();
                stm.setTimestamp(1, new Timestamp(dateCreate.getTime()));
                stm.setInt(2, dto.getTimeDoExam());
                stm.setString(3, dto.getSubjectId());
                stm.setString(4, dto.getName());
                stm.setInt(5, dto.getNumQuest());
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()){
                        int id = rs.getInt(1);
                        dto.setId(id);
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
    
    public void findExamBySubjectId(String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id, timeDoExam , subjectId, name, numQuest, dateCreate "
                        + "FROM ExamInSubject "
                        + "WHERE subjectId=?";

                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id= rs.getInt("id");
                    int timeDoExam= rs.getInt("timeDoExam");
                    int numQuest= rs.getInt("numQuest");
                    String name = rs.getString("name");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    TblExamDTO dto = new TblExamDTO(id, dateCreate, timeDoExam, numQuest, subjectId, name);
                    if (listExam==null){
                        listExam = new ArrayList<>();
                    }
                    listExam.add(dto);
                    
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
    
    public TblExamDTO findExamById(int examId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblExamDTO dto  = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id, timeDoExam , subjectId, name, numQuest "
                        + "FROM ExamInSubject "
                        + "WHERE id=?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, examId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int id= rs.getInt("id");
                    int timeDoExam= rs.getInt("timeDoExam");
                    int numQuest= rs.getInt("numQuest");
                    String name = rs.getString("name");
                    String subjectId = rs.getString("subjectId");
                    dto = new TblExamDTO(id, null, timeDoExam, numQuest, subjectId, name);
                    
                    
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
    
    
    public TblExamDTO findExamByQuizId(int quizId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblExamDTO dto  = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT E.id, E.timeDoExam , E.subjectId, E.name, E.numQuest "
                        + "FROM ExamInSubject E, UserTakeQuiz U "
                        + "WHERE U.id = ? "
                        + "AND E.id = U.examId";

                stm = con.prepareStatement(sql);
                stm.setInt(1, quizId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int id= rs.getInt("id");
                    int timeDoExam= rs.getInt("timeDoExam");
                    int numQuest= rs.getInt("numQuest");
                    String name = rs.getString("name");
                    String subjectId = rs.getString("subjectId");
                    dto = new TblExamDTO(id, null, timeDoExam, numQuest, subjectId, name);
                    
                    
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
    
    public List<TblExamDTO> getListExam() {
        return listExam;
    }
    
    public int countMaxNumQuest(String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int max =0;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {

                String sql = "SELECT MAX(numQuest) "
                        + "FROM ExamInSubject "
                        + "WHERE subjectId=?";

                stm = con.prepareStatement(sql);
                stm.setString(1,subjectId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    max = rs.getInt(1);
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
        return max;
    }
    
}
