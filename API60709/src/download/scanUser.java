package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanUser {
	
	public scanUser (YouTubeService myService) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		new API();
		
		toCheck(myService);
	}
	
	public static void toCheck(YouTubeService myService) {
		int temp = 0;
		String userToCheck;	
		for (; (userToCheck = (DatabaseMySql.extract("utenti", "toCheck", "user"))[1]) != null ;) {
			System.out.println(userToCheck);
			if (!DatabaseMySql.contiene("utenti", "profile", userToCheck)) {  // L'ho già fatto?
				if (API.getActivity(myService, userToCheck)) {	// Ha activityFeed?
					if (API.getUser(myService, "active", userToCheck))			// E' un utente sospeso?  No --> active
						completeScan(myService, userToCheck);	// Si attivo scansione completa senza activity
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.insert("utenti", "profile", userToCheck, "blocked", "block", "block", "block", "block");
					temp++;
					}
				else
					if (!API.getUser(myService, "inactive", userToCheck))
						DatabaseMySql.insert("utenti", "profile", userToCheck, "blocked", "block", "block", "block", "block");
			}
			if (temp == 50)
				return;
		}
	}
	
	
	public static void completeScan(YouTubeService myService, String user) {
		// if (Contatore.checkCompleteScan())  PENSARE SE METTERLO
		API.getVideo(myService, user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);	
		API.getFavorites(myService, user);
		urlReader.userReader("friends", user);
		API.getSubscriptions(myService, user);
	}
	/*
	public static void inactive() {
		String[] userTemp;	
		for (; (userTemp = DatabaseMySql.extract("utenti", "inactive", "user")) != null ;) {
			if (API.getUser("inactive", userTemp[0])) {			// E' un utente sospeso?  No --> active
				if (API.getActivity(userTemp[0]))	// Ha activityFeed? 
					DatabaseMySql.moveUser("utenti", "inactive", "active", "user", userTemp[0]);
				completeScan(userTemp[0], false);	// Si attivo scansione completa senza activity
				Runtime.getRuntime().gc();
			}
			else
				DatabaseMySql.insert("utenti", "blocked", userTemp[0]);
			//Runtime.getRuntime().gc();
		}
	} */
}
