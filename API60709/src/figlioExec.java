

import scansioni.figlio;
import database.DatabaseMySql;

public class figlioExec {

	public static void main(String[] args) {
		new DatabaseMySql("utenti");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		
		String nomeDB = "utenti";
		figlio.scansioneCompleta(nomeDB);
	}
}