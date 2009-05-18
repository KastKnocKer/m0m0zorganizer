package gui.Tabella;

import javax.swing.table.AbstractTableModel;
import database.*;

/**
 * Classe che estende AbstractTableModel, creando così un nuovo modello personalizzato 
 * che sfrutteremo per la JTable contente la Libreria dei FileMp3.
 * 
 * @author Monduzzi Mattia
 * 
 */

@SuppressWarnings("serial")
public class myTableModel extends AbstractTableModel{

	/** Costruttore del modello
	 * 
	 */
	public myTableModel () { super(); }	
	
	/**
	 * Metodo che restituisce, in base all'indice di colonna indicato come parametro
	 * il nome della relativa colonna
	 * 
	 *  @param col
	 *  		l'indice di colonna
	 *  @return il nome della colonna
	 */
	public String getColumnName (int col) {
		switch (col) {
			case 0 : return "N°";
			case 1 : return	"Titolo";	
			case 2 : return "Artista"; 
			case 3 : return "Album"; 	
			case 4 : return "Traccia"; 
			case 5 : return "Anno"; 
			case 6 : return "Genere"; 
			case 7 : return "Path";	
			}
		return "Errore getColumnName";
	}
	
	/**
	 * Metodo che restituisce il numero di colonne definito dal TableModel
	 * 
	 * @return il numero di colonne
	 */
	public int getColumnCount() { return 8; }

	/**
	 * Metodo che restituisce il numero di righe definito dal TableModel
	 * 
	 * @return il numero di righe
	 */
	public int getRowCount() 	{ return TabellaMp3.getLibreriaView().length(); }
	
	/**
	 * Metodo che restituisce, indicando le coordinate di una cella(row, col) il relativo valore 
	 * sottoforma di un generico Object
	 * 
	 * @param row
	 * 			indice di riga della cella
	 * @param col
	 * 			indice di colonna della cella
	 * @return il valore della cella sottoforma di object
	 */
	public Object getValueAt(int row, int col) {
		Mp3 = TabellaMp3.getLibreriaView().getFile(row);

		switch (col) {
			case 0 : return  (int) row + 1;
			case 1 : return	 Mp3.getTitolo();	
			case 2 : return  Mp3.getArtista();
			case 3 : return  Mp3.getAlbum();
			case 4 : return  Mp3.getTraccia();
			case 5 : return  Mp3.getAnno();
			case 6 : return  Mp3.getGenere();
			case 7 : return  Mp3.getPath();
		}
		return "Errore getValueAt";
	}
	
	// Dichiarazione Variabili
	private FileMp3 Mp3;

	
}