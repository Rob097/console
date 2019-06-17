/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.CatBlogDAO;
import database.entities.CatBlog;
import database.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBCDAO Per i metodi relativi alle categorie del blog
 *
 * @author Roberto97
 */
public class JDBCCatBlogDAO extends JDBCDAO implements CatBlogDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     *
     * @param con E' la connessione al DB
     */
    public JDBCCatBlogDAO(Connection con) {
        super(con);
    }

    /**
     * Metodo che restituisce tutte le categorie del blog.
     *
     * @return Restituisce in un arraylist tutte le categorie del blog.
     * @throws DAOException
     */
    @Override
    public ArrayList<CatBlog> getAllCatBlog() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog_cat")) {
            ArrayList<CatBlog> categorie = new ArrayList<>();
            boolean check = false;
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    check = true;
                    CatBlog c = new CatBlog();
                    c.setId_cat(rs.getInt("id_cat"));
                    c.setNome(rs.getString("nome"));
                    categorie.add(c);
                }
                if(check)
                    return categorie;
                else
                    return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutte le categorie del blog. (JDBCCatBlogDAO, getAllCatBlog)", ex);
        }
    }

    /**
     * MEtodo che ritorna un intero che indica il numero di articoli di una
     * particolare categoria.
     *
     * @param cat Indica la categoria.
     * @return Ritorna un intero che indica il numero di articoli di una
     * particolare categoria
     * @throws DAOException
     */
    @Override
    public int getNumberOfBlog(String cat) throws DAOException {
        if (cat == null || cat.contains("'") || cat.contains("\"")) {
            return 0;
        } else {
            try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog where categoria = ?")) {
                stm.setString(1, cat);
                int number = 0;

                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        number++;
                    }

                    return number;
                }
            } catch (SQLException ex) {
                throw new DAOException("Impossibile restituire il numero di blog appartenenti alla categoria. (JDBCCatBlogDAO, getNumberOfBlog)", ex);
            }
        }
    }

    /**
     * Metodo che ritorna una categoria di articoli in base all'id.
     *
     * @param id Indical'id della categoria di articoli che si sta cercando.
     * @return Ritorna un oggetto categoria di articoli.
     * @throws DAOException
     */
    @Override
    public CatBlog getCatById(int id) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog_cat where id_cat = ?")) {
            stm.setInt(1, id);
            CatBlog c = new CatBlog();
            Boolean check = false;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c.setId_cat(rs.getInt("id_cat"));
                    c.setNome(rs.getString("nome"));
                    check = true;
                }

                if (check) {
                    return c;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire la categoria dall'id. (JDBCCatBlogDAO, getCatById)", ex);
        }
    }

    /**
     * Metodo che ritorna una categoria di articoli in base al nome.
     *
     * @param nome Indica il nome della categoria di articoli che si sta
     * cercando.
     * @return Ritorna un oggetto categoria di articoli.
     * @throws DAOException
     */
    @Override
    public CatBlog getCatByName(String nome) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog_cat where nome = ?")) {
            stm.setString(1, nome);
            CatBlog c = new CatBlog();
            Boolean check = false;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c.setId_cat(rs.getInt("id_cat"));
                    c.setNome(rs.getString("nome"));
                    check = true;
                }

                if (check) {
                    return c;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire la categoria dal nome. (JDBCCatBlogDAO, getCatByName)", ex);
        }
    }

    @Override
    public void deleteCat(String nome) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement(
                "delete from blog_cat where nome = ?"
        )) {
            try {
                stm.setString(1, (nome));

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error deleting blog cat " + nome);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addCat(String nome) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("insert into blog_cat (nome) VALUES (?)")) {
            try {
                stm.setString(1, nome);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to add new blog cat");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
