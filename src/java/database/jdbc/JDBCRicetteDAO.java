/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.jdbc;

import database.daos.RicetteDAO;
import database.entities.Commento;
import database.entities.Ricetta;
import database.exceptions.DAOException;
import database.exceptions.DAOFactoryException;
import database.factories.JDBCDAOFactory;
import static database.factories.JDBCDAOFactory.DBURL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import static varie.costanti.NUM_MOST_VIEWED_POSTS;

/**
 * JDBCDAO Per i metodi relativi alle ricette
 *
 * @author Roberto97
 */
public class JDBCRicetteDAO extends JDBCDAO implements RicetteDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     *
     * @param con E' la connessione al DB
     * @throws java.sql.SQLException
     */
    public JDBCRicetteDAO(Connection con) throws SQLException{
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
            if(this.CON == null || this.CON.isClosed() || !this.CON.isValid(0)){
                this.daoFactory = new JDBCDAOFactory(DBURL);
                this.CON = daoFactory.getConnection();         
            }
        } catch (SQLException | DAOFactoryException ex) {
            System.out.println("console jdbc checkCON catch");
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo che restituisce tutte le ricette esistenti ordinandole dalla più
     * recente.
     *
     * @return Ritorna un arraylist di tutte le ricette salvate nel DB.
     * @throws DAOException
     */
    @Override
    public ArrayList<Ricetta> getAllRecipes() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricette")) {
            ArrayList<Ricetta> ricette = new ArrayList<>();
            String[] ingredienti;
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Ricetta r = new Ricetta();
                    r.setId(rs.getInt("id"));
                    r.setId_prod(rs.getInt("id_prod"));
                    r.setNome(rs.getString("nome"));
                    ingredienti = rs.getString("ingredienti").split("_");
                    r.setIngredienti(new ArrayList<>(Arrays.asList(ingredienti)));
                    r.setImmagine(rs.getString("immagine"));
                    r.setProcedimento(rs.getString("procedimento"));
                    r.setTempo(rs.getInt("tempo"));
                    r.setDifficolta(rs.getString("difficolta"));
                    if(!r.getDifficolta().equals("")){
                        String s = Character.toUpperCase(r.getDifficolta().charAt(0)) + r.getDifficolta().substring(1);
                        r.setDifficolta(s);
                    }
                    r.setCreatore(rs.getString("creatore"));
                    r.setData(rs.getTimestamp("data"));
                    r.setDescrizione(rs.getString("descrizione"));
                    r.setMeta_descrizione(rs.getString("meta_descrizione"));
                    r.setViews(rs.getInt("views"));
                    r.setCategory(rs.getBoolean("categoria"));                    
                    r.setApprovata(rs.getBoolean("approvata"));
                    ricette.add(r);
                }

                try {
                    Collections.sort(ricette, (Ricetta r1, Ricetta r2) -> {
                        if (r1.getData().after(r2.getData())) {
                            return -1;
                        }
                        if (r1.getData().before(r2.getData())) {
                            return 1;
                        }
                        return 0;
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return ricette;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutte le ricettee. (JDBCRicetteDAO, getAllRecipes)", ex);
        }
    }

    /**
     * Metodo per trovare una particolare ricetta.
     *
     * @param id E' l'id della ricetta che si vuole ottenere.
     * @return Ritorna un'oggetto di tipo ricetta.
     * @throws DAOException
     */
    @Override
    public Ricetta getRecipe(int id) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricette where id = ?")) {
            stm.setInt(1, id);
            Ricetta r = null;
            String[] ingredienti;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    r = new Ricetta();
                    r.setId(rs.getInt("id"));
                    r.setId_prod(rs.getInt("id_prod"));
                    r.setNome(rs.getString("nome"));
                    ingredienti = rs.getString("ingredienti").split("_");
                    r.setIngredienti(new ArrayList<>(Arrays.asList(ingredienti)));
                    r.setImmagine(rs.getString("immagine"));
                    r.setProcedimento(rs.getString("procedimento"));
                    r.setTempo(rs.getInt("tempo"));
                    r.setDifficolta(rs.getString("difficolta"));
                    if(!r.getDifficolta().equals("")){
                        String s = Character.toUpperCase(r.getDifficolta().charAt(0)) + r.getDifficolta().substring(1);
                        r.setDifficolta(s);
                    }
                    r.setCreatore(rs.getString("creatore"));
                    r.setData(rs.getTimestamp("data"));
                    r.setDescrizione(rs.getString("descrizione"));
                    r.setMeta_descrizione(rs.getString("meta_descrizione"));
                    r.setViews(rs.getInt("views"));
                    r.setCategory(rs.getBoolean("categoria"));                    
                    r.setApprovata(rs.getBoolean("approvata"));
                }
                return r;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo che restituisce tutte le ricette che sono legate ad un determinato
     * prodotto.<br>
     * Guarda tutte le ricette che hanno l'attributo id_prod richiesto. Infatti
     * un prodotto dell'ecommerce può essere associato a una o più ricette.
     *
     * @param id_prod Indica l'id del prodotto.
     * @return Ritorna un arrayList di ricette
     * @throws DAOException
     */
    @Override
    public ArrayList<Ricetta> getRecipeOfProduct(int id_prod) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricette where id_prod = ?")) {
            stm.setInt(1, id_prod);
            Ricetta r;
            ArrayList<Ricetta> ricette = new ArrayList<>();
            String[] ingredienti;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    r = new Ricetta();
                    r.setId(rs.getInt("id"));
                    r.setId_prod(rs.getInt("id_prod"));
                    r.setNome(rs.getString("nome"));
                    ingredienti = rs.getString("ingredienti").split("_");
                    r.setIngredienti(new ArrayList<>(Arrays.asList(ingredienti)));
                    r.setImmagine(rs.getString("immagine"));
                    r.setProcedimento(rs.getString("procedimento"));
                    r.setTempo(rs.getInt("tempo"));
                    r.setDifficolta(rs.getString("difficolta"));
                    if(!r.getDifficolta().equals("")){
                        String s = Character.toUpperCase(r.getDifficolta().charAt(0)) + r.getDifficolta().substring(1);
                        r.setDifficolta(s);
                    }
                    r.setCreatore(rs.getString("creatore"));
                    r.setData(rs.getTimestamp("data"));
                    r.setDescrizione(rs.getString("descrizione"));
                    r.setMeta_descrizione(rs.getString("meta_descrizione"));
                    r.setViews(rs.getInt("views"));
                    r.setCategory(rs.getBoolean("categoria"));                    
                    r.setApprovata(rs.getBoolean("approvata"));
                    ricette.add(r);
                }
                return ricette;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo per raccogliere tutti i commenti di tutte le ricette.
     *
     * @return Ritorna un arrayList di commenti.
     * @throws DAOException
     */
    @Override
    public ArrayList<Commento> getAllComments() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM commenti")) {
            ArrayList<Commento> commenti = new ArrayList<>();
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Commento c = new Commento();
                    c.setId(rs.getInt("id"));
                    c.setId_ricetta(rs.getInt("id_ricetta"));
                    c.setNome(rs.getString("nome"));
                    c.setTesto(rs.getString("testo"));
                    c.setData(rs.getTimestamp("data"));
                    commenti.add(c);
                }

                return commenti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i commenti. (JDBCRicetteDAO, getAllComments)", ex);
        }
    }

    /**
     * Metodo per ricavare tutti i commenti di una particolare ricetta
     *
     * @param id_ricetta Indica l'id della ricetta
     * @return Ritorna un arrayList di commenti
     * @throws DAOException
     */
    @Override
    public ArrayList<Commento> getComments(int id_ricetta) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM commenti where id_ricetta = ?")) {
            stm.setInt(1, id_ricetta);
            ArrayList<Commento> commenti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Commento c = new Commento();
                    c.setId(rs.getInt("id"));
                    c.setId_ricetta(rs.getInt("id_ricetta"));
                    c.setNome(rs.getString("nome"));
                    c.setTesto(rs.getString("testo"));
                    c.setData(rs.getTimestamp("data"));
                    commenti.add(c);
                }
                Collections.sort(commenti, (Commento z1, Commento z2) -> {
                    if (z1.getData().after(z2.getData())) {
                        return -1;
                    }
                    if (z1.getData().before(z2.getData())) {
                        return 1;
                    }
                    return 0;
                });
                return commenti;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo che restituisce le 4 ricette più viste.<br>
     * Se esistono meno di 4 ricette le restituisce tutte ordinandole in base
     * alla più letta altrimenti restituisce le 4 più lette.
     *
     * @return Ritorna un arraylist di ricette
     * @throws DAOException
     */
    @Override
    public ArrayList<Ricetta> getMostViewedRecipes() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM ricette")) {
            ArrayList<Ricetta> ricette = new ArrayList<>();
            ArrayList<Ricetta> viewed = new ArrayList<>();
            String[] ingredienti;
            Ricetta c;
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c = new Ricetta();
                    c.setId(rs.getInt("id"));
                    c.setId_prod(rs.getInt("id_prod"));
                    c.setNome(rs.getString("nome"));
                    c.setProcedimento(rs.getString("procedimento"));
                    ingredienti = rs.getString("ingredienti").split("_");
                    c.setIngredienti(new ArrayList<>(Arrays.asList(ingredienti)));
                    c.setImmagine(rs.getString("immagine"));
                    c.setTempo(rs.getInt("tempo"));
                    c.setDifficolta(rs.getString("difficolta"));
                    if(!c.getDifficolta().equals("")){
                        String s = Character.toUpperCase(c.getDifficolta().charAt(0)) + c.getDifficolta().substring(1);
                        c.setDifficolta(s);
                    }
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getTimestamp("data"));
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setMeta_descrizione(rs.getString("meta_descrizione"));
                    c.setViews(rs.getInt("views"));
                    c.setCategory(rs.getBoolean("categoria"));                    
                    c.setApprovata(rs.getBoolean("approvata"));
                    ricette.add(c);
                }

                Collections.sort(ricette, (Ricetta z1, Ricetta z2) -> {
                    if (z1.getViews() > z2.getViews()) {
                        return -1;
                    }
                    if (z1.getViews() < z2.getViews()) {
                        return 1;
                    }
                    return 0;
                });

                if (ricette.size() > 4) {
                    for (int i = 0; i < NUM_MOST_VIEWED_POSTS; i++) {
                        viewed.add(ricette.get(i));
                    }

                    return viewed;
                } else {
                    return ricette;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire le Idee più lette. (JDBCBlogDAO, getMostViewedRecipes)", ex);
        }
    }

    /**
     * Metodo che restituisce tutte le ricette di un determinato tipo
     *
     * @param type Indica il tipo di ricette che si cercano
     * @return Restituisce un arraylist di tutte le ricette di un determinato
     * tipo
     * @throws DAOException
     */
    @Override
    public ArrayList<Ricetta> getByType(String type) throws DAOException {
        checkCON();
        
        ArrayList<Ricetta> recipes = getAllRecipes();
        ArrayList<Ricetta> ricette = new ArrayList<>();

        switch (type) {
            case "1":
                recipes.stream().filter((r) -> (r.isCategory())).forEachOrdered((r) -> {
                    ricette.add(r);
                });
                break;
            case "2":
                recipes.stream().filter((r) -> (!r.isCategory())).forEachOrdered((r) -> {
                    ricette.add(r);
                });
                break;
            case "0":
                recipes.forEach((r) -> {
                    ricette.add(r);
                });
                break;
            default:
                break;
        }
        return ricette;
    }

    /**
     * Metodo che restituisce il numero di ricette di un determinato tipo.
     *
     * @param type Indica il tipo di ricette
     * @return Restituisce un intero uguale al numero di ricette di un
     * determinato tipo
     * @throws DAOException
     */
    @Override
    public int getTypeSize(String type) throws DAOException {
        checkCON();
        
        ArrayList<Ricetta> recipes = getByType(type);
        return recipes.size();
    }

    /**
     * Metodo che restituisce il numero di valutazioni per una determinata
     * ricetta.
     *
     * @param id Indica l'id della ricetta per cui si vuole conoscere il numero
     * di valutazioni esistenti
     * @return Ritorna il valore intero del numero di valutazioni.
     * @throws DAOException
     */
    @Override
    public int getNumberRate(int id) throws DAOException {
        checkCON();
        
        int val = 0;
        try (PreparedStatement stm = CON.prepareStatement("select * from valutazione_ricetta where id_ricetta = ?")) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    val = rs.getInt("rate05")
                            + rs.getInt("rate1")
                            + rs.getInt("rate15")
                            + rs.getInt("rate2")
                            + rs.getInt("rate25")
                            + rs.getInt("rate3")
                            + rs.getInt("rate35")
                            + rs.getInt("rate4")
                            + rs.getInt("rate45")
                            + rs.getInt("rate5");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
    }

    /**
     * Metodo che ritorna la valutazione media di una ricetta.
     *
     * @param id E' l'id della ricetta che si vuole conoscere la valutazione
     * @return Ritorna il valore della valutazione per esempio 4.5
     * @throws DAOException
     */
    @Override
    public double getRate(int id) throws DAOException {
        checkCON();
        
        double val = 0;
        try (PreparedStatement stm = CON.prepareStatement("select * from valutazione_ricetta where id_ricetta = ?")) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    val = rs.getDouble("value");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
    }

    /**
     * Metodo che ritorna tutti gli autori dell'azienda
     * @return
     * @throws DAOException
     */
    @Override
    public ArrayList<String> getOurCreators() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT creatore FROM ricette where categoria = 1 group by creatore")) {
            ArrayList<String> creators = null;

            try (ResultSet rs = stm.executeQuery()) {
                creators = new ArrayList<>();
                while (rs.next()) {
                    creators.add(rs.getString("creatore"));
                }

                return creators;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i creatori. (JDBCRicetteDAO, getAllCreators)", ex);
        }
    }
    
    /**
     * Metodo che ritorna tutti gli autori in generale
     * @return
     * @throws DAOException
     */
    @Override
    public ArrayList<String> getAllCreators() throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement("SELECT creatore FROM ricette group by creatore")) {
            ArrayList<String> creators = null;

            try (ResultSet rs = stm.executeQuery()) {
                creators = new ArrayList<>();
                while (rs.next()) {
                    creators.add(rs.getString("creatore"));
                }

                return creators;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i creatori. (JDBCRicetteDAO, getAllCreators)", ex);
        }
    }

    /**
     * MEtodo per rimuovere un ingrediente dalla ricettta
     * @param id
     * @param ing
     * @throws DAOException
     */
    @Override
    public void removeIng(int id, String ing) throws DAOException {
        checkCON();
        
        System.out.println("HOLA RECIPE");
        Ricetta idea = getRecipe(id);
        ArrayList<String> ingredienti = idea.getIngredienti();
        String ingS = "";
        for (String i : ingredienti) {
            if (i.equals(ing)) {
            } else {
                if (ingS.equals("")) {
                    ingS = i;
                } else {
                    ingS += "_" + i;
                }
            }
        }
        
        try (PreparedStatement stm = CON.prepareStatement(
                "UPDATE ricette SET ingredienti = ? WHERE id = ?;"
        )) {
            try {
                stm.setString(1, ingS);
                stm.setInt(2, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error adding ingredients to recipe  " + id);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo per aggiolrnare una ricetta
     * @param nome
     * @param procedimento
     * @param descrizione
     * @param meta_descrizione
     * @param immagine
     * @param difficolta
     * @param ingredienti
     * @param creatore
     * @param tempo
     * @param id
     * @param id_prod
     * @param categoria
     * @param approvata serve per scegliere se è pubblicata oppure no
     * @throws DAOException
     */
    @Override
    public void updateRecipe(String nome, String procedimento, String descrizione, String meta_descrizione, String immagine, String difficolta, String ingredienti, String creatore, int tempo, int id, int id_prod, boolean categoria, boolean approvata) throws DAOException {
        checkCON();
        
        if (nome == null || procedimento == null || descrizione == null || meta_descrizione == null || immagine == null || difficolta == null || ingredienti == null || creatore == null) {
        } else {
            
            try (PreparedStatement stm = CON.prepareStatement(
                    "UPDATE ricette SET id_prod = ?, nome = ?, ingredienti = ?, procedimento = ?, descrizione = ?, immagine = ?, tempo = ?, difficolta = ?, creatore = ?, categoria = ?, approvata = ?, meta_descrizione = ? WHERE id = ?;"
            )) {
                try {
                    stm.setInt(1, id_prod);
                    stm.setString(2, nome);
                    stm.setString(3, ingredienti);
                    stm.setString(4, procedimento);
                    stm.setString(5, descrizione);
                    stm.setString(6, immagine);
                    stm.setInt(7, tempo);
                    stm.setString(8, difficolta);
                    stm.setString(9, creatore);
                    stm.setBoolean(10, categoria);
                    stm.setBoolean(11, approvata);
                    stm.setString(12, meta_descrizione);
                    stm.setInt(13, id);

                    if (stm.executeUpdate() == 1) {
                    } else {
                        System.out.println("Error updating recipe " + id);
                    }

                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo per eliminare un'idea
     * @param id
     * @throws DAOException
     */
    @Override
    public void deleteRecipe(int id) throws DAOException {
        checkCON();
        
        try (PreparedStatement stm = CON.prepareStatement(
                "delete from ricette where id = ?"
        )) {
            try {
                stm.setInt(1, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error deleting blog " + id);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo per aggiungere al DB una nuova idea
     * @param nome
     * @param procedimento
     * @param descrizione
     * @param meta_descrizione
     * @param difficolta
     * @param ingredienti
     * @param creatore
     * @param tempo
     * @param id_prod
     * @param categoria
     * @param approvata serve per scegliere se è pubblicata oppure no
     * @return
     * @throws DAOException
     */
    @Override
    public int addRecipe(String nome, String procedimento, String descrizione, String meta_descrizione, String difficolta, String ingredienti, String creatore, int tempo, int id_prod, boolean categoria, boolean approvata) throws DAOException {
        checkCON();
        
        int id = 0;
        try (PreparedStatement stm = CON.prepareStatement("insert into ricette (nome, procedimento, descrizione, difficolta, ingredienti, creatore, tempo, id_prod, categoria, immagine, approvata, meta_descrizione) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)")) {
            try {
                stm.setString(1, nome);
                stm.setString(2, procedimento);
                stm.setString(3, descrizione);
                stm.setString(4, difficolta);
                stm.setString(5, ingredienti);
                stm.setString(6, creatore);
                stm.setInt(7, tempo);
                stm.setInt(8, id_prod);
                stm.setBoolean(9, categoria);
                stm.setString(10, "");
                stm.setBoolean(11, approvata);
                stm.setString(12, meta_descrizione);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to add new recipe");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("select MAX(id) as id from ricette")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException ex) { 
        }
        return id;
    }
}
