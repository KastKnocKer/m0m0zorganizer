package prove;

import database.DatabaseMySql;
import database.OutputTxt;
import download.API;
import download.ethernet;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		new OutputTxt();
		new API();
		//YouTubeService myService = new YouTubeService("Tesina");
		
		ethernet.switchTo(false);
	}
}
