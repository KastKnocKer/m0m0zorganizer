package database;
import java.util.Vector;

/**
 * Classe che definisce un algoritmo di ordinamento alfabetico di tipo QuickSort per ordinare un Vector di stringhe
 * 
 * @author Administrator
 */
public class Sort {
	/**
	 * Metodo per l'ordinamento alfabetico tramite un algoritmo di tipo QuickSort un Vector di stringhe
	 * 
	 * @param array
	 * 				l'array da ordinare
	 * @return	l'array ordinato
	 */	
	public  Vector<String> QuickSort (Vector<String> array) {	
		return QuickSort(array, 0, array.size() - 1);
	}

	/**
	 * Metodo per l'ordinamento alfabetico tramite un algoritmo di tipo QuickSort un Vector di stringhe
	 * 
	 * @param array
	 * 				l'array da ordinare	 * @param left0
	 * @param left0 
	 * 				la posizione del primo elemento della lista
	 * @param right0
	 * 				la posizione del primo elemento della lista
	 * @return	l'array ordinato
	 */
	public  Vector<String> QuickSort(Vector<String> array, int left0, int right0) {

		// Dichiarazione variabili 
		int left;
		int right;
		String pivot = null;
		String tmp = null;
		// Fine dichiarazione variabili

		left = left0;
		right = right0 + 1; 
		
		pivot = array.elementAt(left0); 
		
		do {
			
			do left++; while (left <= right0 && array.elementAt(left).compareTo(pivot) < 0);
			
			
			do right--; while (array.elementAt(right).compareTo(pivot) > 0);
			
			
			if (left < right) {
				tmp = array.elementAt(left);
				array.setElementAt(array.elementAt(right), left);
				array.setElementAt(tmp, right);
			}
			
		}	
		while (left <= right);
		
		tmp = array.elementAt(left0);
		array.setElementAt(array.elementAt(right),left0);
		array.setElementAt(tmp, right);			
		 
		if (left0 < right) QuickSort(array, left0, right);
		if (left < right0) QuickSort(array, left, right0);
		return array;
		}
}
