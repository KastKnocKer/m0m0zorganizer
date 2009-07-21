package prove;

import database.DatabaseMySql;
import database.OutputTxt;
import download.API;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		new OutputTxt();
		new API();
		//YouTubeService myService = new YouTubeService("Tesina");
		DatabaseMySql.delete("utenti", "ethernet", "rete", "eth");
		System.out.print(DatabaseMySql.eseguiQuery("Select flag from utenti.ethernet").get(0)[0].contains("true"));

	}
}
