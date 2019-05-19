/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author della
 */
public class Ricetta {
    private int id;
    private int id_prod;
    private String nome;
    private ArrayList<String> ingredienti;
    private String procedimento;
    private String immagine;
    private int tempo;
    private String difficolta;
    private String creatore;
    private Date data;
    private int views;
    private boolean category;

    public Ricetta() {
    }

    public Ricetta(int id, int id_prod, String nome, ArrayList<String> ingredienti, String procedimento, String immagine, int tempo, String difficolta, String creatore, Date data, int views, boolean category) {
        this.id = id;
        this.id_prod = id_prod;
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.procedimento = procedimento;
        this.immagine = immagine;
        this.tempo = tempo;
        this.difficolta = difficolta;
        this.creatore = creatore;
        this.data = data;
        this.views = views;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public ArrayList<String> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<String> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
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

    public boolean isCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }
    
}
