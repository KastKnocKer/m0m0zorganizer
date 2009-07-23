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
		
		String devKey = "AI39si58jSnL3JZGLEl65owXd86CA5G-s_LR4nSUNEWCXl1LS7-5tkbZXJkw5Ow_I58NlRg1PPSKqkf16h96r9j_cqZE3tsuqg";
		YouTubeService myService = new YouTubeService("Tesi", devKey);
		
		new scanUser(myService, devKey);
		
		DatabaseMySql.Disconnetti();
	}
}
