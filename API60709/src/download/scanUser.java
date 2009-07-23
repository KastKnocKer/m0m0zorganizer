package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanUser {
	
	public scanUser (YouTubeService myService, String devKey) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		new API();
		
		toCheck(myService, devKey);
	}
	
	public static void toCheck(YouTubeService myService, String devKey) {
		int temp = 0;
		String userToCheck;	
		for (; (userToCheck = (DatabaseMySql.extract("utenti", "toCheck", "user"))[1]) != null ;) {
			if (!DatabaseMySql.contiene("utenti", "profile", userToCheck)) {  // L'ho già fatto?
				if (API.getActivity(myService, devKey, userToCheck)) {	// Ha activityFeed?
					if (API.getUser(myService, devKey, "active", userToCheck)) {			// E' un utente sospeso?  No --> active
						completeScan(myService, devKey, userToCheck);	// Si attivo scansione completa senza activity
						temp++;
					}
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.insert("utenti", "profile", userToCheck, "blocked", "block", "block", "block", "block");
					}
				else
					if (!API.getUser(myService, devKey, "inactive", userToCheck))
						DatabaseMySql.insert("utenti", "profile", userToCheck, "blocked", "block", "block", "block", "block");
			}
			if (temp == 100) {
				OutputTxt.writeLog("User scansionati   totale: " + DatabaseMySql.getCount("utenti", "profile"));
				OutputTxt.writeLog("User scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("User scansionati inattivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='inactive'").get(0)[0]);
				OutputTxt.writeLog("User scansionati bloccati: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='blocked'").get(0)[0]);
				return;
			}
		}
	}
	
	
	public static void completeScan(YouTubeService myService, String devKey, String user) {
		// if (Contatore.checkCompleteScan())  PENSARE SE METTERLO
		API.getVideo(myService, devKey, user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);	
		API.getFavorites(myService, devKey, user);
		urlReader.userReader("friends", user);
		API.getSubscriptions(myService, devKey, user);
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
