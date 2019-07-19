/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.exceptions.DAOFactoryException;
import database.factories.DAOFactory;
import database.factories.JDBCDAOFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberto97
 */
public abstract class JDBCDAO {

    // The JDBC {@link Connection} used to access the persistence system.
    protected transient Connection CON;
    DAOFactory daoFactory;

    //costruttore a usare la connessione nei JDBC_DAO
    protected JDBCDAO(Connection con) throws SQLException {
        super();

        if (con == null || con.isClosed() || !con.isValid(0)) {
            try {
                daoFactory = JDBCDAOFactory.getInstance();
            } catch (DAOFactoryException ex) {
                Logger.getLogger(JDBCDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.CON = daoFactory.getConnection();
        } else {
            this.CON = con;
        }
    }
}
