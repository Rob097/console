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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import static varie.costanti.PASS;
import static varie.utili.obtainRootFolderPath;
import static varie.utili.unaccent;

/**
 *
 * @author Roberto97
 */
@MultipartConfig()
public class updateRecipe extends HttpServlet {

    private static final long serialVersionUID = 1L;

    RicetteDAO ricettedao = null;
    private final String UPLOAD_DIRECTORY = "/img/idee/";

    @Override
    public void init() throws ServletException {
        try {
            //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new ServletException("Impossible to get dao factory for user storage system");
            }
            ricettedao = new JDBCRicetteDAO(daoFactory.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(updateRecipe.class.getName()).log(Level.SEVERE, null, ex);
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

        try {
            request.setCharacterEncoding("UTF-8");
            RequestDispatcher view = request.getRequestDispatcher("idee.jsp");
            String nome = "", procedimento = "", descrizione = "", meta_descrizione = "", immagine = "", difficolta = "", creatore = "", url = "";
            int id_prod = 0, tempo = 0, id = 0;
            boolean categoria = true, approvata = true;
            String catS = "";
            String ingS = "";
            Part filePart1 = null;
            boolean checkIMG = true;

            if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            approvata = ricettedao.getRecipe(id).isApprovata();

            if (request.getParameter("DELETE") != null && request.getParameter("DELETE").equals("true")) {
                if (request.getParameter("password") != null && request.getParameter("password").equals(PASS)) {
                    try {
                        String image = "";
                        if (request.getParameter("immagine") != null) {
                            //Il replace viene fatto perchè altrimenti non trova l'immagine.
                            //E' da controllare che funzioni anche in cloud
                            image = unaccent(request.getParameter("immagine").replace("../console", ""));
                        }
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
                        ricettedao.deleteRecipe(id);

                    } catch (DAOException ex) {
                        Logger.getLogger(updateRecipe.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    url = "idee.jsp";
                } else {
                    System.out.println("Password sbagliata");
                }
            } else {

                try {

                    boolean checkOldPubblicato = false;
                    if (approvata) {
                        checkOldPubblicato = approvata;
                    }

                    if (request.getPart("immagine") != null) {
                        if (request.getPart("immagine").getSize() <= MAX_IMG_SIZE) {
                            filePart1 = request.getPart("immagine");
                        } else {
                            checkIMG = false;
                        }
                    }
                    if (checkIMG) {

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

                        if (request.getParameter("timeInput") != null) {
                            tempo = Integer.parseInt(request.getParameter("timeInput"));
                        }
                        if (request.getParameter("difficultInput") != null) {
                            difficolta = request.getParameter("difficultInput");
                        }
                        if (request.getParameter("meta_descrizione") != null) {
                            meta_descrizione = request.getParameter("meta_descrizione");
                        }
                        if (request.getParameter("procedimento") != null) {
                            procedimento = request.getParameter("procedimento");
                            descrizione = procedimento.replaceAll("[<](/)?[^>]*[>]", "");
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
                        if (request.getParameter("approvata") != null) {
                            if (request.getParameter("approvata").equals("1")) {
                                approvata = true;
                            }
                            if (request.getParameter("approvata").equals("0")) {
                                approvata = false;
                            }
                        }

                        ArrayList<String> ingredientiNomi = new ArrayList<>();
                        ArrayList<String> quantitaNomi = new ArrayList<>();

                        ArrayList<String> ingredienti = new ArrayList<>();
                        ArrayList<String> quantita = new ArrayList<>();

                        Enumeration<String> keys = request.getParameterNames();
                        try {
                            while (keys.hasMoreElements()) {
                                String key = keys.nextElement();
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
                                    String imagineName = "uncompressed" + id + "." + extension;

                                    ImageDispatcher.insertCompressedImg(listsFolder, imagineName, filePart1, extension);
                                    immagine = ImageDispatcher.savePathImgInDatabsae(upload_directory, imagineName.replace("uncompressed", ""));
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

                        ricettedao.updateRecipe(nome, procedimento, descrizione, meta_descrizione, immagine, difficolta, ingS, creatore, tempo, id, id_prod, categoria, approvata);

                        if (checkOldPubblicato == false && approvata == true) {
                            request.setAttribute("tipo", "idea");
                            request.setAttribute("id", "" + id);
                            request.setAttribute("titolo", nome);
                            request.setAttribute("creatore", creatore);
                            request.setAttribute("immagine", immagine);
                            view = request.getRequestDispatcher("emailSender");
                        } else {
                            url = "idea.jsp?id=" + id;
                        }

                    } else {
                        response.setHeader("NOTIFICA", "L'immagine supera i 2.4MB di peso");
                        view = request.getRequestDispatcher("idea.jsp?id=" + id);
                    }
                } catch (DAOException ex) {
                    Logger.getLogger(updateRecipe.class.getName()).log(Level.SEVERE, null, ex);
                    view = request.getRequestDispatcher("idea.jsp?id=" + id);
                }
            }
            if (url.equals("")) {
                view.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
        } catch (DAOException ex) {
            Logger.getLogger(updateRecipe.class.getName()).log(Level.SEVERE, null, ex);
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
