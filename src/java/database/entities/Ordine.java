/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import java.util.ArrayList;

/**
 *
 * @author Roberto97
 */
public class Ordine {
    private String id;
    private String data;
    private String nome;
    private String email;
    private String citta;
    private String indirizzo;
    private String zip;
    private ArrayList<String> prodotti;
    private String tipo;
    private double tot;
    private String stato;

    public Ordine() {
    }

    public Ordine(String id, String data, String nome, String email, String citta, String indirizzo, String zip, ArrayList<String> prodotti, String tipo, double tot, String stato) {
        this.id = id;
        this.data = data;
        this.nome = nome;
        this.email = email;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.zip = zip;
        this.prodotti = prodotti;
        this.tipo = tipo;
        this.tot = tot;
        this.stato = stato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public ArrayList<String> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<String> prodotti) {
        this.prodotti = prodotti;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        String s = "";
        s = prodotti.stream().map((i) -> i + " ").reduce(s, String::concat);
        return s;
    }
    
    
}
