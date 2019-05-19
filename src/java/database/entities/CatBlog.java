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
public class CatBlog {
    private int id_cat;
    private String nome;

    public CatBlog() {
    }

    public CatBlog(int id_cat, String nome) {
        this.id_cat = id_cat;
        this.nome = nome;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
