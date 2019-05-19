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
public class Carrello {
    private String emailUtente;
    private int idProd;
    private int quantita;

    public Carrello() {
    }

    public Carrello(String emailUtente, int idProd, int quantita) {
        this.emailUtente = emailUtente;
        this.idProd = idProd;
        this.quantita = quantita;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }    
    
}
