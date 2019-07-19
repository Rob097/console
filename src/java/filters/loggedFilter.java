/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static utente.EncryptDecryptString.encrypt;
import static varie.costanti.PASS;
import static varie.costanti.ROB_PASS;

/**
 *
 * @author Roberto97
 */
public class loggedFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private boolean check = false;

    public loggedFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        check = false;
        if (request instanceof HttpServletRequest) {

            //Cookies
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(encrypt("LOGGED").replace("=",ROB_PASS))) {
                        if (c.getValue().equals(encrypt(PASS).replace("=",ROB_PASS))) {
                            //System.out.println("truedkf");
                            check = true;
                        }
                    }
                }
            }

            HttpSession session = ((HttpServletRequest) request).getSession();
            if (session != null) {
                if (session.getAttribute(encrypt("LOGGED").replace("=",ROB_PASS)) != null && session.getAttribute(encrypt("LOGGED").replace("=",ROB_PASS)).equals(encrypt(PASS).replace("=",ROB_PASS))) {
                    check = true;
                    //System.out.println("session trueòlsdm");                    
                }
            }
        }
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
        //System.out.println("log filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String login = "/login.jsp";
        String loginURI = req.getContextPath() + login;
        String homeURI = req.getContextPath() + "/dashboard.jsp";
        String where = req.getRequestURI().replace(req.getContextPath(), "");

        doBeforeProcessing(request, response);

        if (where.equals(login) || where.equals("/")) {
            if (check) {
                res.sendRedirect(homeURI);
            } else {
                chain.doFilter(request, response);
            }
        } else {

            Throwable problem = null;

            if (check) {
                //System.out.println("second if");
                try {
                    chain.doFilter(request, response);
                } catch (IOException | ServletException t) {
                    // If an exception is thrown somewhere down the filter chain,
                    // we still want to execute our after processing, and then
                    // rethrow the problem after that.
                    problem = t;
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
            } else {
                //System.out.println("else");
                res.sendRedirect(loginURI);
            }
        }
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
     */
    @Override
    public void init(FilterConfig filterConfig
    ) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {
                //log("loggedFilter:Initializing filter");
            }
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
            return ("loggedFilter()");
        }
        StringBuilder sb = new StringBuilder("loggedFilter(");
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

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
