package database.jdbc;

import database.daos.CommBlogDAO;
import database.entities.CommBlog;
import database.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *JDBCDAO Per i metodi relativi ai commenti del blog.
 * 
 * @author Roberto97
 */
public class JDBCCommBlogDAO extends JDBCDAO implements CommBlogDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla connessione aperta con il DB
     * 
     * @param con E' la connessione al DB
     */
    public JDBCCommBlogDAO(Connection con) {
        super(con);
    }

    /**
     * Metodo che ritorna tutti i commenti di tutti i blog.<br>
     * Questo metodo è utilizzato dal CookieFilter per creare un attributo di sessione
     * contenente tutti i commenti di tutti i blog. Questo attributo viene poi
     * usato nei cicli nelle pagine jsp
     * @return Ritorna un arrayList di commenti blog.
     * @throws DAOException
     */
    @Override
    public ArrayList<CommBlog> getAllCommBlog() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog_commenti")) {
            ArrayList<CommBlog> commenti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    CommBlog c = new CommBlog();
                    c.setId_blog(rs.getInt("id_blog"));
                    c.setNome(rs.getString("nome"));
                    c.setCommento(rs.getString("commento"));
                    c.setData(rs.getTimestamp("data"));
                    commenti.add(c);
                }

                return commenti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i commenti dei blog. (JDBCCommBlogDAO, getAllCommBlog)", ex);
        }
    }

    /**
     * Metodo che ritorna tutti i commenti di un particolare blog.
     * @param id_blog Indica l'id del blog per cui si voglioino i commenti.
     * @return Ritorna un arrayList di commenti blog.
     * @throws DAOException
     */
    @Override
    public ArrayList<CommBlog> getAllCommOfBlog(int id_blog) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM blog_commenti where id_blog = ?")) {
            stm.setInt(1, id_blog);
            ArrayList<CommBlog> commenti = new ArrayList<>();

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    CommBlog c = new CommBlog();
                    c.setId_blog(rs.getInt("id_blog"));
                    c.setNome(rs.getString("nome"));
                    c.setCommento(rs.getString("commento"));
                    c.setData(rs.getTimestamp("data"));
                    commenti.add(c);
                }

                Collections.sort(commenti, (CommBlog z1, CommBlog z2) -> {
                    if (z1.getData().after(z2.getData())) {
                        return -1;
                    }
                    if (z1.getData().before(z2.getData())) {
                        return 1;
                    }
                    return 0;
                });
            }
            return commenti;
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i commenti del blog. (JDBCCommBlogDAO, getAllCommOfBlog)", ex);
        }
    }

}
