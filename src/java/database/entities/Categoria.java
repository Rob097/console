/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

/**
 *
 * @author Roberto97
 */
public class Categoria {
    private int id;
    private String nome;
    private String immagine;
    private String descrizione;
    private String meta_descrizione;
    private boolean freschi;

    public Categoria() {
    }   

    public Categoria(int id, String nome, String immagine, String descrizione, String meta_descrizione, boolean freschi) {
        this.id = id;
        this.nome = nome;
        this.immagine = immagine;
        this.descrizione = descrizione;
        this.meta_descrizione = meta_descrizione;
        this.freschi = freschi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
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

    public boolean isFreschi() {
        return freschi;
    }

    public void setFreschi(boolean freschi) {
        this.freschi = freschi;
    }
    
}
