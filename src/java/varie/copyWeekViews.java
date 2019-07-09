/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varie;

import database.daos.ConsoleDAO;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCConsoleDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

/**
 *
 * @author Roberto97
 */
public class copyWeekViews implements Runnable {

    ServletContextEvent sce;
    transient static ConsoleDAO consoledao;

    public copyWeekViews(ServletContextEvent sce) {
        this.sce = sce;
    }   
    
    @Override
    public void run() {
        try {
            getConnection();
            consoledao.copyWeekViews();
        } catch (DAOException | ServletException | SQLException ex) {
            Logger.getLogger(copyWeekViews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getConnection() throws ServletException, SQLException{
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        consoledao = new JDBCConsoleDAO(daoFactory.getConnection());
    }
    
}
