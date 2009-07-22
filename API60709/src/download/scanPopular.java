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
		int temp = 0;
		String popularToCheck;	
		for (; (popularToCheck = DatabaseMySql.extract("utenti", "popToCheck", "user")[0]) != null ;) {
			if (!DatabaseMySql.contiene("utenti", "profile", popularToCheck)) {
				if (API.getActivity(myService, popularToCheck)) {	// Ha activityFeed? 
					if (API.getUser(myService, "active", popularToCheck))		// E' un utente sospeso?  No --> active
						completeScan(myService, popularToCheck);	// Si attivo scansione completa senza activity
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", "block", "block", "block");
				}
				else
					if (!API.getUser(myService, "inactive", popularToCheck))
						DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", "block", "block", "block");
				temp++;
			}
			if (temp == 30)
				return;
		}
	}
	
	public static void completeScan (YouTubeService myService, String user) {
		API.getVideo(myService, user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);
		API.getFavorites(myService, user);
		urlReader.userReader("friends", user);
		API.getSubscriptions(myService, user);
	}
}
