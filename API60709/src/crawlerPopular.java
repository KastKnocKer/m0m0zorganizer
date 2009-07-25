import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.*;

public class crawlerPopular {

	public static void main(String[] args) {		
		new DatabaseMySql("utenti");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		
		YouTubeService myService = new YouTubeService("Tesi");
		
		new scanPopular(myService, DatabaseMySql.eseguiQuery("Select devKey from configurazioni.key where crawler='padre'").get(0)[0], "utenti");
		
		DatabaseMySql.Disconnetti();
	}
}
