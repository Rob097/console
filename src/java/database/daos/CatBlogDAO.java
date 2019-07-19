/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.CatBlog;
import database.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Roberto97
 */
public interface CatBlogDAO {
    public void checkCON() throws DAOException;
    
    
    public ArrayList<CatBlog> getAllCatBlog() throws DAOException;
    public int getNumberOfBlog(String cat) throws DAOException;
    public CatBlog getCatById(int id) throws DAOException;
    public CatBlog getCatByName(String nome) throws DAOException;
    public void addCat(String nome) throws DAOException;
    public void deleteCat(String nome) throws DAOException;
}
