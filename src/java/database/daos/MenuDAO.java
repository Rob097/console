/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.Menu;
import database.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Roberto97
 */
public interface MenuDAO {
    public ArrayList<Menu> getAllMenu() throws DAOException;
    public Menu getMenu(int id) throws DAOException;
    public void updateMenu(int id, String nome, String copertina, String immagine) throws DAOException;
    public void deleteMenu(int id) throws DAOException;
    public void addMenu(String nome, String immagine, String copertina) throws DAOException;
}
