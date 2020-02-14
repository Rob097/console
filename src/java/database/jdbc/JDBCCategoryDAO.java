/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.CategoryDAO;
import database.entities.Categoria;
import database.exceptions.DAOException;
import database.exceptions.DAOFactoryException;
import database.factories.JDBCDAOFactory;
import static database.factories.JDBCDAOFactory.DBURL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBCDAO Per i metodi relativi alle categorie di Prodotti dell'ecommerce
 *
 * @author Roberto97
 */
public class JDBCCategoryDAO extends JDBCDAO implements CategoryDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     *
     * @param con E' la connessione al DB
     * @throws java.sql.SQLException
     */
    public JDBCCategoryDAO(Connection con) throws SQLException {
        super(con);
        try {
            checkCON();
        } catch (DAOException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Controlla che la connessione con il DB sia aperta, altrimenti la riapre
     * @throws DAOException
     */
    @Override
    public final void checkCON() throws DAOException {        
        try {
            if(this.CON == null || this.CON.isClosed() || !this.CON.isValid(0)){
                this.daoFactory = new JDBCDAOFactory(DBURL);
                this.CON = daoFactory.getConnection();         
            }
        } catch (SQLException | DAOFactoryException ex) {
            System.out.println("console jdbc checkCON catch");
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo che restituisce tutte le gategorie di prodotti.
     *
     * @return Ritrorna un arrayList di categorie di categorie.
     * @throws DAOException
     */
    @Override
    public ArrayList<Categoria> getAllCategories() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM categorie")) {
            ArrayList<Categoria> categorie = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setMeta_descrizione(rs.getString("meta_descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setFreschi(rs.getBoolean("freschi"));
                    categorie.add(c);
                }

                return categorie;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutte le categorie. (JDBCCategoryDAO, getAllCategories)", ex);
        }
    }

    /**
     * Metodo che ritorna tutte le categorie di prodotti FRESCHI
     *
     * @return Ritorna un arraylist di categorie.
     * @throws DAOException
     */
    @Override
    public ArrayList<Categoria> getFreshCategories() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM categorie where freschi = 1")) {
            ArrayList<Categoria> categorie = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setMeta_descrizione(rs.getString("meta_descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setFreschi(rs.getBoolean("freschi"));
                    categorie.add(c);
                }

                return categorie;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire le categorie di prodotti freschi. (JDBCCategoryDAO, getFreshCategories)", ex);
        }
    }

    /**
     * Metodo che ritorna tutte le categorie di prodotti NON freschi.
     *
     * @return Ritorna un arraylist di categorie.
     * @throws DAOException
     */
    @Override
    public ArrayList<Categoria> getConfCategories() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM categorie where freschi = 0")) {
            ArrayList<Categoria> categorie = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setMeta_descrizione(rs.getString("meta_descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setFreschi(rs.getBoolean("freschi"));
                    categorie.add(c);
                }

                return categorie;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire le categorie di prodotti conf. (JDBCCategoryDAO, getConfCategories)", ex);
        }
    }

    /**
     * Metodo che restituisce una categoria in base al nome.
     *
     * @param name Indica il nome della categoria.
     * @return Ritorna un oggetto di tipo Categoria.
     * @throws DAOException
     */
    @Override
    public Categoria getByName(String name) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM categorie where nome = ?")) {
            stm.setString(1, name);
            Categoria c = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setMeta_descrizione(rs.getString("meta_descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setFreschi(rs.getBoolean("freschi"));
                }
                return c;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Ritorna una categoria di prodotti dall'id
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Categoria getById(String id) throws DAOException {
        checkCON();
        
        int id1;
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM categorie where id = ?")) {
            id1 = Integer.parseInt(id);
            stm.setInt(1, id1);
            Categoria c = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c = new Categoria();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setMeta_descrizione(rs.getString("meta_descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setFreschi(rs.getBoolean("freschi"));
                }
                return c;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo per aggiornare una categoria di prodotti.il nome è così perchè
 inizialmente aggiornava solo l'immagine e non ho più avuto voglia di cambiarlo
     * @param id
     * @param nome
     * @param url
     * @param descrizione
     * @param meta_descrizione
     * @throws DAOException
     */
    @Override
    public void alterImg(String id, String nome, String url, String descrizione, String meta_descrizione) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement(
                "UPDATE categorie SET immagine = ?, nome = ?, descrizione = ?, meta_descrizione = ? where id = ?"
        )) {
            try {
                stm.setString(1, url);
                stm.setString(2, nome);
                stm.setString(3, descrizione);
                stm.setString(4, meta_descrizione);
                stm.setString(5, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to update category");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo per aggiorngere una nuova categoria di prodotti
     * @param nome
     * @param descrizione
     * @param fresco
     * @return
     * @throws DAOException
     */
    @Override
    public int addCategory(String nome, String descrizione, boolean fresco, String meta_descrizione) throws DAOException {
        checkCON();
        
        int id = 0;
        try (PreparedStatement stm = CON.prepareStatement("insert into categorie (nome, descrizione, freschi, immagine, meta_descrizione) VALUES (?,?,?,?,?)")) {
            try {
                stm.setString(1, nome);
                stm.setString(2, descrizione);
                stm.setBoolean(3, fresco);
                stm.setString(4, "");
                stm.setString(5, meta_descrizione);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to update image of category");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("select MAX(id) as id from categorie")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException ex) { 
        }
        return id;
    }

    /**
     * MEtodo per eliminare una categoria di prodotti
     * @param id
     * @throws DAOException
     */
    @Override
    public void deleteCat(int id) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement(
                "delete from categorie where id = ?"
        )) {
            try {
                stm.setInt(1, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error deleting cat " + id);
                }

            } catch (SQLException ex) {
                System.out.println("Questo catch");
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
