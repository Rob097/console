/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodotti;

import database.daos.CategoryDAO;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCCategoryDAO;
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
import static varie.costanti.PASS;
import static varie.utili.unaccent;

/**
 *
 * @author Roberto97
 */
@MultipartConfig(maxFileSize = 16177215)
public class catImgChange extends HttpServlet {

    CategoryDAO categorydao = null;
    private final String UPLOAD_DIRECTORY = "/img/catProd/";

    @Override
    public void init() throws ServletException {
        //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        categorydao = new JDBCCategoryDAO(daoFactory.getConnection());
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
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("DELETE") != null && request.getParameter("DELETE").equals("true")) {
            if (request.getParameter("password") != null && request.getParameter("password").equals(PASS)) {
                String image = "", id = "";
                if (request.getParameter("immagine") != null) {
                    //Il replace viene fatto perchè altrimenti non trova l'immagine. 
                    //E' da controllare che funzioni anche in cloud
                    image = unaccent(request.getParameter("immagine").replace("../console", ""));
                }
                if (request.getParameter("id") != null) {
                    id = request.getParameter("id");
                }
                try {

                    categorydao.deleteCat(Integer.parseInt(id));
                    String listsFolder;
                    listsFolder = getServletContext().getRealPath(image);
                    listsFolder = listsFolder.replace("\\build", "");
                    //System.out.println("Image: " + image + "\nReal Path: " + getServletContext().getRealPath(image) + "\nlistFolder: " + listsFolder);
                    try {
                        ImageDispatcher.deleteImgFromDirectory(listsFolder);
                    } catch (Exception e) {
                        System.out.println("L'immagine non esiste");
                    }

                } catch (DAOException ex) {
                    Logger.getLogger(catImgChange.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            String id = "", url = "", nome = "";
            String immagine = request.getParameter("oldImg");
            Part filePart1 = null;
            boolean fresco = false;

            if (request.getParameter("id") != null) {
                id = request.getParameter("id");
            }
            if (request.getParameter("nome") != null) {
                nome = unaccent(request.getParameter("nome"));
            }
            if (request.getPart("immagine") != null) {
                filePart1 = request.getPart("immagine");
            }
            if (request.getParameter("fresco") != null) {
                fresco = request.getParameter("fresco").equals("true");
            }
            try {
                //Load dell'immagine
                if (filePart1 != null) {
                    if (filePart1.getContentType().contains("image/")) {
                        String upload_directory = "";
                        if (fresco) {
                            upload_directory = UPLOAD_DIRECTORY + "freschi/";
                        } else {
                            upload_directory = UPLOAD_DIRECTORY + "confezionati/";
                        }
                        try {
                            String listsFolder = obtainRootFolderPath(upload_directory);
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

                categorydao.alterImg(id, nome, immagine);
            } catch (DAOException ex) {
                Logger.getLogger(catImgChange.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect("/console/prodotti.jsp");
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
        request.setCharacterEncoding("UTF-8");
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
        request.setCharacterEncoding("UTF-8");
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

    /**
     * Questo metodo restituisce il percorso il percorso alla directory
     *
     * @param relativePath se questa stringa è vuota allora il metodo
     * restituisce il percorso a "...Web/" altrimenti restituisce il percroso
     * alla cartella "...Web/[relativePath]" Esempio: relativePath =
     * "Image/AvatarImg"
     * @return web/Image/AvatarImg
     */
    public String obtainRootFolderPath(String relativePath) {
        String folder;
        folder = relativePath;
        folder = getServletContext().getRealPath(folder);
        folder = folder.replace("\\build", "");
        return folder;
    }

}
