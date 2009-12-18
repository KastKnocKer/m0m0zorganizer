package analisi;

import Database.DatabaseMySql;

public class Viste_Rel {

	static String user, activity;
	static String[] temp;
	static int totale;
	
	public static void main(String[] args) {
		temp = new String[6];
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		while ((user = DatabaseMySql.extract("totale", "profileTemp", "user")[0]) != null) {
			totale = 0;
			activity = DatabaseMySql.eseguiQuery("Select count(*) from totale.activity where user='"+ user +"'").get(0)[0];
			DatabaseMySql.eseguiAggiornamento("Update totale.visteRelation set activity='" + activity + "' where user='" + user + "'");
			temp = DatabaseMySql.eseguiQuery("Select * from totale.numRelation where user='"+ user +"'").get(0);
			for (int i = 1; i < 6; i++) {
				if (!temp[i].equals("reserved")) 
					totale += Integer.parseInt(temp[i]);
			}
			DatabaseMySql.eseguiAggiornamento("Update totale.visteRelation set relation='" + totale + "' where user='" + user + "'");
		}
	}
}
