package download;

import database.DatabaseMySql;
import database.OutputTxt;

public class Scan {
	
	public Scan() {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		new API();
		new urlReader();
	}
	
	public static void completeScan(String user) {   // Pensare se aggiungere Activity Feed direi NO!
		if(Contatore.checkCompleteScan()) {			 // + che altro eventuale controllo per utenti attivi
			if (API.getUser(user)) {
				urlReader.userReader("friends", user);
				urlReader.userReader("subscriber", user);
				API.getSubscription(user);
				urlReader.favoritesApiReader(user);
				urlReader.videoApiReader(user);			
			}
		}
	}
}
