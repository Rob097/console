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
public class Prodotto {
    private int id;
    private String nome;
    private String categoria;
    private String immagine;
    private String descrizione;
    private String costo;
    private boolean disponibile;
    private int quantita;
    private boolean fresco;

    public Prodotto() {
    }

    public Prodotto(int id, String nome, String categoria, String immagine, String descrizione, String costo, boolean disponibile, int quantita, boolean fresco) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.immagine = immagine;
        this.descrizione = descrizione;
        this.costo = costo;
        this.disponibile = disponibile;
        this.quantita = quantita;
        this.fresco = fresco;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = String.format("%.2f", costo);
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public boolean isFresco() {
        return fresco;
    }

    public void setFresco(boolean fresco) {
        this.fresco = fresco;
    }

    @Override
    public String toString() {
        return "Prodotto{" + "id=" + id + ", nome=" + nome + ", categoria=" + categoria + ", immagine=" + immagine + ", descrizione=" + descrizione + ", costo=" + costo + ", disponibile=" + disponibile + '}';
    }
    
    
}
