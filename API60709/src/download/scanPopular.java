package download;

import com.google.gdata.client.youtube.YouTubeService;
import database.DatabaseMySql;
import database.OutputTxt;

public class scanPopular {

	public scanPopular (YouTubeService myService, String devKey) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt();
		
		popularScan(myService, devKey);
	}
	
	public static void popularScan (YouTubeService myService, String devKey) {
		int temp = 0, control = 0;
		String popularToCheck;	
		control = Integer.parseInt((DatabaseMySql.eseguiQuery("Select count(DISTINCT user) from utenti.popular")).get(0)[0]) / 2 + 1;
		for (; (popularToCheck = DatabaseMySql.extract("utenti", "popToCheck", "user")[0]) != null ;) {
			if (!DatabaseMySql.contiene("utenti", "profile", popularToCheck)) {
				if (API.getActivity(myService, devKey, popularToCheck)) {	// Ha activityFeed? 
					if (API.getUser(myService, devKey, "active", popularToCheck))		// E' un utente sospeso?  No --> active
						completeScan(myService, devKey, popularToCheck);	// Si attivo scansione completa senza activity
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", "block", "block", "block");
				}
				else
					if (!API.getUser(myService, devKey, "inactive", popularToCheck))
						DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", "block", "block", "block");
				temp++;
			}
			if (temp == control) {
				return;
			}
		}
	}
	
	public static void completeScan (YouTubeService myService, String devKey, String user) {
		API.getVideo(myService, devKey, user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);
		API.getFavorites(myService, devKey, user);
		urlReader.userReader("friends", user);
		API.getSubscriptions(myService, devKey, user);
	}
}
