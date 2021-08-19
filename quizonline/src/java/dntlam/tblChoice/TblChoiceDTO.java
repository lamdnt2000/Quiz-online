/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblChoice;

import java.io.Serializable;

/**
 *
 * @author sasuk
 */
public class TblChoiceDTO implements  Serializable{
    private int questionId;
    private int choiceId;
    private String answer;

    public TblChoiceDTO() {
    }

    public TblChoiceDTO(int questionId, int choiceId, String answer) {
        this.questionId = questionId;
        this.choiceId = choiceId;
        this.answer = answer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
}
