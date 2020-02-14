/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.Categoria;
import database.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Roberto97
 */
public interface CategoryDAO {
    public void checkCON() throws DAOException;
    
    
    public ArrayList<Categoria> getAllCategories() throws DAOException;
    public ArrayList<Categoria> getFreshCategories() throws DAOException;
    public ArrayList<Categoria> getConfCategories() throws DAOException;
    public Categoria getByName(String name) throws DAOException;
    public Categoria getById(String id) throws DAOException;
    public void alterImg(String id, String nome, String url, String descrizione, String meta_descrizione) throws DAOException;
    public int addCategory(String nome, String descrizione, boolean fresco, String meta_descrizione) throws DAOException;
    public void deleteCat(int id) throws DAOException;
}
