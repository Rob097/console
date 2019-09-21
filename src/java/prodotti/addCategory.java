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
import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import static varie.costanti.MAX_IMG_SIZE;
import static varie.utili.unaccent;
import static varie.utili.obtainRootFolderPath;

/**
 *
 * @author Roberto97
 */
@MultipartConfig()
public class addCategory extends HttpServlet {

    private static final long serialVersionUID = 1L;

    CategoryDAO categorydao = null;
    private final String UPLOAD_DIRECTORY = "/img/catProd/";

    @Override
    public void init() throws ServletException {
        try {
            //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new ServletException("Impossible to get dao factory for user storage system");
            }
            categorydao = new JDBCCategoryDAO(daoFactory.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(addCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String nome = null, descrizione = null;
        String immagine = "";
        Part filePart1 = null;
        boolean fresco = false;
        RequestDispatcher view = request.getRequestDispatcher("prodotti.jsp");
        String url = "";

        try {
            if (request.getPart("immagine") != null && request.getPart("immagine").getSize() <= MAX_IMG_SIZE) {
                filePart1 = request.getPart("immagine");
                if (request.getParameter("nome") != null) {
                    nome = unaccent(request.getParameter("nome"));
                }
                if (request.getParameter("descrizione") != null) {
                    descrizione = unaccent(request.getParameter("descrizione"));
                }
                if (request.getParameter("fresco") != null) {
                    fresco = true;
                }

                int id = categorydao.addCategory(nome, descrizione, fresco);

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
                            String listsFolder = obtainRootFolderPath(upload_directory, getServletContext()).replaceAll(" ", "");
                            String extension = getImageExtension(filePart1);
                            String imagineName = "uncompressed" + id + "." + extension;

                            ImageDispatcher.insertCompressedImg(listsFolder, imagineName, filePart1, extension);
                            immagine = ImageDispatcher.savePathImgInDatabsae(upload_directory, imagineName.replace("uncompressed", ""));
                        } catch (RuntimeException e) {
                            System.out.println("RuntimeException:");
                            throw e;
                        } catch (IOException e) {
                            System.out.println("Exception:");
                        }
                    } else {
                        System.out.println("FilePart not in image/");
                    }
                } else {
                    System.out.println("filePart = null");
                }

                categorydao.alterImg("" + id, nome, immagine, descrizione);
                url = "prodotti.jsp";

            } else {
                response.setHeader("NOTIFICA", "L'immagine supera i 2.4MB di peso");
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

}
