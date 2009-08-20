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
	
	public static boolean insert (String nomeDB, String lista, int values1, String values2,
			String values3) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (" + values1 + " , \"" + values2 + "\" , \"" + values3 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, int values2,
			String values3) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , " + values2 + " , \"" + values3 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2,
			int values3) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , " + values3 + ")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2,
			String values3, String values4) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" , \"" 
				+ values4 + "\")");
	}
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2,
			String values3, int values4) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" , " 
				+ values4 + ")");
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
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2, String values3,
			String values4, String values5 , String values6, String values7) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" ," +
				"\"" + values4 + "\" , \"" + values5 + "\" , \"" + values6 + "\" , \"" + values7 + "\")");
	} 
	
	public static boolean insert (String nomeDB, String lista, String values1, String values2, String values3,
			long values4, long values5 , long values6, String values7) {
		return db.eseguiAggiornamento("insert into " + nomeDB + "." + lista + 
				" values (\"" + values1 + "\" , \"" + values2 + "\" , \"" + values3 + "\" ," +
				"" + values4 + " , " + values5 + " , " + values6 + " , \"" + values7 + "\")");
	}
	
	public static boolean delete (String nomeDB, String lista, String col, String values) {
		return db.eseguiAggiornamento("delete from " + nomeDB + "." + lista + " where " + 
				col + "= '" + values + "'");
	}
	
	public static boolean delete (String nomeDB, String lista, String col1, String values1, String col2, String values2) {
		return db.eseguiAggiornamento("delete from " + nomeDB + "." + lista + " where " + 
				col1 + "= '" + values1 + "' and " + col2 + "= '" + values2 + "'");
	}
	
	public static String[] extract (String nomeDB, String lista, String col) {
		String[] user; 		
		try {
			user =  (DatabaseMySql.eseguiQuery("Select * from " + nomeDB + "." + lista + " limit 1")).get(0);
			if (lista.equals("toCheck"))
				DatabaseMySql.delete (nomeDB, lista, col, user[1]);
			else
				DatabaseMySql.delete (nomeDB, lista, col, user[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
			OutputTxt.writeLog(nomeDB, "Lista " + lista + " analizzata conclusa.");
			return null;
		}  catch (NullPointerException e) {
			System.out.println("LOL");
			return null;
		}
		return user;
		
	}	public static String[] extractCorrupted (String nomeDB) {
		String[] user; 		
		try {
			user =  (DatabaseMySql.eseguiQuery("Select user, tabella from " + nomeDB + ".corruptedList limit 1")).get(0);
			DatabaseMySql.delete (nomeDB, "corruptedList", "user", user[0], "tabella", user[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
			OutputTxt.writeLog(nomeDB, "Lista corruptedList analizzata conclusa.");
			return null;
		}  catch (NullPointerException e) {
			System.out.println("LOL");
			return null;
		}
		return user;
	}
	
	public static String[] extractActiveList (String nomeDB, String colonna) {
		temp = new String[3];
		try {
			if(DatabaseMySql.contiene(nomeDB, "activeList", "priority", "0"))
				temp =  (DatabaseMySql.eseguiQuery("Select * from " + nomeDB + ".activeList group by priority, data limit 1")).get(0);
			else {
				temp =  (DatabaseMySql.eseguiQuery("Select * from " + nomeDB + ".activeList order by RAND() limit 1")).get(0);
			}
			DatabaseMySql.delete (nomeDB, "activeList", colonna, temp[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
			OutputTxt.writeLog(nomeDB, "Lista activeList analizzata conclusa.");
			return null;
		}  catch (NullPointerException e) {
			System.out.println("Null nel extrackActiveList");
			return null;
		}
		return temp;
	}
	
	public static String selectRandomPriority (String nomeDB) {
		Vector<String[]> v = new Vector<String[]>();
		v = DatabaseMySql.eseguiQuery("Select priority from " + nomeDB + ".activeList group by priority");
		temp = new String[v.size()];
		for (i = 0; i < v.size() ; i++) {
			temp[i] = v.get(i)[0];
		}
		return temp[(int) (Math.random()* v.size())];
	}
	
	public static void inserToCheck (String nomeDB, String user) {
		inserToCheck (nomeDB, user, 0);
	}
	
	public static void inserToCheck (String nomeDB, String user, int num) {
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".toCheck values (" + num + " , \"" + user + "\")" +
				" on duplicate key update priority = priority - 1");
		return;
	}
	
	public static void selectVideoToCheck () {
		System.out.println("Selezione dei video da controllare.");
		Vector<String[]> v = new Vector<String[]>();
		v = DatabaseMySql.eseguiQuery("select id from root.videoUploadedBy where id IN (Select id from root.videoToCheck)");
		for (i = 0; i < v.size() ; i++)
				DatabaseMySql.eseguiAggiornamento("Delete from root.videoToCheck where id='" + v.get(i)[0] + "'");
	}
	
	public static void moveUser (String nomeDB, String from, String to, String col, String user) {
		String[] userTemp;
		userTemp = DatabaseMySql.eseguiExtractUser(nomeDB, from, col, user);
		if (userTemp != null)
			DatabaseMySql.insert(nomeDB, to, userTemp[0], userTemp[1], userTemp[2], userTemp[3], userTemp[4]);
	}
		
	public static boolean contiene (String nomeDB, String lista, String colonna, String id ) {
		try {
			(db.eseguiQuery("select " + colonna + " from " + nomeDB + "." + lista + 
				" where " + colonna + "='" + id + "'")).get(0);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public static boolean contiene (String nomeDB, String lista, String colonna1, String id1, String colonna2, String id2 ) {
		try {
			(db.eseguiQuery("select * from " + nomeDB + "." + lista + " where " + colonna1 + "='" + id1 +
					"' and " + colonna2 + "='" + id2 + "'")).get(0);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public static boolean contiene (String nomeDB, String lista, String colonna1, String id1, String colonna2, String id2, String colonna3, String id3 ) {
		try {
			(db.eseguiQuery("select * from " + nomeDB + "." + lista + " where " + colonna1 + "='" + id1 +
					"' and " + colonna2 + "='" + id2 + "' and " + colonna3 + "='" + id3 + "'")).get(0);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public static void copyAttivi (String nomeDB, int scansioneN) {
		Vector<String[]> v = null;
		temp = new String[1];
		if (scansioneN == 1) 
			v = DatabaseMySql.eseguiQuery("Select user,dataScan from " + nomeDB + ".profile where status='active' or status='*active*' or status ='corrupted'");
		else if (scansioneN > 1) {
			v = DatabaseMySql.eseguiQuery("Select * from " + nomeDB + ".active" + (scansioneN - 1));
			for (int i = 0; i < v.size(); i++) {
				temp[0] = v.get(i)[1];
				temp[0] = temp[0].substring(0, 13);
				 if (temp[0].endsWith("00")) {
					temp[0] = temp[0].substring(0, temp[0].indexOf("T") + 1);
					temp[0] = temp[0] + "01:00:01";
					
				}
				else 
					temp[0] = temp[0] + ":00:00";
				DatabaseMySql.insert(nomeDB, "activeList", 0 , v.get(i)[0], temp[0]);
			}
			v = DatabaseMySql.eseguiQuery("Select * from " + nomeDB + ".inactive" + (scansioneN - 1));
		}
		for (int i = 0; i < v.size(); i++) {
			temp[0] = v.get(i)[1];				
			temp[0] = temp[0].substring(0, 13);
			 if (temp[0].endsWith("00")) {
				temp[0] = temp[0].substring(0, temp[0].indexOf("T") + 1);
				temp[0] = temp[0] + "01:00:01";
				
			}
			else 
				temp[0] = temp[0] + ":00:00";
			DatabaseMySql.insert(nomeDB, "activeList", 0 , v.get(i)[0], temp[0]);
		}
	}
	
	public static void copyCorrupted (String nomeDB) {
		Vector<String[]> v = null;
		v = DatabaseMySql.eseguiQuery("Select user,tabella from " + nomeDB + ".infoCorrupted where error='error500+'");
		for (int i = 0; i < v.size(); i++) {
			DatabaseMySql.insert(nomeDB, "corruptedList", v.get(i)[0], v.get(i)[1]);
		}
	}
	
	public static int getMinPriority (String nomeDB) {
		return Integer.parseInt((DatabaseMySql.eseguiQuery("Select min(priority) from " + nomeDB + ".toCheck")).get(0)[0].toString());
	}

	public static void insert500error(String nomeDB, String tabella, String user) {
		DatabaseMySql.delete(nomeDB, "toCheck", "user", user);
		DatabaseMySql.insert(nomeDB, "infoCorrupted", user, tabella, "error500+");
		if(tabella.equals("profile")) {
			DatabaseMySql.insert(nomeDB , "profile", user, "corrupted", Orario.getDataOra(), 0, 0, 0, "profile");
		}
		else
		DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".profile set status='corrupted' where user='" + user + "'");
		System.out.println("InsertError500+ della tabella dei " + tabella + " dell'utente " + user);
	}

	public static void clearUser(String nomeDB, String user) {
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".profile where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".numVideo where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".numFavorites where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".friends where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".subscriptions where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".subscribers where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".activity where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoReserved where user='" + user + "'");		
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
	} 
	
	private static 	String[] temp; 		
	private static 	int i;
}