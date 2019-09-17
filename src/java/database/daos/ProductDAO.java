/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.Prodotto;
import database.entities.Variante;
import database.exceptions.DAOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author Roberto97
 */
public interface ProductDAO{ 
    public void checkCON() throws DAOException;
    
    
    public ArrayList<Prodotto> getAllProducts() throws DAOException;
    public ArrayList<Prodotto> getFreshProducts() throws DAOException;
    public ArrayList<Prodotto> getConfProducts() throws DAOException;
    public ArrayList<Prodotto> getAllProductsOfCategory(String categoryName) throws DAOException;
    public Prodotto getProduct(int id) throws DAOException;
    public Prodotto getProductByName(String name) throws DAOException;
    public int getNumberRate(int id) throws DAOException;
    public double getRate(int id) throws DAOException;
    public void deleteProd(int id) throws DAOException;
    public void alterProd(int id, String nome, String descrizione, String categoria, String immagine, boolean disponibile, double costo) throws DAOException;
    public int addProd(String nome, String descrizione, String categoria, double costo, boolean disponibile, boolean fresco) throws DAOException;
    public ArrayList<Prodotto> getNullCategoryProducts() throws DAOException;
    
    //Varianti
    public LinkedHashMap<String, ArrayList<Variante>> getProductVariant(int idProd) throws DAOException;
    public Variante getVariant(int id) throws DAOException;
    public ArrayList<Variante> getFrstVariantOfProduct(int idProd) throws DAOException;
    public String getVariantBlock(ArrayList<Variante> blocco) throws DAOException;
    public void removeVariant(int idProd, String variant) throws DAOException;
    public void updateVariant(int idProd, ArrayList<String> variant, ArrayList<String> variantName, ArrayList<String> supplement) throws DAOException;
}
