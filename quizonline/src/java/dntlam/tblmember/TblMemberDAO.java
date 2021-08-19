/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblmember;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import dntlam.utiles.DBHelper;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author sasuk
 */
public class TblMemberDAO implements Serializable {

    public TblMemberDTO checkLogin(String username, String password) throws SQLException, NamingException, NoSuchAlgorithmException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblMemberDTO dto = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email, name, role "
                        + "FROM Member "
                        + "WHERE email=? AND password=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                String sha256Pwd = DBHelper.convertStringToSHA256(password);
                stm.setString(2, sha256Pwd);
                rs = stm.executeQuery();
                if (rs.next()) {
                    System.out.println("oke");
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    dto = new TblMemberDTO(email, "", name, role, "");
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

    public boolean createUser(TblMemberDTO dto) throws SQLException, NamingException, NoSuchAlgorithmException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO Member (email,password,name,role,status) "
                        + "VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(query);
                stm.setString(1, dto.getEmail());
                String sha256Pwd = DBHelper.convertStringToSHA256(dto.getPassword());
                stm.setString(2, sha256Pwd);
                stm.setString(3, dto.getName());
                stm.setString(4, dto.getRole());
                stm.setString(5, dto.getStatus());
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
}
