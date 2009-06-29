
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import database.*;

public class Crawler {

	public static void main(String[] args) throws IOException {

		new Orario();  				 // Definisco l'orologio per tutto il programma
		new DatabaseMySql();		 // Definisco il database per tutto il programma
		DatabaseMySql.connetti();	 // Connessione al database
		new OutputTxt();    	 // Definisco il FileHandler per tutto il programma

  
		// Fornire utenti iniziale
		// Inserirli nel database
		// Inizio scansione dal database di ogni singolo utente
		
		
		
		
		/*
		Vector<String[]> vettore;
		String[] user;

		for ( ; (vettore =  db.eseguiQuery("Select * from utenti.contatti limit 1")) != null ;) {
			user = vettore.get(0);
			ConnessioneMySql.delete("utenti" , "contatti" ,"user" , user[0]);
			System.out.println("Cancellato dalla lista dei contatti");  // LOG
			if (urlReader.friendCount(user[0]))  {
			//	urlReader.friendReader(user[0]);
			}
			else {
				ConnessioneMySql.insert("utenti" , "bloccati" , user[0]);
				System.out.println("UTENTE BLOCCATOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			}			
		} 
		

	} */
	}
}
