package filters;

import database.daos.BlogDAO;
import database.daos.CatBlogDAO;
import database.daos.CategoryDAO;
import database.daos.CommBlogDAO;
import database.daos.ConsoleDAO;
import database.daos.ProductDAO;
import database.daos.RicetteDAO;
import database.entities.Blog;
import database.entities.CatBlog;
import database.entities.Categoria;
import database.entities.CommBlog;
import database.entities.Commento;
import database.entities.Prodotto;
import database.entities.Ricetta;
import database.exceptions.DAOException;
import database.exceptions.DAOFactoryException;
import database.factories.DAOFactory;
import database.factories.JDBCDAOFactory;
import static database.factories.JDBCDAOFactory.DBURL;
import database.jdbc.JDBCBlogDAO;
import database.jdbc.JDBCCatBlogDAO;
import database.jdbc.JDBCCategoryDAO;
import database.jdbc.JDBCCommBlogDAO;
import database.jdbc.JDBCConsoleDAO;
import database.jdbc.JDBCProductDAO;
import database.jdbc.JDBCRicetteDAO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static varie.costanti.MAX_W_PRICE;
import static varie.costanti.MED_W_PRICE;
import static varie.costanti.MIN_W_PRICE;

/**
 * E' il filtro che analizza i cookie e salva in sessione
 * 
 * @author Roberto97
 */
public class cookieFilter implements Filter {

    private static final boolean DEBUG = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private CategoryDAO categorydao;
    private ProductDAO productdao;
    private RicetteDAO ricettedao;
    private CatBlogDAO catblogdao;
    private BlogDAO blogdao;
    private CommBlogDAO commblogdao;
    private ConsoleDAO consoledao;
    DAOFactory daoFactory;

    /**
     * Costruttore Vuoto
     */
    public cookieFilter() {
    }

    /**
     * Inizializza i vari JDBCDAO
     */
    private void conInit(FilterConfig filterConfig) throws ServletException {
        daoFactory = (DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }

        categorydao = new JDBCCategoryDAO(daoFactory.getConnection());
        productdao = new JDBCProductDAO(daoFactory.getConnection());
        ricettedao = new JDBCRicetteDAO(daoFactory.getConnection());
        catblogdao = new JDBCCatBlogDAO(daoFactory.getConnection());
        blogdao = new JDBCBlogDAO(daoFactory.getConnection());
        commblogdao = new JDBCCommBlogDAO(daoFactory.getConnection());
        consoledao = new JDBCConsoleDAO(daoFactory.getConnection());
    }

