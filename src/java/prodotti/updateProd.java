/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodotti;

import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import database.daos.ProductDAO;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCProductDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Roberto97
 */
@MultipartConfig(maxFileSize = 16177215)
public class updateProd extends HttpServlet {

    ProductDAO productdao = null;
    private final String UPLOAD_DIRECTORY = "/img/prodotti/";

    @Override
    public void init() throws ServletException {
        //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        productdao = new JDBCProductDAO(daoFactory.getConnection());
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

        int idProd = 0;
        String nome = null, descrizione = null, categoria = null;
        String immagine = request.getParameter("oldImg");
        Part filePart1 = null;
        double costo = 0;
        boolean fresco = false, disponibile = false;

        if (request.getParameter("DELETE") != null && request.getParameter("DELETE").equals("true")) {
            if (request.getParameter("idProd") != null) {
                try {
                    idProd = Integer.parseInt(request.getParameter("idProd"));
                    productdao.deleteProd(idProd);
                } catch (DAOException ex) {
                    Logger.getLogger(updateProd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                if (request.getParameter("idProd") != null) {
                    idProd = Integer.parseInt(request.getParameter("idProd"));
                }
                if (request.getParameter("nome") != null) {
                    nome = request.getParameter("nome");
                }
                if (request.getParameter("descrizione") != null) {
                    descrizione = request.getParameter("descrizione");
                }
                if (request.getPart("immagine") != null) {
                    filePart1 = request.getPart("immagine");
                }
                if (request.getParameter("categoria") != null) {
                    categoria = request.getParameter("categoria");
                }
                if (request.getParameter("costo") != null) {
                    costo = Double.parseDouble(request.getParameter("costo").replace(",", "."));
                }
                if (request.getParameter("disponibile") != null) {
                    disponibile = true;
                }

                //Load dell'immagine
                if (filePart1 != null) {
                    if (filePart1.getContentType().contains("image/")) {
                        String upload_directory = "";
                        if (fresco) {
                            upload_directory = UPLOAD_DIRECTORY + "freschi/";
                        } else {
                            upload_directory = UPLOAD_DIRECTORY +  "confezionati/";
                        }
                        try {
                            String listsFolder = obtainRootFolderPath(upload_directory);
                            String extension = getImageExtension(filePart1);
                            String imagineName = idProd + "." + extension;
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
                
                productdao.alterProd(idProd, nome, descrizione, categoria, immagine, disponibile, costo);

            } catch (Exception ex) {
                Logger.getLogger(updateProd.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        response.sendRedirect("/console/prodotti.jsp#"+categoria);

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
