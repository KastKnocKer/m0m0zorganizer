package download;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanUser {
	
	public scanUser () {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		new API();
		
		toCheck();
	}
	
	public static void completeScan(String user, Boolean flag) {
		// if (Contatore.checkCompleteScan())  PENSARE SE METTERLO
		urlReader.videoApiReader(user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);
		if (flag)
			urlReader.activityApiReader(user);		
		urlReader.favoritesApiReader(user);
		urlReader.userReader("friends", user);
		urlReader.subscriptionsApiReader(user);
	}
	
	public static void toCheck() {
		int temp = 0;
		String[] userTemp;	
		for (; (userTemp = DatabaseMySql.extract("utenti", "toCheck", "user")) != null ;) {
			if (!DatabaseMySql.checkUserDB("utenti", userTemp[0])) {  // L'ho già fatto?
				if (API.getUser("active", userTemp[0])) {			// E' un utente sospeso?  No --> active
					if (urlReader.activityApiReader(userTemp[0]))	// Ha activityFeed? 
						completeScan(userTemp[0], false);	// Si attivo scansione completa senza activity
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.moveUser("utenti", "active", "inactive", "user", userTemp[0]);
					temp++;
				}
				else
					DatabaseMySql.insert("utenti", "blocked", userTemp[0]);
				if (temp == 100) {
					return;
				}
			}			
		}
	}
	
	public static void inactive() {
		String[] userTemp;	
		for (; (userTemp = DatabaseMySql.extract("utenti", "inactive", "user")) != null ;) {
			if (API.getUser("inactive", userTemp[0])) {			// E' un utente sospeso?  No --> active
				if (urlReader.activityApiReader(userTemp[0]))	// Ha activityFeed? 
					DatabaseMySql.moveUser("utenti", "inactive", "active", "user", userTemp[0]);
				completeScan(userTemp[0], false);	// Si attivo scansione completa senza activity
				Runtime.getRuntime().gc();
			}
			else
				DatabaseMySql.insert("utenti", "blocked", userTemp[0]);
			//Runtime.getRuntime().gc();
		}
	}
}
