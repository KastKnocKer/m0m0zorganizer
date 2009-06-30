package prove;

import java.util.Vector;

import database.DatabaseMySql;
import download.urlReader;

public class Scriptdownload_iterativo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String user;
		new DatabaseMySql();
		DatabaseMySql.connetti();
	//	urlReader.friendCount("hereforyou13", db);
	//	urlReader.friendReader("hereforyou13", 0 , db);

		for ( ; (user =  DatabaseMySql.extract("utenti" , "contatti", "user")) != null ;) {
			if (urlReader.friendCount(user))  {
			//	urlReader.friendReader(user);
			}
			else {
				DatabaseMySql.insert("utenti" , "bloccati" , user);
				System.out.println("UTENTE BLOCCATOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			}			
		} 
			
	}
	
	

}