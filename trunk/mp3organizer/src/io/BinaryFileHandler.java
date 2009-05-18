package io;

import java.io.*;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import database.FileMp3;
import database.ListaMp3;

/**
 * Classe definita per la scrittura e la lettura della Libreria su un file binario. 
 * 
 * @author Monduzzi Mattia
 *
 */

public class BinaryFileHandler {
	
	/**
	 * Metodo per la scrittura della libreria sul file avente come indirizzo assoluto il parametro path
	 * @param Lista
	 * 			la libreria da salvare 
	 * @param file
	 * 			il file su cui effettuare la scrittura
	 */
	public  void write (ListaMp3 Lista, String file) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			for (int i = 0; i < Lista.length(); i++)
				oos.writeObject(Lista.getFile(i).getAbsolutePath());
			oos.writeObject("End");

		} catch (FileNotFoundException e) {
			System.out.println("Errore durante l'OutputAAA.");
		} catch (IOException e) {
			System.out.println("Errore durante l'OutputBBB.");
		}

	}
	
	/**
	 * Metodo per la lettura della libreria dal file binario avente come indirizzo assoluto il parametro path
	 * @param readFile
	 * 			l'indirizzo del file su cui effettuare la lettura 
	 * @return	la libreria letta dal file binario
	 */
	public ListaMp3 read (String readFile) {
		ListaMp3 Lista = new ListaMp3();
		FileMp3 Mp3 = null;
		File file;
		String Path = null;
		
		if (readFile == null)
			return Lista;		
		
		ObjectInputStream is;
		try {
			is = new ObjectInputStream(new FileInputStream(readFile));
			while (!(Path = ((String)is.readObject())).equals("End"))	
				if ((file = new File(Path)).exists()) {
					try { 
						Mp3 = new FileMp3 (file);
						Lista.insert(Mp3);
					} 
					catch (IOException e) {}
					catch (TagException e) {} 
					catch (ReadOnlyFileException e) {} 
					catch (InvalidAudioFrameException e) {}
				}
		} 
		catch (FileNotFoundException e) { return Lista; } 
		catch (IOException e) {return Lista; } 
		catch (ClassNotFoundException e) {return Lista; }
		return Lista;

	}
}
