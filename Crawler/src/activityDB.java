import java.util.Vector;

import database.DatabaseMySql;
import database.OutputTxt;

	// Processo per l'aggiornamento delle liste delle relazioni in base alle nuove attività rilevate in seguito
	// all' analisi dell'utente interessato.

public class activityDB {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Inserire dabatase.");
			System.exit(0);
		}
		String nomeDB = args[0];
		new DatabaseMySql(nomeDB);	// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		new OutputTxt(); 			// Definisco il FileHandler per tutto il programma
		
		int totale = 0; 
		Vector<String[]> vector;
		String[] temp = new String[4] ;
		String uploader;
		for (int i = 0; i < 8; i++) {
			if (i == 0) 
				vector = DatabaseMySql.eseguiQuery("Select * from " + nomeDB + ".activity");
			else 
				vector = DatabaseMySql.eseguiQuery("Select * from " + nomeDB + ".activity" + i + "");
			while (vector.size() != 0) {
				temp[0] = vector.get(0)[0];
				temp[1] = vector.get(0)[1];
				temp[2] = vector.get(0)[2];
				temp[3] = vector.get(0)[3];
				if 		(temp[2] == "friended") {
					totale = Integer.parseInt(DatabaseMySql.eseguiQuery("Select totale from " + nomeDB + 
							".friends where user ='" + temp[0] + "' limit 1").get(0)[0]);
					System.out.println("Aggiornamento totale amici.");
					DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".friends set totale='" + ++totale + 
							"' where user ='"+ temp[0] + "'");
					DatabaseMySql.insert(nomeDB, "friends", temp[0], temp[1], totale);
				}
				else if (temp[2] == "subscribed") {
					totale = Integer.parseInt(DatabaseMySql.eseguiQuery("Select totale from " + nomeDB + 
							".subscriptions where user ='" + temp[0] + "' limit 1").get(0)[0]);
					System.out.println("Aggiornamento totale iscrizioni.");
					DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".subscriptions set totale='" + 
							++totale + "' where user ='"+ temp[0] + "'");
					DatabaseMySql.insert(nomeDB, "subscriptions", temp[0], temp[1], temp[3], totale);
				}				
				else if (temp[2] == "uploaded") {
					System.out.println("Video uppato " + temp[2]);
					totale = Integer.parseInt(DatabaseMySql.eseguiQuery("Select totale from " + nomeDB + 
							".numVideo where user ='" + temp[0] + "'").get(0)[0]);
					DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".numVideo set totale='" + ++totale + 
							"' where user ='"+ temp[0] + "'");
				}				
				else if (temp[2] == "favorited") {
					totale = Integer.parseInt(DatabaseMySql.eseguiQuery("Select totale from " + nomeDB + 
							".numFavorites where user ='" + temp[0] + "'").get(0)[0]);
					DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".numFavorites set totale='" + ++totale + 
							"' where user ='"+ temp[0] + "'");
					System.out.println("Video favorito " + temp[2]);
					uploader = DatabaseMySql.eseguiQuery("Select user from root.videoUploadedBy where id='" 
							+ temp[1] + "'").get(0)[0];
					DatabaseMySql.insert(nomeDB, "favorites", temp[0], temp[1], uploader, temp[3]);
				}					
				vector.removeElementAt(0);
			}
		}
	}

}
