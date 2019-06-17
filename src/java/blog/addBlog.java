/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blog;

import database.daos.BlogDAO;
import database.daos.CatBlogDAO;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCBlogDAO;
import database.jdbc.JDBCCatBlogDAO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import static varie.utili.obtainRootFolderPath;

/**
 *
 * @author Roberto97
 */
@MultipartConfig(maxFileSize = 16177215)
public class addBlog extends HttpServlet {

    BlogDAO blogdao = null;
    CatBlogDAO catblogdao = null;
    private final String UPLOAD_DIRECTORY = "/img/blog/";

    @Override
    public void init() throws ServletException {
        //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        blogdao = new JDBCBlogDAO(daoFactory.getConnection());
        catblogdao = new JDBCCatBlogDAO(daoFactory.getConnection());
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            request.setCharacterEncoding("UTF-8");
            String categoria = "", titolo = "", testo = "", creatore = "", immagine = "", descrizione = "";
            Part filePart1 = null;
            if (request.getParameter("titolo") != null) {
                titolo = request.getParameter("titolo");
            }
            if (request.getParameter("testo") != null) {
                testo = request.getParameter("testo");
            }
            if (request.getParameter("newCreator") != null && !request.getParameter("newCreator").isEmpty()) {
                creatore = request.getParameter("newCreator");
            } else if (request.getParameter("autore") != null){
                creatore = request.getParameter("autore");
            }
            if (request.getParameter("newCategory") != null && !request.getParameter("newCategory").isEmpty()) {
                try {
                    categoria = request.getParameter("newCategory");
                    catblogdao.addCat(categoria);
                } catch (DAOException ex) {
                    Logger.getLogger(addBlog.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                categoria = request.getParameter("categoria");
            }
            if (request.getPart("immagine") != null) {
                filePart1 = request.getPart("immagine");
            }
            System.out.println("Creatore: " + creatore);
            descrizione = testo.replaceAll("[<](/)?[^>]*[>]", "");
            int id = blogdao.addBlog(titolo, testo, creatore, categoria, descrizione);
            //Load dell'immagine
            if (filePart1 != null) {
                if (filePart1.getContentType().contains("image/")) {
                    String upload_directory = "";
                    upload_directory = UPLOAD_DIRECTORY + categoria + "/";
                    try {
                        String listsFolder = obtainRootFolderPath(upload_directory, getServletContext()).replaceAll(" ", "");
                        File directory = new File(listsFolder);
                        if (!directory.exists()) {
                            directory.mkdir();
                            // If you require it to make the entire directory path including parents,
                            // use directory.mkdirs(); here instead.
                        }
                        String extension = getImageExtension(filePart1);
                        String imagineName = id + "." + extension;
                        try {
                            ImageDispatcher.deleteImgFromDirectory(listsFolder + imagineName);
                        } catch (Exception e) {
                            System.out.println("Nessuna immagine da cancellare");
                        }
                        ImageDispatcher.insertImgIntoDirectory(listsFolder, imagineName, filePart1);
                        immagine = ImageDispatcher.savePathImgInDatabsae(upload_directory, imagineName);
                    } catch (RuntimeException e) {
                        System.out.println("RuntimeException:");
                        throw e;
                    } catch (Exception e) {
                        System.out.println("Exception:");
                    }
                } else {
                    System.out.println("FilePart not in image/");
                }
            } else {
                System.out.println("filePart = null");
            }
            blogdao.alterBlog(""+id, titolo, testo, creatore, categoria, immagine, descrizione);
            response.sendRedirect("/console/articolo.jsp?id="+id);
        } catch (DAOException ex) {
            Logger.getLogger(addBlog.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
