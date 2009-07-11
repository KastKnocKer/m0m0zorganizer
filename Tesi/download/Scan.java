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
	
	public static void completeScan(String user) {   
		Contatore.checkCompleteScan();  // Inserire in questo check lo switch delle schede ethernet!				 
		if (API.getUser(user)) {
			urlReader.videoApiReader(user);		// Alternati in modo da limitare i flood di rete
			urlReader.userReader("friends", user);
			urlReader.favoritesApiReader(user);
			urlReader.userReader("subscriber", user);
			API.getSubscription(user);	
		}			
	}
}
