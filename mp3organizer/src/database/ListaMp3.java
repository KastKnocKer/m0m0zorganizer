package database;

/**
 * Classe che definisce una lista linkata semplice tramite l'implementazione dell'interfaccia InterfacciaLista
 * @author Monduzzi Mattia
 */

import gui.Tabella.TabellaMp3;
import io.BinaryFileHandler;

public class ListaMp3 implements InterfacciaLista {

		/**
		 * Costruttore di default. Non fa nulla!
		 */
		public ListaMp3 () {
			super();
			this.head = null;
		}

		/**
		 * Costruttore con un FileMp3 da inserire in cima alla lista.
		 * 
		 * @param mp3
		 *            FileMp3 da inserire nel primo nodo della lista
		 */
		public ListaMp3 (FileMp3 mp3) {
			this();
			this.head = new Node(mp3);
		}
		
		/**
		 * Costruttore con un oggetto da inserire in cima alla lista.
		 * 
		 * @param head
		 *            l'oggetto da inserire nel primo nodo della lista
		 */
		public ListaMp3 (Node head) {
			this.head = head;
		}
		
		/**
		 * Costruttore che accetta più di un elemento da inserire nella lista.
		 * 
		 * @param data
		 *            l'array di oggetti da inserire nella lista
		 */
		public ListaMp3 (FileMp3[] data) {
			this();

			for (int i = 0; data != null && i < data.length; i++) {
				this.insert(data[i]);
			}
		}
		
		/**
		 * Indica se la lista è vuota. Una lista è vuota se non ha nessun nodo,
		 * ossia la sua testa è vuota.
		 * 
		 * @return true se la lista è vuota
		 */
		public final boolean isEmpty() {
			return (this.head == null);
		}

		/**
		 * Fornisce la lunghezza della lista.
		 * 
		 * @return la lugnehzza della lista, ossia quanti nodi contiene la lista
		 */
		public final int length() {
			// uso un nodo temporaneo per scorrere la lista
			Node tmp = null;
			int lunghezza = 0;

			// parto dalla testa della lista e vado avanti
			tmp = this.head;
			while (tmp != null) {
				lunghezza++;
				tmp = tmp.getNext();
			}
			return lunghezza;
		}
		
		/**
		 * Fornisce la posizione del File cercato.
		 * 
		 * @param
		 * 		il file di cui si cerca la posizione 
		 *
		 * @return la posizione del File cercato.
		 */
		public final int getPos (FileMp3 Mp3) {
			// uso un nodo temporaneo per scorrere la lista
			Node tmp = null;
			int pos = 0;

			// parto dalla testa della lista e vado avanti
			tmp = this.head;
			while ((tmp != null) && (!tmp.getFile().equals(Mp3))) {
				pos++;
				tmp = tmp.getNext();
			}
			return pos;
		}

		/**
		 * Restituisce il contenuto del primo nodo (testa) della lista.
		 * 
		 * @return il contenuto del primo nodo o null se la lista è vuota.
		 */
		public final FileMp3 head() {
			// se la lista è non vuota, allora posso estrarre l'informazione
			// del primo nodo, altrimenti non ho nulla da restituire
			if (!isEmpty())
				return this.head.getFile();
			else
				return null;
		}

		/**
		 * Restituisce l'ultimo oggetto contenuto nella lista.
		 * 
		 * @return il contenuto dell'ultimo nodo della lista
		 */
		public final FileMp3 last() {
			// se la lista è vuota non ho nessun elemento da restituire
			if (isEmpty())
				return null;
			// creo un nodo temporaneo per scorrere la lista fino alla fine
			Node tmp = null;

			// inizialmente tmp deve puntare alla cima della lista
			tmp = this.head;

			// ora scorro tutti i nodi fino a quando non trovo un nodo che
			// non ha successore
			while (tmp.getNext() != null)
				tmp = tmp.getNext();

			// sono arrivato all'ultimo nodo della mia lista, ne restituisco il
			// contenuto
			return tmp.getFile();

		}

		/**
		 * Restituisce la coda della lista. La coda di una lista è una lista formata
		 * da tutti gli elementi seguenti la testa.
		 * 
		 * @return la coda della lista
		 */
		public ListaMp3 tail() {
			// se la lista è vuota non ho niente da ritornare
			if (isEmpty())
				return null;

			// ritorno la lista che ha per testa il prossimo nodo della testa
			// corrente
			ListaMp3 ret = new ListaMp3();
			ret.head = this.head.getNext();
			return ret;
		}

