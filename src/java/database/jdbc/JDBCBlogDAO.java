package database.jdbc;

import database.daos.BlogDAO;
import database.entities.Blog;
import database.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import static varie.costanti.NUM_MOST_VIEWED_POSTS;

/**
 * JDBCDAO per i metodi legati al Blog
 * 
 * @author Roberto97
 */
public class JDBCBlogDAO extends JDBCDAO implements BlogDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla connessione aperta con il DB
     * 
     * @param con E' la connessione al DB
     */
    public JDBCBlogDAO(Connection con) {
        super(con);
    }

    /**
     * Metodo per trovare tutti gli articoli del blog.
     * 
     * @return Ritorna un arraylist contenente tutti gli articoli.
     * @throws DAOException
     */
    @Override
    public ArrayList<Blog> getAllBlogs() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog")) {
            ArrayList<Blog> blogs = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Blog c = new Blog();
                    c.setId(rs.getInt("id"));
                    c.setCategoria(rs.getString("categoria"));
                    c.setNome(rs.getString("nome"));
                    c.setTesto(rs.getString("testo"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getDate("data"));
                    c.setViews(rs.getInt("views"));
                    blogs.add(c);
                }
                //Serve per ordinarlo per vedere prima i più recenti
                Collections.sort(blogs, (Blog z1, Blog z2) -> {
                    if (z1.getData().after(z2.getData())) {
                        return -1;
                    }
                    if (z1.getData().before(z2.getData())) {
                        return 1;
                    }
                    return 0;
                });

                return blogs;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i blogs. (JDBCBlogDAO, getAllBlogs)", ex);
        }
    }

    /**
     * Metodo per ritrovare un articolo in base all'id.
     * @param id Indica l'id dell'articolo da ritrovare
     * @return Ritorna un oggetto di tipo Blog (Un articolo)
     * @throws DAOException
     */
    @Override
    public Blog getBlogById(int id) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog where id = ?")) {
            stm.setInt(1, id);
            Blog c = new Blog();
            boolean check = false;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c.setId(rs.getInt("id"));
                    c.setCategoria(rs.getString("categoria"));
                    c.setNome(rs.getString("nome"));
                    c.setTesto(rs.getString("testo"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getDate("data"));
                    c.setViews(rs.getInt("views"));
                    check = true;
                }

                if (check) {
                    return c;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire il blog dall'id. (JDBCCatBlogDAO, getBlogById)", ex);
        }
    }

    /**
     * Metodo per ricavare i 4 articoli più letti.<br>
     * Se ci sono meno di 4 articoli li restituisce tutti in ordine dal più letto,
     * altrimenti restituisce i 4 più letti.
     * @return Ritorna un arrayList di Blog
     * @throws DAOException
     */
    @Override
    public ArrayList<Blog> getMostViewedBlog() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog")) {
            ArrayList<Blog> blogs = new ArrayList<>();
            ArrayList<Blog> viewed = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Blog c = new Blog();
                    c.setId(rs.getInt("id"));
                    c.setCategoria(rs.getString("categoria"));
                    c.setNome(rs.getString("nome"));
                    c.setTesto(rs.getString("testo"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getDate("data"));
                    c.setViews(rs.getInt("views"));
                    blogs.add(c);
                }

                Collections.sort(blogs, (Blog z1, Blog z2) -> {
                    if (z1.getViews() > z2.getViews()) {
                        return -1;
                    }
                    if (z1.getViews() < z2.getViews()) {
                        return 1;
                    }
                    return 0;
                });

                if (blogs.size() > 4) {
                    for (int i = 0; i < NUM_MOST_VIEWED_POSTS; i++) {
                        viewed.add(blogs.get(i));
                    }

                    return viewed;
                } else {
                    return blogs;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire i blogs più visti. (JDBCBlogDAO, getMostViewedBlog)", ex);
        }
    }

    /**
     * Metodo che ritorna tutti gli articoli di una particolare categoria.
     * @param cat Indica la categoria di blog che si sta cercando
     * @return Ritorna un arrayList di rticoli di una particolare categoria.
     * @throws DAOException
     */
    @Override
    public ArrayList<Blog> getBlogByCat(String cat) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog where categoria = ?")) {
            stm.setString(1, cat);
            ArrayList<Blog> cc = new ArrayList<>();
            Blog c;
            boolean check = false;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    c = new Blog();
                    c.setId(rs.getInt("id"));
                    c.setCategoria(rs.getString("categoria"));
                    c.setNome(rs.getString("nome"));
                    c.setTesto(rs.getString("testo"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getDate("data"));
                    c.setViews(rs.getInt("views"));
                    cc.add(c);
                    check = true;
                }

                if (check) {
                    return cc;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire il blog dalla categoria. (JDBCCatBlogDAO, getBlogByCat)", ex);
        }
    }
}
