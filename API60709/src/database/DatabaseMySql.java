package database;

import java.util.Vector;
/**
 * Classe fatta da me per utilizzare staticamente il database :O
 * non usa tutti i comandi possibili della classe Database, ma basta aggiungerli :O
 */
 
public class DatabaseMySql {
	
	private static Database db = null;
	
	public DatabaseMySql(String nomeDB){
		//db = new Database("Nome DB","Utente DB","Pass DB");
		//db.setPublicHost("db4free.net:3306");
		db = new Database(nomeDB ,"root","dumdadum");
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
		String[] userTemp = null;
		try { 
			userTemp = (DatabaseMySql.eseguiQuery("Select * from " + nomeDB + "." + lista + " where user='" +
					user + "' limit 1")).get(0);
			DatabaseMySql.delete (nomeDB, lista, col, user);
		}
		catch (ArrayIndexOutOfBoundsException e) {}
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
		String[] temp = (DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + "." + tabella)).get(0);
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
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2, String values3,
			String values4, String values5 , String values6) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" ," +
				"\"" + values4 + "\" , \"" + values5 + "\" , \"" + values6 + "\")");
	}
	
	public static boolean delete (String nomeDB, String lista, String col, String values) {
		return db.eseguiAggiornamento("delete from " + nomeDB + "." + lista + " where " + 
				col + "= '" + values + "'");
	}
	
	public static String[] extract (String nomeDB, String lista, String col) {
		String[] user; 		
		try {
			user =  (DatabaseMySql.eseguiQuery("Select * from " + nomeDB + "." + lista + " limit 1")).get(0);
			if (lista.equals("toCheck"))
				DatabaseMySql.delete (nomeDB, lista, col, user[1]);
			else
				DatabaseMySql.delete (nomeDB, lista, col, user[0]);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			OutputTxt.writeException(e.getLocalizedMessage());
			OutputTxt.writeLog("Lista analizzata conclusa.");
			return null;
		}
		return user;
	}
	
	public static void inserToCheck (String nomeDB, String user) {
		inserToCheck (nomeDB, user, 0);
	}
	
	public static void inserToCheck (String nomeDB, String user, int num) {
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".toCheck values (\"" + num + "\" , \"" + user + "\") on duplicate key update priority = priority - 1");
		return;
	}
	
	public static void moveUser (String nomeDB, String from, String to, String col, String user) {
		String[] userTemp;
		userTemp = DatabaseMySql.eseguiExtractUser(nomeDB, from, col, user);
		if (userTemp != null)
			DatabaseMySql.insert(nomeDB, to, userTemp[0], userTemp[1], userTemp[2], userTemp[3], userTemp[4]);
	}
		
	public static boolean contiene (String nomeDB, String lista, String user ) {
		try {
			(db.eseguiQuery("select * from " + nomeDB + "." + lista + 
				" where user = '" + user + "'")).get(0);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public static int getMinPriority () {
		return Integer.parseInt((DatabaseMySql.eseguiQuery("Select min(priority) from utenti.toCheck")).get(0)[0].toString());
	}
}