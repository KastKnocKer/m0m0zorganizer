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
		YouTubeService myService = new YouTubeService("Tesi", "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ");
		
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		System.out.println("AVVIO NUOVO FILE");
		OutputTxt.writeLog("Nuovo crawler user");
		
		new scanUser(myService);
		
		DatabaseMySql.Disconnetti();
	}
}
