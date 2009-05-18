package gui.Metadati;

import gui.JPanelLibreria;
import gui.Tabella.TabellaMp3;
import database.ListaMp3;

/**
 * Classe definita per effettuare la ricerca di FileMp3 nella Libreria Globlae tramite 
 * i valori impostati nel JDialog Ricerca 
 * 
 * @author Monduzzi Mattia
 */
public class Ricerca {

	/**
	 * Costruttore della classe. Esso crea una ricerca su elenco filtrato (Elenco = true) o su elenco 
	 * non filtrato in base al parametro passato. 
	 * @param Elenco
	 * 				una flag che imposta se la ricerca va effettuata su elenco filtrato o no.
	 */
	public Ricerca (boolean Elenco) {
		val = JDialogRicerca.getRicerca();
		
		if(!Elenco) {
			JPanelLibreria.ResetComboBox();
			TabellaMp3.loadLibreriaView();
		}

		ListaView = TabellaMp3.getLibreriaView();		
		for (int j = 0; j < 6 ; j++) {
			if (!val[j].isEmpty()) {
				length = ListaView.length();
				if (length > 0) {
					i = 0;
					do 	{
						if (!checkTag(i, j + 1)) {
							ListaView.remove(ListaView.getFile(i));
							length--;
						}
						else 
							i++;
					} while (i < length);
				}
			}	
		}
		if (val[6].equals("Con Copertina")) {
			length = ListaView.length();
			if (length > 0) {
				i = 0;
				do 	{
					if (!ListaView.getFile(i).hasCopertina()) {
						ListaView.remove(ListaView.getFile(i));
						length--;
						}
					else
						i++;
					} while (i < length);
				}
			}
		else if (val[6].equals("Senza Copertina")) {
			length = ListaView.length();
			if (length > 0) {
				i = 0;
				do 	{
					if (ListaView.getFile(i).hasCopertina()) {
						ListaView.remove(ListaView.getFile(i));
						length--;
						}
					else
						i++;
					} while (i < length);
				}
			}
		if (val[7].equals("Con Testo")) {
			length = ListaView.length();
			if (length > 0) {
				i = 0;
				do 	{
					if (!ListaView.getFile(i).hasLyrics()) {
						ListaView.remove(ListaView.getFile(i));
						length--;
						}
					else
						i++;
					} while (i < length);
				}
			}
		else if (val[7].equals("Senza Testo")) {
			length = ListaView.length();
			if (length > 0) {
				i = 0;
				do 	{
					if (ListaView.getFile(i).hasLyrics()) {
						ListaView.remove(ListaView.getFile(i));
						length--;
						}
					else
						i++;
					} while (i < length);
				}
			}
		TabellaMp3.getTabella().updateUI();	
	}

	/**
	 * Metodo per verificare se il FileMp3 avente pos come posizione nella elenco di ricerca, contiene o no
	 * il valore di ricerca all'interno del campo metadato indicato dalla variabile colonna 
	 * @param pos
	 * 			posizione del FileMp3 nell'elenco globale
	 * @param colonna
	 * 			selettore del campo metadati in cui effettuare la ricerca
	 * @return	true se si è trovato il valore di ricerca, false in caso contrario
	 */
	public boolean checkTag (int pos , int colonna) {
		switch (colonna) {
			case 1:
				return val[0].toLowerCase().equals((ListaView.getFile(pos)
						.getTitolo().toLowerCase()));
			case 2:
				return val[1].toLowerCase().equals((ListaView.getFile(pos)
						.getArtista().toLowerCase()));
			case 3:
				return val[2].toLowerCase().equals((ListaView.getFile(pos)
						.getAlbum().toLowerCase()));
			case 4:
				return val[3].equals((ListaView.getFile(pos).getTraccia()));
			case 5:
				return val[4].equals((ListaView.getFile(pos).getAnno()));
			case 6:
				return val[5].toLowerCase().equals((ListaView.getFile(pos)
						.getGenere().toLowerCase()));
			default:
				return false;
		}
	}   
	
	private ListaMp3 ListaView;
	private int length;
	private String[] val; 
	private int i;
}