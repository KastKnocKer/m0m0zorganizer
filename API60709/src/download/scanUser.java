package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanUser {
	
	public scanUser (YouTubeService myService, String devKey, String nomeDB) {
		new DatabaseMySql(nomeDB);		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		toCheck(myService, devKey, nomeDB);
	}
	
	public static void toCheck(YouTubeService myService, String devKey, String nomeDB) {
		int temp = 0;
		String userToCheck;
		try {
			for (; (userToCheck = (DatabaseMySql.extract("utenti", "toCheck", "user"))[1]) != null ;) {
				if (!DatabaseMySql.contiene("utenti", "profile", "user", userToCheck)) {  // L'ho già fatto?
					if (API.getActivity(myService, devKey, nomeDB, userToCheck)) {	// Ha activityFeed?
						if (API.getUser(myService, devKey, "active", nomeDB, userToCheck)) {			// E' un utente sospeso?  No --> active
							if(completeScan(myService, devKey, nomeDB, userToCheck))	// Si attivo scansione completa senza activity
								temp++;
							else
								DatabaseMySql.clearUser(nomeDB, userToCheck);
						}
						else 		// Non è attivo lo tolgo dagli active e lo metto negli inactive
							DatabaseMySql.insert("utenti", "profile", userToCheck, "blocked", "block", 0, 0, 0, "block");
						}
					else
						if (!API.getUser(myService, devKey, "inactive", nomeDB, userToCheck))
							DatabaseMySql.insert("utenti", "profile", userToCheck, "blocked", "block", 0, 0, 0, "block");
				}
				if (temp == 25) {
					return;
				}
			}
		} catch (NullPointerException e) {System.out.println("Lista toCheck terminata.");}
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
