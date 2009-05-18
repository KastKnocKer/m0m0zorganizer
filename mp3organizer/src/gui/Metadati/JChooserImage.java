package gui.Metadati;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Classe che definisce il JChooserImage e le relative impostazioni
 * 
 * @author Monduzzi Mattia
 *
 */

public class  JChooserImage {

    /**
     * Costruttore
     * @return
     */
    public String ChooseImage() {
    	jChooser = new JFileChooser();
    	jChooser.addChoosableFileFilter(new ImageFilter());
    	jChooser.setAcceptAllFileFilterUsed(false);
		jChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jChooser.setFileView( new ImageFileView());
		jChooser.setAccessory(new ImagePreview(jChooser));

        //Show it.
    	returnVal = jChooser.showOpenDialog(null);		

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File file = jChooser.getSelectedFile();
            return  file.getAbsolutePath();
        }
        else
        	return "";
    } 

    /**
     * Classe per filtrare i file visualizzabili dal JChooserImage 
     *
     */
	public class ImageFilter extends FileFilter {

        //Accetta tutte le directory e i files di tipo gif, jpg e png.
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }

            String extension = getExtension(f);
            if (extension != null) {
                if (extension.equals("bmp") ||
                    extension.equals("jpeg") ||
                    extension.equals("jpg") ||
                    extension.equals("png")) {
                        return true;
                } else {
                    return false;
                }
            }

            return false;
        }

        //The description of this filter
        public String getDescription() {
            return "Immagini Jpg, Bmp, Png";
        }
    }
    
	/**
	 * Classe che eredita i vari costruttori della classe FileView
	 * 
	 */
    public class ImageFileView extends FileView {} 
    
    /**
     * Metodo per ricavare l'estensione del file passato come parametro
     *  
     * @param f
     * 			il file di cui bisogna ricavare l'estensione
     * @return l'estensione del file
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
	private int returnVal;   
    private JFileChooser jChooser;
    
}
