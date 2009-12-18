package analisi;

import Database.DatabaseMySql;

public class Profile_Statistic {

	static String user;
	static String[] temp;
	
	public static void main(String[] args) {
		int n = 0;
		temp = new String[5];
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		
		while ((user = DatabaseMySql.extract("totale", "profileTemp", "user")[0]) != null) {
			System.out.println(++n + ": " + user);
			if (DatabaseMySql.contiene("quinta", "profile", "user", user)) {
				temp = DatabaseMySql.eseguiQuery("Select user, status, subcount, viewcount, videowatch from quinta.profile where user='" + user +"'").get(0);
				if (!(temp[1].equals("blocked") || temp[1].equals("inactive"))) {
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='"		+ temp[1] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set subcount='" 	+ temp[2] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set viewcount='" 	+ temp[3] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set videowatch='" + temp[4] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='" 	+ temp[1] + "' where user='" + temp[0] + "'"); 
					continue;
				}
			}	
			if (DatabaseMySql.contiene("quarta", "profile", "user", user)) {
				temp = DatabaseMySql.eseguiQuery("Select user, status, subcount, viewcount, videowatch from quarta.profile where user='" + user +"'").get(0);
				if (!(temp[1].equals("blocked") || temp[1].equals("inactive"))) {
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='"		+ temp[1] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set subcount='" 	+ temp[2] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set viewcount='" 	+ temp[3] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set videowatch='" + temp[4] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='" 	+ temp[1] + "' where user='" + temp[0] + "'"); 
					continue;
				}

			}	
			if (DatabaseMySql.contiene("terza", "profile", "user", user)) {
				temp = DatabaseMySql.eseguiQuery("Select user, status, subcount, viewcount, videowatch from terza.profile where user='" + user +"'").get(0);
				if (!(temp[1].equals("blocked") || temp[1].equals("inactive"))) {
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='"		+ temp[1] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set subcount='" 	+ temp[2] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set viewcount='" 	+ temp[3] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set videowatch='" + temp[4] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='" 	+ temp[1] + "' where user='" + temp[0] + "'"); 
					continue;
				}

			}	
			if (DatabaseMySql.contiene("seconda", "profile", "user", user)) {
				temp = DatabaseMySql.eseguiQuery("Select user, status, subcount, viewcount, videowatch from seconda.profile where user='" + user +"'").get(0);
				if (!(temp[1].equals("blocked") || temp[1].equals("inactive"))) {
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='"		+ temp[1] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set subcount='" 	+ temp[2] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set viewcount='" 	+ temp[3] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set videowatch='" + temp[4] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='" 	+ temp[1] + "' where user='" + temp[0] + "'"); 
					continue;
				}

			}	
			if (DatabaseMySql.contiene("prima", "profile", "user", user)) {
				temp = DatabaseMySql.eseguiQuery("Select user, status, subcount, viewcount, videowatch from prima.profile where user='" + user +"'").get(0);
				if (temp[1].equals("blocked") || temp[1].equals("inactive"))
					DatabaseMySql.delete("totale", "profileStatistic", "user", user);
				else {
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='"		+ temp[1] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set subcount='" 	+ temp[2] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set viewcount='" 	+ temp[3] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set videowatch='" + temp[4] + "' where user='" + temp[0] + "'");
					DatabaseMySql.eseguiAggiornamento("Update totale.profileStatistic set status='" 	+ temp[1] + "' where user='" + temp[0] + "'"); 
				}
			}
		}
	}
}

