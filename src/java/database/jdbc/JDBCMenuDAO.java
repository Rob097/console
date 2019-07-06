/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.MenuDAO;
import database.entities.Menu;
import database.exceptions.DAOException;
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

    public JDBCMenuDAO(Connection con) {
        super(con);
    }

    @Override
    public ArrayList<Menu> getAllMenu() throws DAOException {
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

    @Override
    public Menu getMenu(int id) throws DAOException {
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

    @Override
    public void updateMenu(int id, String nome, String copertina, String immagine) throws DAOException {
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

    @Override
    public void deleteMenu(int id) throws DAOException {
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

    @Override
    public void addMenu(String nome, String immagine, String copertina) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("insert into menu (nome, immagine, copertina) VALUES (?,?,?)")) {
            try {
                stm.setString(1, nome);
                stm.setString(2, immagine);
                stm.setString(3, copertina);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to add new product");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
