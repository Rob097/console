/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import java.sql.Connection;

/**
 *
 * @author Roberto97
 */
public abstract class JDBCDAO{
    // The JDBC {@link Connection} used to access the persistence system.
    protected final Connection CON;
    
    //costruttore a usare la connessione nei JDBC_DAO
    protected JDBCDAO(Connection con) {
        super();
        this.CON = con;
    }
}
