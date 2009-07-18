package prove;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.API;
import download.urlReader;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		YouTubeService myService = new YouTubeService("Tesi", "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ");
		//vdovina,staffgrillo,covy1986
		new OutputTxt();
		new API();
		String[] userTemp;
		for (; DatabaseMySql.getCount("utenti","popToCheck") > 0 ;) {
			userTemp = DatabaseMySql.extract("utenti", "popToCheck", "user");
			API.getUser(myService, "active", userTemp[0]);
			API.getVideo(myService, userTemp[0]);		// Alternati in modo da limitare i flood di rete
			API.getFavorites(myService, userTemp[0]);
			API.getSubscriptions(myService, userTemp[0]);
			urlReader.userReader("subscribers", userTemp[0]);
			urlReader.userReader("friends", userTemp[0]);			
		}
		//urlReader.favoritesApiReader("marcoEalyssa");
		
	}
}
