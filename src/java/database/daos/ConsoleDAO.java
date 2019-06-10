/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.exceptions.DAOException;
import java.util.Map;

/**
 *
 * @author Roberto97
 */
public interface ConsoleDAO {
    public int getWeekViews() throws DAOException;
    public Map<String, Integer> getMonthViews() throws DAOException;
    public Map<String, Integer> getLastMonthViews() throws DAOException;
    public Object getViewsChanges(boolean lastValue) throws DAOException;
    public Map<String, Integer> getPagesViews() throws DAOException;
    public Map<String, Integer> getMonthEmailSub(boolean isLast) throws DAOException;
    public int getEmailChanges(boolean lastValue) throws DAOException;
    public String getTotalEmailSub() throws DAOException;
    public Map<String, Double> getMonthRevenue(boolean isLast) throws DAOException;
    public Object getRevenueChanges(boolean lastValue) throws DAOException;
    public String getTotalRevenue() throws DAOException;
    public String getLastEmailSub() throws DAOException;
    public String getLastRevenue() throws DAOException;
    public Map<String, Integer> getProductBuy() throws DAOException;
}
