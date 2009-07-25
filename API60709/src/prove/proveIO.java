package prove;

//import com.google.gdata.client.youtube.YouTubeService;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.API;
import download.scanUser;
import download.urlReader;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		new OutputTxt();
		new API();
		//YouTubeService myService = new YouTubeService("Tesina");
		//API.getUser(myService, "active", "momoz1987");
		String devKey = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		//YouTubeService myService = new YouTubeService("Tesi");
		//new scanUser(myService, devKey);
		//System.out.println(devKey.length());
		DatabaseMySql.extract("utenti", "toCheck", "user");
		urlReader.pausa("utentu", 5, "nonna");
	}
}
