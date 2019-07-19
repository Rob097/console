package filters;

import database.daos.BlogDAO;
import database.daos.CatBlogDAO;
import database.daos.CategoryDAO;
import database.daos.CommBlogDAO;
import database.daos.ConsoleDAO;
import database.daos.MenuDAO;
import database.daos.ProductDAO;
import database.daos.RicetteDAO;
import database.exceptions.DAOFactoryException;
import database.factories.DAOFactory;
import database.factories.JDBCDAOFactory;
import database.jdbc.JDBCBlogDAO;
import database.jdbc.JDBCCatBlogDAO;
import database.jdbc.JDBCCategoryDAO;
import database.jdbc.JDBCCommBlogDAO;
import database.jdbc.JDBCConsoleDAO;
import database.jdbc.JDBCMenuDAO;
import database.jdbc.JDBCProductDAO;
import database.jdbc.JDBCRicetteDAO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
    private MenuDAO menudao;
    DAOFactory daoFactory;
    Connection connection;

    /**
     * Costruttore Vuoto
     */
    public cookieFilter() {
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

        if (request instanceof HttpServletRequest) {

            HttpSession session = ((HttpServletRequest) request).getSession();

            try {

                daoFactory = (DAOFactory) request.getServletContext().getAttribute("daoFactory");
                if (daoFactory == null) {
                    daoFactory = JDBCDAOFactory.getInstance();
                    System.out.println("\n\nErroe conInit\n");
                    throw new ServletException("Impossible to get dao factory for user storage system");
                }
                connection = daoFactory.getConnection();
                if (categorydao == null) {
                    categorydao = new JDBCCategoryDAO(connection);
                }
                if (productdao == null) {
                    productdao = new JDBCProductDAO(connection);
                }
                if (ricettedao == null) {
                    ricettedao = new JDBCRicetteDAO(connection);
                }
                if (catblogdao == null) {
                    catblogdao = new JDBCCatBlogDAO(connection);
                }
                if (blogdao == null) {
                    blogdao = new JDBCBlogDAO(connection);
                }
                if (commblogdao == null) {
                    commblogdao = new JDBCCommBlogDAO(connection);
                }
                if (consoledao == null) {
                    consoledao = new JDBCConsoleDAO(connection);
                }
                if (menudao == null) {
                    menudao = new JDBCMenuDAO(connection);
                }

            } catch (DAOFactoryException | SQLException ex) {
                System.out.println("errore init cookie filter");
                Logger.getLogger(cookieFilter.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (session != null) {
                //Console
                if (session.getAttribute("consoledao") == null || session.getAttribute("consoledao") != consoledao) {
                    session.setAttribute("consoledao", consoledao);
                }
                //Categorie
                if (session.getAttribute("categorydao") == null || session.getAttribute("categorydao") != categorydao) {
                    session.setAttribute("categorydao", categorydao);
                }
                //Prodotti
                if (session.getAttribute("productdao") == null || session.getAttribute("productdao") != productdao) {
                    session.setAttribute("productdao", productdao);
                }
                //Ricette
                if (session.getAttribute("ricettedao") == null || session.getAttribute("ricettedao") != ricettedao) {
                    session.setAttribute("ricettedao", ricettedao);
                }
                //Blog
                if (session.getAttribute("catblogdao") == null || session.getAttribute("catblogdao") != catblogdao) {
                    session.setAttribute("catblogdao", catblogdao);
                }
                if (session.getAttribute("blogdao") == null || session.getAttribute("blogdao") != blogdao) {
                    session.setAttribute("blogdao", blogdao);
                }
                if (session.getAttribute("commblogdao") == null || session.getAttribute("commblogdao") != commblogdao) {
                    session.setAttribute("commblogdao", commblogdao);
                }
                //Menu
                if (session.getAttribute("menudao") == null || session.getAttribute("menudao") != menudao) {
                    session.setAttribute("menudao", menudao);
                }

                //Cookies
                Cookie[] cookies = ((HttpServletRequest) request).getCookies();
                String ids;
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        switch (c.getName()) {
                            case ("preferiti"):
                                ids = c.getValue();
                                if (session.getAttribute("preferiti") == null || session.getAttribute("preferiti") != ids) {
                                    session.setAttribute("preferiti", ids);
                                }
                                break;
                            case ("carrello"):
                                ids = c.getValue();
                                if (session.getAttribute("carrello") == null || session.getAttribute("carrello") != ids) {
                                    session.setAttribute("carrello", ids);
                                }
                                break;
                        }
                    }
                }
            } else {
                System.out.println("cookiefilter session is null");
            }
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

        /*try {
            doAfterProcessing(request, response);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(cookieFilter.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            try {
                if (problem instanceof ServletException) {
                    throw (ServletException) problem;
                }
                if (problem instanceof IOException) {
                    throw (IOException) problem;
                }
                sendProcessingError(problem, request, response);
            } catch (DAOFactoryException | SQLException ex) {
                Logger.getLogger(cookieFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        Filter.super.destroy();
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

    private void sendProcessingError(Throwable t, ServletRequest request, ServletResponse response) throws DAOFactoryException, SQLException {
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

}
