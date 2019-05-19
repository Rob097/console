/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author della
 */
public class Commento {
    private int id;
    private int id_ricetta;
    private String nome;
    private String testo;
    private Timestamp data;

    public Commento() {
    }

    public Commento(int id, int id_ricetta, String nome, String testo, Timestamp data) {
        this.id = id;
        this.id_ricetta = id_ricetta;
        this.nome = nome;
        this.testo = testo;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_ricetta() {
        return id_ricetta;
    }

    public void setId_ricetta(int id_ricetta) {
        this.id_ricetta = id_ricetta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
    
}
