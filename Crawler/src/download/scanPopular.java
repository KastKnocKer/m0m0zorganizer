package download;

import com.google.gdata.client.youtube.YouTubeService;
import database.DatabaseMySql;
import database.OutputTxt;

// Modulo per l'analisi dei semi iniziali della scansione, gli utenti popular
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
			control = Integer.parseInt((DatabaseMySql.eseguiQuery("Select count(DISTINCT user) from " + nomeDB + ".popular")).get(0)[0]) / 4 + 1;
			for (; (popularToCheck = DatabaseMySql.extract(nomeDB, "popToCheck", "user")[0]) != null ;) {
				if (!DatabaseMySql.contiene(nomeDB, "profile", "user", popularToCheck)) {
					if (API.getActivity(myService, devKey, nomeDB, popularToCheck)) {	// Ha activityFeed? 
						if (API.getUser(myService, devKey, "active", nomeDB, popularToCheck)) { 	// E' un utente sospeso?  No --> active
							completeScan(myService, devKey, nomeDB, popularToCheck);	// Si attivo scansione completa senza activity
						}
					}
					else
						API.getUser(myService, devKey, "inactive", nomeDB, popularToCheck);
					temp++;
				}
				if (temp == control) {
					OutputTxt.writeLog(nomeDB, "Richieste API per il processo: " + Contatore.getTotApi());
					OutputTxt.writeLog(nomeDB, "Richieste URL per il processo: " + Contatore.getTotUrl());
					return;
				}
			}
		} catch (NullPointerException e) {System.out.println("Lista popular terminata.");}
	}
	
	public static void  completeScan (YouTubeService myService, String devKey, String nomeDB, String user) {
		API.getSubscriptions(myService, devKey, nomeDB, user);
		API.getFavorites(myService, devKey, nomeDB, user);
		urlReader.userReader(nomeDB, "subscribers", user);	
		urlReader.userReader(nomeDB, "friends", user);
		API.getVideo(myService, devKey, nomeDB, user);
	}
}
