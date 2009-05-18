package gui.Tabella;

import gui.JPanelLibreria;
import gui.JPanelSud;

import java.awt.Point;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import database.*;

/**
 * Classe che definisce la Tabella di visualizzazione dei FileMp3 della libreria globale e le azioni su tale libreria,
 * tramite l'eriditarietà dei metodi delle classi JTable, MouseListener e KeyListener e il modello
 * creato tramite la classe myTableModel.
 * Questa tabella si basa su 2 librerie: la LibrerieBack che è la libreria in cui si vanno a caricare/rimuovere
 * i FileMp3 in fase di Input o rimozione di un file, ed una LirberiaView(che viene creata mediate 
 * l'inserimento al suo interno di tutti i FileMp3 presenti nella LibreriaBack) sulla quale si vanno 
 * ad effettuare tutte le operazioni eseguibili tramite l'interfaccia grafica (quali filtraggio, riproduzione,
 * ricerca). 
 * 
 * @author Monduzzi Mattia
 *
 */

@SuppressWarnings("serial")
public class TabellaMp3 extends JTable implements MouseListener, KeyListener {
	
	/**
	 * Costruttore della Tabella di visualizzazione della Libreria Globale
	 */
	public TabellaMp3 () {
		super(new myTableModel()); 
		LinkTabella = this;
		LibreriaBack = new ListaMp3();
		LibreriaView = new ListaMp3();
		PopupMenu = new PopupMenuTabella();
		loadLibreriaBack("./Salvataggi/LibreriaGlobale.dat");
	
		setAutoCreateRowSorter(false);
		getTableHeader().addMouseListener(this);
		getTableHeader().setReorderingAllowed(false); 
		getColumnModel().getColumn(0).setPreferredWidth(35);
		getColumnModel().getColumn(0).setMaxWidth(100);
		
		TableHeader = this.getTableHeader();
		TableHeader.setName("JTableHeader");
		TableHeader.addMouseListener(this);
		
		this.addMouseListener(this);		
		this.addKeyListener(this);
	}

	/**
	 * Metodo che restituisce un link dinamico alla TabellaMp3 stessa
	 * @return link alla TabellaMp3
	 */
	public static TabellaMp3 getTabella() {
		return LinkTabella;
		}
	
	/**
	 * Metodo che restituisce un link dinamico alla LibreriaBack
	 * @return link alla LibreriaBack
	 */
	public static ListaMp3 getLibreriaBack() {
		return LibreriaBack;
		}
	
	/**
	 * Metodo che restituisce un link dinamico alla LibreriaView
	 * @return link alla LibreriaView
	 */
	public static ListaMp3 getLibreriaView() {
		return LibreriaView;
		}
	
	/**
	 * Metodo per caricare la LibreriaBack all'interno della LibreriaView
	 */
	public static void loadLibreriaView () {
		LibreriaView.Svuota();
		for (int i = 0; i < LibreriaBack.length(); i++)
			LibreriaView.insert(LibreriaBack.getFile(i));
		}
	
	/**
	 * Metodo per caricare la LibreriaBack da un file presente sul disco fisso all'indirizzo 
	 * passato come parametro
	 * 
	 * @param Path
	 * 				l'indirizzo del file da cui caricare la LibreriaBack
	 */
	public static void loadLibreriaBack(String Path) {
		LibreriaBack = ListaMp3.loadLista(Path);
		loadLibreriaView();
		LinkTabella.updateUI();
		return;
		}	
	
	/**
	 * Metodo per salvare la LibreriaBack su un file presente sul disco fisso all'indirizzo 
	 * passato come parametro
	 * 
	 * @param Path
	 * 				l'indirizzo del file su cui salvare la LibreriaBack
	 */
	public static void saveLibreriaBack(String Path) {
		ListaMp3.saveLista(Path);
		}
	
	/**
	 * Metodo sfruttato per controllare il click destro sulla TabellaMp3, per gestire la selezione
	 * delle righe e l'apertura o meno del PopupMenu relativo alla tabella.
	 * 
	 * @param point
	 * 				il punto in cui si è effettuato il click destro
	 */
	public void checkRightClick (Point point) {
		int row = this.rowAtPoint(point);
		int[] rows = this.getSelectedRows();
		int flag = 0;
		for (int i = 0; i < rows.length; i++)
				if (row == rows[i])
					flag = 1;
		if (flag == 0)
			this.getSelectionModel().setSelectionInterval(row,row);
	}		
	
