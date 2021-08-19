/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblsubject;

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
public class TblSubjectDAO implements Serializable{
    private List<TblSubjectDTO> listSubject;
    
    public void findAllSubject() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT id, name, dateCreate "
                        + "FROM Subject "
                        + "order by dateCreate DESC";
                        
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String subjectId = rs.getString("id");
                    String subjectName = rs.getString("name");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    TblSubjectDTO dto = new TblSubjectDTO(subjectId,subjectName,dateCreate);

                    if (listSubject == null) {
                        listSubject = new ArrayList<>();
                    }
                    listSubject.add(dto);
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
    
    
    
    public boolean createSubject(TblSubjectDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO Subject (id,name,dateCreate) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(query);
                stm.setString(1, dto.getId());
                stm.setString(2, dto.getName());
                Date date = new Date();
                stm.setTimestamp(3, new Timestamp(date.getTime()));
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

    public List<TblSubjectDTO> getListSubject() {
        return listSubject;
    }

    

}
