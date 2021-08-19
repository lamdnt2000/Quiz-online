/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblExam;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sasuk
 */
public class TblExamDTO implements Serializable{
    private int id;
    private Timestamp dateCreate;
    private int timeDoExam;
    private int numQuest;
    private String subjectId;
    private String name;
    public TblExamDTO() {
    }

    public TblExamDTO(int id, Timestamp dateCreate, int timeDoExam, int numQuest, String subjectId, String name) {
        this.id = id;
        this.dateCreate = dateCreate;
        this.timeDoExam = timeDoExam;
        this.numQuest = numQuest;
        this.subjectId = subjectId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getTimeDoExam() {
        return timeDoExam;
    }

    public void setTimeDoExam(int timeDoExam) {
        this.timeDoExam = timeDoExam;
    }

    public int getNumQuest() {
        return numQuest;
    }

    public void setNumQuest(int numQuest) {
        this.numQuest = numQuest;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

   
    
    
}
