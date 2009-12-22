import java.util.UUID;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.*;

	// Processo per l'analisi dei semi iniziali della scansione, gli utenti popular
public class crawlerPopular {

	public static void main(String[] args) {		
		new DatabaseMySql(args[1]);		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		
		YouTubeService myService = new YouTubeService("ytapi-" + UUID.randomUUID().toString().substring(0, (int)(Math.random() * 3) + 2) + "-" +
				UUID.randomUUID().toString().substring(0, (int)(Math.random() * 6) + 12) + "-" + UUID.randomUUID().toString().substring(0,(int)( Math.random() * 3) + 2),
				DatabaseMySql.eseguiQuery("Select devKey from root.key where crawler='"+ args[0] + "'").get(0)[0]);
		
		new scanPopular(myService, DatabaseMySql.eseguiQuery("Select devKey from root.key where crawler='"+ args[0] + "'").get(0)[0], args[1]);
		
		DatabaseMySql.Disconnetti();
	}
}
