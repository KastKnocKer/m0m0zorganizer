import database.DatabaseMySql;
import database.OutputTxt;
import download.Contatore;
import download.popularReader;


public class start {
	public static void main(String[] args) {
		
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		OutputTxt.writeLog("Inizio scansione");
		new popularReader();
		// LOL
		DatabaseMySql.Disconnetti();
	}
}
