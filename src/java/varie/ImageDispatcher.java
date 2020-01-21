/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varie;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Dmytr
 */
public class ImageDispatcher {

    /**
     * qesto metodo elimina un file dalla drectory
     *
     * @param fileName questo è il percorso completo dell'immagine dal
     * cancellare
     */
    public static void deleteImgFromDirectory(String fileName) {
        // Creo un oggetto file
        File f = new File(fileName);
        // Mi assicuro che il file esista
        if (!f.exists()) {
            throw new IllegalArgumentException("Il File o la Directory non esiste: " + fileName);
        }

        // Mi assicuro che il file sia scrivibile
        if (!f.canWrite()) {
            throw new IllegalArgumentException("Non ho il permesso di scrittura: " + fileName);
        }

        // Se è una cartella verifico che sia vuota
        if (f.isDirectory()) {
            String[] files = f.list();
            if (files.length > 0) {
                throw new IllegalArgumentException("La Directory non è vuota: " + fileName);
            }
        }

        // Profo a cancellare
        boolean success = f.delete();

        // Se si è verificato un errore...
        if (!success) {
            throw new IllegalArgumentException("Cancellazione fallita");
        }
    }

    public static void insertImgIntoDirectory(String Directory, String imgName, Part filePart1) {

        //nome dell'immagine con estensione 
        String CompleteImgName;
        if ((filePart1 != null) && (filePart1.getSize() > 0)) {

            CompleteImgName = imgName;
            CompleteImgName = CompleteImgName.replaceAll("\\s+", "");

            File file1 = new File(Directory, CompleteImgName);
            try (InputStream fileContent = filePart1.getInputStream()) {
                Files.copy(fileContent, file1.toPath());
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception imgexception) {
                System.out.println("exception image #1");
                imgexception.printStackTrace();
                imgexception.getMessage();

            }
        }

    }

    public static void insertCompressedImg(String Directory, String imgName, Part filePart1, String extension) throws FileNotFoundException, IOException, IOException, IOException, IOException, IOException {
        //Creo l'immagine della cartella in modo da avere un file da comprimere
        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("JPG")) {
            insertImgIntoDirectory(Directory, imgName, filePart1);
            String CompleteImgName, CompleteImgName1;

            if ((filePart1 != null) && (filePart1.getSize() > 0)) {

                CompleteImgName = imgName;
                CompleteImgName = CompleteImgName.replaceAll("\\s+", "");
                CompleteImgName1 = imgName.replace("uncompressed", "");
                CompleteImgName1 = CompleteImgName1.replaceAll("\\s+", "");

                //File originale
                File file1 = new File(Directory, CompleteImgName);
                //File compresso
                File file2 = new File(Directory, CompleteImgName1);

                InputStream inputStream = new FileInputStream(file1);
                OutputStream outputStream = new FileOutputStream(file2);

                //Qualità del file compresso
                float imageQuality = 0.3f;
                //Create the buffered image
                BufferedImage bufferedImage = ImageIO.read(inputStream);

                //Get image writers
                Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(extension);
                if (!imageWriters.hasNext()) {
                    throw new IllegalStateException("Writers Not Found!!");
                }

                ImageWriter imageWriter = imageWriters.next();
                ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
                imageWriter.setOutput(imageOutputStream);

                ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

                //Set the compress quality metrics
                imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                imageWriteParam.setCompressionQuality(imageQuality);

                try {
                    //Created image
                    imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, extension, os);
                    InputStream is = new ByteArrayInputStream(os.toByteArray());
                    // close all streams
                    inputStream.close();
                    outputStream.close();
                    imageOutputStream.close();
                    imageWriter.dispose();

                    try (InputStream fileContent = is) {
                        Files.copy(fileContent, file2.toPath());
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception imgexception) {
                        System.out.println("L'immagine esiste già ma verrà sostituita");
                    }

                } catch (IOException | RuntimeException e) {
                    inputStream.close();
                    outputStream.close();
                    imageOutputStream.close();
                    imageWriter.dispose();
                }
                try {
                    deleteImgFromDirectory(Directory + imgName);
                } catch (Exception e) {
                    System.out.println("Nessuna immagine da cancellare2");
                }
            }
        }else{
            System.out.println("formato strano");
        }
    }

    /**
     * quando si prende un immagine dal form e si salva nella cartella delle
     * immagini bosgna conoscere l'esensione dell'immagine per poterla caricare
     * correttamente questo metodo prende PART che sarebbe l'iimagine e ne
     * estrae l'estensione
     *
     * @param filePart1 PART dell'immagine
     * @return
     */
    public static String getImageExtension(Part filePart1) {
        //System.out.println("Extension: "+Paths.get(filePart1.getSubmittedFileName()).getFileName().toString().split(Pattern.quote("."))[1]);
        String extension = FilenameUtils.getExtension(Paths.get(filePart1.getSubmittedFileName()).getFileName().toString());
        System.out.println("Extension: " + extension);
        return extension;
    }

    /**
     * questa funzione configura il formato con il quali le immagini o meglio il
     * percorso delle immagini verranno salvate del DB
     *
     * @param relativePath percorso relativo dell'immagine
     * @param imgName il nome dell immagine con estensione
     * @return [reative path] + imgName.jpg
     */
    public static String savePathImgInDatabsae(String relativePath, String imgName) {
        String immagine = "../console";
        //String immagine = "http://www.macelleriadellantonio.it/console";
        immagine += relativePath + imgName;
        immagine = immagine.replaceAll("\\s+", "");
        return immagine;
    }

    public static String setImgName(String name, String extension) {

        String s;
        s = name;
        s = s.trim();
        s = s.replace("@", "");
        s = s.replace(".", "");

        return s + "." + extension;
    }

}
