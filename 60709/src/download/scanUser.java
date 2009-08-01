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
			for (; (userToCheck = (DatabaseMySql.extract(nomeDB, "toCheck", "user"))[1]) != null ;) {
				if (!DatabaseMySql.contiene(nomeDB, "profile", "user", userToCheck)) {  // L'ho già fatto?
					if (API.getActivity(myService, devKey, nomeDB, userToCheck)) {	// Ha activityFeed?
						if (API.getUser(myService, devKey, "active", nomeDB, userToCheck)) {			// E' un utente sospeso?  No --> active
							completeScan(myService, devKey, nomeDB, userToCheck);	// Si attivo scansione completa senza activity
							temp++;
						}
					}
					else
						API.getUser(myService, devKey, "inactive", nomeDB, userToCheck);
				}
				if (temp == 30) { // DA MODIFICARE
					OutputTxt.writeLog("Richieste API per il processo: " + Contatore.getTotApi());
					OutputTxt.writeLog("Richieste URL per il processo: " + Contatore.getTotUrl());
					return;
				}
			}
		} catch (NullPointerException e) {System.out.println("Lista toCheck terminata.");}
	}
	
	
	public static void  completeScan (YouTubeService myService, String devKey, String nomeDB, String user) {
		API.getSubscriptions(myService, devKey, nomeDB, user);
		API.getFavorites(myService, devKey, nomeDB, user);
		urlReader.userReader(nomeDB, "subscribers", user);	
		urlReader.userReader(nomeDB, "friends", user);
		API.getVideo(myService, devKey, nomeDB, user);
	}
}
