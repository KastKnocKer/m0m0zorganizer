package download;

import com.google.gdata.client.youtube.YouTubeService;
import database.DatabaseMySql;
import database.OutputTxt;

public class scanPopular {

	public scanPopular (YouTubeService myService, String devKey, String nomeDB) {
		new DatabaseMySql(nomeDB);		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt();
		new Contatore ();
		
		popularScan(myService, devKey, nomeDB);
	}
	
	public static void popularScan (YouTubeService myService, String devKey, String nomeDB) {
		int temp = 0, control = 0;
		String popularToCheck;	
		try {
			control = Integer.parseInt((DatabaseMySql.eseguiQuery("Select count(DISTINCT user) from utenti.popular")).get(0)[0]) / 4 + 1;
			for (; (popularToCheck = DatabaseMySql.extract("utenti", "popToCheck", "user")[0]) != null ;) {
				if (!DatabaseMySql.contiene("utenti", "profile", "user", popularToCheck)) {
					if (API.getActivity(myService, devKey, nomeDB, popularToCheck)) {	// Ha activityFeed? 
						if (API.getUser(myService, devKey, "active", nomeDB, popularToCheck)) { 	// E' un utente sospeso?  No --> active
							if (!completeScan(myService, devKey, nomeDB, popularToCheck))	// Si attivo scansione completa senza activity
								DatabaseMySql.clearUser(nomeDB, popularToCheck);	
						}
						else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
							DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", 0, 0, 0, "block");
					}
					else
						if (!API.getUser(myService, devKey, "inactive", nomeDB, popularToCheck))
							DatabaseMySql.insert("utenti", "profile", popularToCheck, "blocked", "block", 0, 0, 0, "block");
					temp++;
				}
				if (temp == control) {
					return;
				}
			}
		} catch (NullPointerException e) {System.out.println("Lista popular terminata.");}
	}
	
	public static boolean  completeScan (YouTubeService myService, String devKey, String nomeDB, String user) {
		if(!API.getVideo(myService, devKey, nomeDB, user))
			return false;
		if(!urlReader.userReader(nomeDB, "subscribers", user))
			return false;	
		if(!API.getFavorites(myService, devKey, nomeDB, user))
			return false;
		if(!urlReader.userReader(nomeDB, "friends", user))
			return false;
		if(!API.getSubscriptions(myService, devKey, nomeDB, user))
			return false;
		return true;
	}
}
