/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblChoice;

import dntlam.utiles.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sasuk
 */
public class TblChoiceDAO implements Serializable {
    List<TblChoiceDTO> listChoice;
    public boolean createChoice(TblChoiceDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO Choice (questionId,choiceId,answer) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(query);
                stm.setInt(1, dto.getQuestionId());
                stm.setInt(2, dto.getChoiceId());
                stm.setString(3, dto.getAnswer());
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
    public boolean updateChoice(TblChoiceDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "UPDATE Choice "
                        + "SET answer=? "
                        + "WHERE questionId=? "
                        + "AND choiceId=?";
                stm = con.prepareStatement(query);
                
                stm.setString(1, dto.getAnswer());
                stm.setInt(2, dto.getQuestionId());
                stm.setInt(3, dto.getChoiceId());
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
    
    public void findChoiceById(int questionId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT questionId, choiceId, answer "
                        + "FROM Choice "
                        + "WHERE questionId=?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, questionId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int choiceId = rs.getInt("choiceId");
                    String answer = rs.getString("answer");
                    TblChoiceDTO dto = new TblChoiceDTO(questionId, choiceId, answer);
                    if (listChoice==null){
                        listChoice = new ArrayList<>();
                    }
                    listChoice.add(dto);
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

    public List<TblChoiceDTO> getListChoice() {
        return listChoice;
    }
    
    
}
