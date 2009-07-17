package prove;

import database.DatabaseMySql;
import download.API;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		
		new API();
		API.getFavorites("hereforyou13");
		
	}
}
