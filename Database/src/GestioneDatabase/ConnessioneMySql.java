package GestioneDatabase;

import java.util.Vector;
/**
 * Classe fatta da me per utilizzare staticamente il database :O
 * non usa tutti i comandi possibili della classe Database, ma basta aggiungerli :O
 */

public class ConnessioneMySql {
	
	private static Database db = null;
	
	public ConnessioneMySql(){
		db = new Database("NOMEDATABASE","UTENTE","PASSWORD");
		db.setPublicHost("db4free.net:3306");
		//db = new Database("db_newprojectsd","root","mysql");
		//db.setPublicHost("localhost");
	}
	
	public static void connetti(){
		if ( !db.connetti() ) {
			   System.out.println("Errore durante la connessione.");
			   System.out.println( db.getErrore() );
			   System.exit(0);
			}
			
		if( db.isConnesso() )System.out.println("Connessione MySql Effettuata");
			
	}
	
	public static void Disconnetti(){
		db.disconnetti();
		System.out.println("Disconnessione MySql Effettuata");
	}
	
	public static Vector eseguiQuery(String query){
		return db.eseguiQuery(query);
	}
	
	public static boolean eseguiAggiornamento(String query){
		return db.eseguiAggiornamento(query);
	}

}
