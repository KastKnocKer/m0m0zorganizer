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
	
	public static Vector<String[]> eseguiQuery(String query){
		return db.eseguiQuery(query);
	}
	
	public static String[] eseguiExtractUser(String nomeDB, String lista, String col, String user){
		Vector<String[]> vettore;
		String[] userTemp = null;
		vettore =  DatabaseMySql.eseguiQuery("Select * from " + nomeDB + "." + lista + " where user='" +
				user + "' limit 1");
		try { 
			userTemp = vettore.get(0);
		}
		catch (ArrayIndexOutOfBoundsException e) {}
		DatabaseMySql.delete (nomeDB, lista, col, user);
		return userTemp;
	}
	
	
	public static boolean eseguiAggiornamento(String query){
		return db.eseguiAggiornamento(query);
	}	   
	
	public static boolean insert (String nomeDB, String lista, String values) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values + "\")");
	}
	
	
	public static int getCount (String nomeDB, String tabella) {
		Vector<String[]> tempVect;
		String[] temp;
		tempVect = DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + "." + tabella);
		temp = tempVect.get(0);
		return Integer.parseInt(temp[0]);
		}
	
	public static boolean insert (String nomeDB, String lista, String values1 , String values2) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1 , int num) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , " + num + ")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2,
			String values3) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2,
			String values3, String values4) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" , \"" 
				+ values4 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2,
			String values3, String values4, String values5) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" , \""
				+ values4 + "\" , \"" + values5 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, Long values2,
			Long values3, Long values4 , String values5) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" ," +
						" \"" + values4 + "\" , \"" + values5 + "\")");
	}
	
	public static boolean delete (String nomeDB, String lista, String col, String values) {
		return db.eseguiAggiornamento("delete from " + nomeDB + "." + lista + " where " + 
				col + "= '" + values + "'");
	}
	
	public static String[] extract (String nomeDB, String lista, String col) {
		Vector<String[]> vettore;
		String[] user; 
		if (lista.equals("toCheck"))
			vettore =  DatabaseMySql.eseguiQuery("Select * from " + nomeDB + "." + lista + " ORDER BY priority DESC limit 1");
		else
			vettore =  DatabaseMySql.eseguiQuery("Select * from " + nomeDB + "." + lista + " limit 1");
		try {
			user = vettore.get(0);
		}
      catch (ArrayIndexOutOfBoundsException e) {
    	  OutputTxt.writeException(e.getLocalizedMessage());
    	  OutputTxt.writeLog("Lista analizzata conclusa.");
    	  return null;
      }
      DatabaseMySql.delete (nomeDB, lista, col, user[0]);
      return user;
	}
	
	public static void inserToCheck (String nomeDB, String user) {
		inserToCheck (nomeDB, user, 0);
	}
	
	public static void inserToCheck (String nomeDB, String user, int num) {
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".toCheck values (\"" + user + "\" , "
				+ num + ") on duplicate key update priority = priority + 1"); 	
	}
	
	public static void moveUser (String nomeDB, String from, String to, String col, String user) {
		String[] userTemp;
		userTemp = DatabaseMySql.eseguiExtractUser(nomeDB, from, col, user);
		if (userTemp != null)
			DatabaseMySql.insert(nomeDB, to, userTemp[0], userTemp[1], userTemp[2], userTemp[3], userTemp[4]);
	}
		
	public static boolean contiene (String nomeDB, String lista, String user ) {
		Vector<String[]> vettore = db.eseguiQuery("select * from " + nomeDB + "." + lista + 
				" where user = '" + user + "'");
		String[] record;
		for(int i=0; i<vettore.size(); i++){
			record = vettore.get(i);
			if (record[0].equals(user))
				return true;
		}
		return false;
	}
	
	public static boolean checkUserDB (String nomeDB, String user) {
		if 	(contiene(nomeDB ,"active", user)) {
			System.out.println("Errore: utente " + user + " scartato perchè già presente nei active");
			return true;
		}
		else if (contiene(nomeDB, "inactive", user)) {
			System.out.println("Errore: utente " + user + " scartato perchè già presente nei inactive");
			return true;
		}
		else if (contiene(nomeDB , "blocked", user)) {
			System.out.println("Errore: utente " + user + " scartato perchè già presente nei blocked");
			return true;
		}
		return false;
	}
}