import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.Contatore;
import download.scanUser;


public class crawlerUser {
	
	public static void main(String[] args) {		
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		OutputTxt.writeLog("Nuovo crawler user");
		
		YouTubeService myService = new YouTubeService("Tesi");
		
		new scanUser(myService, DatabaseMySql.eseguiQuery("Select key from utenti.ethernet where rete ='dev'").get(0)[0]);
		
		DatabaseMySql.Disconnetti();
	}
}
