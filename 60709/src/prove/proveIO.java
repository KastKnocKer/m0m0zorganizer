package prove;

import database.DatabaseMySql;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		
		DatabaseMySql.inserToCheck("utenti", "moo");
		DatabaseMySql.inserToCheck("utenti", "loool");
		DatabaseMySql.inserToCheck("utenti", "moo");
		DatabaseMySql.inserToCheck("utenti", "moo");
		
		
	}
}
