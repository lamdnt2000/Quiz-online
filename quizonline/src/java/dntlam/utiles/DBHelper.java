/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.utiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dntlam.tblmember.TblMemberDTO;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author sasuk
 */
public class DBHelper implements Serializable{
    public static Connection makeConnection() throws NamingException, SQLException{
        Context context = new InitialContext();
        Context tomcatContext = (Context)context.lookup("java:/comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("dntlam");
        Connection con = ds.getConnection();
        return  con;
    }
    
    public static String convertStringToSHA256(String str) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }
    
    public  static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte: hash) {
            String hex = String.format("%02X", aByte);
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static String convertListToJson(List list, int counter) {
        Gson gson = new GsonBuilder().create();
        JsonArray jarray = gson.toJsonTree(list).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", jarray);
        jsonObject.addProperty("counter", counter);
        return jsonObject.toString();
    }
    
    public static String getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String email = null;
        if (session!=null){
            TblMemberDTO member = (TblMemberDTO) session.getAttribute("RESULTLOGIN");
            if (member!=null){
                email = member.getEmail();
            }
        }
        return email;
    }
}
