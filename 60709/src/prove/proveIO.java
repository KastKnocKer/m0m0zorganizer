
	package prove;

//import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.client.youtube.YouTubeService;
import database.DatabaseMySql;
import database.OutputTxt;
import download.API;
import download.Contatore;


public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		new Contatore ();
		
		//YouTubeService myService = new YouTubeService("Tesina");
		//API.getUser(myService, "active", "momoz1987");
		String devKey = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		YouTubeService myService = new YouTubeService("Tesi");
		API.getUser(myService, devKey, "active", "prima", "Pumpkin71193");
		

	}
}
