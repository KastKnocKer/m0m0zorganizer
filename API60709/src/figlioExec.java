

import scansioni.figlio;
import scansioni.padre;
import database.DatabaseMySql;

public class figlioExec {

	public static void main(String[] args) {
		new DatabaseMySql("utenti");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		
		String nomeDB = "utenti";
		padre.scansioneVeloce(1, nomeDB, "2009-07-21T01:01:01");
	}
}