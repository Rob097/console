/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.ConsoleDAO;
import database.entities.Ordine;
import database.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("SELECT categoria, sum(views) as views from blog group by categoria")) {
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
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select nome, views from blog where categoria = ?")) {
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
        HashMap<String, Integer> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select nome, views from ricette where categoria = ?")) {
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
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select blog.categoria, AVG(valutazione_blog.value) as rate from blog INNER JOIN valutazione_blog on blog.id = valutazione_blog.id_blog group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate);
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
                    String rateS = String.format("%.2f", rate);
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
        HashMap<String, Double> dati = new HashMap<>();
        String categoria = "";
        try (PreparedStatement stm = CON.prepareStatement("select prodotto.categoria, AVG(valutazione_prod.value) as rate from prodotto INNER JOIN valutazione_prod on prodotto.id = valutazione_prod.id_prod group by categoria;")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate);
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
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select blog.nome, valutazione_blog.value as rate from blog INNER JOIN valutazione_blog on blog.id = valutazione_blog.id_blog where categoria = ?;")) {
            stm.setString(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate);
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
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select ricette.nome, valutazione_ricetta.value as rate from ricette INNER JOIN valutazione_ricetta on ricette.id = valutazione_ricetta.id_ricetta where categoria = ?;")) {
            stm.setBoolean(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate);
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
        HashMap<String, Double> dati = new HashMap<>();
        try (PreparedStatement stm = CON.prepareStatement("select prodotto.nome, valutazione_prod.value as rate from prodotto INNER JOIN valutazione_prod on prodotto.id = valutazione_prod.id_prod where categoria = ?;")) {
            stm.setString(1, categoria);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double rate = rs.getDouble("rate");
                    String rateS = String.format("%.2f", rate);
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

        double number = 0.00;

        try (PreparedStatement stm = CON.prepareStatement("select sum(totale) as tot from orderSum where delivery = ?")) {
            stm.setString(1, type);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Double tot = rs.getDouble("tot");
                    String totS = String.format("%.2f", tot);
                    number = Double.parseDouble(totS);
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
                    String[] prodotti = rs.getString("items").split(":");
                    ordine.getProdotti().addAll(Arrays.asList(prodotti));
                    ordine.setTot(rs.getDouble("totale"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordine;
    }
    
    @Override
    public ArrayList<Ordine> getOrdersOfType(String type) throws DAOException {
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
                    String[] prodotti = rs.getString("items").split(":");
                    ordine.getProdotti().addAll(Arrays.asList(prodotti));
                    ordine.setTot(rs.getDouble("totale"));
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

}
