/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.utiles;

import dntlam.tblmember.TblMemberDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sasuk
 */
public class AccessUtils {
    public static final String STUDENT_ROLE = "student";
    public static final String ADMIN_ROLE = "admin";
    public static boolean checkAccessRole(HttpServletRequest request, String role){
        HttpSession session = request.getSession(false);
        boolean access = false;
        if (session!=null){
            TblMemberDTO member = (TblMemberDTO) session.getAttribute("RESULTLOGIN");
            if (member!=null &&member.getRole().equals(role)){
                access=true;
            }
        }
        return access;
    }
    
    
}
