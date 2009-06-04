package database;

import java.io.IOException;
import java.util.Vector;

public class prova {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConnessioneMySql db = new ConnessioneMySql();
		db.connetti();
		
		// Scrittura
	//	String stringa = "insert into utenti.contatti(user) values (\"momoz1987\")";
	//	db.eseguiAggiornamento(stringa);
		
		// Lettura
		String[] conteggio = null;
		String stringa = "Select * from utenti.username";
		Vector<String[]> vettore = db.eseguiQuery(stringa);
		TextFileHandler File = new TextFileHandler();
		File.readFile();
		}
		
		
	//	File.writeFile(vettore);
		
	/*	String[] record;
		if (vettore.size() == 0)
			System.out.println("Tabella vuota");
		int n = 0;
		for(int i=0; i<vettore.size(); i++){
			n++;
			record = vettore.get(i);
			System.out.println(record[0] + " : " + record[1]);
		} 
	//	System.out.println(db.contiene("1938superman", "username")); 
	} */
}
