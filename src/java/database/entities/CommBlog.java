/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entities;

import java.sql.Timestamp;

/**
 *
 * @author Roberto97
 */
public class CommBlog {
    private int id_blog;
    private String nome;
    private String commento;
    private Timestamp data;

    public CommBlog() {
    }

    public CommBlog(int id_blog, String nome, String commento, Timestamp data) {
        this.id_blog = id_blog;
        this.nome = nome;
        this.commento = commento;
        this.data = data;
    }

    public int getId_blog() {
        return id_blog;
    }

    public void setId_blog(int id_blog) {
        this.id_blog = id_blog;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }    
    
}
