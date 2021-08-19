/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblquestion;

import dntlam.tblChoice.TblChoiceDTO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author sasuk
 */
public class TblQuestionDTO implements Serializable{
    private int id;
    private String questionTitle;
    private String subjectId;
    private int correctAnswer;
    private int status;
    private Timestamp dateCreate;
    private Timestamp dateUpdate;
    private String userCreate;
    private String userUpdate;
    private List<TblChoiceDTO> listChoice;
    public TblQuestionDTO() {
    }

    public TblQuestionDTO(int id, String questionTitle, String subjectId, int correctAnswer, int status, Timestamp dateCreate, Timestamp dateUpdate, String userCreate, String userUpdate) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.subjectId = subjectId;
        this.correctAnswer = correctAnswer;
        this.status = status;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public List<TblChoiceDTO> getListChoice() {
        return listChoice;
    }

    public void setListChoice(List<TblChoiceDTO> listChoice) {
        this.listChoice = listChoice;
    }
    
    
    
}
