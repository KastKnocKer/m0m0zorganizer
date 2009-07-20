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
				if (API.getActivity(myService, userTemp[0])) {	// Ha activityFeed? 
					if (API.getUser(myService, "active", userTemp[0]))		// E' un utente sospeso?  No --> active
						completeScan(myService, userTemp[0]);	// Si attivo scansione completa senza activity
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.insert("utenti", "blocked", userTemp[0]);
				}
				else
					if (!API.getUser(myService, "inactive", userTemp[0]))
						DatabaseMySql.insert("utenti", "blocked", userTemp[0]);			
				DatabaseMySql.insert("utenti", "scanned", userTemp[0]);			
			}
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
