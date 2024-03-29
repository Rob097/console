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
    public void checkCON() throws DAOException;    
    
    public ArrayList<Blog> getAllBlogs() throws DAOException;
    public Blog getBlogById(int id) throws DAOException;
    public ArrayList<Blog> getBlogByCat(String cat) throws DAOException;
    public ArrayList<Blog> getMostViewedBlog() throws DAOException;
    public ArrayList<String> getAllCreators() throws DAOException;
    public void alterBlog(String id, String titolo, String testo, String creator, String categoria, String immagine, String descrizione, String meta_descrizione, boolean pubblicato) throws DAOException;
    public void deleteBlog(String id)  throws DAOException;
    public int addBlog(String titolo, String testo, String creator, String categoria, String descrizione, String meta_descrizione, boolean pubblicato) throws DAOException;
   
     //Valutazione
    public int getNumberRate(int id) throws DAOException;
    public double getRate(int id) throws DAOException;
    
    /* TAGS */
    public void addTags(ArrayList<String> prodTags, int idBlog) throws DAOException;
    public String getTagName(int id) throws DAOException;
    public ArrayList<Integer> getAllTagsOfBlog(int id_blog) throws DAOException;    
    public ArrayList<String> getAllTextTagsOfBlog(int id_blog) throws DAOException;
    public ArrayList<Integer> getProductTagsOfBlog(int id_blog) throws DAOException;
    public ArrayList<Integer> getCategoryTagsOfBlog(int id_blog) throws DAOException;
    public void cleanTags() throws DAOException;
}
