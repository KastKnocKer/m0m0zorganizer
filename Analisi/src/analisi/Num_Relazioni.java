package analisi;

import Database.DatabaseMySql;

public class Num_Relazioni {
	
	static String user, max;
 	
	public static void main(String[] args) {
		int n = 0;
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		while ((user = DatabaseMySql.extract("totale", "profileTemp", "user")[0]) != null) {
			max = foundMax("numVideo");
			DatabaseMySql.eseguiAggiornamento("insert into totale.numRelation (user, video) values ('" + user + 
					"' , '" + max +"')");
			max = foundMax("numFavorites");
			DatabaseMySql.eseguiAggiornamento("Update totale.numRelation set favorite='" + max + 
					"' where user='" + user +"'");
			max = foundMax("friends");
			DatabaseMySql.eseguiAggiornamento("Update totale.numRelation set friend='" + max + 
					"' where user='" + user +"'");
			max = foundMax("subscriptions");
			DatabaseMySql.eseguiAggiornamento("Update totale.numRelation set subscription='" + max + 
					"' where user='" + user +"'");
			max = foundMax("subscribers");
			DatabaseMySql.eseguiAggiornamento("Update totale.numRelation set subscriber='" + max + 
					"' where user='" + user +"'");

			System.out.println(++n + ": " + user);
			if (DatabaseMySql.contiene("root", "config", "id", "figlio", "status", "off")) {
				System.out.println("Bloccato da Controllo DB");
				System.exit(0);
			}
		}
		
	}

	public static String foundMax (String tabella) {
		if(!DatabaseMySql.contiene("quinta", "infoCorrupted", "user", user, "tabella", tabella)) {
			if (DatabaseMySql.contiene("quinta", tabella, "user", user)) {
				return DatabaseMySql.eseguiQuery("Select totale from quinta." + tabella+ " where user='" + user +"'  limit 1").get(0)[0];
			}
		}
		if(!DatabaseMySql.contiene("quarta", "infoCorrupted", "user", user, "tabella", tabella)) { 
			if (DatabaseMySql.contiene("quarta", tabella, "user", user)) {
				return DatabaseMySql.eseguiQuery("Select totale from quarta." + tabella+ " where user='" + user +"'  limit 1").get(0)[0];
			} 
		}
		if(!DatabaseMySql.contiene("terza", "infoCorrupted", "user", user, "tabella", tabella)) {
			if (DatabaseMySql.contiene("terza", tabella, "user", user)) {
				return DatabaseMySql.eseguiQuery("Select totale from terza." + tabella+ " where user='" + user +"' limit 1").get(0)[0];
			}
		}
		if(!DatabaseMySql.contiene("seconda", "infoCorrupted", "user", user, "tabella", tabella)) {
			if (DatabaseMySql.contiene("seconda", tabella, "user", user)) {
				return DatabaseMySql.eseguiQuery("Select totale from seconda." + tabella+ " where user='" + user +"'  limit 1").get(0)[0];
			}				
		}
		if(!DatabaseMySql.contiene("prima", "infoCorrupted", "user", user, "tabella", tabella)) {
			if (DatabaseMySql.contiene("prima", tabella, "user", user)) {
				return DatabaseMySql.eseguiQuery("Select totale from prima." + tabella+ " where user='" + user +"'  limit 1").get(0)[0];
			}
		}
		return "reserved";
	}	
}
