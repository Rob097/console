/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.Notifica;
import database.entities.Ordine;
import database.entities.Prodotto;
import database.entities.Variante;
import database.exceptions.DAOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Roberto97
 */
public interface ConsoleDAO {
    public void checkCON() throws DAOException;
    
    /* VIEWS */
    public int getWeekViews() throws DAOException;
    public Map<String, Integer> getMonthViews() throws DAOException;
    public Map<String, Integer> getLastMonthViews() throws DAOException;
    public Object getViewsChanges(boolean lastValue) throws DAOException;
    public Map<String, Integer> getPagesViews() throws DAOException;
    public Map<String, Integer> getBlogCatViews() throws DAOException;
    public Map<String, Integer> getRecipeCatViews() throws DAOException;
    public Map<String, Integer> getBlogArtViews(String categoria) throws DAOException;
    public Map<String, Integer> getRecipeArtViews(boolean categoria) throws DAOException;
    public void copyWeekViews() throws DAOException;
    
    /* EMAIL */
    public Map<String, Integer> getMonthEmailSub(boolean isLast) throws DAOException;
    public int getEmailChanges(boolean lastValue) throws DAOException;
    public String getTotalEmailSub() throws DAOException;
    public String getLastEmailSub() throws DAOException;
    public ArrayList<String> getAllEmail() throws DAOException;
    public void annullaIscrizione(String email) throws DAOException;
    
    /* REVENUE */
    public Map<String, Double> getMonthRevenue(boolean isLast) throws DAOException;
    public Object getRevenueChanges(boolean lastValue) throws DAOException;
    public String getTotalRevenue() throws DAOException;
    public String getLastRevenue() throws DAOException;
    public Map<String, Integer> getProductBuy() throws DAOException;
    
    /* COMMENTS */
    public Map<String, Integer> getBlogCatComments() throws DAOException;
    public Map<String, Integer> getRecipeCatComments() throws DAOException;
    public Map<String, Integer> getBlogArtComments(String categoria) throws DAOException;
    public Map<String, Integer> getRecipeArtComments(boolean categoria) throws DAOException;
    
    /* RATE */
    public Map<String, Double> getBlogCatRate() throws DAOException;
    public Map<String, Double> getRecipeCatRate() throws DAOException;
    public Map<String, Double> getProductCatRate() throws DAOException;
    public Map<String, Double> getBlogArtRate(String categoria) throws DAOException;
    public Map<String, Double> getRecipeArtRate(boolean categoria) throws DAOException;
    public Map<String, Double> getProductArtRate(String categoria) throws DAOException;
    
    /* ORDERS */
    public ArrayList<String> getTypeDelivery() throws DAOException;
    public int getNumberOfType(String type) throws DAOException;
    public double getTotOfType(String type) throws DAOException;
    public Ordine getLastOfType(String type) throws DAOException;
    public Ordine getOrder(String id) throws DAOException;
    public ArrayList<Ordine> getOrdersOfType(String type) throws DAOException;
    public ArrayList<Prodotto> getProdOfOrder(ArrayList<String> prodotti, HttpServletRequest request) throws DAOException;
    public void setOrderStatus(String id, int stato) throws DAOException;
    public double getOrderDeliveryCost(ArrayList<String> prodotti, HttpServletRequest request) throws DAOException;
    public String getfreshBoxType(double totale, ArrayList<String> prodotti, HttpServletRequest request) throws DAOException;
    public String getfreshBoxCost(double totale, ArrayList<String> prodotti, HttpServletRequest request) throws DAOException;
    public int getNumberByStatusOfType(String stato, String tipo) throws DAOException;
    public Variante getVariant(int id) throws DAOException;
    public LinkedHashMap<ArrayList<Variante>, Integer> getVariants(String var) throws DAOException;
    public boolean orderContainProdVariant(String idOrder, int idProd) throws DAOException;
    
    /* NOTIFICHE */
    public ArrayList<Notifica> getAllNotifiche() throws DAOException;
    public Notifica getNotifica(int id) throws DAOException;
    public ArrayList<Notifica> getNotificheByType(String testo) throws DAOException;
    public void deleteNotifica(int id) throws DAOException;
    public void deleteALLNotifiche() throws DAOException;
}
