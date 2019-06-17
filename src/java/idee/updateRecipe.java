/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idee;

import database.daos.RicetteDAO;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCRicetteDAO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class updateRecipe extends HttpServlet {

    RicetteDAO ricettedao = null;
    private final String UPLOAD_DIRECTORY = "/img/idee/";

    @Override
    public void init() throws ServletException {
        //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        ricettedao = new JDBCRicetteDAO(daoFactory.getConnection());
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
        String nome = "", procedimento = "", descrizione = "", immagine = "", difficolta = "", creatore = "";
        int id_prod = 0, tempo = 0, id = 0;
        boolean categoria = true;
        String catS = "";
        String ingS = "";
        Part filePart1 = null;
        try {
            if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            if (request.getParameter("titolo") != null) {
                nome = request.getParameter("titolo");
            }
            if (request.getParameter("product") != null) {
                id_prod = Integer.parseInt(request.getParameter("product"));
            }

            if (request.getParameter("autore") != null) {
                creatore = request.getParameter("autore");
            }
            if (request.getParameter("newCreator") != null && !request.getParameter("newCreator").isEmpty()) {
                creatore = request.getParameter("newCreator");
            }
            System.out.println("Creatore: " + creatore);

            if (request.getParameter("timeInput") != null) {
                tempo = Integer.parseInt(request.getParameter("timeInput"));
            }
            if (request.getParameter("difficultInput") != null) {
                difficolta = request.getParameter("difficultInput");
            }
            if (request.getParameter("procedimento") != null) {
                procedimento = request.getParameter("procedimento");
                descrizione = procedimento.replaceAll("[<](/)?[^>]*[>]", "");
            }
            if (request.getPart("immagine") != null) {
                filePart1 = request.getPart("immagine");
            } 
            if (request.getParameter("oldIMG") != null) {
                immagine = request.getParameter("oldIMG");
            }
            
            if (request.getParameter("categoria") != null) {
                if (request.getParameter("categoria").equals("nostre")) {
                    categoria = true;
                }
                if (request.getParameter("categoria").equals("utenti")) {
                    categoria = false;
                }
                catS = request.getParameter("categoria");
            }

            ArrayList<String> ingredientiNomi = new ArrayList<>();
            ArrayList<String> quantitaNomi = new ArrayList<>();

            ArrayList<String> ingredienti = new ArrayList<>();
            ArrayList<String> quantita = new ArrayList<>();

            Enumeration keys = request.getParameterNames();
            try {
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    if (key.contains("[ingrediente]")) {
                        ingredientiNomi.add(key);
                    }
                    if (key.contains("[quantity]")) {
                        quantitaNomi.add(key);
                    }
                }

                for (int k = 0; k < ingredientiNomi.size(); k++) {
                    if (!request.getParameter(ingredientiNomi.get(k)).equals("")) {
                        ingredienti.add(request.getParameter(ingredientiNomi.get(k)));
                    }
                    if (!request.getParameter(quantitaNomi.get(k)).equals("")) {
                        quantita.add(request.getParameter(quantitaNomi.get(k)));
                    }
                }

                for (int k = 0; k < ingredienti.size(); k++) {
                    ingS += ingredienti.get(k) + " " + quantita.get(k) + "_";
                }

                String old = "";
                if (!ingS.equals("")) {
                    StringBuilder b = new StringBuilder(ingS);
                    b.replace(ingS.lastIndexOf("_"), ingS.lastIndexOf("_"), "");
                    old = b.toString();
                    ingS = "";
                }

                for (String g : ricettedao.getRecipe(id).getIngredienti()) {
                    if (!g.equals("")) {
                        ingS += g + "_";
                    }
                }

                
                if (!old.equals("") && !old.isEmpty()) {
                    ingS += old;
                }
                
            } catch (DAOException e) {
                System.out.println("Errore form ingredienti");
            }

            //Load dell'immagine
            if (filePart1 != null) {
                System.out.println("IMAGE CONTENT: " + filePart1.getContentType());
                if (filePart1.getContentType().contains("image/")) {
                    String upload_directory = "";
                    upload_directory = UPLOAD_DIRECTORY + catS + "/";
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
                    }
                } else {
                    System.out.println("FilePart not in image/");
                }
            } else {
                System.out.println("filePart = null");
            }
            
            ricettedao.updateRecipe(nome, procedimento, descrizione, immagine, difficolta, ingS, creatore, tempo, id, id_prod, categoria);
        } catch (DAOException ex) {
            Logger.getLogger(updateRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/console/idea.jsp?id=" + id);
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
