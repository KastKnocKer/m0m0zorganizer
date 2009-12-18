package analisi;

import Database.DatabaseMySql;

public class Profile_Composta {

	static String user;
	static String status;
	
	public static void main(String[] args) {
		int n = 0;
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		
		while ((user = DatabaseMySql.extract("totale", "profileTemp", "user")[0]) != null) {
			if (DatabaseMySql.contiene("prima", "profile", "user", user)) {
				status = DatabaseMySql.eseguiQuery("Select status from prima.profile where user='" + user +"'").get(0)[0];
				DatabaseMySql.eseguiAggiornamento("Update totale.profileStatus set status1 ='" + status + 
						"' where user='" + user + "'");
			}	
			if (DatabaseMySql.contiene("seconda", "profile", "user", user)) {
				status = DatabaseMySql.eseguiQuery("Select status from seconda.profile where user='" + user +"'").get(0)[0];
				DatabaseMySql.eseguiAggiornamento("Update totale.profileStatus set status2 ='" + status + 
						"' where user='" + user + "'");
			}	
			if (DatabaseMySql.contiene("terza", "profile", "user", user)) {
				status = DatabaseMySql.eseguiQuery("Select status from terza.profile where user='" + user +"'").get(0)[0];
				DatabaseMySql.eseguiAggiornamento("Update totale.profileStatus set status3 ='" + status + 
						"' where user='" + user + "'");
			}	
			if (DatabaseMySql.contiene("quarta", "profile", "user", user)) {
				status = DatabaseMySql.eseguiQuery("Select status from quarta.profile where user='" + user +"'").get(0)[0];
				DatabaseMySql.eseguiAggiornamento("Update totale.profileStatus set status4 ='" + status + 
						"' where user='" + user + "'");
			}	
			if (DatabaseMySql.contiene("quinta", "profile", "user", user)) {
				status = DatabaseMySql.eseguiQuery("Select status from quinta.profile where user='" + user +"'").get(0)[0];
				DatabaseMySql.eseguiAggiornamento("Update totale.profileStatus set status5 ='" + status + 
						"' where user='" + user + "'");
			}
			System.out.println(++n + ": " + user);
		}
	}
}
