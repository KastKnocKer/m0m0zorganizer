package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanPopular {

	public scanPopular (YouTubeService myService) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt();
		
		popularScan(myService);
	}
	
	public static void popularScan (YouTubeService myService) {
		String[] userTemp;	
		for (; (userTemp = DatabaseMySql.extract("utenti", "popToCheck", "user")) != null ;) {
			if (!DatabaseMySql.contiene("utenti", "active", userTemp[0])) {	
				if (API.getUser(myService, "active", userTemp[0])) {		// E' un utente sospeso?  No --> active
					if (API.getActivity(myService, userTemp[0])) {	// Ha activityFeed? 
						completeScan(myService, userTemp[0], false);	// Si attivo scansione completa senza activity
					}
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.moveUser("utenti", "active", "inactive", "user", userTemp[0]);
				}
				else
					DatabaseMySql.insert("utenti", "blocked", userTemp[0]);
				DatabaseMySql.insert("utenti", "popular", userTemp[0], userTemp[1], userTemp[2]);
			}
		}
	}
	
	public static void completeScan(YouTubeService myService, String user, Boolean flag) {
		API.getVideo(myService, user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);
		if (flag)
			API.getActivity(myService, user);		
		API.getFavorites(myService, user);
		urlReader.userReader("friends", user);
		API.getSubscriptions(myService, user);
	}
}
