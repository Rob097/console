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
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
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
        try (PreparedStatement stm = CON.prepareStatement("select SUM(views) AS views from curr_week_views")) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        HashMap<String, Integer> dati = new HashMap<>();
        LocalDate date;
        try (PreparedStatement stm = CON.prepareStatement("select week, SUM(views) AS views from weeks_views group by week desc limit 4")) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        HashMap<String, Integer> dati = new HashMap<>();
        LocalDate date;
        int counter = 4;
        int c = 0;
        try (PreparedStatement stm = CON.prepareStatement("select week, SUM(views) AS views from weeks_views group by week desc limit 8")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if (counter > 0) {
                        counter--;
                        c++;
                    } else {
                        date = rs.getDate("week").toLocalDate();
                        dati.put(formatter.format(date), rs.getInt("views"));
                        c++;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Integer> map = new TreeMap<>(dati);
        if (c < 8) {
            return null;
        }
        return map;
    }

    @Override
    public Object getViewsChanges(boolean lastValue) throws DAOException {
        Map<String, Integer> current = getMonthViews();
        Map<String, Integer> last = getLastMonthViews();

        double currentSum = 0;
        double lastSum = 0;

        if (last == null) {
            if (lastValue) {
                return 0;
            } else {
                return "Dati insufficienti per un confronto";
            }
        } else {

            for (int i : current.values()) {
                currentSum += i;
            }
            for (int i : last.values()) {
                lastSum += i;
            }

            double diff;

            if (lastSum == 0) {
                diff = currentSum;
            } else {
                diff = (((currentSum - lastSum) / lastSum) * 100);
            }

            int diffI = (int) Math.round(diff);
            if (lastValue) {
                return (int) Math.round(lastSum);
            } else {
                return diffI;
            }
        }
    }

    @Override
    public Map<String, Integer> getPagesViews() throws DAOException {
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select pagina, sum(views) as views from weeks_views group by pagina")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("pagina"), rs.getInt("views"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dati;
    }

    @Override
    public Map<String, Integer> getMonthEmailSub(boolean isLast) throws DAOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        if (isLast) {
            today = today.withMonth(today.getMonthValue() - 1);
        }
        LocalDate previousMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        if (today.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())) {
            previousMonday = today;
        }
        LocalDate secondMonday = previousMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate thirdMonday = secondMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate fourthMonday = thirdMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        //System.out.println("TODAY: " + today + "\nFIRST: " + previousMonday + "\n SECOND: " + secondMonday + "\nTHIRD: " + thirdMonday + "\nFOURTH: " + fourthMonday);

        int fourth = 0, third = 0, second = 0, first = 0;

        ArrayList<LocalDate> date = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select date from email_sub")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    date.add(rs.getDate("date").toLocalDate());

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        for (LocalDate d : date) {

            if (d.isAfter(fourthMonday.minusDays(1)) && d.isBefore(thirdMonday)) {

                fourth++;
            } else if (d.isAfter(thirdMonday.minusDays(1)) && d.isBefore(secondMonday)) {

                third++;
            } else if (d.isAfter(secondMonday.minusDays(1)) && d.isBefore(previousMonday)) {

                second++;
            } else if (d.isAfter(previousMonday.minusDays(1)) && d.isBefore(today.plusDays(1))) {

                first++;
            }
        }
        Map<String, Integer> map = new TreeMap<>();
        map.put(previousMonday.format(formatter), first);
        map.put(secondMonday.format(formatter), second);
        map.put(thirdMonday.format(formatter), third);
        map.put(fourthMonday.format(formatter), fourth);

        return map;
    }

    @Override
    public int getEmailChanges(boolean lastValue) throws DAOException {
        Map<String, Integer> current = getMonthEmailSub(false);
        Map<String, Integer> last = getMonthEmailSub(true);
        double currentSum = 0;
        double lastSum = 0;
        for (int i : current.values()) {
            currentSum += i;
        }
        for (int i : last.values()) {
            lastSum += i;
        }
        /*System.out.println("1: " + currentSum);
        System.out.println("2: " + lastSum);*/
        double diff;
        if (lastSum == 0) {
            diff = 100;
        } else {
            diff = (((currentSum - lastSum) / lastSum) * 100);
        }
        int diffI = (int) Math.round(diff);

        if (lastValue) {
            return (int) Math.round(lastSum);
        } else {
            return diffI;
        }
    }

    @Override
    public String getTotalEmailSub() throws DAOException {
        int total = 0;
        String totale;
        try (PreparedStatement stm = CON.prepareStatement("select * from email_sub")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    total++;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        totale = "" + total;
        return totale;
    }

    @Override
    public Map<String, Double> getMonthRevenue(boolean isLast) throws DAOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        if (isLast) {
            today = today.withMonth(today.getMonthValue() - 1);
        }
        LocalDate previousMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        if (today.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())) {
            previousMonday = today;
        }
        LocalDate secondMonday = previousMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate thirdMonday = secondMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate fourthMonday = thirdMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

        double fourth = 0, third = 0, second = 0, first = 0;
        LocalDate d;

        try (PreparedStatement stm = CON.prepareStatement("select * from orderSum")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    d = rs.getDate("date").toLocalDate();
                    if (d.isAfter(fourthMonday.minusDays(1)) && d.isBefore(thirdMonday)) {

                        fourth += rs.getDouble("totale");
                    } else if (d.isAfter(thirdMonday.minusDays(1)) && d.isBefore(secondMonday)) {

                        third += rs.getDouble("totale");
                    } else if (d.isAfter(secondMonday.minusDays(1)) && d.isBefore(previousMonday)) {

                        second += rs.getDouble("totale");
                    } else if (d.isAfter(previousMonday.minusDays(1)) && d.isBefore(today.plusDays(1))) {

                        first += rs.getDouble("totale");

                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCProductDAO.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Map<String, Double> map = new TreeMap<>();
        map.put(previousMonday.format(formatter), first);
        map.put(secondMonday.format(formatter), second);
        map.put(thirdMonday.format(formatter), third);
        map.put(fourthMonday.format(formatter), fourth);

        return map;
    }

    @Override
    public Object getRevenueChanges(boolean lastValue) throws DAOException {
        Map<String, Double> current = getMonthRevenue(false);
        Map<String, Double> last = getMonthRevenue(true);
        if (last == null) {
            if (lastValue) {
                return 0;
            } else {
                return "Dati insufficienti per un confronto";
            }
        } else {

            double currentSum = 0;
            double lastSum = 0;
            for (double i : current.values()) {
                currentSum += i;
            }
            for (double i : last.values()) {
                lastSum += i;
            }

            /*System.out.println("1: " + currentSum);
            System.out.println("2: " + lastSum);*/
            double diff;
            DecimalFormat df = new DecimalFormat(".##");
            if (lastSum == 0) {
                diff = currentSum;
            } else {
                diff = (((currentSum - lastSum) / lastSum) * 100);
            }

            if (lastValue) {
                return Double.parseDouble(df.format(lastSum).replace(",", "."));
            } else {
                return Double.parseDouble(df.format(diff).replace(",", "."));
            }
        }
    }

    @Override
    public String getTotalRevenue() throws DAOException {
        double total = 0.00;
        String totale;
        try (PreparedStatement stm = CON.prepareStatement("select * from orderSum")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    total += rs.getDouble("totale");

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        totale = String.format("%.2f", total);
        return totale;
    }

    @Override
    public String getLastEmailSub() throws DAOException {
        LocalDateTime data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try (PreparedStatement stm = CON.prepareStatement("SELECT MAX(date) as data FROM email_sub")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    data = rs.getTimestamp("data").toLocalDateTime();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (data != null) {
            return data.format(formatter);
        }
        return null;
    }

    @Override
    public String getLastRevenue() throws DAOException {
        LocalDateTime data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try (PreparedStatement stm = CON.prepareStatement("SELECT MAX(date) as data FROM orderSum")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    data = rs.getTimestamp("data").toLocalDateTime();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (data != null) {
            return data.format(formatter);
        }
        return null;
    }

    @Override
    public Map<String, Integer> getProductBuy() throws DAOException {
        HashMap<String, Integer> dati = new HashMap<>();
        boolean check = false;
        try (PreparedStatement stm = CON.prepareStatement("select nome, num_acquisti from prodotto")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if(rs.getInt("num_acquisti") != 0){
                        dati.put(rs.getString("nome"), rs.getInt("num_acquisti"));
                    }else{
                        check = true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(dati.isEmpty())
            dati.put("Tutti (0%)", 1);
        else if(check)
            dati.put("Altri (0%)", 0);
        Map<String, Integer> map = new TreeMap<>(dati);
        return map;
    }
}
