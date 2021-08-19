/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblquestioninquiz;

import java.io.Serializable;

/**
 *
 * @author sasuk
 */
public class TblQuestionInQuizDTO implements Serializable{
    private int questionId;
    private int quizId;
    private int result;

    public TblQuestionInQuizDTO(int questionId, int quizId, int result) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.result = result;
    }

    public TblQuestionInQuizDTO() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    
    
    @Override
    public String toString() {
        return "(" + questionId + "," + quizId +","+result+ ")";
    }
    
    
}
