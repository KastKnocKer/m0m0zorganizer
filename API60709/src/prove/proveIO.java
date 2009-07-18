package prove;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;
import download.API;

public class proveIO {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database		
		YouTubeService myService = new YouTubeService("Tesi", "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ");
		//vdovina,staffgrillo,covy1986
		new OutputTxt();
		new API();
		
		for (;;)
			API.getActivity(myService,"momoz1987");
		//urlReader.favoritesApiReader("marcoEalyssa");
		
	}
}
