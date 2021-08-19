/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblquestion;

import java.io.Serializable;

/**
 *
 * @author sasuk
 */
public class Parameter implements Serializable{
    private String title;
    private int status;
    private String subjectId;
    private int page;
    public Parameter() {
    }

    public Parameter(String title, int status, String subjectId, int page) {
        this.title = title;
        this.status = status;
        this.subjectId = subjectId;
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    
    
    
}
