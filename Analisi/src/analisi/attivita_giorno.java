package analisi;

import Database.DatabaseMySql;

public class attivita_giorno {

	private static String meseInizio, giornoInizio, meseFine, giornoFine;
	private static int gInizio, gFine;
	private static String num;
	
	
	public static void main(String[] args) {
		num = "55";
		gInizio=13;
		gFine=14;
		meseInizio="08";
		meseFine="08";
		giornoInizio="13";
		giornoFine="14";
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		while (!(meseFine.equals("10") && giornoFine.equals("23"))) {
			num = DatabaseMySql.eseguiQuery("select count(*) from totale.activity where " + 
					"updated>'2009-" + meseInizio + "-" + giornoInizio + "T00:00:00' and " +
					"updated<'2009-" + meseFine   + "-" + giornoFine   + "T00:00:00'").get(0)[0];
			//System.out.print("Inizio: " + giornoInizio + "-" + meseInizio + "\n");
			//System.out.print(" Fine: " 	+ giornoFine   + "-" + meseFine + "\n");
			//System.out.print(" Attivita': \n");
			//System.out.print(" Attività: " + num + "\n");
			System.out.print(num + "\n");
			if (meseInizio.equals("08")) {
				if (giornoInizio.equals("31")) {
					meseInizio="09";
					giornoInizio="01";
					gInizio=1;
				}
				else {
					giornoInizio = "0" + ++gInizio;
					if (gInizio >= 10)
						giornoInizio = "" + gInizio;					
				}
					
			} 
			else if (meseInizio.equals("09")) {
				if (giornoInizio.equals("30")) {
					meseInizio="10";
					giornoInizio="01";
					gInizio=1;
				}
				else {
					giornoInizio = "0" + ++gInizio;
					if (gInizio >= 10)
						giornoInizio = "" + gInizio;					
				}
			}
			else if (meseInizio.equals("10")) {
				giornoInizio = "0" + ++gInizio;
				if (gInizio >= 10)
					giornoInizio = "" + gInizio;					
			}
			if (meseFine.equals("08")) {
				if (giornoFine.equals("31")) {
					meseFine="09";
					gFine=1;
					giornoFine="01";
				}
				else {
					giornoFine = "0" + ++gFine;
					if (gFine >= 10)
						giornoFine = "" + gFine;					
				}
			} 
			else if (meseFine.equals("09")) {
				if (giornoFine.equals("30")) {
					meseFine="10";
					giornoFine="01";
					gFine=1;
				}
				else {
					giornoFine = "0" + ++gFine;
					if (gFine >= 10)
						giornoFine = "" + gFine;					
				}
			}
			else if (meseFine.equals("10")) {
				giornoFine = "0" + ++gFine;
				if (gFine >= 10)
					giornoFine = "" + gFine;					
			}
		}
	}
}
