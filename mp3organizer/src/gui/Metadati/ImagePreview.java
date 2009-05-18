package gui.Metadati;

import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;
/**
 * Classe per la visualizzazione dell'anteprima dei file nel JChooserImage
 * 
 * @author Monduzzi Mattia
 */
@SuppressWarnings("serial")
public class ImagePreview extends JComponent implements PropertyChangeListener {
    
    /**
     * Costruttore della classe
     * @param fc
     */
    public ImagePreview(JFileChooser fc) {
        setPreferredSize(new Dimension(160, 160));
        fc.addPropertyChangeListener(this);
    }

    /**
     * Metodo per caricare l'anteprima dell'immagine
     */
    public void loadImage() {
        if (file == null) {
            retIcon = null;
            return;
        }

        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 150) {
            	tmpIcon = new ImageIcon(tmpIcon.getImage().
                		getScaledInstance(150, 150,Image.SCALE_SMOOTH));
            }
            retIcon = tmpIcon;
            }
        }
    /**
     * Metodo per ascoltare gli eventi del tipo propertyChange e gestirne le varie 
     * operazioni
     */
    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();

        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            retIcon = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    /**
     * Metodo per disegnare l'anteprima dell'immagine selezionata
     */
    protected void paintComponent(Graphics g) {
        if (retIcon == null) {
            loadImage();
        }
        if (retIcon != null) {
            int x = getWidth()/2 - retIcon.getIconWidth()/2;
            int y = getHeight()/2 - retIcon.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            retIcon.paintIcon(this, g, x, y);
        }
    }
    
    ImageIcon retIcon = null;
    File file = null;
}
