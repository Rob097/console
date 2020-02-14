/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utente;

import database.daos.BlogDAO;
import database.daos.ConsoleDAO;
import database.daos.RicetteDAO;
import database.entities.Blog;
import database.entities.Ricetta;
import database.exceptions.DAOException;
import database.factories.DAOFactory;
import database.jdbc.JDBCBlogDAO;
import database.jdbc.JDBCConsoleDAO;
import database.jdbc.JDBCRicetteDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ordini.orderSent;
import static varie.costanti.MAC_MAIL;
import static varie.costanti.MAC_PASS;
import static varie.costanti.ROB_MAIL;
import static varie.costanti.ROB_PASS;

/**
 *
 * @author Roberto97
 */
public class emailSender extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ConsoleDAO consoledao = null;
    BlogDAO blogdao = null;
    RicetteDAO ideedao = null;

    @Override
    public void init() throws ServletException {
        try {
            //carica la Connessione inizializzata in JDBCDAOFactory, quindi ritorna il Class.for() e la connessione
            DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new ServletException("Impossible to get dao factory for user storage system");
            }
            consoledao = new JDBCConsoleDAO(daoFactory.getConnection());
            blogdao = new JDBCBlogDAO(daoFactory.getConnection());
            ideedao = new JDBCRicetteDAO(daoFactory.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(orderSent.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException, DAOException {
        response.setContentType("text/html;charset=UTF-8");

        String tipo = "";
        if (request.getAttribute("tipo") != null) {
            tipo = (String) request.getAttribute("tipo");
        }
        String oggetto = "", corpo = "", redirect1 = "";
        ArrayList<String> emailS = new ArrayList<>();
        emailS = consoledao.getAllEmail();
        System.out.println("TIPO: " + tipo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm");
        String titolo = "", creatore = "", immagine = "", id = "", data = "";
        
        for (String email : emailS) {
            switch (tipo) {
                case ("blog"):                    
                    oggetto = "Nuovo articolo nel blog de 'l Bortoleto";                    
                    if (request.getAttribute("titolo") != null) {
                        titolo = (String) request.getAttribute("titolo");
                    }
                    if (request.getAttribute("creatore") != null) {
                        creatore = (String) request.getAttribute("creatore");
                    }
                    if (request.getAttribute("immagine") != null) {
                        immagine = (String) request.getAttribute("immagine");
                    }
                    if (request.getAttribute("id") != null) {
                        id = (String) request.getAttribute("id");
                    }
                    redirect1 = "/console/articoli.jsp";
                    data = blogdao.getBlogById(Integer.parseInt(id)).getData().toLocalDateTime().format(formatter);

                    corpo = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                            + "\n"
                            + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n"
                            + "<head>\n"
                            + "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\n"
                            + "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n"
                            + "<meta content=\"width=device-width\" name=\"viewport\"/>\n"
                            + "<!--[if !mso]><!-->\n"
                            + "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n"
                            + "<!--<![endif]-->\n"
                            + "<title></title>\n"
                            + "<!--[if !mso]><!-->\n"
                            + "<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                            + "<!--<![endif]-->\n"
                            + "<style type=\"text/css\">\n"
                            + "		body {\n"
                            + "			margin: 0;\n"
                            + "			padding: 0;\n"
                            + "		}\n"
                            + "\n"
                            + "		table,\n"
                            + "		td,\n"
                            + "		tr {\n"
                            + "			vertical-align: top;\n"
                            + "			border-collapse: collapse;\n"
                            + "		}\n"
                            + "\n"
                            + "		* {\n"
                            + "			line-height: inherit;\n"
                            + "		}\n"
                            + "\n"
                            + "		a[x-apple-data-detectors=true] {\n"
                            + "			color: inherit !important;\n"
                            + "			text-decoration: none !important;\n"
                            + "		}\n"
                            + "	</style>\n"
                            + "<style id=\"media-query\" type=\"text/css\">\n"
                            + "		@media (max-width: 670px) {\n"
                            + "\n"
                            + "			.block-grid,\n"
                            + "			.col {\n"
                            + "				min-width: 320px !important;\n"
                            + "				max-width: 100% !important;\n"
                            + "				display: block !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.block-grid {\n"
                            + "				width: 100% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.col {\n"
                            + "				width: 100% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.col>div {\n"
                            + "				margin: 0 auto;\n"
                            + "			}\n"
                            + "\n"
                            + "			img.fullwidth,\n"
                            + "			img.fullwidthOnMobile {\n"
                            + "				max-width: 100% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col {\n"
                            + "				min-width: 0 !important;\n"
                            + "				display: table-cell !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack.two-up .col {\n"
                            + "				width: 50% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num4 {\n"
                            + "				width: 33% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num8 {\n"
                            + "				width: 66% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num4 {\n"
                            + "				width: 33% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num3 {\n"
                            + "				width: 25% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num6 {\n"
                            + "				width: 50% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num9 {\n"
                            + "				width: 75% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.video-block {\n"
                            + "				max-width: none !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.mobile_hide {\n"
                            + "				min-height: 0px;\n"
                            + "				max-height: 0px;\n"
                            + "				max-width: 0px;\n"
                            + "				display: none;\n"
                            + "				overflow: hidden;\n"
                            + "				font-size: 0px;\n"
                            + "			}\n"
                            + "\n"
                            + "			.desktop_hide {\n"
                            + "				display: block !important;\n"
                            + "				max-height: none !important;\n"
                            + "			}\n"
                            + "		}\n"
                            + "	</style>\n"
                            + "</head>\n"
                            + "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #F1F3F3;\">\n"
                            + "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n"
                            + "<table bgcolor=\"#F1F3F3\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #F1F3F3; width: 100%;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#F1F3F3\"><![endif]-->\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:transparent;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n"
                            + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"15\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 0px solid transparent; height: 15px;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td height=\"15\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top:50px; padding-bottom:5px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:50px; padding-bottom:5px; padding-right: 50px; padding-left: 50px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#CFCFCF;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"font-size: 12px; line-height: 14px; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; color: #CFCFCF;\">\n"
                            + "<p style=\"font-size: 14px; line-height: 16px; text-align: left; margin: 0;\"><strong>" + data + "</strong></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"width: fit-content; color:#66BECD;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"line-height: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 12px; color: #66BECD;\">\n"
                            + "<p style=\"line-height: 40px; font-size: 12px; margin: 10px 0 10px 0;\"><span style=\"font-size: 50px;\">" + titolo + "</span></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#66BECD;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"line-height: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 12px; color: #66BECD;\">\n"
                            + "<p style=\"line-height: 28px; text-align: left; font-size: 12px; margin: 0;\"><span style=\"font-size: 24px;\">Scritto da " + creatore + "</span></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:25px; padding-bottom:0px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:25px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth fullwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n"
                            + "<div style=\"font-size:1px;line-height:10px\"> </div><img align=\"center\" alt=\"divider\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://lh3.googleusercontent.com/8ei1gOARYhBUCSdemLZMpl4LLM9lgge2V_mdHCnXh8wytaeNLD_anSJ1bes-1fEcMsbmVJ7VRVkfXJS28xhDqPuwkzt5KTUaa6IEmAUdHQZF19JunfciUFkgLeEsjX0w4UKb7J6nIw=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 634px; display: block;\" title=\"Divider\" width=\"634\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top:35px; padding-bottom:5px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:35px; padding-bottom:5px; padding-right: 50px; padding-left: 50px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth fullwidth\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\""+titolo+"\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://macelleriadellantonio.it" + immagine.replace("..", "") + "\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 250px; border-radius: 5px; display: block;\" title=\""+titolo+"\" width=\"534\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            +"<table width=\"70%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border-collapse:collapse;border-spacing:0px;margin: 2rem auto 2rem auto;\"><tbody><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #cccccc;background:none;height:1px;width:100%;margin:0px\"></td></tr></tbody></table>"
                            +"<span class=\"m_4446209186151968903es-button-border\" style=\"border-style:solid;border-color:#e7e8e9;background: #66becd;border-width:0px;display:inline-block;border-radius:0px;width:auto;\"> <a href=\"https://macelleriadellantonio.it/Bortoleto/articolo/"+id+"/"+ titolo.replace(' ', '-') +"\" class=\"m_4446209186151968903es-button\" style=\"text-decoration:none;font-family:arial,'helvetica neue',helvetica,sans-serif;font-size:14px;color:#ffffff;border-style:solid;border-color: #66becd;border-width:10px 20px 10px 20px;display:inline-block;background: #66becd;border-radius:0px;font-weight:normal;font-style:normal;line-height:17px;width:auto;box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.50);text-align:center;\" target=\"_blank\">LEGGI L'ARTICOLO</a> </span>"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:45px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:0px; padding-bottom:45px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth fullwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n"
                            + "<div style=\"font-size:1px;line-height:10px\"> </div><img align=\"center\" alt=\"divider\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://lh3.googleusercontent.com/8ei1gOARYhBUCSdemLZMpl4LLM9lgge2V_mdHCnXh8wytaeNLD_anSJ1bes-1fEcMsbmVJ7VRVkfXJS28xhDqPuwkzt5KTUaa6IEmAUdHQZF19JunfciUFkgLeEsjX0w4UKb7J6nIw=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 634px; display: block;\" title=\"Divider\" width=\"634\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 14px; color: #555555;\">\n"
                            + "<p style=\"font-size: 14px; line-height: 24px; text-align: center; margin: 0;\"><span style=\"font-size: 20px;\"><em><strong>ARTICOLI CHE TI POTREBBERO INTERESSARE</strong></em></span></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid three-up\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"216\" style=\"background-color:#FFFFFF;width:216px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n";
                    ArrayList<Blog> mostViewed = blogdao.getMostViewedBlog();
                    int n1 = 3;
                    if(mostViewed.size() < 3){
                        n1 = mostViewed.size();
                    }
                    Blog b;
                    for (int i = 0; i < n1; i++) {
                        b = mostViewed.get(i);
                        corpo += "<div class=\"col num4\" style=\"max-width: 320px; min-width: 216px; display: table-cell; vertical-align: top; width: 216px;\">\n"
                                + "<div style=\"width:100% !important;\">\n"
                                + "<!--[if (!mso)&(!IE)]><!-->\n"
                                + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                                + "<!--<![endif]-->\n"
                                + "<div align=\"center\" class=\"img-container center autowidth\">\n"
                                + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\""+b.getNome()+"\" border=\"0\" class=\"center autowidth\" src=\"https://macelleriadellantonio.it" + b.getImmagine().replace("..", "") + "\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 189px; display: block;\" title=\""+b.getNome()+"\" width=\"189\"/>\n"
                                + "<!--[if mso]></td></tr></table><![endif]-->\n"
                                + "</div>\n"
                                + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                                + "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                                + "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 14px; color: #555555;\">\n"
                                + "<a style=\"color: unset; text-decoration: none;\" target=\"_blank\" href=\"https://macelleriadellantonio.it/Bortoleto/articolo"+b.getId()+"/" + b.getNome().replace(' ', '-') + "\"><p style=\"text-align: center; font-size: 14px; line-height: 16px; margin: 0;\"><strong>"+b.getNome()+"</strong>.</p></a>\n"
                                + "</div>\n"
                                + "</div>\n"
                                + "<!--[if mso]></td></tr></table><![endif]-->\n"
                                + "<!--[if (!mso)&(!IE)]><!-->\n"
                                + "</div>\n"
                                + "<!--<![endif]-->\n"
                                + "</div>\n"
                                + "</div>"
                                + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                                + "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"216\" style=\"background-color:#FFFFFF;width:216px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n";
                    }
                    corpo += "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid two-up\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"325\" style=\"background-color:#FFFFFF;width:325px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num6\" style=\"min-width: 320px; max-width: 325px; display: table-cell; vertical-align: top; width: 325px;\">\n"
                            + "<div style=\"width:100% !important; margin: 2rem 0 2rem 0;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\"La Macelleria\" border=\"0\" class=\"center autowidth\" src=\"https://lh3.googleusercontent.com/1nJwqw8n93uSSVkiOcuosGxA84pLvNAH5WDakvcRHohk2ccrL0SmxBlHB87WOxZXcWkD2ToK0YmNzspklIqHjZI8XQcVFfiDhpawN03k_rwm2pARMbFxIFSQiI3fvlC529-UVTMNbg=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 200px; display: block;\" title=\"La Macelleria\" width=\"100\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"325\" style=\"background-color:#FFFFFF;width:325px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num6\" style=\"min-width: 320px; max-width: 325px; display: table-cell; vertical-align: top; width: 325px;\">\n"
                            + "<div style=\"width:100% !important; margin: 2rem 0 2rem 0;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\"Il Risotante\" border=\"0\" class=\"center autowidth\" src=\"https://lh3.googleusercontent.com/kbvJO4XfUpn2DEQ_7l789Dz_nZjXYGJVsr_Ot22TEtR0LfqMVDPXWujbQqCwP8zQ6X3k75CaRVG61LZw-nC2DHpoi1hSGsqEMnZBmfloRAH4vvD2lWgZVRzrEC_g-NtFWt_mB2DRkA=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 300px; display: block;\" title=\"Il Ristorante\" width=\"100\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #E3FAFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#E3FAFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#E3FAFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#E3FAFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:30px; padding-bottom:25px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:30px; padding-bottom:25px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<table cellpadding=\"0\" cellspacing=\"0\" class=\"social_icons\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-top: 5px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n"
                            + "<table activate=\"activate\" align=\"center\" alignment=\"alignment\" cellpadding=\"0\" cellspacing=\"0\" class=\"social_table\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: undefined; mso-table-tspace: 0; mso-table-rspace: 0; mso-table-bspace: 0; mso-table-lspace: 0;\" to=\"to\" valign=\"top\">\n"
                            + "<tbody>\n"
                            + "<tr align=\"center\" style=\"vertical-align: top; display: inline-block; text-align: center;\" valign=\"top\">\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;\" valign=\"top\"><a href=\"https://www.facebook.com/macelleriadellantonio/\" target=\"_blank\"><img alt=\"Facebook\" height=\"32\" src=\"https://lh3.googleusercontent.com/NFCnbNkvBqXbF51WPiqrJFuZoFrDTVwGE0lpb5Pzjsn4KtZPJNqUf7b5XNn7j_9fSvTSHFN_ESBxXhf3eoqJ7KojReZyg9QGmHUuDS7_GcDGlqoyogpfR4ElCcGYnrZmmTYZHEQemw=s64-p-k\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;\" title=\"Facebook\" width=\"32\"/></a></td>\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;\" valign=\"top\"><a href=\"https://www.youtube.com/watch?v=9-3If8AhVwM\" target=\"_blank\"><img alt=\"YouTube\" height=\"32\" src=\"https://lh3.googleusercontent.com/7wcSr4oGFk92uc4lp_6mf2YJneNCBjOiSu1E70IPTVeLL8iIYu47usYLfdaRXnVie0fBSH0MZDqZku0eT_dIgT8LTMdS7vhwEu_ApPoYkIc48VHLYfJ1n-Mm9fLMQVUKCSvu-b1lAQ=s64-p-k\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;\" title=\"YouTube\" width=\"32\"/></a></td>\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;\" valign=\"top\"><a href=\"https://www.facebook.com/sharer.php?u=https://www.facebook.com/LBortoleto/\" target=\"_blank\"><img alt=\"Facebook\" height=\"32\" src=\"https://lh3.googleusercontent.com/NFCnbNkvBqXbF51WPiqrJFuZoFrDTVwGE0lpb5Pzjsn4KtZPJNqUf7b5XNn7j_9fSvTSHFN_ESBxXhf3eoqJ7KojReZyg9QGmHUuDS7_GcDGlqoyogpfR4ElCcGYnrZmmTYZHEQemw=s64-p-k\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;\" title=\"Facebook\" width=\"32\"/></a></td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top:30px; padding-bottom:30px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:30px; padding-bottom:30px; padding-right: 50px; padding-left: 50px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<p style=\"font-size: 12px; line-height: 14px; text-align: center; color: #555555; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; margin: 0;\">Se vuoi annullare l'iscrizione alle nostre email fai <a \"\"=\"\" +=\"\" encryptdecryptstring.encrypt(email)=\"\" href=\"https://macelleriadellantonio.it/console/annullaIscrizione?email=" + EncryptDecryptString.encrypt(email) + "\" rel=\"noopener\" style=\"text-decoration: underline; color: #66BECD;\" target=\"_blank\">click qui</a> </p>\n"
                            + "<p style=\"font-size: 12px; line-height: 14px; text-align: center; color: #555555; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; margin: 0;\">© 2019 Macelleria Dellantonio by Dellantonio Roberto.</p>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:transparent;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n"
                            + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"15\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 0px solid transparent; height: 15px;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td height=\"15\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (IE)]></div><![endif]-->\n"
                            + "</body>\n"
                            + "</html>";

                    break;
                    
                    case ("idea"):
                        
                    oggetto = "Nuova idea da 'l Bortoleto";
                    
                    if (request.getAttribute("titolo") != null) {
                        titolo = (String) request.getAttribute("titolo");
                    }
                    if (request.getAttribute("creatore") != null) {
                        creatore = (String) request.getAttribute("creatore");
                    }
                    if (request.getAttribute("immagine") != null) {
                        immagine = (String) request.getAttribute("immagine");
                    }
                    if (request.getAttribute("id") != null) {
                        id = (String) request.getAttribute("id");
                    }
                    redirect1 = "/console/idee.jsp";
                    data = ideedao.getRecipe(Integer.parseInt(id)).getData().toLocalDateTime().format(formatter);

                    corpo = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                            + "\n"
                            + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n"
                            + "<head>\n"
                            + "<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->\n"
                            + "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n"
                            + "<meta content=\"width=device-width\" name=\"viewport\"/>\n"
                            + "<!--[if !mso]><!-->\n"
                            + "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"/>\n"
                            + "<!--<![endif]-->\n"
                            + "<title></title>\n"
                            + "<!--[if !mso]><!-->\n"
                            + "<link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                            + "<!--<![endif]-->\n"
                            + "<style type=\"text/css\">\n"
                            + "		body {\n"
                            + "			margin: 0;\n"
                            + "			padding: 0;\n"
                            + "		}\n"
                            + "\n"
                            + "		table,\n"
                            + "		td,\n"
                            + "		tr {\n"
                            + "			vertical-align: top;\n"
                            + "			border-collapse: collapse;\n"
                            + "		}\n"
                            + "\n"
                            + "		* {\n"
                            + "			line-height: inherit;\n"
                            + "		}\n"
                            + "\n"
                            + "		a[x-apple-data-detectors=true] {\n"
                            + "			color: inherit !important;\n"
                            + "			text-decoration: none !important;\n"
                            + "		}\n"
                            + "	</style>\n"
                            + "<style id=\"media-query\" type=\"text/css\">\n"
                            + "		@media (max-width: 670px) {\n"
                            + "\n"
                            + "			.block-grid,\n"
                            + "			.col {\n"
                            + "				min-width: 320px !important;\n"
                            + "				max-width: 100% !important;\n"
                            + "				display: block !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.block-grid {\n"
                            + "				width: 100% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.col {\n"
                            + "				width: 100% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.col>div {\n"
                            + "				margin: 0 auto;\n"
                            + "			}\n"
                            + "\n"
                            + "			img.fullwidth,\n"
                            + "			img.fullwidthOnMobile {\n"
                            + "				max-width: 100% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col {\n"
                            + "				min-width: 0 !important;\n"
                            + "				display: table-cell !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack.two-up .col {\n"
                            + "				width: 50% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num4 {\n"
                            + "				width: 33% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num8 {\n"
                            + "				width: 66% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num4 {\n"
                            + "				width: 33% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num3 {\n"
                            + "				width: 25% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num6 {\n"
                            + "				width: 50% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.no-stack .col.num9 {\n"
                            + "				width: 75% !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.video-block {\n"
                            + "				max-width: none !important;\n"
                            + "			}\n"
                            + "\n"
                            + "			.mobile_hide {\n"
                            + "				min-height: 0px;\n"
                            + "				max-height: 0px;\n"
                            + "				max-width: 0px;\n"
                            + "				display: none;\n"
                            + "				overflow: hidden;\n"
                            + "				font-size: 0px;\n"
                            + "			}\n"
                            + "\n"
                            + "			.desktop_hide {\n"
                            + "				display: block !important;\n"
                            + "				max-height: none !important;\n"
                            + "			}\n"
                            + "		}\n"
                            + "	</style>\n"
                            + "</head>\n"
                            + "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #F1F3F3;\">\n"
                            + "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n"
                            + "<table bgcolor=\"#F1F3F3\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #F1F3F3; width: 100%;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td style=\"word-break: break-word; vertical-align: top;\" valign=\"top\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#F1F3F3\"><![endif]-->\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:transparent;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n"
                            + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"15\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 0px solid transparent; height: 15px;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td height=\"15\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top:50px; padding-bottom:5px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:50px; padding-bottom:5px; padding-right: 50px; padding-left: 50px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#CFCFCF;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"font-size: 12px; line-height: 14px; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; color: #CFCFCF;\">\n"
                            + "<p style=\"font-size: 14px; line-height: 16px; text-align: left; margin: 0;\"><strong>" + data + "</strong></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"width: fit-content; color:#66BECD;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"line-height: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 12px; color: #66BECD;\">\n"
                            + "<p style=\"line-height: 40px; font-size: 12px; margin: 10px 0 10px 0;\"><span style=\"font-size: 50px;\">" + titolo + "</span></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#66BECD;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"line-height: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 12px; color: #66BECD;\">\n"
                            + "<p style=\"line-height: 28px; text-align: left; font-size: 12px; margin: 0;\"><span style=\"font-size: 24px;\">Scritto da " + creatore + "</span></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:25px; padding-bottom:0px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:25px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth fullwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n"
                            + "<div style=\"font-size:1px;line-height:10px\"> </div><img align=\"center\" alt=\"divider\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://lh3.googleusercontent.com/8ei1gOARYhBUCSdemLZMpl4LLM9lgge2V_mdHCnXh8wytaeNLD_anSJ1bes-1fEcMsbmVJ7VRVkfXJS28xhDqPuwkzt5KTUaa6IEmAUdHQZF19JunfciUFkgLeEsjX0w4UKb7J6nIw=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 634px; display: block;\" title=\"Divider\" width=\"634\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top:35px; padding-bottom:5px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:35px; padding-bottom:5px; padding-right: 50px; padding-left: 50px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth fullwidth\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\""+titolo+"\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://macelleriadellantonio.it" + immagine.replace("..", "") + "\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 250px; border-radius: 5px; display: block;\" title=\""+titolo+"\" width=\"534\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            +"<table width=\"70%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border-collapse:collapse;border-spacing:0px;margin: 2rem auto 2rem auto;\"><tbody><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #cccccc;background:none;height:1px;width:100%;margin:0px\"></td></tr></tbody></table>"
                            +"<span class=\"m_4446209186151968903es-button-border\" style=\"border-style:solid;border-color:#e7e8e9;background: #66becd;border-width:0px;display:inline-block;border-radius:0px;width:auto;\"> <a href=\"https://macelleriadellantonio.it/Bortoleto/idea/"+id+"/" + titolo.replace(' ', '-') + "\" class=\"m_4446209186151968903es-button\" style=\"text-decoration:none;font-family:arial,'helvetica neue',helvetica,sans-serif;font-size:14px;color:#ffffff;border-style:solid;border-color: #66becd;border-width:10px 20px 10px 20px;display:inline-block;background: #66becd;border-radius:0px;font-weight:normal;font-style:normal;line-height:17px;width:auto;box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.50);text-align:center;\" target=\"_blank\">LEGGI L'ARTICOLO</a> </span>"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:0px; padding-bottom:45px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:0px; padding-bottom:45px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth fullwidth\" style=\"padding-right: 0px;padding-left: 0px;\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\"><![endif]-->\n"
                            + "<div style=\"font-size:1px;line-height:10px\"> </div><img align=\"center\" alt=\"divider\" border=\"0\" class=\"center autowidth fullwidth\" src=\"https://lh3.googleusercontent.com/8ei1gOARYhBUCSdemLZMpl4LLM9lgge2V_mdHCnXh8wytaeNLD_anSJ1bes-1fEcMsbmVJ7VRVkfXJS28xhDqPuwkzt5KTUaa6IEmAUdHQZF19JunfciUFkgLeEsjX0w4UKb7J6nIw=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 634px; display: block;\" title=\"Divider\" width=\"634\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 14px; color: #555555;\">\n"
                            + "<p style=\"font-size: 14px; line-height: 24px; text-align: center; margin: 0;\"><span style=\"font-size: 20px;\"><em><strong>ALTRO CHE TI POTREBBE INTERESSARE</strong></em></span></p>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid three-up\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"216\" style=\"background-color:#FFFFFF;width:216px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n";
                    ArrayList<Ricetta> mostViewedRecipes = ideedao.getMostViewedRecipes();
                    int n = 3;
                    if(mostViewedRecipes.size()<3){
                        n = mostViewedRecipes.size();
                    };
                    Ricetta r;
                    for (int i = 0; i < n; i++) {
                        r = mostViewedRecipes.get(i);
                        corpo += "<div class=\"col num4\" style=\"max-width: 320px; min-width: 216px; display: table-cell; vertical-align: top; width: 216px;\">\n"
                                + "<div style=\"width:100% !important;\">\n"
                                + "<!--[if (!mso)&(!IE)]><!-->\n"
                                + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                                + "<!--<![endif]-->\n"
                                + "<div align=\"center\" class=\"img-container center autowidth\">\n"
                                + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\""+r.getNome()+"\" border=\"0\" class=\"center autowidth\" src=\"https://macelleriadellantonio.it" + r.getImmagine().replace("..", "") + "\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 189px; display: block;\" title=\""+r.getNome()+"\" width=\"189\"/>\n"
                                + "<!--[if mso]></td></tr></table><![endif]-->\n"
                                + "</div>\n"
                                + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                                + "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                                + "<div style=\"font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 12px; line-height: 14px; color: #555555;\">\n"
                                + "<a style=\"color: unset; text-decoration: none;\" target=\"_blank\" href=\"https://macelleriadellantonio.it/Bortoleto/idea/"+r.getId()+"/" + r.getNome().replace(' ', '-') + "\"><p style=\"text-align: center; font-size: 14px; line-height: 16px; margin: 0;\"><strong>"+r.getNome()+"</strong>.</p></a>\n"
                                + "</div>\n"
                                + "</div>\n"
                                + "<!--[if mso]></td></tr></table><![endif]-->\n"
                                + "<!--[if (!mso)&(!IE)]><!-->\n"
                                + "</div>\n"
                                + "<!--<![endif]-->\n"
                                + "</div>\n"
                                + "</div>"
                                + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                                + "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"216\" style=\"background-color:#FFFFFF;width:216px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n";
                    }
                    corpo += "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid two-up\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"325\" style=\"background-color:#FFFFFF;width:325px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num6\" style=\"min-width: 320px; max-width: 325px; display: table-cell; vertical-align: top; width: 325px;\">\n"
                            + "<div style=\"width:100% !important; margin: 2rem 0 2rem 0;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\"La Macelleria\" border=\"0\" class=\"center autowidth\" src=\"https://lh3.googleusercontent.com/1nJwqw8n93uSSVkiOcuosGxA84pLvNAH5WDakvcRHohk2ccrL0SmxBlHB87WOxZXcWkD2ToK0YmNzspklIqHjZI8XQcVFfiDhpawN03k_rwm2pARMbFxIFSQiI3fvlC529-UVTMNbg=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 200px; display: block;\" title=\"La Macelleria\" width=\"100\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td><td align=\"center\" width=\"325\" style=\"background-color:#FFFFFF;width:325px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num6\" style=\"min-width: 320px; max-width: 325px; display: table-cell; vertical-align: top; width: 325px;\">\n"
                            + "<div style=\"width:100% !important; margin: 2rem 0 2rem 0;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<div align=\"center\" class=\"img-container center autowidth\">\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px\"><td style=\"\" align=\"center\"><![endif]--><img align=\"center\" alt=\"Il Risotante\" border=\"0\" class=\"center autowidth\" src=\"https://lh3.googleusercontent.com/kbvJO4XfUpn2DEQ_7l789Dz_nZjXYGJVsr_Ot22TEtR0LfqMVDPXWujbQqCwP8zQ6X3k75CaRVG61LZw-nC2DHpoi1hSGsqEMnZBmfloRAH4vvD2lWgZVRzrEC_g-NtFWt_mB2DRkA=w2400\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; border: 0; height: auto; width: 100%; max-width: 300px; display: block;\" title=\"Il Ristorante\" width=\"100\"/>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #E3FAFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#E3FAFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#E3FAFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#E3FAFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:30px; padding-bottom:25px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:30px; padding-bottom:25px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<table cellpadding=\"0\" cellspacing=\"0\" class=\"social_icons\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-top: 5px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n"
                            + "<table activate=\"activate\" align=\"center\" alignment=\"alignment\" cellpadding=\"0\" cellspacing=\"0\" class=\"social_table\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: undefined; mso-table-tspace: 0; mso-table-rspace: 0; mso-table-bspace: 0; mso-table-lspace: 0;\" to=\"to\" valign=\"top\">\n"
                            + "<tbody>\n"
                            + "<tr align=\"center\" style=\"vertical-align: top; display: inline-block; text-align: center;\" valign=\"top\">\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;\" valign=\"top\"><a href=\"https://www.facebook.com/macelleriadellantonio/\" target=\"_blank\"><img alt=\"Facebook\" height=\"32\" src=\"https://lh3.googleusercontent.com/NFCnbNkvBqXbF51WPiqrJFuZoFrDTVwGE0lpb5Pzjsn4KtZPJNqUf7b5XNn7j_9fSvTSHFN_ESBxXhf3eoqJ7KojReZyg9QGmHUuDS7_GcDGlqoyogpfR4ElCcGYnrZmmTYZHEQemw=s64-p-k\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;\" title=\"Facebook\" width=\"32\"/></a></td>\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;\" valign=\"top\"><a href=\"https://www.youtube.com/watch?v=9-3If8AhVwM\" target=\"_blank\"><img alt=\"YouTube\" height=\"32\" src=\"https://lh3.googleusercontent.com/7wcSr4oGFk92uc4lp_6mf2YJneNCBjOiSu1E70IPTVeLL8iIYu47usYLfdaRXnVie0fBSH0MZDqZku0eT_dIgT8LTMdS7vhwEu_ApPoYkIc48VHLYfJ1n-Mm9fLMQVUKCSvu-b1lAQ=s64-p-k\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;\" title=\"YouTube\" width=\"32\"/></a></td>\n"
                            + "<td style=\"word-break: break-word; vertical-align: top; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;\" valign=\"top\"><a href=\"https://www.facebook.com/sharer.php?u=https://www.facebook.com/LBortoleto/\" target=\"_blank\"><img alt=\"Facebook\" height=\"32\" src=\"https://lh3.googleusercontent.com/NFCnbNkvBqXbF51WPiqrJFuZoFrDTVwGE0lpb5Pzjsn4KtZPJNqUf7b5XNn7j_9fSvTSHFN_ESBxXhf3eoqJ7KojReZyg9QGmHUuDS7_GcDGlqoyogpfR4ElCcGYnrZmmTYZHEQemw=s64-p-k\" style=\"text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;\" title=\"Facebook\" width=\"32\"/></a></td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FFFFFF;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FFFFFF;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:#FFFFFF\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:#FFFFFF;width:650px; border-top: 0px solid transparent; border-left: 8px solid #F1F3F3; border-bottom: 0px solid transparent; border-right: 8px solid #F1F3F3;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 50px; padding-left: 50px; padding-top:30px; padding-bottom:30px;background-color:#FFFFFF;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 634px;\">\n"
                            + "<div style=\"background-color:#FFFFFF;width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:8px solid #F1F3F3; border-bottom:0px solid transparent; border-right:8px solid #F1F3F3; padding-top:30px; padding-bottom:30px; padding-right: 50px; padding-left: 50px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif\"><![endif]-->\n"
                            + "<div style=\"color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n"
                            + "<p style=\"font-size: 12px; line-height: 14px; text-align: center; color: #555555; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; margin: 0;\">Se vuoi annullare l'iscrizione alle nostre email fai <a \"\"=\"\" +=\"\" encryptdecryptstring.encrypt(email)=\"\" href=\"https://macelleriadellantonio.it/console/annullaIscrizione?email=" + EncryptDecryptString.encrypt(email) + "\" rel=\"noopener\" style=\"text-decoration: underline; color: #66BECD;\" target=\"_blank\">click qui</a> </p>\n"
                            + "<p style=\"font-size: 12px; line-height: 14px; text-align: center; color: #555555; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; margin: 0;\">© 2019 Macelleria Dellantonio by Dellantonio Roberto.</p>\n"
                            + "</div>\n"
                            + "<!--[if mso]></td></tr></table><![endif]-->\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<div style=\"background-color:transparent;\">\n"
                            + "<div class=\"block-grid\" style=\"Margin: 0 auto; min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;\">\n"
                            + "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n"
                            + "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:650px\"><tr class=\"layout-full-width\" style=\"background-color:transparent\"><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]><td align=\"center\" width=\"650\" style=\"background-color:transparent;width:650px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n"
                            + "<div class=\"col num12\" style=\"min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;\">\n"
                            + "<div style=\"width:100% !important;\">\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n"
                            + "<!--<![endif]-->\n"
                            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;\" valign=\"top\">\n"
                            + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"15\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 0px solid transparent; height: 15px;\" valign=\"top\" width=\"100%\">\n"
                            + "<tbody>\n"
                            + "<tr style=\"vertical-align: top;\" valign=\"top\">\n"
                            + "<td height=\"15\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\"><span></span></td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (!mso)&(!IE)]><!-->\n"
                            + "</div>\n"
                            + "<!--<![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "</div>\n"
                            + "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n"
                            + "</td>\n"
                            + "</tr>\n"
                            + "</tbody>\n"
                            + "</table>\n"
                            + "<!--[if (IE)]></div><![endif]-->\n"
                            + "</body>\n"
                            + "</html>";

                    break;

                default:
                    break;
            }

            try {
                //Dati email Macelleria
                String to1 = email;
                final String user1 = MAC_MAIL;
                final String pass1 = MAC_PASS;
                Properties props1 = new Properties();
                props1.put("mail.smtp.host", "smtp.gmail.com");
                //below mentioned mail.smtp.port is optional
                props1.put("mail.smtp.port", "587");
                props1.put("mail.smtp.auth", "true");
                props1.put("mail.smtp.starttls.enable", "true");
                Session session2;
                session2 = Session.getInstance(props1, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user1, pass1);
                    }
                });
                MimeMessage message = new MimeMessage(session2);
                message.setFrom(new InternetAddress(user1));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to1));
                message.setSubject(oggetto);

                Multipart mp = new MimeMultipart();
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(corpo, "text/html");
                mp.addBodyPart(htmlPart);
                message.setContent(mp);

                // Transport class is used to deliver the message to the recipients 
                Transport.send(message);
            } catch (MessagingException ex) {
                String testo1 = "Erroe nell'invio dell'email\n\n";
                testo1 += "\nEmail: " + email;
                //Dati email roberto
                try {
                    String to1 = MAC_MAIL;
                    final String user1 = ROB_MAIL;
                    final String pass1 = ROB_PASS;
                    Properties props1 = new Properties();
                    props1.put("mail.smtp.host", "smtp.gmail.com");
                    //below mentioned mail.smtp.port is optional
                    props1.put("mail.smtp.port", "587");
                    props1.put("mail.smtp.auth", "true");
                    props1.put("mail.smtp.starttls.enable", "true");
                    Session session2;
                    session2 = Session.getInstance(props1, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user1, pass1);
                        }
                    });
                    MimeMessage message = new MimeMessage(session2);
                    message.setFrom(new InternetAddress(user1));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to1));
                    message.setSubject("Errore invio email");
                    message.setText(testo1);

                    // Transport class is used to deliver the message to the recipients 
                    Transport.send(message);
                } catch (MessagingException ex1) {
                    System.out.println("Errore invio email");
                }
            }
        }

        response.sendRedirect(redirect1);
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
        try {
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(emailSender.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(emailSender.class.getName()).log(Level.SEVERE, null, ex);
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
