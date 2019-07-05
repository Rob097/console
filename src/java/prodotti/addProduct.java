/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodotti;

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
import varie.ImageDispatcher;
import static varie.ImageDispatcher.getImageExtension;
import static varie.utili.obtainRootFolderPath;
import static varie.utili.unaccent;

/**
 *
 * @author Roberto97
 */
@MultipartConfig(maxFileSize = 16177215)
public class addProduct extends HttpServlet {

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

        try {

            String nome = null, descrizione = null, immagine = null, categoria = null;
            double costo = 0.01;
            Part filePart1 = null;
            boolean fresco = false, disponibile = true;

            if (request.getParameter("nome") != null) {
                nome = unaccent(request.getParameter("nome"));
            }
            if (request.getParameter("categoria") != null) {
                categoria = unaccent(request.getParameter("categoria"));
            }
            if (request.getParameter("descrizione") != null) {
                descrizione = unaccent(request.getParameter("descrizione"));
            }
            if (request.getParameter("costo") != null) {
                if (request.getParameter("costo").equals(".") || request.getParameter("costo").equals(",")) {
                    costo = 0.01;
                } else {
                    try{
                    costo = Double.parseDouble(request.getParameter("costo").replace(",", "."));
                    }catch(NumberFormatException e){
                        e.printStackTrace();
                    }
                }
            }
            if (request.getPart("immagine") != null) {
                filePart1 = request.getPart("immagine");
            }
            if (request.getParameter("fresco") != null) {
                fresco = request.getParameter("fresco").equals("true");
            }
            if (request.getParameter("disponibile") != null) {
                disponibile = true;
            }

            int id = productdao.addProd(nome, descrizione, categoria, costo, disponibile, fresco);

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
                        String listsFolder = obtainRootFolderPath(upload_directory, getServletContext());
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

            productdao.alterProd(id, nome, descrizione, categoria, immagine, disponibile, costo);
            response.sendRedirect("/console/prodotti.jsp#"+categoria);

        } catch (DAOException ex) {
            Logger.getLogger(addProduct.class.getName()).log(Level.SEVERE, null, ex);
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