/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.MenuDAO;
import database.entities.Menu;
import database.exceptions.DAOException;
import database.exceptions.DAOFactoryException;
import database.factories.JDBCDAOFactory;
import static database.factories.JDBCDAOFactory.DBURL;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberto97
 */
public class JDBCMenuDAO extends JDBCDAO implements MenuDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     *
     * @param con E' la connessione al DB
     * @throws java.sql.SQLException
     */
    public JDBCMenuDAO(Connection con) throws SQLException{
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
     * MEtodo che ritorna tutti i menu nel DB
     * @return
     * @throws DAOException
     */
    @Override
    public ArrayList<Menu> getAllMenu() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM menu")) {
            ArrayList<Menu> menu = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Menu p = new Menu();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCopertina(rs.getString("copertina"));
                    menu.add(p);
                }

                return menu;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i menu. (JDBCMenuDAO, getAllMenu)", ex);
        }
    }

    /**
     * Metodo che ritorna un menu dall'id
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Menu getMenu(int id) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM menu where id = ?")) {
            stm.setInt(1, id);
            Menu p = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    p = new Menu();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCopertina(rs.getString("copertina"));
                }

                return p;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire il menu. (JDBCMenuDAO, getMenu)", ex);
        }
    }

    /**
     * Metodo per aggiornare un menu
     * @param id
     * @param nome
     * @param copertina
     * @param immagine
     * @throws DAOException
     */
    @Override
    public void updateMenu(int id, String nome, String copertina, String immagine) throws DAOException {
        checkCON();
        
        if (nome == null || copertina == null || immagine == null) {
        } else {
            try (PreparedStatement stm = CON.prepareStatement(
                    "UPDATE menu SET nome = ?, copertina = ?, immagine = ? WHERE id = ?;"
            )) {
                try {
                    stm.setString(1, nome);
                    stm.setString(2, copertina);
                    stm.setString(3, immagine);
                    stm.setInt(4, id);

                    if (stm.executeUpdate() == 1) {
                    } else {
                        System.out.println("Error updating menu " + id);
                    }

                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo per eliminare un menu
     * @param id
     * @throws DAOException
     */
    @Override
    public void deleteMenu(int id) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement(
                "delete from menu where id = ?"
        )) {
            try {
                stm.setInt(1, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error deleting menu " + id);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo per aggiungere una nuova sezione al menu
     * @param nome
     * @param immagine
     * @param copertina
     * @throws DAOException
     */
    @Override
    public void addMenu(String nome, String immagine, String copertina) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("insert into menu (nome, immagine, copertina) VALUES (?,?,?)")) {
            try {
                stm.setString(1, nome);
                stm.setString(2, immagine);
                stm.setString(3, copertina);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to add new menu");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
