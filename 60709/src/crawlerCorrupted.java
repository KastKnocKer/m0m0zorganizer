import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.scanCorrupted;

public class crawlerCorrupted {

	public static void main(String[] args) {		
		new DatabaseMySql(args[1]);		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		YouTubeService myService = new YouTubeService("Tesi", DatabaseMySql.eseguiQuery("Select devKey from utenti.key where crawler='"+ args[0] + "'").get(0)[0]);
	
		new scanCorrupted(myService, DatabaseMySql.eseguiQuery("Select devKey from utenti.key where crawler='"+ args[0] + "'").get(0)[0], args[1]);
	
		DatabaseMySql.Disconnetti();
	}
}
