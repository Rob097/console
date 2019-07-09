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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import prodotti.catImgChange;
import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import static varie.costanti.PASS;
import static varie.utili.obtainRootFolderPath;
import static varie.utili.unaccent;

/**
 *
 * @author Roberto97
 */
@MultipartConfig(maxFileSize = 16177215)
public class updateBlog extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");

        String id = "", categoria = "";
        if (request.getParameter("id") != null) {
            id = request.getParameter("id");
            if (request.getParameter("DELETE") != null && request.getParameter("DELETE").equals("true")) {
                if (request.getParameter("password") != null && request.getParameter("password").equals(PASS)) {
                    String image = "";
                    if (request.getParameter("immagine") != null) {
                        //Il replace viene fatto perchè altrimenti non trova l'immagine. 
                        //E' da controllare che funzioni anche in cloud
                        image = unaccent(request.getParameter("immagine").replace("../console", ""));
                    }
                    try {

                        String listsFolder;
                        listsFolder = getServletContext().getRealPath(image);
                        if (listsFolder != null) {
                            listsFolder = listsFolder.replace("\\build", "");
                        }
                        try {
                            ImageDispatcher.deleteImgFromDirectory(listsFolder);
                        } catch (Exception e) {
                            System.out.println("L'immagine non esiste");
                        }
                        blogdao.deleteBlog(id);

                        if (request.getParameter("categoria") != null) {
                            categoria = request.getParameter("categoria");
                        }

                        if (catblogdao.getNumberOfBlog(categoria) == 0) {
                            catblogdao.deleteCat(categoria);
                        }
                    } catch (DAOException ex) {
                        Logger.getLogger(catImgChange.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect("/console/articoli.jsp");
            } else {

                try {

                    String titolo = "", testo = "", creatore = "", immagine = "", descrizione = "", tag = "";
                    ArrayList<String> tags = new ArrayList<>();
                    Part filePart1 = null;

                    if (request.getParameter("titolo") != null) {
                        titolo = request.getParameter("titolo");
                    }
                    if (request.getParameter("testo") != null) {
                        testo = request.getParameter("testo");
                    }
                    if (request.getParameter("newCreator") != null && !request.getParameter("newCreator").isEmpty()) {
                        System.out.println("CREATORE: " + request.getParameter("newCreator"));
                        creatore = request.getParameter("newCreator");
                    } else {
                        creatore = request.getParameter("autore");
                    }
                    if (request.getParameter("newCategory") != null && !request.getParameter("newCategory").isEmpty()) {
                        System.out.println("CATEGORIA: " + request.getParameter("newCategory"));
                        categoria = request.getParameter("newCategory");
                        catblogdao.addCat(categoria);
                    } else {
                        categoria = request.getParameter("categoria");
                    }
                    if (request.getPart("immagine") != null) {
                        filePart1 = request.getPart("immagine");
                    }
                    if (request.getParameter("oldIMG") != null) {
                        immagine = request.getParameter("oldIMG");
                    }
                    if (request.getParameter("tag") != null) {
                        tag = request.getParameter("tag");
                    }
                    
                    tags.addAll(Arrays.asList(tag.split(";")));
                    
                    blogdao.addTags(tags, Integer.parseInt(id));
                    
                    descrizione = testo.replaceAll("[<](/)?[^>]*[>]", "");

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
                                String imagineName = "uncompressed" + id + "." + extension;

                            ImageDispatcher.insertCompressedImg(listsFolder, imagineName, filePart1, extension);
                            immagine = ImageDispatcher.savePathImgInDatabsae(upload_directory, imagineName.replace("uncompressed", ""));
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

                    blogdao.alterBlog(id, titolo, testo, creatore, categoria, immagine, descrizione);

                } catch (DAOException ex) {
                    Logger.getLogger(updateBlog.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect("/console/articolo.jsp?id=" + id);
            }
        } else {
            response.sendRedirect("/console/articoli.jsp");
        }
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
