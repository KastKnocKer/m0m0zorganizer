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
			urlReader.userReader("subscribers", user);
			urlReader.subscriptionsApiReader(user);	
		}			
	}
	
	public static void popularScan () {
		String[] userTemp;	
		for (; (userTemp = DatabaseMySql.extract("utenti", "popToCheck", "user")) != null ;) {
			if (!DatabaseMySql.contiene(userTemp[0], "active")) {
				
				if (API.getUser(userTemp[0])) {
					DatabaseMySql.insert("utenti", "popular", userTemp[0], userTemp[1], userTemp[2]);
					urlReader.videoApiReader(userTemp[0]);		// Alternati in modo da limitare i flood di rete
					urlReader.userReader("subscribers", userTemp[0]);
					urlReader.activityApiReader(userTemp[0]);		
					Runtime.getRuntime().gc();			
					urlReader.favoritesApiReader(userTemp[0]);
					urlReader.userReader("friends", userTemp[0]);
					urlReader.subscriptionsApiReader(userTemp[0]);
					Runtime.getRuntime().gc();
					
				}
			}
		}
	}
}
