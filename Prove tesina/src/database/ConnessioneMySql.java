package database;

import java.util.Vector;
/**
 * Classe fatta da me per utilizzare staticamente il database :O
 * non usa tutti i comandi possibili della classe Database, ma basta aggiungerli :O
 */

public class ConnessioneMySql {
	
	private static Database db = null;
	
	public ConnessioneMySql(){
		//db = new Database("NOMEDB","UTENTEDB","PWDDB");
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
	
	public static String[] eseguiQueryToString(String query) {
		Vector<String[]> vettore = db.eseguiQuery(query);
		String[] record = new String[vettore.size()];
		String[] stringa;
		for(int i=0; i<vettore.size(); i++){
			stringa = vettore.get(i);
			record[i] = stringa[0];
		}
		return record;
	}
	
	public static boolean eseguiAggiornamento(String query){
		return db.eseguiAggiornamento(query);
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