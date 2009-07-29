
	package prove;

//import com.google.gdata.client.youtube.YouTubeService;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import download.API;
import download.Contatore;
import download.popularReader;
import download.scanActivity;
import download.scanPopular;
import download.scanUser;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql("utenti");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
	/*	
		//YouTubeService myService = new YouTubeService("Tesina");
		//API.getUser(myService, "active", "momoz1987");
		String devKey = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		YouTubeService myService = new YouTubeService("Tesi");
		//new scanUser(myService, devKey);
		//System.out.println(devKey.length());
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"padre\", \"true\")");
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"figlio\", \"true\")");
	//	new popularReader("utenti");
	//	new scanPopular(myService, devKey, "utenti");
		new scanUser(myService, devKey, "utenti");
		//DatabaseMySql.insertError("utenti", "robyb70");
		System.out.println(DatabaseMySql.contiene("utenti", "profile", "user", "lanicosun")); */

	}
}
