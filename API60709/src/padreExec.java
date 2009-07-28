
import scansioni.padre;
import database.DatabaseMySql;
import database.OutputTxt;

public class padreExec {

	public static void main(String[] args) {
		new DatabaseMySql("utenti"); // DA CONFIGURARE
		DatabaseMySql.connetti();	// Connessione al database
		OutputTxt.writeLog("Inizio scansione");
		String nomeDB;
		nomeDB = "utenti";  // Momentaneo		
		
		// Messo perchè non penso di utilizzare il figlio nelle scansioni veloci.
		DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag ='false' where rete='figlio'");
		
		padre.scansioneCompleta(nomeDB);
	}
}