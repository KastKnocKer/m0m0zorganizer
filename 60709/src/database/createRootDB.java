package database;

import java.sql.SQLException;

public class createRootDB {

	public createRootDB () {
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		
		DatabaseMySql.eseguiAggiornamento("Drop database root");
		DatabaseMySql.eseguiAggiornamento("Drop database prima");
		DatabaseMySql.eseguiAggiornamento("Drop database seconda");
		DatabaseMySql.eseguiAggiornamento("Drop database terza");
		DatabaseMySql.eseguiAggiornamento("Drop database quarta");
		DatabaseMySql.eseguiAggiornamento("Drop database quinta");
				
		DatabaseMySql.eseguiAggiornamento("create database root");				
		DatabaseMySql.eseguiAggiornamento("create table root.scansioni 	(nomeDB Char(10), lista char(10), completed Char(5), inizio Char(19), fine Char(19), PRIMARY KEY(nomeDB, lista))");
		
		makeListeScansione("prima");
		new createDatabase ("prima");
		makeListeScansione("seconda");
		new createDatabase ("seconda");
		makeListeScansione("terza");
		new createDatabase ("terza");
		makeListeScansione("quarta");
		new createDatabase ("quarta");
		makeListeScansione("quinta");
		new createDatabase ("quinta");
		
		DatabaseMySql.Disconnetti();
	}
	
	public static void makeListeScansione (String database) {
		DatabaseMySql.insert("root", "scansioni", database, "popular", 	 "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "user", 	 "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "corrupted", "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce1",   "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce2",   "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce3",   "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce4",   "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce5",   "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce6",   "false", "toDefine", "toDefine");
		DatabaseMySql.insert("root", "scansioni", database, "veloce7",   "false", "toDefine", "toDefine");		
	}
}
