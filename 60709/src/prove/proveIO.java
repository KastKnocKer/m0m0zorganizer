
	package prove;

//import com.google.gdata.client.youtube.YouTubeService;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import database.createDatabase;
import download.API;
import download.Contatore;
import download.scanCorrupted;
import download.urlReader;


public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql("scansione");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		//YouTubeService myService = new YouTubeService("Tesina");
		//API.getUser(myService, "active", "momoz1987");
		String devKey = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		YouTubeService myService = new YouTubeService("Tesi");
		//new scanUser(myService, devKey);
		//System.out.println(devKey.length());
		DatabaseMySql.eseguiAggiornamento("insert into scansione.ethernet values (\"padre\", \"true\")");
		DatabaseMySql.eseguiAggiornamento("update scansione.ethernet set flag='true' where rete='padre'");
		DatabaseMySql.eseguiAggiornamento("insert into scansione.ethernet values (\"figlio\", \"false\")");
		if (API.getActivity(myService, devKey, "scansione","AndrejBiondic")) {	// Ha activityFeed?
			if (API.getUser(myService, devKey, "active", "scansione","AndrejBiondic")) { 	// E' un utente sospeso?  No --> active
				API.getSubscriptions(myService, devKey, "scansione","AndrejBiondic");
				urlReader.userReader("scansione", "subscribers","AndrejBiondic");
				API.getFavorites(myService, devKey, "scansione","AndrejBiondic");
				urlReader.userReader("scansione", "friends","AndrejBiondic");
				API.getVideo(myService, devKey, "scansione","AndrejBiondic");;
				
			}
		}
		else 
			API.getUser(myService, devKey, "inactive", "scansione","AndrejBiondic");
	}
}
