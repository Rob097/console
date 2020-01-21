package database.jdbc;

import database.daos.ProductDAO;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
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
     * @throws java.sql.SQLException
     */
    public JDBCProductDAO(Connection con) throws SQLException {
        super(con);
        try {
            checkCON();
        } catch (DAOException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Controlla che la connessione con il DB sia aperta, altrimenti la riapre
     *
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
     * Metodo che ritorna un arrayList di tutti i prodotti del DB<br>
     * Lo uso in casi come per il ciclo per stampare tutti i prodotti, ma anche
     * per salvare i prodotti in attributi di sessione (?)
     *
     * @return Ritorna un arrayList di tutti i prodotti del DB
     * @throws DAOException
     */
    @Override
    public ArrayList<Prodotto> getAllProducts() throws DAOException {
        checkCON();

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
                    p.setPeso(rs.getDouble("peso"));
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
        checkCON();

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
                    p.setPeso(rs.getDouble("peso"));
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
        checkCON();

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
                    p.setPeso(rs.getDouble("peso"));
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
        checkCON();

        if (categoryName == null) {
            throw new DAOException("categoryName is a mandatory fields", new NullPointerException("categoryName is null"));
        }

        categoryName = categoryName.replace("\"", "").replace("'", "");

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
                    p.setPeso(rs.getDouble("peso"));
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
        checkCON();

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
                    p.setPeso(rs.getDouble("peso"));
                }
                return p;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo che ritorna un prodotto partendo dal suo nome
     *
     * @param name
     * @return
     * @throws DAOException
     */
    @Override
    public Prodotto getProductByName(String name) throws DAOException {
        checkCON();

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM prodotto where nome = ?")) {
            stm.setString(1, name);
            Prodotto p = new Prodotto();

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
                    p.setPeso(rs.getDouble("peso"));
                }
                return p;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return new Prodotto();
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
        checkCON();

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
        checkCON();

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

    /**
     * Metodo per eliminare un prodotto dal DB
     *
     * @param id
     * @throws DAOException
     */
    @Override
    public void deleteProd(int id) throws DAOException {
        checkCON();

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

    /**
     * Metodo per aggiornare un prodotto
     *
     * @param id
     * @param nome
     * @param descrizione
     * @param categoria
     * @param immagine
     * @param disponibile
     * @param costo
     * @throws DAOException
     */
    @Override
    public void alterProd(int id, String nome, String descrizione, String categoria, String immagine, boolean disponibile, double costo, double peso) throws DAOException {
        checkCON();

        if (nome == null || descrizione == null || categoria == null || immagine == null) {
        } else {
            try (PreparedStatement stm = CON.prepareStatement(
                    "UPDATE prodotto SET nome = ?, categoria = ?, immagine = ?, descrizione = ?, costo = ?, disponibile = ?, peso = ? WHERE id = ?;"
            )) {
                try {
                    stm.setString(1, nome);
                    stm.setString(2, categoria);
                    stm.setString(3, immagine);
                    stm.setString(4, descrizione);
                    stm.setDouble(5, costo);
                    stm.setBoolean(6, disponibile);
                    stm.setDouble(7, peso);
                    stm.setInt(8, id);

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

    /**
     * MEtodo per aggiungere un nuovo prodotto al DB
     *
     * @param nome
     * @param descrizione
     * @param categoria
     * @param costo
     * @param disponibile
     * @param fresco
     * @return
     * @throws DAOException
     */
    @Override
    public int addProd(String nome, String descrizione, String categoria, double costo, boolean disponibile, boolean fresco, double peso) throws DAOException {
        checkCON();

        int id = 0;
        try (PreparedStatement stm = CON.prepareStatement("insert into prodotto (nome, categoria, immagine, descrizione, costo, disponibile, fresco, peso) VALUES (?,?,?,?,?,?,?,?)")) {
            try {
                stm.setString(1, nome);
                stm.setString(2, categoria);
                stm.setString(3, "");
                stm.setString(4, descrizione);
                stm.setDouble(5, costo);
                stm.setBoolean(6, disponibile);
                stm.setBoolean(7, fresco);
                stm.setDouble(8, peso);

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

    /**
     * MEtodo che ritorna tutti i prodotti che hanno la categoria settata a
     * null. questo succede nei bug o sopratutto se viene eliminata una
     * categoria, tutti i prodotti legati alla categoria non vengono eliminati
     * ma gli viene settata la categoria a null.
     *
     * @return
     * @throws DAOException
     */
    @Override
    public ArrayList<Prodotto> getNullCategoryProducts() throws DAOException {
        checkCON();

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
                    p.setPeso(rs.getDouble("peso"));
                    prodotti.add(p);
                }

                if (check) {
                    return prodotti;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i prodotti della categoria NULL. (JDBCProductDAO, getNullCategoryProducts())", ex);
        }
        return null;
    }

    /**
     * Metodo che ritorna tutte le varianti disponibili per un prodotto
     *
     * @param idProd
     * @return
     * @throws DAOException
     */
    @Override
    public LinkedHashMap<String, ArrayList<Variante>> getProductVariant(int idProd) throws DAOException {
        checkCON();

        Variante v;
        ArrayList<Variante> varianti;
        ArrayList<ArrayList<Variante>> prodVar = new ArrayList<>();
        ArrayList<String> names = null;
        LinkedHashMap<String, ArrayList<Variante>> var = new LinkedHashMap<>();
        boolean check = false, ck = false;

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM products_variants where idProd = ?")) {
            stm.setInt(1, idProd);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ck = false;
                    v = new Variante();
                    v.setId(rs.getInt("id"));
                    v.setId_prod(rs.getInt("idProd"));
                    v.setVariant(rs.getString("variant"));
                    v.setVariantName(rs.getString("variantName"));
                    v.setSupplement(rs.getDouble("supplement"));
                    v.setPesoMaggiore(rs.getDouble("pesoMaggiore"));

                    for (Map.Entry<String, ArrayList<Variante>> entry : var.entrySet()) {
                        ArrayList<Variante> value = entry.getValue();
                        String key = entry.getKey();
                        if (v.getVariant().equals(key)) {
                            value.add(v);
                            ck = true;
                        }
                    }
                    if (!ck) {
                        varianti = new ArrayList<>();
                        varianti.add(v);
                        var.put(v.getVariant(), varianti);
                    }
                }
                return var;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutte le varianti del prodotto. (JDBCProductDAO, getProductVariant)", ex);
        }
    }

    /**
     * MEtodo che ritorna una variante partendo dall'id
     *
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
                    v.setPesoMaggiore(rs.getDouble("pesoMaggiore"));
                }
                return v;
            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * Metodo che ritorna la prima scelta di ogni variante del prodotto. questo
     * metodo serve se viene aggiunto il prodotto al carrello dalla bottega
     * quindi non scegliendo che tipo di variante aggiungere. Si aggiunge quella
     * di default che è la prima.
     *
     * @param idProd
     * @return
     * @throws DAOException
     */
    @Override
    public ArrayList<Variante> getFrstVariantOfProduct(int idProd) throws DAOException {
        checkCON();

        try (PreparedStatement stm = CON.prepareStatement("select * from products_variants where idProd = ? and supplement = 0 group by variant")) {
            stm.setInt(1, idProd);
            Variante v;
            ArrayList<Variante> varianti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    v = new Variante();
                    v.setId(rs.getInt("id"));
                    v.setId_prod(rs.getInt("idProd"));
                    v.setVariant(rs.getString("variant"));
                    v.setVariantName(rs.getString("variantName"));
                    v.setSupplement(rs.getDouble("supplement"));
                    v.setPesoMaggiore(rs.getDouble("pesoMaggiore"));
                    varianti.add(v);
                }
                if (varianti == null || varianti.isEmpty()) {
                    return null;
                } else {
                    return varianti;
                }

            }
        } catch (SQLException ex) {
            //throw new DAOException("Impossibile restituire il prodotto. (JDBCProductDAO, getProduct)", ex);
            return null;
        }
    }

    /**
     * ritorna una stringa concatenzione degli id delle varianti
     *
     * @param blocco
     * @return
     * @throws DAOException
     */
    @Override
    public String getVariantBlock(ArrayList<Variante> blocco) throws DAOException {
        String ids = "";
        for (Variante v : blocco) {
            ids += v.getId() + "_";
        }
        ids = ids.substring(0, ids.length() - 1);
        return ids;
    }

    /**
     * MEtodo per eliminare una variante con tutte le sue scelte
     *
     * @param idProd
     * @param variant
     * @throws DAOException
     */
    @Override
    public void removeVariant(int idProd, String variant) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("delete from products_variants where idProd = ? and variant = ?")) {
            try {
                stm.setInt(1, idProd);
                stm.setString(2, variant);

                if (stm.executeUpdate() >= 1) {
                } else {
                    System.out.println("Error removing variant of product " + idProd);
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo per aggiungere ad un prodotto una variante
     *
     * @param idProd
     * @param variant
     * @param variantName
     * @param supplement
     * @param peso_variante
     * @throws DAOException
     */
    @Override
    public void updateVariant(int idProd, ArrayList<String> variant, ArrayList<String> variantName, ArrayList<String> supplement, ArrayList<String> peso_variante) throws DAOException {
        if (variant != null && variantName != null && supplement != null && !variant.isEmpty() && !variantName.isEmpty() && !supplement.isEmpty() && variant.size() == variantName.size() && variant.size() == supplement.size()) {

            Variante v;
            ArrayList<Variante> varianti = new ArrayList<>();

            for (int i = 0; i < variant.size(); i++) {
                v = new Variante(0, idProd, variant.get(i), variantName.get(i), Double.parseDouble(supplement.get(i).replace(",", ".")), 1, Double.parseDouble(peso_variante.get(i).replace(",", ".")));
                varianti.add(v);
            }
            
            Collections.sort(varianti, (Variante c1, Variante c2) -> Double.compare(c1.getSupplement(), c2.getSupplement()));
            
            for (int i = 0; i < varianti.size(); i++) {
                v = varianti.get(i);
                try (PreparedStatement stm = CON.prepareStatement("insert into products_variants (idProd, variant, variantName, supplement, pesoMaggiore) values (?, ?, ?, ?, ?)")) {
                    try {
                        stm.setInt(1, v.getId_prod());
                        stm.setString(2, v.getVariant());
                        stm.setString(3, v.getVariantName());
                        stm.setDouble(4, v.getSupplement());
                        stm.setDouble(5, v.getPesoMaggiore());

                        if (stm.executeUpdate() >= 1) {
                        } else {
                            System.out.println("Error update variant of product " + v.getId_prod());
                        }

                    } catch (NumberFormatException | SQLException ex) {
                        throw new DAOException(ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            /* no variants change */
        }
    }

}
