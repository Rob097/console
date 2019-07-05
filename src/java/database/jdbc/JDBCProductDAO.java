package database.jdbc;

import database.daos.ProductDAO;
import database.entities.Prodotto;
import database.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBCDAO Per i metodi relativi ai Prodotti dell'ecommerce
 *
 * @author Roberto97
 */
public class JDBCProductDAO extends JDBCDAO implements ProductDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     *
     * @param con E' la connessione al DB
     */
    public JDBCProductDAO(Connection con) {
        super(con);
    }

    /**
     * Metodo che ritorna un arrayList di tutti i prodotti del DB<br>
     * Lo uso in casi come per il ciclo per stampare tutti i prodotti, ma anche
     * per salvare i prodotti in attributi di sessione (?)
     *
     * @return Ritorna un arrayList di tutti i prodotti del DB
     * @throws DAOException
     */
    @Override
    public ArrayList<Prodotto> getAllProducts() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto")) {
            ArrayList<Prodotto> prodotti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                    prodotti.add(p);
                }

                return prodotti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i prodotti. (JDBCProductDAO, getAllProducts)", ex);
        }
    }

    /**
     * Metodo che ritorna tutti i prodotti che sono classificati come prodotti
     * freschi.<br>
     * Quindi qguarda l'attributo boolean fresco dei prodotti nel DB e prende i
     * true
     *
     * @return Ritorna un arrayList di tutti i prodotti FRESCHI del DB
     * @throws DAOException
     */
    @Override
    public ArrayList<Prodotto> getFreshProducts() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where fresco = 1")) {
            ArrayList<Prodotto> prodotti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                    prodotti.add(p);
                }

                return prodotti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire i prodotti freschi. (JDBCProductDAO, getFreshProducts)", ex);
        }
    }

    /**
     * Metodo che ritorna tutti i prodotti che sono classificati come prodotti
     * NON freschi.<br>
     * Quindi qguarda l'attributo boolean fresco dei prodotti nel DB e prende i
     * false
     *
     * @return Ritorna un arrayList di tutti i prodotti NON freschi del DB
     * @throws DAOException
     */
    @Override
    public ArrayList<Prodotto> getConfProducts() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where fresco = 0")) {
            ArrayList<Prodotto> prodotti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                    prodotti.add(p);
                }

                return prodotti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire i prodotti Conf. (JDBCProductDAO, getConfProducts)", ex);
        }
    }

    /**
     * Metodo che serve a trovare tutti i prodotti di una determinata cattegoria
     * di prodotti.<br>
     * Guarda l'attributo categoria nel DB che è una foreign key alla tabella
     * categoria.
     *
     * @param categoryName E' una Stringa con il nome della categoria da cercare
     * i prodotti
     * @return Ritorna un ArrayList di prodotti con attributo categoria uguale
     * al parametro CategoryName
     * @throws DAOException
     */
    @Override
    public ArrayList<Prodotto> getAllProductsOfCategory(String categoryName) throws DAOException {
        if (categoryName == null) {
            throw new DAOException("categoryName is a mandatory fields", new NullPointerException("categoryName is null"));
        }
        if (categoryName.contains("'") || categoryName.contains("\"")) {
            categoryName.replace("\"", "");
            categoryName.replace("'", "");
        }
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where categoria = ?")) {
            stm.setString(1, categoryName);
            ArrayList<Prodotto> prodotti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                    prodotti.add(p);
                }

                return prodotti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i prodotti della categoria. (JDBCProductDAO, getAllProductsOfCategory)", ex);
        }
    }

    /**
     * Metodo per ritrovare un prodotto in base all'id
     *
     * @param id
     * @return Ritorna un prodotto
     * @throws DAOException
     */
    @Override
    public Prodotto getProduct(int id) throws DAOException {

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where id = ?")) {
            stm.setInt(1, id);
            Prodotto p = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                }
                return p;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }
    
    @Override
    public Prodotto getProductByName(String name) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where nome = ?")) {
            stm.setString(1, name);
            Prodotto p = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                }
                return p;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo che ritorna la valutazione media di un prodotto.
     *
     * @param id E' l'id del prodotto che si vuole conoscere la valutazione
     * @return Ritorna il valore della valutazione per esempio 4.5
     * @throws DAOException
     */
    @Override
    public double getRate(int id) throws DAOException {
        double val = 0;
        try (PreparedStatement stm = CON.prepareStatement("select * from valutazione_prod where id_prod = ?")) {
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
     * Metodo che restituisce il numero di valutazioni per un determinato
     * prodotto.
     *
     * @param id Indica l'id per cui si vuole conoscere il numero di valutazioni
     * esistenti
     * @return Ritorna il valore intero del numero di valutazioni.
     * @throws DAOException
     */
    @Override
    public int getNumberRate(int id) throws DAOException {
        int val = 0;
        try (PreparedStatement stm = CON.prepareStatement("select * from valutazione_prod where id_prod = ?")) {
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

    @Override
    public void deleteProd(int id) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement(
                "delete from prodotto where id = ?"
        )) {
            try {
                stm.setInt(1, id);

                if (stm.executeUpdate() == 1) {
                } else {
                    System.out.println("Error deleting prod " + id);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void alterProd(int id, String nome, String descrizione, String categoria, String immagine, boolean disponibile, double costo) throws DAOException {
        if (nome == null || descrizione == null || categoria == null || immagine == null) {
        } else {
            try (PreparedStatement stm = CON.prepareStatement(
                    "UPDATE prodotto SET nome = ?, categoria = ?, immagine = ?, descrizione = ?, costo = ?, disponibile = ? WHERE id = ?;"
            )) {
                try {
                    stm.setString(1, nome);
                    stm.setString(2, categoria);
                    stm.setString(3, immagine);
                    stm.setString(4, descrizione);
                    stm.setDouble(5, costo);
                    stm.setBoolean(6, disponibile);
                    stm.setInt(7, id);

                    if (stm.executeUpdate() == 1) {
                    } else {
                        System.out.println("Error updating prod " + id);
                    }

                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int addProd(String nome, String descrizione, String categoria, double costo, boolean disponibile, boolean fresco) throws DAOException {
        int id = 0;
        try (PreparedStatement stm = CON.prepareStatement("insert into prodotto (nome, categoria, immagine, descrizione, costo, disponibile, fresco) VALUES (?,?,?,?,?,?,?)")) {
            try {
                stm.setString(1, nome);
                stm.setString(2, categoria);
                stm.setString(3, "");
                stm.setString(4, descrizione);
                stm.setDouble(5, costo);
                stm.setBoolean(6, disponibile);
                stm.setBoolean(7, fresco);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to add new product");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("select MAX(id) as id from prodotto")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException ex) { 
        }
        return id;
    }

    @Override
    public ArrayList<Prodotto> getNullCategoryProducts() throws DAOException {
        boolean check = false;
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where categoria IS NULL")) {
            ArrayList<Prodotto> prodotti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    check = true;
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setImmagine(rs.getString("immagine"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setCosto(rs.getFloat("costo"));
                    p.setDisponibile(rs.getBoolean("disponibile"));
                    p.setFresco(rs.getBoolean("fresco"));
                    prodotti.add(p);
                }

                if(check)
                return prodotti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i prodotti della categoria NULL. (JDBCProductDAO, getNullCategoryProducts())", ex);
        }
        return null;
    }

}
