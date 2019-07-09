/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import database.daos.MenuDAO;
import database.factories.DAOFactory;
import database.jdbc.JDBCMenuDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import prodotti.updateProd;
import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import static varie.utili.obtainRootFolderPath;
import static varie.utili.unaccent;

/**
 *
 * @author Roberto97
 */
@MultipartConfig(maxFileSize = 16177215)
public class addMenu extends HttpServlet {

    MenuDAO menudao = null;
    private final String UPLOAD_DIRECTORY_IMG = "/img/menu/";
    private final String UPLOAD_DIRECTORY_COPERTINA = "/img/menu/copertine/";

    @Override
    public void init() throws ServletException {
        //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        menudao = new JDBCMenuDAO(daoFactory.getConnection());
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

        String nome = "", immagine = "", copertina = "";
        Part immaginePart = null, copertinaPart = null;

        try {

            if (request.getParameter("nome") != null) {
                nome = unaccent(request.getParameter("nome"));
            }
            if (request.getPart("ADDimmagineMenu") != null) {
                immaginePart = request.getPart("ADDimmagineMenu");
            }
            if (request.getPart("ADDcopertinaMenu") != null) {
                copertinaPart = request.getPart("ADDcopertinaMenu");
            }

            //Load dell'immagine
            if (immaginePart != null) {
                if (immaginePart.getContentType().contains("image/")) {
                    try {
                        String listsFolder = obtainRootFolderPath(UPLOAD_DIRECTORY_IMG, getServletContext());
                        String extension = getImageExtension(immaginePart);
                        String imagineName = "uncompressed" + nome + "." + extension;
                        imagineName = imagineName.replace(" ", "");
                        ImageDispatcher.insertCompressedImg(listsFolder, imagineName, immaginePart, extension);
                        immagine = ImageDispatcher.savePathImgInDatabsae(UPLOAD_DIRECTORY_IMG, imagineName.replace("uncompressed", ""));

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

            //Load della copertina
            if (copertinaPart != null) {
                if (copertinaPart.getContentType().contains("image/")) {
                    try {
                        String listsFolder = obtainRootFolderPath(UPLOAD_DIRECTORY_COPERTINA, getServletContext());
                        String extension = getImageExtension(copertinaPart);
                        String imagineName = "uncompressed" + nome + "." + extension;
                        imagineName = imagineName.replace(" ", "");
                        ImageDispatcher.insertCompressedImg(listsFolder, imagineName, copertinaPart, extension);
                        copertina = ImageDispatcher.savePathImgInDatabsae(UPLOAD_DIRECTORY_COPERTINA, imagineName.replace("uncompressed", ""));

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

            menudao.addMenu(nome, immagine, copertina);

        } catch (Exception ex) {
            Logger.getLogger(updateProd.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("menu.jsp");
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
