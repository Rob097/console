/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.factories;

import database.exceptions.DAOFactoryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberto97
 */
public class JDBCDAOFactory implements DAOFactory{
    private final  String DRIVER = "com.mysql.jdbc.Driver";
    /*DB on cloud
    public static final String DBURL = "jdbc:mysql://macelleriadellantonio.it:3306/maceller_MacAPPDB?confluence?autoReconnect=true";
    private final  String USERNAME = "maceller_Mac_Usr";
    private final String PASSWORD ="Bortoleto1901";
    
    /*DB on Rasp*/
    public static final String DBURL = "jdbc:mysql://ourlists.ddns.net:3306/DBB?zeroDateTimeBehavior=convertToNull";
    private final  String USERNAME = "user";
    private final String PASSWORD ="the_password";
    private transient Connection CON;
    private static JDBCDAOFactory instance;
    
    //ritorna un istanza di questa Classe
     public static synchronized JDBCDAOFactory getInstance() throws DAOFactoryException {
        if (instance == null) {
            //crea un istanza di questa classe e la inizializza con il costruttore. 
            instance = new JDBCDAOFactory(DBURL);
        } else {
            throw new DAOFactoryException("DAOFactory already configured");
        }
         if (instance == null) {
            throw new DAOFactoryException("DAOFactory not yet configured. Call DAOFactory.getInstnce() before use the class");
        }
        return instance;
    }
    //Costruttore
    public JDBCDAOFactory(String dbUrl) throws DAOFactoryException {
        super();

        try {
            // dynamically loading the appropriate driver class with a call to Class.forName()
            Class.forName(DRIVER);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }

        try {
            //assegna la connessione
            CON = DriverManager.getConnection(dbUrl,USERNAME,PASSWORD);
        } catch (SQLException sqle) {
            throw new DAOFactoryException("Cannot create connection", sqle);
        }
        
    }
    //ritorna una connessione
    @Override
    public Connection getConnection(){
        try {
            if(CON.isValid(0)){
            }else{
                try {
                    Class.forName(DRIVER);
                    CON = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
                    System.out.println("CONNESSIONE");
                } catch (SQLException | ClassNotFoundException e) {
                }
            }
        }catch (SQLException ex) {
            try {
                Class.forName(DRIVER);
                CON = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
                System.out.println("CONNESSIONE IN CATCH");
            } catch (SQLException | ClassNotFoundException e) {
            }
        }
        return CON;
    }
    
    //Chiude la connessione
    @Override
    public void shutdown() {
        try {
            CON.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
