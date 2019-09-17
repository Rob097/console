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
import database.entities.Variante;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import static varie.costanti.MAX_COSTO;
import static varie.costanti.MAX_PRICE;
import static varie.costanti.MAX_W_PRICE;
import static varie.costanti.MED_COSTO;
import static varie.costanti.MED_PRICE;
import static varie.costanti.MED_W_PRICE;
import static varie.costanti.MIN_COSTO;
import static varie.costanti.MIN_PRICE;
import static varie.costanti.MIN_W_PRICE;

/**
 *
 * @author Roberto97
 */
public class JDBCConsoleDAO extends JDBCDAO implements ConsoleDAO {

    /**
     * Questo è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     * @param con
     * @throws SQLException
     */
    public JDBCConsoleDAO(Connection con) throws SQLException {
        super(con);
        try {
            checkCON();
        } catch (DAOException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Controlla che la connessione con il DB sia aperta, altrimenti la riapre
     * @throws DAOException
     */
    @Override
    public final void checkCON() throws DAOException {
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

    /**
     * Metodo che ritorna il numero di visualizzazioni complessive del sito
     * ci sono nella settimana corrente, partendo dal lunedì
     * @return
     * @throws DAOException
     */
    @Override
    public int getWeekViews() throws DAOException {
        checkCON();
        Calendar calendar = Calendar.getInstance();
        LocalDate monday;
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        if (ourJavaDateObject.toLocalDate().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            monday = ourJavaDateObject.toLocalDate();
        } else {
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

    /**
     * Metodo che ritorna le visualizzazioni complessive del sito in tutto il mese.<br>
     * Divide le ultime 4 settimane dove l'ultima è la corrente, e ad ogni settimana
     * associa le rispettive visualizzazioni.
     * @return
     * @throws DAOException
     */
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

    /**
     * Metodo che ritorna le visualizzazioni complessive del sito in tutto il mese scorso.<br>
     * Divide le 4 settimane, e ad ogni settimana associa le rispettive visualizzazioni.
     * @return
     * @throws DAOException
     */
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

    /**
     * Compara le visualizioni del mese scorso con quelle di questo mese
     * e ritorna la differenza sia positiva che negativa
     * @param lastValue
     * @return
     * @throws DAOException
     */
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

    /**
     * ritorna le visualizzazioni complessive per ogi pagina del sito
     * @return
     * @throws DAOException
     */
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

    /**
     * ritorna il numero di iscrizioni alla newsletter nelle 4 settimane correnti
     * o nelle 4 settimane precedenti
     * @param isLast
     * @return
     * @throws DAOException
     */
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

    /**
     * fa il confronto delle iscrizioni alla nesletter nelle 4 settimane precedenti
     * e in quelle correnti.
     * @param lastValue
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna il numero complessivo degli iscritti alla newsletter
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna le entrate nelle 4 settimane correnti o passate dividendoli per settimane
     * e associando ad ogni settimana il rispettivo importo
     * @param isLast
     * @return
     * @throws DAOException
     */
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

    /**
     * Confronta le entrate nelle 4 settimane scorse con quelle correnti e ritorna
     * la differenza sia positiva che negativa
     * @param lastValue
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna le entrate complessivamente.
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la data dell'ultima iscrizione alla neswletter
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la data dell'ultimo acquisto
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna tutti i prodotti che sono stati acquistati almento una volta 
     * e il numero di volte che seono stati acquistati
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna le visualizzazioni degli articoli del blog associandoli per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna le visualizzazioni delle idee associandole per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna le visualizzazioni dei blog di una determinata categoria che sono 
     * maggiori di 0
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna le visualizzazioni delle idee di una determinata categoria che sono 
     * maggiori di 0
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna i commenti degli articoli del blog associandoli per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna i commenti delle idee associandole per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna i commenti dei blog di una determinata categoria
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna i commenti delle idee di una determinata categoria
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la valutazione degli articoli del blog associandoli per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la valutazione delle idee associandole per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la valutazione dei prodotti associandoli per categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la valutazione deglia rticoli del blog di una certa categoria
     * che hanno almeno una valutazione
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la valutazione delle idee di una certa categoria che hanno almeno
     * una valutazione
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna la valutazione dei prodotti di una certa categoria che hanno
     * almeno una valutazione
     * @param categoria
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna tutti i vari tipi di spedizione
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna il numero di ordini per un determinato tipo di spedizione
     * @param type
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna l'importo totale di tutti gli ordini di un determinato tipo di spedizione
     * @param type
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna una variante a partire dall'id
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Variante getVariant(int id) throws DAOException {
        checkCON();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM products_variants where id = ?")) {
            stm.setInt(1, id);
            Variante v = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    v = new Variante();
                    v.setId(rs.getInt("id"));
                    v.setId_prod(rs.getInt("idProd"));
                    v.setVariant(rs.getString("variant"));
                    v.setVariantName(rs.getString("variantName"));
                    v.setSupplement(rs.getDouble("supplement"));
                }
                return v;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Ritorna tutte le varianti partendo dalla stringa con la concatenzaione degli id
     * @param var
     * @return
     * @throws DAOException
     */
    @Override
    public LinkedHashMap<ArrayList<Variante>, Integer> getVariants(String var) throws DAOException {

        LinkedHashMap<ArrayList<Variante>, Integer> varianti = new LinkedHashMap<>();

        ArrayList<Variante> prodVars;
        String[] blocchi;
        String[] vars;
        String[] ids;
        int q;

        if (var != null) {
            blocchi = var.split(":");
            if (blocchi != null && blocchi.length > 0) {
                for (String s : blocchi) {
                    prodVars = new ArrayList<>();
                    vars = s.split("\\*");
                    if (vars != null && vars.length > 1) {
                        ids = vars[0].split("_");
                        q = Integer.parseInt(vars[1]);
                        if (ids != null && ids.length > 0) {
                            for (String f : ids) {
                                prodVars.add(getVariant(Integer.parseInt(f)));
                            }
                        }
                        varianti.put(prodVars, q);
                    }
                }
            }
        }

        return varianti;
    }

    /**
     * Controlla se un ordine contiene almeno un prodotto con delle varianti
     * @param idOrder
     * @param idProd
     * @return
     * @throws DAOException
     */
    @Override
    public boolean orderContainProdVariant(String idOrder, int idProd) throws DAOException {
        LinkedHashMap<ArrayList<Variante>, Integer> varianti = getOrder(idOrder).getVarianti();

        for (Map.Entry<ArrayList<Variante>, Integer> entry : varianti.entrySet()) {
            ArrayList<Variante> key = entry.getKey();
            if (key != null) {
                for (Variante v : key) {
                    if (v != null && v.getId_prod() == idProd) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * ritorna l'ultimo ordine fatto con un determinato tipo di spedizione
     * @param type
     * @return
     * @throws DAOException
     */
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
                    ordine.setVarianti(getVariants(rs.getString("varianti")));
                    ordine.setTot(rs.getDouble("totale"));
                    ordine.setStato(rs.getString("stato"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordine;
    }

    /**
     * Ritorna un determinato ordien partendo dall'id
     * @param id
     * @return
     * @throws DAOException
     */
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
                    ordine.setVarianti(getVariants(rs.getString("varianti")));
                    ordine.setTot(rs.getDouble("totale"));
                    ordine.setStato(rs.getString("stato"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordine;
    }

    /**
     * Ritorna tutti gli ordini fatyti con un determinato tipo di spedizione
     * @param type
     * @return
     * @throws DAOException
     */
    @Override
    public ArrayList<Ordine> getOrdersOfType(String type) throws DAOException {
        checkCON();
        ArrayList<Ordine> ordini = new ArrayList<>();
        Ordine ordine;

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
                    ordine.setVarianti(getVariants(rs.getString("varianti")));
                    ordine.setTot(rs.getDouble("totale"));
                    ordine.setStato(rs.getString("stato"));
                    ordini.add(ordine);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!ordini.isEmpty()) {
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

    /**
     * Ritorna tutti i prodotti di un determinato ordine
     * @param prodotti
     * @param request
     * @return
     * @throws DAOException
     */
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
                for (int i = 0; i < nomi.size(); i++) {
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
        } catch (DAOException | NumberFormatException s) {
            return products;
        }
    }

    /**
     * Metodo per aggiornare lo stato dell'ordine
     * @param id
     * @param stato
     * @throws DAOException
     */
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

    /**
     * Metodo che restituisce i costi di spedizione di un ordine
     * @param prodotti
     * @param request
     * @return
     * @throws DAOException
     */
    @Override
    public double getOrderDeliveryCost(ArrayList<String> prodotti, HttpServletRequest request) throws DAOException {
        checkCON();

        ArrayList<Prodotto> products = getProdOfOrder(prodotti, request);
        ArrayList<Prodotto> freschi = new ArrayList<>();
        LinkedHashMap<ArrayList<Variante>, Integer> varianti;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        double totale = 0.00, varTot;

        try {
            if (products != null) {
                for (Prodotto p : products) {
                    if (p != null) {
                        varianti = getCartProductVariant(request, p.getId());

                        if (varianti != null && !varianti.isEmpty()) {
                            for (Map.Entry<ArrayList<Variante>, Integer> entry : varianti.entrySet()) {
                                varTot = 0.00;
                                ArrayList<Variante> key = entry.getKey();
                                Integer value = entry.getValue();
                                for (Variante v : key) {
                                    varTot += v.getSupplement();
                                }
                                totale += (df.parse(p.getCosto()).doubleValue() + varTot) * value;
                            }
                        } else {
                            if (p.getCosto() != null) {
                                totale += (df.parse(p.getCosto()).doubleValue() * p.getQuantita());
                            }
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String costo = String.format("%.2f", totale).replace(",", ".");

        if (products != null) {
            for (Prodotto p : products) {
                if (p.isFresco()) {
                    freschi.add(p);
                }
            }
        }

        if (costo == null || costo.isEmpty()) {
            return 0.0;
        } else {
            df.setMaximumFractionDigits(2);
            double costoN = 0.00;
            String spedizione = null;

            try {
                costoN += (df.parse(costo).doubleValue());
                if (freschi.isEmpty()) {
                    if (costoN <= MIN_COSTO) {
                        spedizione = "" + MIN_PRICE;
                    } else if (costoN > MIN_COSTO && costoN <= MED_COSTO) {
                        spedizione = "" + MED_PRICE;
                    } else if (costoN > MED_COSTO && costoN < MAX_COSTO) {
                        spedizione = "" + MAX_PRICE;
                    } else if (costoN >= MAX_COSTO) {
                        spedizione = "0.00";
                    }
                } else {
                    if (costoN <= MIN_COSTO) {
                        spedizione = "" + (MIN_PRICE + MIN_W_PRICE);
                    } else if (costoN > MIN_COSTO && costoN <= MED_COSTO) {
                        spedizione = "" + (MED_PRICE + MED_W_PRICE);
                    } else if (costoN > MED_COSTO && costoN < MAX_COSTO) {
                        spedizione = "" + (MAX_PRICE + MAX_W_PRICE);
                    } else if (costoN >= MAX_COSTO) {
                        spedizione = String.format("%.2f", MAX_W_PRICE).replace(",", ".");
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Double.parseDouble(spedizione.replace(",", "."));
        }

    }

    /**
     * MEtodo che indica che tipo di box serve per l'ordine
     * @param totale
     * @param prodotti
     * @param request
     * @return
     * @throws DAOException
     */
    @Override
    public String getfreshBoxType(double totale, ArrayList<String> prodotti,
            HttpServletRequest request) throws DAOException {
        checkCON();

        String box = "";
        double totaleProducts = totale - getOrderDeliveryCost(prodotti, request);

        if (totaleProducts > 0 && totaleProducts <= MIN_COSTO) {
            box = "Box piccolo";
        } else if (totaleProducts > MIN_COSTO && totaleProducts <= MED_COSTO) {
            box = "Box medio";
        } else if (totaleProducts > MED_COSTO) {
            box = "Box grande";
        }
        return box;
    }

    /**
     * Metodo che indica il costo del box da usare per l'ordine
     * @param totale
     * @param prodotti
     * @param request
     * @return
     * @throws DAOException
     */
    @Override
    public String getfreshBoxCost(double totale, ArrayList<String> prodotti,
            HttpServletRequest request) throws DAOException {
        checkCON();

        double tot = 0.0;
        double totaleProducts = totale - getOrderDeliveryCost(prodotti, request);

        if (totaleProducts > 0 && totaleProducts <= MIN_COSTO) {
            tot = MIN_W_PRICE;
        } else if (totaleProducts > MIN_COSTO && totaleProducts <= MAX_COSTO) {
            tot = MED_W_PRICE;
        } else if (totaleProducts > MAX_COSTO) {
            tot = MAX_W_PRICE;
        }
        String totS = String.format("%.2f", tot).replace(",", ".");
        return totS;
    }

    /**
     *
     * @throws DAOException
     */
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
                System.out.println("Error Code: " + ex.getErrorCode());
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            System.out.println("Error Code: " + ex.getErrorCode());
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

    /**
     * Ritorna tutte le email iscritte alla newsletter
     * @return
     * @throws DAOException
     */
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

    /**
     * Elimina una email dalle iscrizioni alla newsletter
     * @param email
     * @throws DAOException
     */
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

    /**
     * Ritorna il numero di ordini di un determinato tipo di spedizione in un
     * determinato stato
     * @param stato
     * @param tipo
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna tutte le notifiche
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna una particolare notifica
     * @param id
     * @return
     * @throws DAOException
     */
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

    /**
     * Ritorna tutte le notifiche di un determinato tipo
     * @param testo
     * @return
     * @throws DAOException
     */
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

    /**
     * Elimina un notifica specifica
     * @param id
     * @throws DAOException
     */
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

    /**
     * Elimina tutte le notifiche
     * @throws DAOException
     */
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

    /**
     * Ritorna tutte le varianti salvate e aggiunte al carrello
     * @param request
     * @return
     * @throws DAOException
     */
    @Override
    public LinkedHashMap<ArrayList<Variante>, Integer> getCartVariant(HttpServletRequest request) throws DAOException {
        checkCON();

        ProductDAO productdao = (ProductDAO) request.getSession().getAttribute("productdao");
        ArrayList<Variante> varianti;
        LinkedHashMap<ArrayList<Variante>, Integer> variants = new LinkedHashMap<>();;
        Variante v;
        Prodotto p;
        Cookie[] cookies = request.getCookies();
        String arrayString = null;
        String[] blocchi = null;
        String[] VarQuan;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("varianti")) {
                    arrayString = c.getValue();
                }
            }
        }
        if (arrayString != null) {
            blocchi = arrayString.split(":");
        }

        if (blocchi != null) {
            for (String blocchi1 : blocchi) {
                if (blocchi1 != null && !blocchi1.equals("")) {
                    varianti = new ArrayList<>();
                    VarQuan = blocchi1.split("\\*");
                    String vars = VarQuan[0];
                    int q = 1;
                    if (VarQuan != null && VarQuan.length == 2) {
                        q = Integer.parseInt(VarQuan[1]);
                    }
                    String[] var = vars.split("_");
                    for (String s : var) {
                        if (!s.equals("")) {
                            v = getVariant(Integer.parseInt(s));
                            if (v != null) {
                                p = productdao.getProduct(v.getId_prod());
                                if (p != null) {
                                    if (p.isDisponibile()) {
                                        varianti.add(v);
                                    }
                                }
                            }
                        }

                    }
                    variants.put(varianti, q);
                }
            }
        }

        if (!variants.isEmpty()) {
            return variants;
        } else {
            return null;
        }
    }

    /**
     * Ritorna tutte le varianti aggiunte al carrello di un determinato prodotto
     * @param request
     * @param idProduct
     * @return
     * @throws DAOException
     */
    @Override
    public LinkedHashMap<ArrayList<Variante>, Integer> getCartProductVariant(HttpServletRequest request, int idProduct) throws DAOException {
        checkCON();

        LinkedHashMap<ArrayList<Variante>, Integer> prodVariant = new LinkedHashMap<>();
        LinkedHashMap<ArrayList<Variante>, Integer> cartVariant = getCartVariant(request);
        if (cartVariant != null) {
            for (Map.Entry<ArrayList<Variante>, Integer> entry : cartVariant.entrySet()) {
                ArrayList<Variante> key = entry.getKey();
                Integer value = entry.getValue();

                for (Variante v : key) {
                    if (v.getId_prod() == idProduct) {
                        prodVariant.put(key, value);
                    }
                }

            }
        }
        return prodVariant;
    }

}
