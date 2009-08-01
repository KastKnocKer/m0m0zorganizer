package database;

public class createRootDB {

	public createRootDB () {
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		
		DatabaseMySql.eseguiAggiornamento("create database root");
		DatabaseMySql.Disconnetti();
		new DatabaseMySql("root");
		DatabaseMySql.connetti();
		
		DatabaseMySql.eseguiAggiornamento("create table root.scansioni (nomeDB Char(10), lista char(10), status Char(5), PRIMARY KEY(nomeDB, lista))");
		makeListeScansione("prima");
		makeListeScansione("seconda");
		makeListeScansione("terza");
		makeListeScansione("quarta");
		makeListeScansione("quinta");
		
		DatabaseMySql.Disconnetti();
	}
	
	public static void makeListeScansione (String database) {
		DatabaseMySql.insert("root", "scansioni", database, "popular", 	 "false");
		DatabaseMySql.insert("root", "scansioni", database, "user", 	 "false");
		DatabaseMySql.insert("root", "scansioni", database, "corrupted", "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce1",   "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce2",   "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce3",   "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce4",   "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce5",   "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce6",   "false");
		DatabaseMySql.insert("root", "scansioni", database, "veloce7",   "false");		
	}
}
