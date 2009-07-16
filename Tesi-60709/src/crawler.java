import database.DatabaseMySql;
import database.OutputTxt;
import download.*;

public class crawler {

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
		
		new scanPopular();
		
		DatabaseMySql.Disconnetti();
	}
}
