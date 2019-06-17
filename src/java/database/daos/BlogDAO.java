/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.Blog;
import database.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Roberto97
 */
public interface BlogDAO {
    public ArrayList<Blog> getAllBlogs() throws DAOException;
    public Blog getBlogById(int id) throws DAOException;
    public ArrayList<Blog> getBlogByCat(String cat) throws DAOException;
    public ArrayList<Blog> getMostViewedBlog() throws DAOException;
    public ArrayList<String> getAllCreators() throws DAOException;
    public void alterBlog(String id, String titolo, String testo, String creator, String categoria, String immagine, String descrizione) throws DAOException;
    public void deleteBlog(String id)  throws DAOException;
    public int addBlog(String titolo, String testo, String creator, String categoria, String descrizione) throws DAOException;
}
