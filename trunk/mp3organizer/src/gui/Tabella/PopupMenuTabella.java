package gui.Tabella;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import gui.*;
import gui.Metadati.JDialogMetadati;

/**
 * Classe che tramite anche l'ereditarietà dei metodi delle classi JPopupMenu e ActionListener
 * definisce il menu "a tendina" della Tabella contente i FileMp3 della Libreria
 * 
 * @author Monduzzi Mattia
 *
 */
@SuppressWarnings("serial")
public class PopupMenuTabella extends JPopupMenu implements ActionListener {

	/**
	 * Costruttore del PopupMenu
	 */
	public PopupMenuTabella () {
		LinkPopunpMenu = this;
	    add(jMenuItem1 = new JMenuItem("Riproduci"));
	    jMenuItem1.addActionListener(this);
	    add(jMenuItem2 = new JMenuItem("Rimuovi Mp3"));
	    jMenuItem2.addActionListener(this);
	    add(jMenuItem3 = new JMenuItem("Rimuovi Filtri"));
	    jMenuItem3.addActionListener(this);
	    addSeparator();
	    add(jMenuItem4 = new JMenuItem("Scheda Artista"));
	    jMenuItem4.addActionListener(this);
	    add(jMenuItem5 = new JMenuItem("Proprietà Mp3"));
	    jMenuItem5.addActionListener(this);
	    setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	  public void actionPerformed(ActionEvent e) {
		  
		  if (e.getActionCommand().equals("Riproduci"))
			  JPanelSud.setMp3toPlay("Play", false);
		  
		  else if (e.getActionCommand().equals("Rimuovi Mp3"))
			  Rimuovi();
		  
		   else if (e.getActionCommand().equals("Rimuovi Filtri")) 
			  JPanelLibreria.ResetComboBox();
		  
		  else if (e.getActionCommand().equals("Scheda Artista")) {
			  int[] x = TabellaMp3.getTabella().getSelectedRows();
			  JPanelLibreria.ViewArtisti();
			  JPanelArtisti.loadArtisti(TabellaMp3.getLibreriaView().getFile(x[0]).getArtista());
		  }
		  else if (e.getActionCommand().equals("Proprietà Mp3"))
			  Proprietà();
	  	}
	  
	  /**
	   * Metodo per rimuovere dalla Libreria e quindi anche dalla tabella di visualizzazione
	   * dei FileMp3, le righe selezionate
	   */
	  public static void Rimuovi () {
		  TabellaMp3 Tabella = TabellaMp3.getTabella();
		  if (Tabella.getRowCount() == 0)
			  return;
		  LinkPopunpMenu.setVisible(false);
		  int[] rows = Tabella.getSelectedRows(); 
		  for (int i = 0; i < rows.length; i++)
			  TabellaMp3.getLibreriaBack().remove(TabellaMp3.getLibreriaView().getFile(rows[i]));
		  TabellaMp3.getLibreriaView().removePos(rows);
		  JPanelLibreria.UpdateComboBox(TabellaMp3.getLibreriaBack(), true);
		  if (TabellaMp3.getLibreriaView().length() == 0)
			  TabellaMp3.loadLibreriaView();	
		  Tabella.getSelectionModel().setSelectionInterval(0,0);
		  TabellaMp3.saveLibreriaBack("./Salvataggi/LibreriaGlobale.dat");
	  }
	  
	  /**
	   * Metodo per visualizzare le proprietà, ovvero i metadati del FileMp3 selezionato
	   * (in caso di selezione multipla si visualizzano i metadati del primo FileMp3
	   * selezionato)
	   */
	  public static void Proprietà () {
		  if (TabellaMp3.getTabella().getRowCount() == 0)
	    	return;
		  int[] rows = TabellaMp3.getTabella().getSelectedRows();
		  new JDialogMetadati(rows[0]);
		  LinkPopunpMenu.setVisible(false);		  
	  }
	  
		private static PopupMenuTabella LinkPopunpMenu;
		private JMenuItem jMenuItem1;
		private JMenuItem jMenuItem2;
		private JMenuItem jMenuItem3;
		private JMenuItem jMenuItem4;
		private JMenuItem jMenuItem5;
		
}


