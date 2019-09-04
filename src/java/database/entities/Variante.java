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
public class Variante {
    private int id;
    private int id_prod;
    private String variant;
    private String variantName;
    private double supplement;
    private int quantita;

    public Variante() {
    }

    public Variante(int id, int id_prod, String variant, String variantName, double supplement, int quantita) {
        this.id = id;
        this.id_prod = id_prod;
        this.variant = variant;
        this.variantName = variantName;
        this.supplement = supplement;
        this.quantita = quantita;
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

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public double getSupplement() {
        return supplement;
    }

    public void setSupplement(double supplement) {
        this.supplement = supplement;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    
    
}
