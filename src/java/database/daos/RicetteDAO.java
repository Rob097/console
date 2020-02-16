/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.daos;

import database.entities.Commento;
import database.entities.Ricetta;
import database.exceptions.DAOException;
import java.util.ArrayList;

/**
 *
 * @author della
 */
public interface RicetteDAO {
    public void checkCON() throws DAOException;
    
    
    public ArrayList<Ricetta> getAllRecipes() throws DAOException;
    public Ricetta getRecipe(int id) throws DAOException;
    public ArrayList<Ricetta> getRecipeOfProduct(int id_prod) throws DAOException;
    public ArrayList<Ricetta> getMostViewedRecipes() throws DAOException;;
    public ArrayList<Ricetta> getByType(String type) throws DAOException;
    public int getTypeSize(String type) throws DAOException;
    public ArrayList<String> getOurCreators() throws DAOException;
    public ArrayList<String> getAllCreators() throws DAOException;
    public void removeIng(int id, String ing) throws DAOException;
    public void updateRecipe(String nome, String procedimento, String descrizione, String meta_descrizione, String immagine, String difficolta, String ingredienti, String creatore, int tempo, int id, boolean categoria, boolean approvata) throws DAOException;
    public int addRecipe(String nome, String procedimento, String descrizione, String meta_descrizione, String difficolta, String ingredienti, String creatore, int tempo, boolean categoria, boolean approvata) throws DAOException;
    public void deleteRecipe(int id)  throws DAOException;
    
    //Valutazione
    public int getNumberRate(int id) throws DAOException;
    public double getRate(int id) throws DAOException;
    
    //Commenti
    public ArrayList<Commento> getAllComments() throws DAOException;
    public ArrayList<Commento> getComments(int id_ricetta) throws DAOException;
    
    //Prodotti-Idea
    public ArrayList<Integer> getAllProdottiIdea() throws DAOException;
    public int getProdFormProdIdea(int id) throws DAOException;
    public int getIdeaFormProdIdea(int id) throws DAOException;
    public ArrayList<Integer> getAllProdsOfIdea(int id) throws DAOException;
    public void addProdottoIdea(ArrayList<Integer> prods, int idIdea) throws DAOException;
    public void cleanProdottiIdea() throws DAOException;
}
