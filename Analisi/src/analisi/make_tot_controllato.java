package analisi;

import Database.DatabaseMySql;

public class make_tot_controllato {
	
	private static long n;
	private static String lista;
	private static String[] temp;

	public static void main(String[] args) {
		lista = "favorites";
	
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		
		//favoriti ("prima");
		favoriti ("seconda");
		favoriti ("terza");
		favoriti ("quarta");
		favoriti ("quinta");
		
		amici ("prima");
		amici ("seconda");
		amici ("terza");
		amici ("quarta");
		amici ("quinta");

		iscritti ("prima");
		iscritti ("seconda");
		iscritti ("terza");
		iscritti ("quarta");
		iscritti ("quinta");

		iscrizioni ("prima");
		iscrizioni ("seconda");
		iscrizioni ("terza");
		iscrizioni ("quarta");
		iscrizioni ("quinta");

		activityDB ("prima");
		activityDB ("seconda");
		activityDB ("terza");
		activityDB ("quarta"); 
		activityDB ("quinta");
		System.out.println("Completata.");
	}
	
	public static void favoriti (String nomeDB) {
		n = 0;
		lista = "favorites";
		temp = new String[4];
		while ((temp = DatabaseMySql.extract(nomeDB, lista, "user", "id")) != null) { 
			DatabaseMySql.insert("totale", lista, temp[0], temp[1], temp[2], temp[3]);
			System.out.println(++n + ": Favoriti: " + nomeDB + " PK: " + temp[0] + "-" + temp[1]);
			if (DatabaseMySql.contiene("totale", "config", "id", "figlio", "status", "off")) {
				System.out.println("Bloccato da Controllo DB");
				System.exit(0);
			}
		}
	}	

	public static void amici(String nomeDB) {
		n = 0;
		lista = "friends";
		temp = new String[3];
		while ((temp = DatabaseMySql.extract(nomeDB, lista, "user", "friend")) != null) {
			DatabaseMySql.insert("totale", lista, temp[0], temp[1]);
			System.out.println(++n + ": Amici: " + nomeDB + " PK: " + temp[0] + "-" + temp[1]);
			if (DatabaseMySql.contiene("totale", "config", "id", "figlio", "status", "off")) {
				System.out.println("Bloccato da Controllo DB");
				System.exit(0);
			}
		}
	}
	
	public static void iscrizioni (String nomeDB) {
		n = 0;
		lista = "subscriptions";
		temp = new String[4];
		while ((temp = DatabaseMySql.extract(nomeDB, lista, "user", "subscription")) != null) {
			DatabaseMySql.insert("totale", lista, temp[0], temp[1], temp[2]);
			System.out.println(++n + ": Subcriptions: " + nomeDB + " PK: " + temp[0] + "-" + temp[1]);
			if (DatabaseMySql.contiene("totale", "config", "id", "figlio", "status", "off")) {
				System.out.println("Bloccato da Controllo DB");
				System.exit(0);
			}
		}
	}
	
	public static void iscritti(String nomeDB) {
		n = 0;
		lista = "subscribers";
		temp = new String[3];
		while ((temp = DatabaseMySql.extract(nomeDB, lista, "user", "subscriber")) != null) {
			DatabaseMySql.insert("totale", lista, temp[0], temp[1]);
			System.out.println(++n + ": Subscribers: " + nomeDB + " PK: " + temp[0] + "-" + temp[1]);
			if (DatabaseMySql.contiene("totale", "config", "id", "figlio", "status", "off")) {
				System.out.println("Bloccato da Controllo DB");
				System.exit(0);
			}
		}
	}
	
	public static void activity (String nomeDB, String listaA) {
		n = 0;
		temp = new String[4];
		while ((temp = DatabaseMySql.extract(nomeDB, listaA, "user", "id", "action", "updated")) != null) {
			DatabaseMySql.insert("totale", "activity", temp[0], temp[1], temp[2], temp[3]);
			System.out.println(++n + " :" + listaA + ": " + nomeDB + " PK: " + temp[0] + "-" + temp[1] +
						"-" + temp[2] + "-" + temp[3]);
			if (DatabaseMySql.contiene("totale", "config", "id", "figlio", "status", "off")) {
				System.out.println("Bloccato da Controllo DB");
				System.exit(0);
			}
		}
	}
	
	public static void activityDB (String nomeDB) {
		activity (nomeDB, "activity");
		activity (nomeDB, "activity1");
		activity (nomeDB, "activity2");
		activity (nomeDB, "activity3");
		activity (nomeDB, "activity4");
		activity (nomeDB, "activity5");
		activity (nomeDB, "activity6");
		activity (nomeDB, "activity7"); 
		
	}
}