    /**
     * Qui vengono creati diversi attributi di sessione utli da usare in JSTL.
     * Inoltre vengono processati i cookie e sistemati in attributi di sessione.
     */
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException, DAOFactoryException {
        if (DEBUG) {
            log("cookieFilter:DoBeforeProcessing");
        }

        if (request instanceof HttpServletRequest) {

            HttpSession session = ((HttpServletRequest) request).getSession();
            if (session != null) {
                try {
                    //Categorie
                    ArrayList<Categoria> categorie = categorydao.getAllCategories();
                    session.setAttribute("allCategories", categorie);
                    session.setAttribute("categorydao", categorydao);

                    //Prodotti
                    ArrayList<Prodotto> prodotti = productdao.getAllProducts();
                    session.setAttribute("allProducts", prodotti);
                    session.setAttribute("productdao", productdao);

                    //Ricette
                    ArrayList<Ricetta> ricette = ricettedao.getAllRecipes();
                    ArrayList<Commento> commenti = ricettedao.getAllComments();
                    session.setAttribute("allRecipes", ricette);
                    session.setAttribute("commenti", commenti);
                    session.setAttribute("ricettedao", ricettedao);

                    //Blog
                    ArrayList<CatBlog> CatBlog = catblogdao.getAllCatBlog();
                    ArrayList<Blog> Blog = blogdao.getAllBlogs();
                    ArrayList<CommBlog> CommBlog = commblogdao.getAllCommBlog();
                    session.setAttribute("CatBlog", CatBlog);
                    session.setAttribute("CommBlog", CommBlog);
                    session.setAttribute("Blog", Blog);
                    session.setAttribute("catblogdao", catblogdao);
                    session.setAttribute("blogdao", blogdao);
                    session.setAttribute("commblogdao", commblogdao);
                    
                    //Costanti
                    session.setAttribute("MIN_W_PRICE", MIN_W_PRICE);
                    session.setAttribute("MED_W_PRICE", MED_W_PRICE);
                    session.setAttribute("MAX_W_PRICE", MAX_W_PRICE);

                    //Console                    
                    session.setAttribute("consoledao", consoledao);
                    
                    //Cookies
                    Cookie[] cookies = ((HttpServletRequest) request).getCookies();
                    String ids;
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            switch (c.getName()) {
                                case ("preferiti"):
                                    ids = c.getValue();
                                    session.setAttribute("preferiti", ids);
                                    break;
                                case ("carrello"):
                                    ids = c.getValue();
                                    session.setAttribute("carrello", ids);
                                    break;
                            }
                        }
                    }
                } catch (DAOException ex) {
                    ifBadCon(request, response);
                }
            }
        }
    }

    /**
     * QEsatta replica di doBeforeProcessing 
     */
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException, DAOFactoryException {
        if (DEBUG) {
            log("cookieFilter:DoAfterProcessing");
        }
        doBeforeProcessing(request, response);
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (DEBUG) {
            log("cookieFilter:doFilter()");
        }

        try {
            doBeforeProcessing(request, response);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(cookieFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
        }

        try {
            doAfterProcessing(request, response);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(cookieFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {
                log("CookieFilter:Initializing filter");
            }
            conInit(filterConfig);
        }
    }

    /**
     * Return a String representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("cookieFilter()");
        }
        StringBuilder sb = new StringBuilder("cookieFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                try (PrintStream ps = new PrintStream(response.getOutputStream()); PrintWriter pw = new PrintWriter(ps)) {
                    pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                    // PENDING! Localize this for next official release
                    pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                    pw.print(stackTrace);
                    pw.print("</pre></body>\n</html>"); //NOI18N
                }
                response.getOutputStream().close();
            } catch (IOException ex) {
            }
        } else {
            try {
                try (PrintStream ps = new PrintStream(response.getOutputStream())) {
                    t.printStackTrace(ps);
                }
                response.getOutputStream().close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (IOException ex) {
        }
        return stackTrace;
    }

    /**
     *
     * @param msg
     */
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    /**
     *  Se ci sono problemi con la connessione allora riesegue le connessione e i cookie
     * @param request
     * @param response
     * @throws DAOFactoryException
     */
    public void ifBadCon(ServletRequest request, ServletResponse response) throws DAOFactoryException {

        System.out.println("\nIn Bad Con\n");

        daoFactory = new JDBCDAOFactory(DBURL);
        categorydao = new JDBCCategoryDAO(daoFactory.getConnection());
        productdao = new JDBCProductDAO(daoFactory.getConnection());
        ricettedao = new JDBCRicetteDAO(daoFactory.getConnection());
        catblogdao = new JDBCCatBlogDAO(daoFactory.getConnection());
        blogdao = new JDBCBlogDAO(daoFactory.getConnection());
        commblogdao = new JDBCCommBlogDAO(daoFactory.getConnection());
        consoledao = new JDBCConsoleDAO(daoFactory.getConnection());
        if (DEBUG) {
            log("cookieFilter:DoBeforeProcessing");
        }
        if (request instanceof HttpServletRequest) {

            HttpSession session = ((HttpServletRequest) request).getSession();
            if (session != null) {
                try {
                    //Categorie
                    ArrayList<Categoria> categorie = categorydao.getAllCategories();
                    session.setAttribute("allCategories", categorie);
                    session.setAttribute("categorydao", categorydao);

                    //Prodotti
                    ArrayList<Prodotto> prodotti = productdao.getAllProducts();
                    session.setAttribute("allProducts", prodotti);
                    session.setAttribute("productdao", productdao);

                    //Ricette
                    ArrayList<Ricetta> ricette = ricettedao.getAllRecipes();
                    ArrayList<Commento> commenti = ricettedao.getAllComments();
                    session.setAttribute("allRecipes", ricette);
                    session.setAttribute("commenti", commenti);
                    session.setAttribute("ricettedao", ricettedao);

                    //Blog
                    ArrayList<CatBlog> CatBlog = catblogdao.getAllCatBlog();
                    ArrayList<Blog> Blog = blogdao.getAllBlogs();
                    ArrayList<CommBlog> CommBlog = commblogdao.getAllCommBlog();
                    session.setAttribute("CatBlog", CatBlog);
                    session.setAttribute("CommBlog", CommBlog);
                    session.setAttribute("Blog", Blog);
                    session.setAttribute("catblogdao", catblogdao);
                    session.setAttribute("blogdao", blogdao);
                    session.setAttribute("commblogdao", commblogdao);
                    
                    //Costanti
                    session.setAttribute("MIN_W_PRICE", MIN_W_PRICE);
                    session.setAttribute("MED_W_PRICE", MED_W_PRICE);
                    session.setAttribute("MAX_W_PRICE", MAX_W_PRICE);

                    //Console                    
                    session.setAttribute("consoledao", consoledao);

                    //Cookies
                    Cookie[] cookies = ((HttpServletRequest) request).getCookies();
                    String ids;
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            switch (c.getName()) {
                                case ("preferiti"):
                                    ids = c.getValue();
                                    session.setAttribute("preferiti", ids);
                                    break;
                                case ("carrello"):
                                    ids = c.getValue();
                                    session.setAttribute("carrello", ids);
                                    break;
                            }
                        }
                    }
                } catch (DAOException ex1) {
                    Logger.getLogger(cookieFilter.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

}