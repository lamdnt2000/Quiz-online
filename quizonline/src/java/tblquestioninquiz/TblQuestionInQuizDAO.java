/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblquestioninquiz;

import dntlam.utiles.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sasuk
 */
public class TblQuestionInQuizDAO implements Serializable {

    public boolean createQuiz(List<TblQuestionInQuizDTO> list) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO QuestionInQuiz (questionId,quizId,result) "
                        + "VALUES listValue";

                StringBuilder listValue = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    listValue.append(list.get(i).toString());
                    if (i < list.size() - 1) {
                        listValue.append(",");
                    }
                }
                query = query.replace("listValue", listValue.toString());
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
            if (rs != null) {
                rs.close();
            }

        }
        return result;
    }
}
