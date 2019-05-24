/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.ConsoleDAO;
import database.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roberto97
 */
public class JDBCConsoleDAO extends JDBCDAO implements ConsoleDAO {

    public JDBCConsoleDAO(Connection con) {
        super(con);
    }

    @Override
    public int getWeekViews() throws DAOException {
        int views = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime lastMonday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));;
        String data = formatter.format(lastMonday);
        try (PreparedStatement stm = CON.prepareStatement("select views from views_week where week = ?")) {
            stm.setString(1, data);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    views = rs.getInt("views");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return views;
    }

    @Override
    public Map<String, Integer> getMonthViews() throws DAOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        HashMap<String, Integer> dati = new HashMap<>();
        LocalDate date;
        try (PreparedStatement stm = CON.prepareStatement("select * from views_week order by week desc limit 4")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    date = rs.getDate("week").toLocalDate();
                    dati.put(formatter.format(date), rs.getInt("views"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Integer> map = new TreeMap<>(dati);
        return map;
    }

    @Override
    public Map<String, Integer> getLastMonthViews() throws DAOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        HashMap<String, Integer> dati = new HashMap<>();
        LocalDate date;
        int counter = 4;
        try (PreparedStatement stm = CON.prepareStatement("select * from views_week order by week desc limit 8")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if (counter > 0) {
                        counter--;
                    } else {
                        date = rs.getDate("week").toLocalDate();
                        dati.put(formatter.format(date), rs.getInt("views"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Integer> map = new TreeMap<>(dati);
        return map;
    }

    @Override
    public int getViewsChanges() throws DAOException {
        Map<String, Integer> current = getMonthViews();
        Map<String, Integer> last = getLastMonthViews();
        double currentSum = 0;
        double lastSum = 0;
        for (int i : current.values()) {
            currentSum += i;
        }
        for (int i : last.values()) {
            lastSum += i;
        }
        System.out.println("1: " + currentSum);
        System.out.println("2: " + lastSum);
        double diff = (((currentSum - lastSum) / lastSum) * 100);
        int diffI = (int) Math.round(diff);
        return diffI;
    }

}
