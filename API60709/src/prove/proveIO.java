
	package prove;

//import com.google.gdata.client.youtube.YouTubeService;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.Contatore;
import download.scanActivity;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql("utenti");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		//YouTubeService myService = new YouTubeService("Tesina");
		//API.getUser(myService, "active", "momoz1987");
		String devKey = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		YouTubeService myService = new YouTubeService("Tesi");
		//new scanUser(myService, devKey);
		//System.out.println(devKey.length());
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"padre\", \"true\")");
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"figlio\", \"true\")");
		new scanActivity (myService, devKey, "utenti", "2009-07-10T09:39:22", 1);
	}
}
