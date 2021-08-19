/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblquiz;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sasuk
 */
public class TblQuizDTO implements Serializable{
    private int id;
    private int resultId;
    private String studentId;
    private Timestamp dateCreate;
    private int examId;
    private int status;
    private float total;
    private String examName;
    private String subjectId;
    private long milisecon;
    public TblQuizDTO() {
    }
    
    
    
    public TblQuizDTO(int id, String studentId, Timestamp dateCreate, int examId, int status) {
        this.id = id;
        this.studentId = studentId;
        this.dateCreate = dateCreate;
        this.examId = examId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public long getMilisecon() {
        return milisecon;
    }

    public void setMilisecon(long milisecon) {
        this.milisecon = milisecon;
    }
    
    
    
}