	/**
	 * Metodo per costruire le liste che andranno a creare il Modello dei JComboBox utilizzati per
	 * il filtraggio della Libreria
	 * 
	 * @param Libreria
	 * 				la libreria da cui creare la lista 
	 * @param Colonna
	 * 				il campo che rappresenta il filtro da applicare
	 * @return	la lista che andrà inserita nei JComboBox
	 */
	public static Vector<String> makeListaComboBox (ListaMp3 Libreria, String Colonna) {
		Lista = new Vector<String>();
		String tmp = null;
		for (int i = 0; i < Libreria.length() ; i++) { 
			if 		(Colonna.equals("Artista"))
				tmp = (String) Libreria.getFile(i).getArtista();
			else if (Colonna.equals("Album"))
				tmp = (String) Libreria.getFile(i).getAlbum();
			else if (Colonna.equals("Anno"))
				tmp = (String) Libreria.getFile(i).getAnno();
			else if (Colonna.equals("Genere"))
				tmp = (String) Libreria.getFile(i).getGenere();
			if (!Lista.contains(tmp))
				Lista.addElement(tmp);
		}
		if (Lista.size() > 0)
			Lista = new Sort().QuickSort(Lista);
		Lista.insertElementAt("Seleziona " + Colonna, 0);
		return Lista;
		}

	/**
	 * Metodo per il filtraggio della LibreriaView in base al SelectedItem dei vari JComboBox 
	 * utilizzati come filtri
	 */
	public static void FiltraLista () {
		int length;
		TabellaMp3.loadLibreriaView();
		
		Filtro = (String) JPanelLibreria.getJComboFiltro("Artista").getSelectedItem();
		
		if (!Filtro.equals("Seleziona Artista")) {
			length = LibreriaView.length();
			if (length > 0) {
				int i = 0;
				do 	{
					if (!Filtro.equals(LibreriaView.getFile(i).getArtista())) {
						LibreriaView.remove(LibreriaView.getFile(i));
						length--;
					}
					else 
						i++;
				} while (i < length);
			}
		}
		
		Filtro = (String) JPanelLibreria.getJComboFiltro("Album").getSelectedItem();
		
		if (!Filtro.equals("Seleziona Album")) {
			length = LibreriaView.length();
			if (length > 0) {
				int i = 0;
				do 	{
					if (!Filtro.equals(LibreriaView.getFile(i).getAlbum())) {
						LibreriaView.remove(LibreriaView.getFile(i));
						length--;
					}
					else 
						i++;
				} while (i < length);
			}
		}
		
		Filtro = (String) JPanelLibreria.getJComboFiltro("Anno").getSelectedItem();
		
		if (!Filtro.equals("Seleziona Anno")) {
			length = LibreriaView.length();
			if (length > 0) {
				int i = 0;
				do {
					if (!Filtro.equals(LibreriaView.getFile(i).getAnno())) {
						LibreriaView.remove(LibreriaView.getFile(i));
						length--;
					}
					else 
						i++;
				} while (i < length);
			}
		}

		Filtro = (String) JPanelLibreria.getJComboFiltro("Genere").getSelectedItem();
		
		if (!Filtro.equals("Seleziona Genere")) {
			length = LibreriaView.length();
			if (length > 0) {
				int i = 0;
				do {
					if (!Filtro.equals(LibreriaView.getFile(i).getGenere())) {
						LibreriaView.remove(LibreriaView.getFile(i));
						length--;
					}
					else 
						i++;
				} while (i < length);
			}
		}
		
		TabellaMp3.getTabella().updateUI();
		if (TabellaMp3.getTabella().getRowCount() != 0)
			TabellaMp3.getTabella().getSelectionModel().setSelectionInterval(0,0);
	}
	
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * MouseListener, in questo caso il MouseClicked
     */
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() == TableHeader)
				return;
		else if (SwingUtilities.isLeftMouseButton(e)) {
		    	if (e.getClickCount() == 2)  
		    		JPanelSud.setMp3toPlay("Play", false);
		    	else
		    		PopupMenu.setVisible(false);
		}
			
		else if (SwingUtilities.isRightMouseButton(e)) {
			checkRightClick(e.getPoint());
			PopupMenu.show(this, e.getX(), e.getY());
		}
	}	

    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * KeyListener in questo caso il KeyPressed 
     */
	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_DELETE)
			PopupMenuTabella.Rimuovi();					
	}

	public void keyReleased(KeyEvent k) {}
	public void keyTyped(KeyEvent k) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}	
	public void mouseReleased(MouseEvent e) {}
	
	// Dichiarazione Variabili
	private static ListaMp3 LibreriaView;
	private static ListaMp3 LibreriaBack;
	private static TabellaMp3 LinkTabella;
	private PopupMenuTabella PopupMenu;
	private static Vector<String> Lista;
	private static String Filtro;
	private static JTableHeader TableHeader;


}
