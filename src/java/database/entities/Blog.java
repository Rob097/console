/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Roberto97
 */
public class Blog {

    private int id;
    private String categoria;
    private String nome;
    private String testo;
    private String immagine;
    private String creatore;
    private Date data;
    private int views;

    public Blog() {
    }

    public Blog(int id, String categoria, String nome, String testo, String immagine, String creatore, Date data, int views) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.testo = testo;
        this.immagine = immagine;
        this.creatore = creatore;
        this.data = data;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getCreatore() {
        return creatore;
    }

    public void setCreatore(String creatore) {
        this.creatore = creatore;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
    
    
}
