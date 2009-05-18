package database;

/**
 * Classe che definisce un singolo nodo della lista. Questo nodo può essere
 * utilizzato sia come nodo di una lista linkata semplice che linkata doppia.
 * 
 * @author Monduzzi Mattia
 */
public class Node {

	/**
	 * Il contenuto di questo nodo, può essere sol un oggetto di tipo FileMp3.
	 */
	protected FileMp3 file = null;

	/**
	 * Riferimento al nodo successivo, usato in una lista linkata semplice e
	 * doppia.
	 */
	protected Node next = null;

	/**
	 * Riferimento al nodo precedente, usato SOLO in una lista linkata doppia.
	 * Se la lista linkata è semplice, ignorare questo nodo.
	 */
	protected Node prev = null;

	/**
	 * Costruttore base, imposta i valori del contenuto del nodo e dei
	 * riferimenti ai nodi precedenti e successivi. 
	 * 
	 * @param file
	 *            il contenuto del nodo
	 * @param next
	 *            il nodo successivo
	 * @param prev
	 *            il nodo precedente
	 */
	protected Node(FileMp3 file, Node next, Node prev) {
		this.file = file;
		this.next = next;
		this.prev = prev;
	}

	/**
	 * Costruttore base, imposta i valori del contenuto del nodo e il 
	 * riferimento al nodo successivo. 
	 * 
	 * @param file
	 *            il contenuto del nodo
	 * @param next
	 *            il nodo successivo
	 */
	protected Node(FileMp3 file, Node next) {
		this(file, next, null);
	}

	/**
	 * Costruttore base, imposta solo i valori del contenuto del nodo 
	 * 
	 * @param file
	 *            il contenuto del nodo
	 */
	protected Node(FileMp3 file) {
		this(file, null, null);
	}

	/**
	 * Restituisce il contenuto del nodo, ovvero il FileMp3.
	 * 
	 * @return il contenuto di questo nodo
	 */
	public final FileMp3 getFile() {
		return this.file;
	}

	/**
	 * Restituisce il nodo succesivo a questo.
	 * 
	 * @return il nodo successivo, null se non ci sono altri nodi
	 */
	public final Node getNext() {
		return this.next;
	}

	/**
	 * Restituisce il nodo precedente.
	 * 
	 * @return il nodo precedente
	 */
	public final Node getPrev() {
		return this.prev;
	}

	/**
	 * Imposta il prossimo nodo. Metodo protetto, usabile solo all'interno del
	 * package.
	 * 
	 * @param next
	 *            il prossimo nodo
	 */
	protected final void setNext(Node next) {
		this.next = next;
	}
	
	/**
	 * Imposta il nodo precedente. Il metodo è protetto, può essere usato solo
	 * in questo package.
	 * 
	 * @param prev
	 *            il nodo precedente
	 */
	protected final void setPrev(Node prev) {
		this.prev = prev;
	}
}
