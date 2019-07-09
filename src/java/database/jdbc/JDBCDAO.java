/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import static database.factories.JDBCDAOFactory.DBURL;
import static database.factories.JDBCDAOFactory.PASSWORD;
import static database.factories.JDBCDAOFactory.USERNAME;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Roberto97
 */
public abstract class JDBCDAO {

    // The JDBC {@link Connection} used to access the persistence system.
    protected final Connection CON;

    //costruttore a usare la connessione nei JDBC_DAO
    protected JDBCDAO(Connection con) throws SQLException  {
        super();

        if (con == null || con.isClosed() || !con.isValid(0)) {
            System.out.println("CON not valid in JDBCDAO");
            this.CON = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } else {            
            System.out.println("CON is valid in JDBCDAO");
            this.CON = con;
        }
    }
}
