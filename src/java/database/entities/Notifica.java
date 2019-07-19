/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import java.sql.Timestamp;

/**
 *
 * @author Roberto97
 */
public class Notifica {
    private int id;
    private String testo;
    private Timestamp data;
    private String link;

    public Notifica() {
    }

    public Notifica(int id, String testo, Timestamp data, String link) {
        this.id = id;
        this.testo = testo;
        this.data = data;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
