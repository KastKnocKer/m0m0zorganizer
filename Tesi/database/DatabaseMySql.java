package database;

import java.util.Vector;
/**
 * Classe fatta da me per utilizzare staticamente il database :O
 * non usa tutti i comandi possibili della classe Database, ma basta aggiungerli :O
 */

public class DatabaseMySql {
	
	private static Database db = null;
	
	public DatabaseMySql(){
		//db = new Database("Nome DB","Utente DB","Pass DB");
		//db.setPublicHost("db4free.net:3306");
		db = new Database("utenti","root","dumdadum");
		db.setPublicHost("localhost:3306");
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
	
	public static boolean insert (String nomeDB, String lista, String values) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1 , String values2 ) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\")");
	}
	
	public static boolean delete (String nomeDB, String lista, String col, String values) {
		return db.eseguiAggiornamento("delete from " + nomeDB + "." + lista + " where " + 
				col + "= '" + values + "'");
	}
		
	public static boolean contiene (String user, String lista) {
		Vector<String[]> vettore = db.eseguiQuery("select * from utenti." + lista + " where user = '" + user + "'");
		String[] record;
		for(int i=0; i<vettore.size(); i++){
			record = vettore.get(i);
			if (record[0].equals(user))
				return true;
		}
		return false;
	}

}