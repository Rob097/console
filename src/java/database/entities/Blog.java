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
 * @author Roberto97
 */
public class Blog {

    private int id;
    private String categoria;
    private String nome;
    private String testo;
    private String descrizione;
    private String meta_descrizione;
    private String immagine;
    private String creatore;
    private Timestamp data;
    private int views;
    private boolean pubblicato;

    public Blog() {
    }

    public Blog(int id, String categoria, String nome, String testo, String descrizione, String meta_descrizione, String immagine, String creatore, Timestamp data, int views, boolean pubblicato) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.testo = testo;
        this.descrizione = descrizione;
        this.meta_descrizione = meta_descrizione;
        this.immagine = immagine;
        this.creatore = creatore;
        this.data = data;
        this.views = views;
        this.pubblicato = pubblicato;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMeta_descrizione() {
        return meta_descrizione;
    }

    public void setMeta_descrizione(String meta_descrizione) {
        this.meta_descrizione = meta_descrizione;
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

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isPubblicato() {
        return pubblicato;
    }

    public void setPubblicato(boolean pubblicato) {
        this.pubblicato = pubblicato;
    }
    
}
