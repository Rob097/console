/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import database.exceptions.DAOFactoryException;
import database.factories.DAOFactory;
import database.factories.JDBCDAOFactory;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Roberto97
 */
public class WebAppListener implements ServletContextListener {

    //Ascolta, se viene fatto una richiesta al sito crea una connessione con mysql
    @Override//Ascolta, se viene fatto una richiesta al sito crea una connessione con mysql
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //Crea una daoFactory e la inizializza con un istanza di JDBCDAOFactoy(db driver e db connection)
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            //System.out.println("Log: WebAPPListener initialized");

            //l`evento sce mette nella Servlet-Context un attributo daoFactory ad accedere da ogni servlet
            sce.getServletContext().setAttribute("daoFactory", daoFactory);

        } catch (DAOFactoryException ex) {
            System.out.println("webAppListener init catch");
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            Logger.getLogger(WebAppListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Quando il browser viene chiuso anche la connessione viene chiuso
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //crea un oggetto daoFactory inizzializzato col attributo daoFactory (inizzializzato sopra)
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        //System.out.println("Log:WebAppListener destroyed");
        //chiude la connessione 
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
        daoFactory = null;
        try {
            com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException t) {
        }
        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks
        Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            java.sql.Driver driver = drivers.nextElement();
            try {
                java.sql.DriverManager.deregisterDriver(driver);
            } catch (SQLException t) {
            }
        }
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
        }
    }
}
