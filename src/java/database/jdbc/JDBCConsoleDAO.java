/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.ConsoleDAO;
import database.daos.ProductDAO;
import database.entities.Notifica;
import database.entities.Ordine;
import database.entities.Prodotto;
import database.exceptions.DAOException;
import database.exceptions.DAOFactoryException;
import database.factories.JDBCDAOFactory;
import static database.factories.JDBCDAOFactory.DBURL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import static varie.costanti.MAX_COSTO;
import static varie.costanti.MAX_W_PRICE;
import static varie.costanti.MED_W_PRICE;
import static varie.costanti.MIN_COSTO;
import static varie.costanti.MIN_W_PRICE;

/**
 *
 * @author Roberto97
 */
public class JDBCConsoleDAO extends JDBCDAO implements ConsoleDAO {

    public JDBCConsoleDAO(Connection con) throws SQLException {
        super(con);
        try {
            checkCON();
        } catch (DAOException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void checkCON() throws DAOException {
        try {
            if (this.CON == null || this.CON.isClosed() || !this.CON.isValid(0)) {
                this.daoFactory = new JDBCDAOFactory(DBURL);
                this.CON = daoFactory.getConnection();
            }
        } catch (SQLException | DAOFactoryException ex) {
            System.out.println("console jdbc checkCON catch");
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getWeekViews() throws DAOException {
        checkCON();
        Calendar calendar = Calendar.getInstance();
        LocalDate monday = LocalDate.now();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        if(ourJavaDateObject.toLocalDate().getDayOfWeek().equals(DayOfWeek.MONDAY)){
            monday = ourJavaDateObject.toLocalDate();
        }else{
            monday = ourJavaDateObject.toLocalDate().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }
        int views = 0;
        try (PreparedStatement stm = CON.prepareStatement("select SUM(views) AS views from weeks_views where week = ?")) {
            stm.setDate(1, java.sql.Date.valueOf(monday));
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
        checkCON();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate previousMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        if (today.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())) {
            previousMonday = today;
        }
        LocalDate secondMonday = previousMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate thirdMonday = secondMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate fourthMonday = thirdMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        //System.out.println("TODAY: " + today + "\nFIRST: " + previousMonday + "\n SECOND: " + secondMonday + "\nTHIRD: " + thirdMonday + "\nFOURTH: " + fourthMonday);

        int fourth = 0, third = 0, second = 0, first = 0;

        Map<LocalDate, Integer> date = new TreeMap<>();

        try (PreparedStatement stm = CON.prepareStatement("select week, SUM(views) AS views from weeks_views group by week desc")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    date.put(rs.getDate("week").toLocalDate(), rs.getInt("views"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Iterator<Map.Entry<LocalDate, Integer>> it = date.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<LocalDate, Integer> v = it.next();

            if (v.getKey().isAfter(fourthMonday.minusDays(1)) && v.getKey().isBefore(thirdMonday)) {
                fourth += v.getValue();
            } else if (v.getKey().isAfter(thirdMonday.minusDays(1)) && v.getKey().isBefore(secondMonday)) {
                third += v.getValue();
            } else if (v.getKey().isAfter(secondMonday.minusDays(1)) && v.getKey().isBefore(previousMonday)) {
                second += v.getValue();
            } else if (v.getKey().isAfter(previousMonday.minusDays(1)) && v.getKey().isBefore(today.plusDays(1))) {
                first += v.getValue();
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
    public Map<String, Integer> getLastMonthViews() throws DAOException {
        checkCON();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        today = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(4);

        LocalDate previousMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        if (today.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())) {
            previousMonday = today;
        }
        LocalDate secondMonday = previousMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate thirdMonday = secondMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate fourthMonday = thirdMonday.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        //System.out.println("TODAY: " + today + "\nFIRST: " + previousMonday + "\n SECOND: " + secondMonday + "\nTHIRD: " + thirdMonday + "\nFOURTH: " + fourthMonday);

        int fourth = 0, third = 0, second = 0, first = 0;

        Map<LocalDate, Integer> date = new TreeMap<>();

        try (PreparedStatement stm = CON.prepareStatement("select week, SUM(views) AS views from weeks_views group by week desc")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    date.put(rs.getDate("week").toLocalDate(), rs.getInt("views"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        Iterator<Map.Entry<LocalDate, Integer>> it = date.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<LocalDate, Integer> v = it.next();

            if (v.getKey().isAfter(fourthMonday.minusDays(1)) && v.getKey().isBefore(thirdMonday)) {
                fourth += v.getValue();
            } else if (v.getKey().isAfter(thirdMonday.minusDays(1)) && v.getKey().isBefore(secondMonday)) {
                third += v.getValue();
            } else if (v.getKey().isAfter(secondMonday.minusDays(1)) && v.getKey().isBefore(previousMonday)) {
                second += v.getValue();
            } else if (v.getKey().isAfter(previousMonday.minusDays(1)) && v.getKey().isBefore(today.plusDays(1))) {
                first += v.getValue();
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
    public Object getViewsChanges(boolean lastValue) throws DAOException {
        checkCON();
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
        checkCON();
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
        checkCON();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        if (isLast) {
            today = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(4);
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
        checkCON();
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
        checkCON();
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
        checkCON();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate today = LocalDate.now();
        if (isLast) {
            today = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(4);
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
        checkCON();
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
        checkCON();
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
        checkCON();
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
        checkCON();
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
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        boolean check = false;
        try (PreparedStatement stm = CON.prepareStatement("select nome, num_acquisti from prodotto")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt("num_acquisti") != 0) {
                        dati.put(rs.getString("nome"), rs.getInt("num_acquisti"));
                    } else {
                        check = true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (dati.isEmpty()) {
            dati.put("Tutti (0%)", 1);
        } else if (check) {
            dati.put("Altri (0%)", 0);
        }
        Map<String, Integer> map = new TreeMap<>(dati);
        return map;
    }

    @Override
    public Map<String, Integer> getBlogCatViews() throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT categoria, sum(views) as views from blog where views > 0 group by categoria")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("categoria"), rs.getInt("views"));
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
    public Map<String, Integer> getRecipeCatViews() throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        String categoria = "";
        try (PreparedStatement stm = CON.prepareStatement("select categoria, sum(views) as views from ricette group by categoria")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if (rs.getBoolean("categoria")) {
                        categoria = "Nostre";
                    } else {
                        categoria = "Utenti";
                    }
                    dati.put(categoria, rs.getInt("views"));
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
    public Map<String, Integer> getBlogArtViews(String categoria) throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select nome, views from blog where categoria = ? AND views > 0")) {
            stm.setString(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("nome"), rs.getInt("views"));
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
    public Map<String, Integer> getRecipeArtViews(boolean categoria) throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select nome, views from ricette where categoria = ? AND views > 0")) {
            stm.setBoolean(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("nome"), rs.getInt("views"));
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
    public Map<String, Integer> getBlogCatComments() throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT blog.categoria, COUNT(blog.id) as commenti FROM blog_commenti INNER JOIN blog ON blog.id = blog_commenti.id_blog group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("categoria"), rs.getInt("commenti"));
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
    public Map<String, Integer> getRecipeCatComments() throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        String categoria = "";
        try (PreparedStatement stm = CON.prepareStatement("SELECT ricette.categoria, COUNT(ricette.id) as commenti FROM commenti INNER JOIN ricette ON ricette.id = commenti.id_ricetta group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if (rs.getBoolean("categoria")) {
                        categoria = "Nostre";
                    } else {
                        categoria = "Utenti";
                    }
                    dati.put(categoria, rs.getInt("commenti"));
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
    public Map<String, Integer> getBlogArtComments(String categoria) throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT blog.nome, COUNT(blog.id) as commenti FROM blog_commenti INNER JOIN blog ON blog.id = blog_commenti.id_blog where categoria = ? group by blog.id;")) {
            stm.setString(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("nome"), rs.getInt("commenti"));
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
    public Map<String, Integer> getRecipeArtComments(boolean categoria) throws DAOException {
        checkCON();
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT ricette.nome, COUNT(ricette.id) as commenti FROM commenti INNER JOIN ricette ON ricette.id = commenti.id_ricetta where categoria = ? group by ricette.id")) {
            stm.setBoolean(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    dati.put(rs.getString("nome"), rs.getInt("commenti"));
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
    public Map<String, Double> getBlogCatRate() throws DAOException {
        checkCON();
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select blog.categoria, AVG(valutazione_blog.value) as rate from blog INNER JOIN valutazione_blog on blog.id = valutazione_blog.id_blog group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate).replace(",", ".");
                    rate = Double.parseDouble(rateS);
                    dati.put(rs.getString("categoria"), rate);
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
    public Map<String, Double> getRecipeCatRate() throws DAOException {
        checkCON();
        HashMap<String, Double> dati = new HashMap<>();
        String categoria = "";
        try (PreparedStatement stm = CON.prepareStatement("select ricette.categoria, AVG(valutazione_ricetta.value) as rate from ricette INNER JOIN valutazione_ricetta on ricette.id = valutazione_ricetta.id_ricetta group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    if (rs.getBoolean("categoria")) {
                        categoria = "Nostre";
                    } else {
                        categoria = "Utenti";
                    }
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate).replace(",", ".");
                    rate = Double.parseDouble(rateS);
                    dati.put(categoria, rate);
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
    public Map<String, Double> getProductCatRate() throws DAOException {
        checkCON();
        HashMap<String, Double> dati = new HashMap<>();
        String categoria = "";
        try (PreparedStatement stm = CON.prepareStatement("select prodotto.categoria, AVG(valutazione_prod.value) as rate from prodotto INNER JOIN valutazione_prod on prodotto.id = valutazione_prod.id_prod group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate).replace(",", ".");
                    rate = Double.parseDouble(rateS);
                    dati.put(rs.getString("categoria"), rate);
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
    public Map<String, Double> getBlogArtRate(String categoria) throws DAOException {
        checkCON();
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select blog.nome, valutazione_blog.value as rate from blog INNER JOIN valutazione_blog on blog.id = valutazione_blog.id_blog where categoria = ?;")) {
            stm.setString(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate).replace(",", ".");
                    rate = Double.parseDouble(rateS);
                    dati.put(rs.getString("nome"), rate);
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
    public Map<String, Double> getRecipeArtRate(boolean categoria) throws DAOException {
        checkCON();
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select ricette.nome, valutazione_ricetta.value as rate from ricette INNER JOIN valutazione_ricetta on ricette.id = valutazione_ricetta.id_ricetta where categoria = ?;")) {
            stm.setBoolean(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate).replace(",", ".");
                    rate = Double.parseDouble(rateS);
                    dati.put(rs.getString("nome"), rate);
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
    public Map<String, Double> getProductArtRate(String categoria) throws DAOException {
        checkCON();
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select prodotto.nome, valutazione_prod.value as rate from prodotto INNER JOIN valutazione_prod on prodotto.id = valutazione_prod.id_prod where categoria = ?;")) {
            stm.setString(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate).replace(",", ".");
                    rate = Double.parseDouble(rateS);
                    dati.put(rs.getString("nome"), rate);
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
    public ArrayList<String> getTypeDelivery() throws DAOException {
        checkCON();
        ArrayList<String> tipi = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select delivery from orderSum group by delivery order by max(date) desc")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    tipi.add(rs.getString("delivery"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return tipi;
    }

    @Override
    public int getNumberOfType(String type) throws DAOException {
        checkCON();

        int number = 0;

        try (PreparedStatement stm = CON.prepareStatement("select count(id) as number from orderSum where delivery = ?")) {
            stm.setString(1, type);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    number = Integer.parseInt(rs.getString("number"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return number;
    }

    @Override
    public double getTotOfType(String type) throws DAOException {
        checkCON();

        double number = 0.00;

        try (PreparedStatement stm = CON.prepareStatement("select sum(totale) as tot from orderSum where delivery = ?")) {
            stm.setString(1, type);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double tot = rs.getDouble("tot");
                    String totS = String.format("%.2f", tot);
                    number = Double.parseDouble(totS.replace(",", "."));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return number;

    }

    @Override
    public Ordine getLastOfType(String type) throws DAOException {
        checkCON();
        Ordine ordine = null;
        try (PreparedStatement stm = CON.prepareStatement("select * from orderSum where delivery = ? order by date desc limit 1")) {
            stm.setString(1, type);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ordine = new Ordine();
                    ordine.setId(rs.getString("id"));
                    ordine.setData(rs.getTimestamp("date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    ordine.setNome(rs.getString("nome"));
                    ordine.setEmail(rs.getString("email"));
                    ordine.setCitta(rs.getString("citta"));
                    ordine.setIndirizzo(rs.getString("indirizzo"));
                    ordine.setZip(rs.getString("zip"));
                    ordine.setTipo(rs.getString("delivery"));
                    ordine.setProdotti(new ArrayList<>());
                    String[] prodotti = rs.getString("items").split(";");
                    ordine.getProdotti().addAll(Arrays.asList(prodotti));
                    ordine.setTot(rs.getDouble("totale"));
                    ordine.setStato(rs.getString("stato"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordine;
    }

    @Override
    public Ordine getOrder(String id) throws DAOException {
        checkCON();
        Ordine ordine = null;
        try (PreparedStatement stm = CON.prepareStatement("select * from orderSum where id = ?")) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ordine = new Ordine();
                    ordine.setId(rs.getString("id"));
                    ordine.setData(rs.getTimestamp("date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    ordine.setNome(rs.getString("nome"));
                    ordine.setEmail(rs.getString("email"));
                    ordine.setCitta(rs.getString("citta"));
                    ordine.setIndirizzo(rs.getString("indirizzo"));
                    ordine.setZip(rs.getString("zip"));
                    ordine.setTipo(rs.getString("delivery"));
                    ordine.setProdotti(new ArrayList<>());
                    String[] prodotti = rs.getString("items").split(";");
                    ordine.getProdotti().addAll(Arrays.asList(prodotti));
                    ordine.setTot(rs.getDouble("totale"));
                    ordine.setStato(rs.getString("stato"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordine;
    }

    @Override
    public ArrayList<Ordine> getOrdersOfType(String type) throws DAOException {
        checkCON();
        ArrayList<Ordine> ordini = new ArrayList<>();
        Ordine ordine = null;

        try (PreparedStatement stm = CON.prepareStatement("select * from orderSum where delivery = ?")) {
            stm.setString(1, type);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ordine = new Ordine();
                    ordine.setId(rs.getString("id"));
                    ordine.setData(rs.getTimestamp("date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    ordine.setNome(rs.getString("nome"));
                    ordine.setEmail(rs.getString("email"));
                    ordine.setCitta(rs.getString("citta"));
                    ordine.setIndirizzo(rs.getString("indirizzo"));
                    ordine.setZip(rs.getString("zip"));
                    ordine.setTipo(rs.getString("delivery"));
                    ordine.setProdotti(new ArrayList<>());
                    String[] prodotti = rs.getString("items").split(";");
                    ordine.getProdotti().addAll(Arrays.asList(prodotti));
                    ordine.setTot(rs.getDouble("totale"));
                    ordine.setStato(rs.getString("stato"));
                    ordini.add(ordine);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ordini != null && !ordini.isEmpty()) {
            try {
                Collections.sort(ordini, (Ordine z1, Ordine z2) -> {
                    Timestamp t1 = Timestamp.valueOf(z1.getData());
                    Timestamp t2 = Timestamp.valueOf(z2.getData());
                    if (t1.after(t2)) {
                        return -1;
                    }
                    if (t1.before(t2)) {
                        return 1;
                    }
                    return 0;
                });
            } catch (Exception e) {
                System.out.println("Errore ordinamento Ordini");
            }
        }

        return ordini;
    }

    @Override
    public ArrayList<Prodotto> getProdOfOrder(ArrayList<String> prodotti, HttpServletRequest request) throws DAOException {
        checkCON();
        ArrayList<Prodotto> products = new ArrayList<>();
        try {
            ProductDAO productdao = (ProductDAO) request.getSession().getAttribute("productdao");

            if (productdao != null) {

                ArrayList<String> nomi = new ArrayList<>();
                ArrayList<String> quantita = new ArrayList<>();

                for (String split1 : prodotti) {
                    String[] s = split1.split("_");
                    if (s.length > 1) {
                        nomi.add(s[0]);
                        quantita.add(s[1]);
                    }
                }
                int z = 0;
                for (int i = 0; i < nomi.size(); i++) {
                    z++;
                    if (productdao.getProductByName(nomi.get(i)) == null) {
                        products.add(new Prodotto());
                    } else {
                        products.add(productdao.getProductByName(nomi.get(i)));
                    }
                    products.get(i).setQuantita(Integer.parseInt(quantita.get(i).replace(" ", "")));
                }

                return products;

            } else {
                System.out.println("PRODUCTDAO IS NULL");
                return null;
            }
        } catch (Exception s) {
            s.printStackTrace();
            return products;
        }
    }

    @Override
    public void setOrderStatus(String id, int stato) throws DAOException {
        checkCON();
        String statoS = "";

        switch (stato) {
            case (1):
                statoS = "preparazione";
                break;
            case (2):
                statoS = "spedito";
                break;
            case (3):
                statoS = "consegnato";
                break;
            case (4):
                statoS = "ritirato";
                break;
            case (5):
                statoS = "altro";
        }

        try (PreparedStatement stm = CON.prepareStatement("update orderSum set stato = ? where id = ?;")) {
            try {
                stm.setString(1, statoS);
                stm.setString(2, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error updating order status: " + id);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double getOrderDeliveryCost(ArrayList<String> prodotti, HttpServletRequest request) throws DAOException {
        checkCON();

        ArrayList<Prodotto> products = getProdOfOrder(prodotti, request);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        double totale = 0.00;

        try {
            if (prodotti != null) {
                for (Prodotto p : products) {
                    if (!p.isEmpty()) {
                        totale += (df.parse(p.getCosto()).doubleValue() * p.getQuantita());
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String costo = String.format("%.2f", totale);

        if (costo == null || costo.isEmpty()) {
            return 0.0;
        } else {
            df.setMaximumFractionDigits(2);
            double costoN = 0.00, tot = 0.00;
            String spedizione = null;
            String freshBox;

            try {
                costoN += (df.parse(costo).doubleValue());
                if (costoN <= MIN_COSTO) {
                    spedizione = "17.50";
                } else if (costoN > MIN_COSTO && costoN < MAX_COSTO) {
                    spedizione = "24.00";
                } else if (costoN >= MAX_COSTO) {
                    spedizione = String.format("%.2f", MAX_W_PRICE).replace(",", ".");
                }

                if (costoN >= MAX_COSTO) {
                    spedizione = String.format("%.2f", MAX_W_PRICE).replace(",", ".");
                } else {

                    if (totale > 0 && totale < MIN_COSTO) {
                        tot = MIN_W_PRICE;
                    } else if (totale >= MIN_COSTO && totale < MAX_COSTO) {
                        tot = MED_W_PRICE;
                    } else if (totale >= MAX_COSTO) {
                        tot = MAX_W_PRICE;
                    }
                    freshBox = String.format("%.2f", tot);
                    freshBox = freshBox.replace(",", ".");

                    Double sD = Double.parseDouble(spedizione);
                    Double fD = Double.parseDouble(freshBox);
                    Double tD = sD + fD;

                    spedizione = String.format("%.2f", tD);
                }

            } catch (ParseException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Double.parseDouble(spedizione.replace(",", "."));
        }

    }

    @Override
    public String getfreshBoxType(double totale, ArrayList<String> prodotti, HttpServletRequest request) throws DAOException {
        checkCON();

        String box = "";
        double totaleProducts = totale - getOrderDeliveryCost(prodotti, request);

        if (totaleProducts > 0 && totaleProducts < MIN_COSTO) {
            box = "Box piccolo";
        } else if (totaleProducts >= MIN_COSTO && totaleProducts < MAX_COSTO) {
            box = "Box medio";
        } else if (totaleProducts >= MAX_COSTO) {
            box = "Box grande";
        }
        return box;
    }

    @Override
    public String getfreshBoxCost(double totale, ArrayList<String> prodotti, HttpServletRequest request) throws DAOException {
        checkCON();

        double tot = 0.0;
        double totaleProducts = totale - getOrderDeliveryCost(prodotti, request);

        if (totaleProducts > 0 && totaleProducts < MIN_COSTO) {
            tot = MIN_W_PRICE;
        } else if (totaleProducts >= MIN_COSTO && totaleProducts < MAX_COSTO) {
            tot = MED_W_PRICE;
        } else if (totaleProducts >= MAX_COSTO) {
            tot = MAX_W_PRICE;
        }
        String totS = String.format("%.2f", tot).replace(",", ".");
        return totS;
    }

    @Override
    public void copyWeekViews() throws DAOException {
        checkCON();

        try (PreparedStatement stm = CON.prepareStatement("INSERT INTO weeks_views (pagina, week, views) SELECT pagina, ? AS date, SUM(`views`) AS views FROM curr_week_views group by pagina;")) {
            try {
                Calendar cal = Calendar.getInstance();
                Timestamp timestamp = new Timestamp(new Date().getTime());
                cal.setTimeInMillis(timestamp.getTime());
                cal.add(Calendar.HOUR, -10);
                timestamp = new Timestamp(cal.getTime().getTime());
                stm.setTimestamp(1, timestamp);
                if (stm.executeUpdate() >= 1) {
                } else {
                    throw new DAOException("impossibile to copy week views");
                };
            } catch (SQLException ex) {
                System.out.println("E rror Code: " + ex.getErrorCode());
                ex.printStackTrace();
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error Code: " + ex.getErrorCode());
            ex.printStackTrace();
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM curr_week_views;")) {
            try {
                if (stm.executeUpdate() >= 1) {
                } else {
                    throw new DAOException("Impossible to delete this week views");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<String> getAllEmail() throws DAOException {
        checkCON();
        ArrayList<String> emailS = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select email from email_sub")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    emailS.add(rs.getString("email"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailS;
    }

    @Override
    public void annullaIscrizione(String email) throws DAOException {
        checkCON();
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM email_sub where email = ?;")) {
            stm.setString(1, email);
            try {
                if (stm.executeUpdate() >= 1) {
                } else {
                    System.out.println("Impossible to delete email");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getNumberByStatusOfType(String stato, String tipo) throws DAOException {
        checkCON();

        int num = 0;

        try (PreparedStatement stm = CON.prepareStatement("select COUNT(id) as num from orderSum where delivery = ? AND stato = ?")) {
            stm.setString(1, tipo);
            stm.setString(2, stato);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    num += rs.getInt("num");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    @Override
    public ArrayList<Notifica> getAllNotifiche() throws DAOException {
        checkCON();

        ArrayList<Notifica> notifiche = new ArrayList<>();
        Notifica notifica;

        try (PreparedStatement stm = CON.prepareStatement("SELECT * from notifiche order by data desc")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    notifica = new Notifica();
                    notifica.setId(rs.getInt("id"));
                    notifica.setTesto(rs.getString("testo"));
                    notifica.setData(rs.getTimestamp("data"));
                    notifica.setLink(rs.getString("link"));
                    notifiche.add(notifica);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifiche;
    }

    @Override
    public Notifica getNotifica(int id) throws DAOException {
        checkCON();

        Notifica notifica = null;

        try (PreparedStatement stm = CON.prepareStatement("SELECT * from notifiche where id = ?")) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    notifica = new Notifica();
                    notifica.setId(rs.getInt("id"));
                    notifica.setTesto(rs.getString("testo"));
                    notifica.setData(rs.getTimestamp("data"));
                    notifica.setLink(rs.getString("link"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifica;
    }

    @Override
    public ArrayList<Notifica> getNotificheByType(String testo) throws DAOException {
        checkCON();
        ArrayList<Notifica> notifiche = new ArrayList<>();
        Notifica notifica;

        try (PreparedStatement stm = CON.prepareStatement("SELECT * from notifiche where testo = ?")) {
            stm.setString(1, testo);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    notifica = new Notifica();
                    notifica.setId(rs.getInt("id"));
                    notifica.setTesto(rs.getString("testo"));
                    notifica.setData(rs.getTimestamp("data"));
                    notifica.setLink(rs.getString("link"));
                    notifiche.add(notifica);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifiche;
    }

    @Override
    public void deleteNotifica(int id) throws DAOException {
        checkCON();
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM notifiche where id = ?;")) {
            stm.setInt(1, id);
            try {
                if (stm.executeUpdate() >= 1) {
                } else {
                    System.out.println("Impossible to delete notifica");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteALLNotifiche() throws DAOException {
        checkCON();
        try (PreparedStatement stm = CON.prepareStatement("DELETE FROM notifiche")) {
            try {
                if (stm.executeUpdate() >= 1) {
                } else {
                    System.out.println("Impossible to delete all notifiche");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
