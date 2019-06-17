/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varie;

import java.text.Normalizer;
import javax.servlet.ServletContext;

/**
 *
 * @author Roberto97
 */
public class utili {

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "'");
    }
    
    /**
     * Questo metodo restituisce il percorso il percorso alla directory
     *
     * @param relativePath se questa stringa è vuota allora il metodo
     * restituisce il percorso a "...Web/" altrimenti restituisce il percroso
     * alla cartella "...Web/[relativePath]" Esempio: relativePath =
     * "Image/AvatarImg"
     * @param servlet
     * @return web/Image/AvatarImg
     */
    public static String obtainRootFolderPath(String relativePath, ServletContext servlet) {
        String folder;
        folder = relativePath;
        folder = servlet.getRealPath(folder);
        if(folder != null)
            folder = folder.replace("\\build", "");
        return folder;
    }

}
