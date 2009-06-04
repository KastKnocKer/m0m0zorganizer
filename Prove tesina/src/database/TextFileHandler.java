package database;

import java.io.*; 
import java.util.Vector;


/**
 * Classe definita per la scrittura e la lettura degli aristi/gruppi musicali su file txt. 
 * 
 * @author Monduzzi Mattia
 *
 */


public class TextFileHandler {
	
	/**
	 * Metodo per scrivere un Vector di SoloMusicArtist su un file. Lo schema del file di testo
	 * e' il seguente: vengono inseriti i parametri del artista musicale nel seguente ordine:
	 * nome, cognome, data di Nascita, data di Morte, nazionalit�, nome d'arte, biografia
	 * data di esordio, generi, discografia, fanpage e casa discografica.
	 * 
	 * @param Artista
	 *            il SoloMusicArtist da scrivere
	 */
	public void writeFile(Vector<String[]> user) {
		BufferedWriter writer; // il writer che user� per scrivere i
		// parametri
		String[] record;

		// controllo che l'artista sia un parametro valido
		try {
			// apro il buffered writer
			writer = new BufferedWriter(new FileWriter(file, false));
			writer.write("Lista Contatti controllati");
			writer.newLine();
			// Scrivo per artista le informazioni 
			for(int i=0; i<user.size(); i++){
				record = user.get(i);
				System.out.println("Scrittura di "+ record[0] + " : " + record[1]);
				writer.write(record[0] + " : " + record[1]);	
				writer.newLine();
			}
			writer.write("FINE");
			writer.newLine();
		
			// chiudo il file
			writer.flush();
			writer.close();
			}
		catch (IOException e) {
			System.out.println("Eccezione durante un'operazione di Output artista");
			}
		

	}
	
	/**
	 * Metodo per leggere un vector di SoloMusicArtist da un file.
	 * 
	 * @param file
	 *            il nome del file di testo
	 * @return il vector di SoloMusicArtist letto
	 */
	 public String[] readFile () {
		String[][] utenti;
		String stringa;
		Vector<String> vector = new Vector<String>();
		
		BufferedReader reader = null; // il reader che user� sul file
		try {
			// apro il reader e svuoto la tabella
			reader = new BufferedReader(new FileReader (file));			
			reader.readLine();
			while ((stringa = reader.readLine()) != null) {
					if (!stringa.equals("FINE"))
						vector.add(stringa);
			}
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		utenti = new String[vector.size()][2];
		int j = 0, n = 0;
		int mille = 0, cinquecento = 0, duecentocinquanta = 0, cento = 0, cinquanta = 0, uno = 0;
		for (int i = 0; i < utenti.length; i++ ) {
			n++;
			stringa = vector.get(i);
			// utenti[i][0] = stringa.substring(0 , stringa.indexOf(":"));
			utenti[i][1] = stringa.substring(stringa.indexOf(":") + 2 , stringa.length());
			j = Integer.parseInt(utenti[i][1]);
			if (j > 999) {
				mille++; 
				continue;				
			}
			else if (j > 499) {
				cinquecento++; 
				continue;				
			}
			else if (j > 249) {
				duecentocinquanta++; 
				continue;				
			}
			else if (j > 99) {
				cento++; 
				continue;				
			}
			else if (j > 49) {
				cinquanta++; 
				continue;				
			}
			else 
				uno++;	
			}
		
		System.out.println ("Con almeno 1000 utenti: " + mille);
		System.out.println ("Con almeno  500 utenti: " + cinquecento);
		System.out.println ("Con almeno  250 utenti: " + duecentocinquanta);
		System.out.println ("Con almeno  100 utenti: " + cento);
		System.out.println ("Con almeno   50 utenti: " + cinquanta);
		System.out.println ("Con almeno    1 utenti: " + uno);
		System.out.println ("Contatti controllati " + n);
		
		return null;
	}	
	
	private String file = "./Salvataggio";
}