		/**
		 * Inserimento di un nuovo nodo contenente un FileMp3
		 * 
		 * @param toInsert
		 *            l'oggetto da inserire
		 */
		public void insert(Object toInsert) {
			// controllo i parametri
			if (toInsert == null)
				return;
			else if (isEmpty()) {
				 this.head = new Node((FileMp3)toInsert);
				 return;
				}
			else if (contains((FileMp3) toInsert))
				return ;
			Node tmp = this.head;
			while (tmp.getNext() != null)
				tmp = tmp.getNext();
			tmp.setNext(new Node((FileMp3)toInsert));
		}

		/**
		 * Cerca un dato oggetto nella lista.
		 * 
		 * @param toSearch
		 *            l'oggetto da cercare nel contenuto di ogni nodo
		 * @return true se l'oggetto viene trovato, false altrimenti.
		 */
		public final boolean contains(Object toSearch) {
			// controllo il parametro
			if (toSearch == null)
				return false;

			// prendo un nodo temporaneo per scorrere la lista
			Node tmp = this.head;

			// scorro la lista
			while (tmp != null) {
				if (tmp.getFile().getAbsolutePath().equals(((FileMp3) toSearch).getAbsolutePath()))
					return true;
				tmp = tmp.getNext();
				}
			// se sono qui non ho trovato corrispondenza nella lista
			return false;
		}

		/**
		 * Rimuove un oggetto dalla lista.
		 * 
		 * @param toRemove
		 *            l'oggetto da rimuovere
		 */
		public void remove(Object toRemove) {
			// controllo parametri
			if (toRemove == null || (!contains(toRemove))) {
				// niente da rimuovere
				return;
			}

			// devo scorrere la lista un nodo alla volta, se il nodo corrente
			Node tmp = null;

			// nodi temporanei per tenere traccia di quello che devo eliminare
			Node prev = null;
			Node next = null;

			// parto dall testa
			tmp = this.head;

			// scorro la lista e OGNI nodo che contiene l'oggetto cercato
			// viene rimosso.
			while (tmp != null) {
				// memorizzo subito l'eventuale nodo seguente
				next = tmp.getNext();
				if (tmp.getFile().getAbsolutePath().equals(((FileMp3) toRemove).getAbsolutePath())) {
					// devo rimuovere questo nodo

					if (prev != null) {
						// aggancio il nodo precedente quello corrente a quello
						// successivo,
						// in questo modo salto un nodo (quello corrente) e quindi
						// lo elimino
						// dalla lista
						prev.setNext(next);
					}
					else if (prev == null) {
						// non ho un nodo precedente, significa che sto eliminando
						// la testa della lista
						this.head = next;
					}
				} 
				// salvo il nodo precedente
				prev = tmp;

				// avanzo al nodo successivo
				tmp = tmp.getNext();

			} // fine del while

		}
		
		/**
		 * Rimuove l'oggetto alla posizione pos dalla lista.
		 * 
		 * @param Pos
		 *		  posizione dell'oggetto da rimuovere
		 */		 
		public void removePos(int[] Pos) {
			for (int i = 0; i < Pos.length; i++)
				remove(getFile(Pos[i]-i));
		}
		
		/**
		 * Restituisce il File nel nodo di posizione pos
		 * 
		 * @param pos
		 * 			posizione del File da recuperare
		 * @return
		 * 			restituisce il FileMp3 alla posizione pos
		 */			
		public FileMp3 getFile (int pos){
			Node tmp = null;
			if (length() < pos) {
				System.out.println("Errore pos all'esterno della lista.");
				return null;
			}
			tmp = this.head;
			for (int i = 0; i < pos ; i++)
				tmp = tmp.getNext();
			return tmp.getFile();
		}
		
		/**
		 * Carica la lista dal file passato come parametro
		 * @param file
		 * 			file da cui caricare la lista
		 */	
		public static ListaMp3 loadLista (String filePath){
			return new BinaryFileHandler().read(filePath);
			
		}
		
		/**
		 * Salva la lista sul file passato come parametro
		 * @param file
		 * 			file su cui salvare la lista
		 */	
		public static void saveLista (String filePath){
			new BinaryFileHandler().write(TabellaMp3.getLibreriaBack(), filePath);
			return;
		}
		
		
		/**
		 * Svuota la lista
		 */
		public ListaMp3 Svuota () {
			this.head = null;
			return this;
		}
	
		// Dichiarazione Variabili 
		protected Node head = null;
}
