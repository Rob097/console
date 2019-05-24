/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.exceptions.DAOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Roberto97
 */
public interface ConsoleDAO {
    public int getWeekViews() throws DAOException;
    public Map<String, Integer> getMonthViews() throws DAOException;
    public Map<String, Integer> getLastMonthViews() throws DAOException;
    public int getViewsChanges() throws DAOException;
}
