package prove;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.API;
import download.ethernet;
import download.scanUser;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		new OutputTxt();
		new API();
		YouTubeService myService = new YouTubeService("Tesina");
		new scanUser(myService);
		
	}
}
