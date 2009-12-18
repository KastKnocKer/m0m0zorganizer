package analisi;

import Database.DatabaseMySql;

public class correlazione {
	
	private static String[] lista;
	private static int num;
	public static void main(String[] args) {
		lista = new String[9];
		lista[0] = "Most_Discussed";
		lista[1] = "Most_Linked";
		lista[2] = "Most_Popular";
		lista[3] = "Most_Responded";
		lista[4] = "Most_Viewed";
		lista[5] = "Recently_Featured";
		lista[6] = "Top_Favorites";
		lista[7] = "Top_Rated";
		lista[8] = "YouTube_Mobile_Videos";
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		for (int i = 0; i < 9; i++)
			checkCorrelazione (lista[i]);
	}

	
	private static void checkCorrelazione(final String lista) {
		num = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(distinct Author) from video." + lista + " where Author IN " +
		"( Select user from totale.popular)").get(0)[0]);	
		System.out.println(DatabaseMySql.eseguiAggiornamento("Insert into totale.correlazione (lista, utentiPopular) values ('" + lista + "', '" + num + "')"));
		num = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(distinct Author) from video." + lista + " where Author IN " +
		"( Select user from totale.profileStatus)").get(0)[0]);	
		DatabaseMySql.eseguiAggiornamento("Update totale.correlazione set utenti='" + num + "' where lista='" + lista + "'");
		num = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(distinct Author) from video." + lista).get(0)[0]);		
		DatabaseMySql.eseguiAggiornamento("Update totale.correlazione set uploaderTopVideo='" + num + "' where lista='" + lista + "'");
		
		num = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(Author) from video." + lista + " where Author IN " +
		"( Select user from totale.popular)").get(0)[0]);	
		System.out.println(DatabaseMySql.eseguiAggiornamento("Update totale.correlazione set videoUtentiPopular='" + num + "' where lista='" + lista + "'"));
		num = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(Author) from video." + lista + " where Author IN " +
		"( Select user from totale.profileStatus)").get(0)[0]);	
		DatabaseMySql.eseguiAggiornamento("Update totale.correlazione set videoUtenti='" + num + "' where lista='" + lista + "'");
		num = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(*) from video." + lista).get(0)[0]);		
		DatabaseMySql.eseguiAggiornamento("Update totale.correlazione set videoLista='" + num + "' where lista='" + lista + "'");
	}

}
