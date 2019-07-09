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
import java.util.logging.Level;
import java.util.logging.Logger;
import static varie.costanti.NUM_MOST_VIEWED_POSTS;

/**
 * JDBCDAO per i metodi legati al Blog
 *
 * @author Roberto97
 */
public class JDBCBlogDAO extends JDBCDAO implements BlogDAO {

    /**
     * Questa è il costruttore e serve fondamentalmente per collegarsi alla
     * connessione aperta con il DB
     *
     * @param con E' la connessione al DB
     * @throws java.sql.SQLException
     */
    public JDBCBlogDAO(Connection con) throws SQLException {
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
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getTimestamp("data"));
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
     *
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
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getTimestamp("data"));
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
     * Se ci sono meno di 4 articoli li restituisce tutti in ordine dal più
     * letto, altrimenti restituisce i 4 più letti.
     *
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
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getTimestamp("data"));
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
     *
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
                    c.setDescrizione(rs.getString("descrizione"));
                    c.setImmagine(rs.getString("immagine"));
                    c.setCreatore(rs.getString("creatore"));
                    c.setData(rs.getTimestamp("data"));
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

    @Override
    public ArrayList<String> getAllCreators() throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement("SELECT creatore FROM blog group by creatore")) {
            ArrayList<String> creators = null;

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    creators = new ArrayList<>();
                    creators.add(rs.getString("creatore"));
                }

                return creators;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile restituire tutti i creatori. (JDBCBlogDAO, getAllCreators)", ex);
        }
    }

    @Override
    public void alterBlog(String id, String titolo, String testo, String creator, String categoria, String immagine, String descrizione) throws DAOException {
        if (id == null || titolo == null || testo == null || creator == null || categoria == null || immagine == null) {
        } else {
            try (PreparedStatement stm = CON.prepareStatement(
                    "UPDATE blog SET nome = ?, testo = ?, creatore = ?, categoria = ?, immagine = ?, descrizione = ? WHERE id = ?;"
            )) {
                try {
                    stm.setString(1, titolo);
                    stm.setString(2, testo);
                    stm.setString(3, creator);
                    stm.setString(4, categoria);
                    stm.setString(5, immagine);
                    stm.setString(6, descrizione);
                    stm.setInt(7, Integer.parseInt(id));

                    if (stm.executeUpdate() == 1) {
                    } else {
                        System.out.println("Error updating blog " + id);
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
    public void deleteBlog(String id) throws DAOException {
        try (PreparedStatement stm = CON.prepareStatement(
                "delete from blog where id = ?"
        )) {
            try {
                stm.setInt(1, Integer.parseInt(id));

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

    @Override
    public int addBlog(String titolo, String testo, String creator, String categoria, String descrizione) throws DAOException {
        int id = 0;
        try (PreparedStatement stm = CON.prepareStatement("insert into blog (nome, testo, creatore, categoria, immagine, descrizione) VALUES (?,?,?,?,?,?)")) {
            try {
                stm.setString(1, titolo);
                stm.setString(2, testo);
                stm.setString(3, creator);
                stm.setString(4, categoria);
                stm.setString(5, "");
                stm.setString(6, descrizione);

                if (stm.executeUpdate() == 1) {
                } else {
                    throw new DAOException("Impossible to add new blog");
                }

            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PreparedStatement stm = CON.prepareStatement("select MAX(id) as id from blog")) {
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
    public void addTags(ArrayList<String> tags, int idBlog) throws DAOException {

        boolean check = false;
        System.out.println(tags.toString());
        ArrayList<Integer> prodT = new ArrayList<>(); //id dei tag aggiornati del blog
        for (String s : tags) {
            if (!s.isEmpty()) {
                check = false;
                try (PreparedStatement stm = CON.prepareStatement("select id from tags where testo = ?")) {
                    stm.setString(1, s);
                    try (ResultSet rs = stm.executeQuery()) {
                        while (rs.next()) {
                            check = true;
                            prodT.add(rs.getInt("id"));
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (!check) {

                    try (PreparedStatement stm = CON.prepareStatement("insert into tags (testo) value (?)")) {
                        stm.setString(1, s);
                        try {
                            if (stm.executeUpdate() == 1) {
                            } else {
                                throw new DAOException("Impossible to add new tag (addProdTag)");
                            }
                        } catch (SQLException ex) {
                            throw new DAOException(ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try (PreparedStatement stm = CON.prepareStatement("select id from tags where testo = ?")) {
                        stm.setString(1, s);
                        try (ResultSet rs = stm.executeQuery()) {
                            while (rs.next()) {
                                prodT.add(rs.getInt("id"));
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }

        try (PreparedStatement stm = CON.prepareStatement("delete from blog_tags where blog = ?")) {
            stm.setInt(1, idBlog);
            try {
                stm.execute();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int q : prodT) {
            try (PreparedStatement stm = CON.prepareStatement("insert into blog_tags (blog, tag) values (?, ?);")) {
                System.out.println("idBlog: " + idBlog + "\nidTag: " + q);
                stm.setInt(1, idBlog);
                stm.setInt(2, q);
                try {
                    stm.execute();
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        cleanTags();

    }

    /*@Override
    public void addProdTags(ArrayList<String> prodTags, int idBlog) throws DAOException {

        boolean check = false;

        ArrayList<String> prods = new ArrayList<String>();
        for (String element : prodTags) {

            // If this element is not present in newList 
            // then add it 
            if (!prods.contains(element)) {

                prods.add(element);
            }
        }
        System.out.println("prods: " + prods.toString());
        ArrayList<Integer> prodT = new ArrayList<>(); //id dei tag aggiornati del blog
        for (String s : prods) {
            if (!s.isEmpty()) {
                check = false;
                try (PreparedStatement stm = CON.prepareStatement("select id from tags where prodotto = ?")) {
                    stm.setInt(1, Integer.parseInt(s));
                    try (ResultSet rs = stm.executeQuery()) {
                        while (rs.next()) {
                            check = true;
                            prodT.add(rs.getInt("id"));
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (!check) {

                    try (PreparedStatement stm = CON.prepareStatement("insert into tags (prodotto) value (?)")) {
                        stm.setInt(1, Integer.parseInt(s));
                        try {
                            if (stm.executeUpdate() == 1) {
                            } else {
                                throw new DAOException("Impossible to add new tag (addProdTag)");
                            }
                        } catch (SQLException ex) {
                            throw new DAOException(ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try (PreparedStatement stm = CON.prepareStatement("select id from tags where prodotto = ?")) {
                        stm.setInt(1, Integer.parseInt(s));
                        try (ResultSet rs = stm.executeQuery()) {
                            while (rs.next()) {
                                prodT.add(rs.getInt("id"));
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

            System.out.println("prodT: " + prodT.toString());
            try (PreparedStatement stm = CON.prepareStatement("delete from blog_tags where blog = ?")) {
                stm.setInt(1, idBlog);
                try {
                    stm.execute();
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int q : prodT) {
                try (PreparedStatement stm = CON.prepareStatement("insert into blog_tags (blog, tag) values (?, ?)")) {
                    stm.setInt(1, idBlog);
                    stm.setInt(2, q);
                    try {
                        if (stm.executeUpdate() == 1) {
                        } else {
                            throw new DAOException("Impossible to add tags to blog");
                        }
                    } catch (SQLException ex) {
                        throw new DAOException(ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void addCatTags(ArrayList<String> catTags, int idBlog) throws DAOException {
        boolean check = false;

        ArrayList<String> prods = new ArrayList<String>();
        for (String element : catTags) {

            // If this element is not present in newList 
            // then add it 
            if (!prods.contains(element)) {

                prods.add(element);
            }
        }
        System.out.println("prods: " + prods.toString());
        ArrayList<Integer> prodT = new ArrayList<>(); //id dei tag aggiornati del blog
        for (String s : prods) {
            if (!s.isEmpty()) {
                check = false;
                try (PreparedStatement stm = CON.prepareStatement("select id from tags where categoria = ?")) {
                    stm.setInt(1, Integer.parseInt(s));
                    try (ResultSet rs = stm.executeQuery()) {
                        while (rs.next()) {
                            check = true;
                            prodT.add(rs.getInt("id"));
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (!check) {

                    try (PreparedStatement stm = CON.prepareStatement("insert into tags (categoria) value (?)")) {
                        stm.setInt(1, Integer.parseInt(s));
                        try {
                            if (stm.executeUpdate() == 1) {
                            } else {
                                throw new DAOException("Impossible to add new tag (addCatTags)");
                            }
                        } catch (SQLException ex) {
                            throw new DAOException(ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try (PreparedStatement stm = CON.prepareStatement("select id from tags where categoria = ?")) {
                        stm.setInt(1, Integer.parseInt(s));
                        try (ResultSet rs = stm.executeQuery()) {
                            while (rs.next()) {
                                prodT.add(rs.getInt("id"));
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        System.out.println("prodT: " + prodT.toString());
        try (PreparedStatement stm = CON.prepareStatement("delete from blog_tags where blog = ?")) {
            stm.setInt(1, idBlog);
            try {
                stm.execute();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int s : prodT) {
            try (PreparedStatement stm = CON.prepareStatement("insert into blog_tags (blog, tag) values (?, ?)")) {
                stm.setInt(1, idBlog);
                stm.setInt(2, s);
                try {
                    if (stm.executeUpdate() == 1) {
                    } else {
                        throw new DAOException("Impossible to add tags to blog");
                    }
                } catch (SQLException ex) {
                    throw new DAOException(ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/
    @Override
    public String getTag(int id) throws DAOException {
        String query;
        String tag = "";

        try (PreparedStatement stm = CON.prepareStatement("select testo from tags where id = ?")) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    try {
                        tag = rs.getString("testo");
                    } catch (SQLException e) {
                        System.out.println(e.getSQLState());
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DAOException("getTag error", ex);
        }
        return tag;
    }

    @Override
    public String getTagName(int id) throws DAOException {
        String tag = "";
        try (PreparedStatement stm = CON.prepareStatement("select testo from tags where id = ?")) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    tag = rs.getString("testo");
                }
            }

        } catch (SQLException ex) {
            throw new DAOException("getTag error", ex);
        }

        return tag;
    }

    @Override
    public ArrayList<Integer> getAllTagsOfBlog(int id_blog) throws DAOException {
        ArrayList<Integer> tags = new ArrayList<>();
        ArrayList<Integer> prods = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select tag from blog_tags where blog=?")) {
            stm.setInt(1, id_blog);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int a = rs.getInt("tag");
                    if (a != 0) {
                        tags.add(a);
                    }
                }
            }

        } catch (SQLException ex) {
            throw new DAOException("getAllTagsOfBlog error 1", ex);
        }
        return tags;
    }

    /*
    This method return id of tag that contain only product
     */
    @Override
    public ArrayList<Integer> getProductTagsOfBlog(int id_blog) throws DAOException {
        ArrayList<String> tags = getAllTextTagsOfBlog(id_blog);
        ArrayList<Integer> prodotti = new ArrayList<>();

        for (String nome : tags) {
            try (PreparedStatement stm = CON.prepareStatement("select id from prodotto where nome = ?")) {
                stm.setString(1, nome);
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        prodotti.add(rs.getInt("id"));
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException("Impossibile restituire tutti i prodotti dei tag del blog. (JDBCBlogDAO, getProductTagsOfBlog)", ex);
            }
        }

        Collections.shuffle(prodotti);

        return prodotti;
    }

    /*
    This method return id of tag that contain only category
     */
    @Override
    public ArrayList<Integer> getCategoryTagsOfBlog(int id_blog) throws DAOException {
        ArrayList<String> tags = getAllTextTagsOfBlog(id_blog);
        ArrayList<Integer> categorie = new ArrayList<>();

        for (String nome : tags) {
            try (PreparedStatement stm = CON.prepareStatement("select id from categorie where nome = ?")) {
                stm.setString(1, nome);
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        categorie.add(rs.getInt("id"));
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException("Impossibile restituire tutte le categorie dei tag del blog. (JDBCBlogDAO, getCategoryTagsOfBlog)", ex);
            }
        }

        Collections.shuffle(categorie);

        return categorie;
    }

    @Override
    public ArrayList<String> getAllTextTagsOfBlog(int id_blog) throws DAOException {
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select tag from blog_tags where blog=?")) {
            stm.setInt(1, id_blog);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("tag"));
                }
            }

        } catch (SQLException ex) {
            throw new DAOException("getAllTagsOfBlog error 1", ex);
        }

        for (int id : ids) {
            try (PreparedStatement stm = CON.prepareStatement("select testo from tags where id=?")) {
                stm.setInt(1, id);

                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        tags.add(rs.getString("testo"));
                    }
                }

            } catch (SQLException ex) {
                throw new DAOException("getAllTagsOfBlog error 1", ex);
            }
        }

        Collections.shuffle(tags);

        return tags;
    }

    @Override
    public void cleanTags() throws DAOException {
        boolean check = false;
        ArrayList<Integer> ids = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement("select id from tags")) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int id : ids) {
            try (PreparedStatement stm = CON.prepareStatement("select id from blog_tags where tag = ?")) {
                stm.setInt(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        check = true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!check) {
                try (PreparedStatement stm = CON.prepareStatement("delete from tags where id = ?")) {
                    stm.setInt(1, id);
                    try {
                        stm.execute();
                    } catch (SQLException ex) {
                        throw new DAOException(ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCConsoleDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
