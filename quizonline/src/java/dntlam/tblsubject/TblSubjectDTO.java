/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.tblsubject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sasuk
 */
public class TblSubjectDTO implements Serializable{
    private String id;
    private String name;
    private Timestamp dateCreate;
    public TblSubjectDTO() {
    }

    public TblSubjectDTO(String id, String name, Timestamp dateCreate) {
        this.id = id;
        this.name = name;
        this.dateCreate = dateCreate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    
    
}
