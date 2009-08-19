package scansioni;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;

import database.DatabaseMySql;

public class reInsert {

	public static void main(String[] args) {
		String nomeDB, stringa;
		
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		nomeDB = DatabaseMySql.eseguiQuery("Select nomeDB from root.scansioni where completed='false' limit 1").get(0)[0];
		BufferedReader reader = null; // il reader che userò sul file
		try {
			// apro il reader e svuoto la tabella
			reader = new BufferedReader(new FileReader (nomeDB + "Exception"));			
			reader.readLine();
			while ((stringa = reader.readLine()) != null) { // Eventualmente inserire check su un
				if (stringa.contains("Errore nel getErrorCode dell'utente:")) {
					stringa = stringa.substring(stringa.indexOf("utente:") + 8);
					System.out.println("Reinserimento dell'utente: " + stringa);
					DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".profile where user='" + stringa + "'");
					DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".toCheck values ( -9999 , \"" + stringa + "\")" +
						" on duplicate key update priority = priority -9999");
				}
			}
			reader.close();
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		DatabaseMySql.Disconnetti();
	}		
}

