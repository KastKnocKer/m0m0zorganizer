package prove;

import java.util.Vector;

import database.DatabaseMySql;
import download.urlReader;

public class Scriptdownload_iterativo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector<String[]> vettore;
		String[] user;
		DatabaseMySql db = new DatabaseMySql();
		DatabaseMySql.connetti();
	//	urlReader.friendCount("hereforyou13", db);
	//	urlReader.friendReader("hereforyou13", 0 , db);

		for ( ; (vettore =  db.eseguiQuery("Select * from utenti.contatti limit 1")) != null ;) {
			user = vettore.get(0);
			DatabaseMySql.delete("utenti" , "contatti" ,"user" , user[0]);
			System.out.println("Cancellato dalla lista dei contatti");
			if (urlReader.friendCount(user[0], db))  {
			//	urlReader.friendReader(user[0], 0, db);
			}
			else {
				DatabaseMySql.insert("utenti" , "bloccati" , user[0]);
				System.out.println("UTENTE BLOCCATOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			}			
		} 
			
	}
	
	

}