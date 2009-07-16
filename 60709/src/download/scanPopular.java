package download;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanPopular {

	public scanPopular () {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt();
		new API();
		
		popularScan();
	}
	
	public static void popularScan () {
		int temp = 0;
		String[] userTemp;	
		for (; (userTemp = DatabaseMySql.extract("utenti", "popToCheck", "user")) != null ;) {
			if (!DatabaseMySql.contiene("utenti", userTemp[0], "active")) {	
				if (API.getUser("active", userTemp[0])) {		// E' un utente sospeso?  No --> active
					if (urlReader.activityApiReader(userTemp[0])) {	// Ha activityFeed? 
						completeScan(userTemp[0], false);	// Si attivo scansione completa senza activity
					}
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.moveUser("utenti", "active", "inactive", "user", userTemp[0]);
					temp++;
				}
				else
					DatabaseMySql.insert("utenti", "blocked", userTemp[0]);
				DatabaseMySql.insert("utenti", "popular", userTemp[0], userTemp[1], userTemp[2]);
			}
			if (temp == 100)
				return;
		}
	}
	
	public static void completeScan(String user, Boolean flag) {
		// if (Contatore.checkCompleteScan())  PENSARE SE METTERLO
		System.out.println("Scansione completa dell'utente: " + user);
		urlReader.videoApiReader(user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);
		if (flag)
			urlReader.activityApiReader(user);		
		urlReader.favoritesApiReader(user);
		urlReader.userReader("friends", user);
		urlReader.subscriptionsApiReader(user);
	}
}
