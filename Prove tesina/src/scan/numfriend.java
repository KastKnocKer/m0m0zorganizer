package scan;

import java.util.Vector;

import database.ConnessioneMySql;
import download.urlReader;

public class numfriend {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector<String[]> vettore;
		String[] user;
		ConnessioneMySql db = new ConnessioneMySql();
		ConnessioneMySql.connetti();
		urlReader.friendCount("BarackObamadotcom", db);
		urlReader.friendReader("BarackObamadotcom", 0 , db);

	/*	for ( ; (vettore =  db.eseguiQuery("Select * from utenti.contatti limit 1")) != null ;) {
			user = vettore.get(0);
			ConnessioneMySql.eseguiAggiornamento("delete from utenti.contatti where user= '" + user[0] + "'");
			System.out.println("Cancellato dalla lista dei contatti");
			if (urlReader.friendCount(user[0], db))  {
				urlReader.friendReader(user[0], 0, db);
			}
			else {
				ConnessioneMySql.eseguiAggiornamento("insert into utenti.bloccati values (\"" + user[0] + "\")");
				System.out.println("UTENTE BLOCCATOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			}			
		} */
			
	}
	
	
	public static void scanFriend (String user) {
		
	}
}
