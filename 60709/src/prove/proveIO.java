package prove;

import database.DatabaseMySql;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		
		DatabaseMySql.eseguiExtractUser("utenti", "active", "user", "moo");
		
	}
}
