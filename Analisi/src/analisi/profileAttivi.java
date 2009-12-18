package analisi;

import Database.DatabaseMySql;

public class profileAttivi {
	static String user, status;
	static String[] temp;
	
	public static void main(String[] args) {
		int attivi = 0, inattivi = 0;
		temp = new String[6];
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		while ((user = DatabaseMySql.extract("totale", "profileTemp", "user")[0]) != null) {
			System.out.println("Totale attivi:   " + attivi);
			System.out.println("Totale inattivi: " + inattivi);
			status = "Inattivo";
			temp = DatabaseMySql.eseguiQuery("Select * from totale.profileStatus where user ='" + user + "'").get(0);
			for (int i = 1; i < 6; i++) {
				if (temp[i] != null) {
					if (temp[i].equals("active") || temp[i].equals("*active*") || temp[i].equals("corrupted")) {
						DatabaseMySql.insert("totale", "profileAttivi", temp[0]);
						System.out.println(++attivi + ": " + temp[0] + " - attivo");
						break;
					}
					else {
						System.out.println(++inattivi + ": " + temp[0] + " - inattivo");
						break;
					}
				}
			}			
		}	
		System.out.println("Totale attivi:   " + attivi);
		System.out.println("Totale inattivi: " + inattivi);
	}
}
