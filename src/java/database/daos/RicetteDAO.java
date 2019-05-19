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
    public ArrayList<Ricetta> getAllRecipes() throws DAOException;
    public Ricetta getRecipe(int id) throws DAOException;
    public ArrayList<Ricetta> getRecipeOfProduct(int id_prod) throws DAOException;
    public ArrayList<Ricetta> getMostViewedRecipes() throws DAOException;;
    public ArrayList<Ricetta> getByType(String type) throws DAOException;
    public int getTypeSize(String type) throws DAOException;
    
    //Valutazione
    public int getNumberRate(int id) throws DAOException;
    public double getRate(int id) throws DAOException;
    
    //Commenti
    public ArrayList<Commento> getAllComments() throws DAOException;
    public ArrayList<Commento> getComments(int id_ricetta) throws DAOException;
}
