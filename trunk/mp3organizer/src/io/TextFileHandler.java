package io;

import java.io.*; 
import java.util.Vector;

import artisti.*;

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
	 * nome, cognome, data di Nascita, data di Morte, nazionalità, nome d'arte, biografia
	 * data di esordio, generi, discografia, fanpage e casa discografica.
	 * 
	 * @param Artista
	 *            il SoloMusicArtist da scrivere
	 */
	public void writeArtista(Vector<SoloMusicArtist> Artista) {
		BufferedWriter writer; // il writer che userò per scrivere i
		// parametri

		// controllo che l'artista sia un parametro valido
		try {
			// apro il buffered writer
			writer = new BufferedWriter(new FileWriter(fileArtisti, false));
			writer.write("Lista Artisti");
			writer.newLine();
			// Scrivo per artista le informazioni 
			for (int i = 0; i < Artista.size(); i++) {
				writer.write("Artista:");
				writer.newLine();
				writer.write(Artista.elementAt(i).getNome());
				writer.newLine();
				writer.write(Artista.elementAt(i).getCognome());
				writer.newLine();
				writer.write(Artista.elementAt(i).getDataNascita());
				writer.newLine();
				writer.write(Artista.elementAt(i).getDataMorte());
				writer.newLine();
				writer.write(Artista.elementAt(i).getNazionalità());
				writer.newLine();
				writer.write(Artista.elementAt(i).getNomeArte());
				writer.newLine();
				writer.write(Artista.elementAt(i).getBiografia());
				writer.newLine();
				writer.write(Artista.elementAt(i).getDataEsordio());
				writer.newLine();
				writer.write(Artista.elementAt(i).getGeneri());
				writer.newLine();
				writer.write(Artista.elementAt(i).getDiscografia());
				writer.newLine();
				writer.write(Artista.elementAt(i).getFanPage());
				writer.newLine();
				writer.write(Artista.elementAt(i).getCasaDisco());
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
	public Vector<SoloMusicArtist> readArtista () {
		String stringa;
		SoloMusicArtist Artista;
		Vector<SoloMusicArtist> vector = new Vector<SoloMusicArtist>();
		
		BufferedReader reader = null; // il reader che userò sul file
		try {
			// apro il reader e svuoto la tabella
			reader = new BufferedReader(new FileReader (fileArtisti));			
			/** Leggo la prima riga se è differente da Artista non è quello che sto cercando
			 * allora non faccio nulla e leggo tutto il file fino a quando trovo una stringa
			 * Artista, vado a leggere la riga successiva, che è la stringa contenente 
			 * il nome dell'artista salvato sul file txt.
			 */
			while ((stringa = reader.readLine()) != null) {
				if( stringa.equals("Artista:")) {
					Artista = new SoloMusicArtist();
					Artista.setNome(reader.readLine());
					Artista.setCognome(reader.readLine());
					Artista.setDataNascita(reader.readLine());
					Artista.setDataMorte(reader.readLine());
					Artista.setNazionalità(reader.readLine());
					Artista.setNomeArte(reader.readLine());
					Artista.setBiografia(reader.readLine());
					Artista.setDataEsordio(reader.readLine());
					Artista.setGeneri(reader.readLine());
					Artista.setDiscografia(reader.readLine());
					Artista.setFanPage(reader.readLine());
					Artista.setCasaDisco(reader.readLine());
					vector.add(Artista);
					}
				}
			}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		return vector;
	}	

	/**
	 * Metodo per scrivere un Vector di MusicGroup su un file. Lo schema del file di testo
	 * e' il seguente: vengono inseriti i parametri del gruppo musicale nel seguente ordine:
	 * nome del gruppo, numero di componenti, data di formazione, data di scioglimento e poi
	 * per ogni componente viene scritto i relativi dati nel seguente ordine:
	 * nome, cognome, data di Nascita, data di Morte, nazionalità, nome d'arte, biografia
	 * data di esordio, generi, discografia, fanpage e casa discografica.
	 * 
	 * @param Artista
	 *            il SoloMusicArtist da scrivere
	 */
	public void writeGruppo(Vector<MusicGroup> gruppo) {
		BufferedWriter writer; // il writer che userò per scrivere i
		try {
			// apro il buffered writer
			writer = new BufferedWriter(new FileWriter(fileGruppi, false));
			// Scrivo per gruppo i relativi dati ed artisti
			writer.write("Lista Gruppi");
			writer.newLine();
			for (int j = 0; j < gruppo.size(); j++) {
				writer.write("Gruppo:");
				writer.newLine();
				writer.write(gruppo.elementAt(j).getNumeroArtistiString());
				writer.newLine();
				writer.write(gruppo.elementAt(j).getNomeGruppo());
				writer.newLine();
				writer.write(gruppo.elementAt(j).getDataFormazione());
				writer.newLine();
				writer.write(gruppo.elementAt(j).getDataScioglimento());
				writer.newLine();
				for (int i = 0; i < gruppo.elementAt(j).getNumeroArtisti(); i++) {
					writer.write(gruppo.elementAt(j).getArtista(i).getNome());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getCognome());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getDataNascita());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getDataMorte());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getNazionalità());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getNomeArte());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getBiografia());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getDataEsordio());

					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getGeneri());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getDiscografia());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getFanPage());
					writer.newLine();
					writer.write(gruppo.elementAt(j).getArtista(i).getCasaDisco());
					writer.newLine();
				}
			}
			writer.write("FINE");
			writer.newLine();
		
			// chiudo il file
			writer.flush();
			writer.close();
			}
		catch (IOException e) {
			System.out.println("Eccezione durante un'operazione di Output Gruppo");
		}
	}	

	/**
	 * Metodo per leggere un vector di MusicGroup da un file.
	 * 
	 * @param file
	 *            il nome del file di testo
	 * @return il vector MusicGroup letto
	 */
	public Vector<MusicGroup> readGruppo() {
		String stringa;
		MusicGroup Gruppo = null;
		Vector<MusicGroup> vector = new Vector<MusicGroup>();
		int numero;
		
		BufferedReader reader; // il reader che userò sul file
		try {
			// apro il reader e svuoto la tabella
			reader = new BufferedReader(new FileReader(fileGruppi));			

			while ((stringa = reader.readLine()) != null) {
				if( stringa.equals("Gruppo:")) {	
					numero =  new Integer(reader.readLine());	
					Gruppo = new MusicGroup(numero);
					Gruppo.setNomeGruppo(reader.readLine());
					Gruppo.setDataFormazione(reader.readLine());
					Gruppo.setDataScioglimento(reader.readLine());
					for (int i = 0; i < numero; i++) {
						Gruppo.getArtista(i).setNome(reader.readLine());
						Gruppo.getArtista(i).setCognome(reader.readLine());
						Gruppo.getArtista(i).setDataNascita(reader.readLine());
						Gruppo.getArtista(i).setDataMorte(reader.readLine());
						Gruppo.getArtista(i).setNazionalità(reader.readLine());
						Gruppo.getArtista(i).setNomeArte(reader.readLine());
						Gruppo.getArtista(i).setBiografia(reader.readLine());
						Gruppo.getArtista(i).setDataEsordio(reader.readLine());
						Gruppo.getArtista(i).setGeneri(reader.readLine());
						Gruppo.getArtista(i).setDiscografia(reader.readLine());
						Gruppo.getArtista(i).setFanPage(reader.readLine());
						Gruppo.getArtista(i).setCasaDisco(reader.readLine());
					}
				vector.add(Gruppo);
				}
			}
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		return vector;
	}
	
	private String fileArtisti = "./Salvataggi/Artisti.txt";
	private String fileGruppi = "./Salvataggi/Gruppi.txt";
}

