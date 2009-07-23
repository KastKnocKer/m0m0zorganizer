package download;

import com.google.gdata.client.youtube.YouTubeService;
import database.DatabaseMySql;
import database.OutputTxt;

public class scanPopular {

	public scanPopular (YouTubeService myService, String ClientID , String devKey) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt();
		
		popularScan(myService, ClientID, devKey);
	}
	
	public static void popularScan (YouTubeService myService, String ClientID , String devKey) {
		int temp = 0, control = 0;
		String popularToCheck;	
		control = (DatabaseMySql.getCount("utenti", "popToCheck") / 2) + 1;
		for (; (popularToCheck = DatabaseMySql.extract("utenti", "popToCheck", "user")[0]) != null ;) {
			if (!DatabaseMySql.contiene("utenti", "profile", popularToCheck)) {
				if (API.getActivity(myService, ClientID, devKey, popularToCheck)) {	// Ha activityFeed? 
					if (API.getUser(myService, ClientID, devKey, "active", popularToCheck))		// E' un utente sospeso?  No --> active
						completeScan(myService, ClientID, devKey, popularToCheck);	// Si attivo scansione completa senza activity
					else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
						DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", "block", "block", "block");
				}
				else
					if (!API.getUser(myService, ClientID, devKey, "inactive", popularToCheck))
						DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", "block", "block", "block");
				temp++;
			}
			if (temp == control) {
				OutputTxt.writeLog("Popular scansionati   totale: " + DatabaseMySql.getCount("utenti", "profile"));
				OutputTxt.writeLog("Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Popular scansionati inattivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='inactive'").get(0)[0]);
				OutputTxt.writeLog("Popular scansionati bloccati: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='blocked'").get(0)[0]);
				return;
			}
		}
	}
	
	public static void completeScan (YouTubeService myService, String ClientID , String devKey, String user) {
		API.getVideo(myService, ClientID, devKey, user);		// Alternati in modo da limitare i flood di rete
		urlReader.userReader("subscribers", user);
		API.getFavorites(myService, ClientID, devKey, user);
		urlReader.userReader("friends", user);
		API.getSubscriptions(myService, ClientID, devKey, user);
	}
}
