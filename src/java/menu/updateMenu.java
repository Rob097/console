/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import database.daos.MenuDAO;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCMenuDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import prodotti.updateProd;
import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import static varie.costanti.MAX_IMG_SIZE;
import static varie.utili.obtainRootFolderPath;
import static varie.utili.unaccent;

/**
 *
 * @author Roberto97
 */
@MultipartConfig()
public class updateMenu extends HttpServlet {

    private static final long serialVersionUID = 1L;

    MenuDAO menudao = null;
    private final String UPLOAD_DIRECTORY_IMG = "/img/menu/";
    private final String UPLOAD_DIRECTORY_COPERTINA = "/img/menu/copertine/";

    @Override
    public void init() throws ServletException {
        try {
            //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new ServletException("Impossible to get dao factory for user storage system");
            }
            menudao = new JDBCMenuDAO(daoFactory.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(updateMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        response.setContentType("text/html;charset=UTF-8");

        int idMenu = 0;
        String nome = null, immagine = null, copertina = null;
        Part immaginePart = null, copertinaPart = null;
        RequestDispatcher view = request.getRequestDispatcher("menu.jsp");
        String url = "";
        boolean checkIMG = true;

        try {

            if (request.getParameter("id") != null) {
                idMenu = Integer.parseInt(request.getParameter("id"));
            }
            if (request.getParameter("nome") != null) {
                nome = unaccent(request.getParameter("nome"));
            }
            if (request.getParameter("oldImg") != null) {
                immagine = unaccent(request.getParameter("oldImg"));
            }
            if (request.getParameter("oldCopertina") != null) {
                copertina = unaccent(request.getParameter("oldCopertina"));
            }

            if (request.getParameter("DELETE") != null && request.getParameter("DELETE").equals("true")) {
                if (immagine != null) {
                    //Il replace viene fatto perchè altrimenti non trova l'immagine. 
                    //E' da controllare che funzioni anche in cloud
                    immagine = immagine.replace("../console", "");
                }
                if (copertina != null) {
                    //Il replace viene fatto perchè altrimenti non trova l'immagine. 
                    //E' da controllare che funzioni anche in cloud
                    copertina = copertina.replace("../console", "");
                }
                String listsFolder;
                listsFolder = getServletContext().getRealPath(immagine);
                if (listsFolder != null) {
                    listsFolder = listsFolder.replace("\\build", "");
                }
                try {
                    ImageDispatcher.deleteImgFromDirectory(listsFolder);
                } catch (Exception e) {
                    System.out.println("L'immagine non esiste");
                }

                String listsFolderCopertina;
                listsFolderCopertina = getServletContext().getRealPath(copertina);
                if (listsFolderCopertina != null) {
                    listsFolderCopertina = listsFolderCopertina.replace("\\build", "");
                }
                try {
                    ImageDispatcher.deleteImgFromDirectory(listsFolderCopertina);
                } catch (Exception e) {
                    System.out.println("L'copertina non esiste");
                }
                menudao.deleteMenu(idMenu);
            } else {

                if (request.getPart("immagineMenu") != null) {
                    if (request.getPart("immagineMenu").getSize() <= MAX_IMG_SIZE) {
                        immaginePart = request.getPart("immagineMenu");
                    } else {
                        checkIMG = false;
                    }
                }
                if (request.getPart("copertinaMenu") != null) {
                    if (request.getPart("copertinaMenu").getSize() <= MAX_IMG_SIZE) {
                        copertinaPart = request.getPart("copertinaMenu");
                    } else {
                        checkIMG = false;
                    }
                }
                if (checkIMG) {
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
                            } catch (IOException e) {
                                System.out.println("Exception:");
                            }
                        } else {
                            System.out.println("FilePart not in image/ updateMenu 1");
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
                            System.out.println("FilePart not in image/ updateMenu2");
                        }
                    } else {
                        System.out.println("filePart = null");
                    }

                    menudao.updateMenu(idMenu, nome, copertina, immagine);
                    url = "menu.jsp";
                } else {
                    response.setHeader("NOTIFICA", "L'immagine supera i 2MB di peso");
                }
            }

        } catch (DAOException | IOException | RuntimeException | ServletException ex) {
            Logger.getLogger(updateProd.class.getName()).log(Level.SEVERE, null, ex);
            response.setHeader("NOTIFICA", "Errore generico");
        }
        if (url.equals("")) {
            view.forward(request, response);
        } else {
            response.sendRedirect(url);
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
