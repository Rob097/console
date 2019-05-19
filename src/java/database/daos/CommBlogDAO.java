/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.CommBlog;
import database.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Roberto97
 */
public interface CommBlogDAO {
    public ArrayList<CommBlog> getAllCommBlog() throws DAOException;
    public ArrayList<CommBlog> getAllCommOfBlog(int id_blog) throws DAOException;
}
