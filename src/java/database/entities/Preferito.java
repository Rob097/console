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
public class Preferito {
    private String emailUtente;
    private int idProd;

    public Preferito() {
    }

    public Preferito(String emailUtente, int idProd) {
        this.emailUtente = emailUtente;
        this.idProd = idProd;
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
    
}
