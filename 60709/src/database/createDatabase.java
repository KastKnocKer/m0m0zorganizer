package database;

public class createDatabase {
	
	public createDatabase (String nomeDB) {
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		DatabaseMySql.eseguiAggiornamento("create database " + nomeDB);
		currentDB = nomeDB;
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".key 				(crawler Char(10), devKey char(100), PRIMARY KEY(crawler))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".ethernet 		(rete Char(6), flag char(5), PRIMARY KEY (rete))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".popular 			(user Char(20), data char(19), tipo Char(10), PRIMARY KEY(user, tipo))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".popToCheck 		(user Char(20), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".profile 			(user Char(20) UNIQUE, status char(10), dataScan char (19), subcount int(12), viewcount int(12), videowatch int(12), lastacc char(20), PRIMARY KEY(user, status))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".infoCorrupted 	(user Char(20), tabella char(15), error char(10), PRIMARY KEY (user, tabella))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".toCheck 			(priority int(10), user Char(20) UNIQUE, PRIMARY KEY (priority, user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".friends 			(user Char(20), friend char(20),     totale int(10), PRIMARY KEY (user, friend))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".subscribers 		(user Char(20), subscriber char(20), totale int(10), PRIMARY KEY (user, subscriber))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".subscriptions 	(user Char(20), subscription char(20), published char(19), totale int(10), PRIMARY KEY (user, subscription))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".favorites 		(user Char(20), id Char(11), uploader char(20), published char(19), PRIMARY KEY(user, id))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".numVideo			(user Char(20), totale int(10), data char(19),  PRIMARY KEY(user))");		
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".numFavorites		(user Char(20), totale int(10), data char(19),  PRIMARY KEY(user))");		
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".videoUploadedBy 	(user Char(20), id Char(11), PRIMARY KEY(user, id))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".videoToCheck		(id char(11), PRIMARY KEY (id))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corruptedList	(user Char(20), tabella char(15), PRIMARY KEY (user, tabella))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activeList 		(priority char(10), user Char(20) UNIQUE, data char(19), PRIMARY KEY(priority, user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active1 			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive1 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted1 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity1 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active2 			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive2		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted2 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity2		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active3			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive3 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted3 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity3 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active4			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive4 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted4 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity4 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active5			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive5		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted5 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity5 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active6			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive6		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted6 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity6 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".active7			(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".inactive7		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".corrupted7 		(user Char(20), data char(19), PRIMARY KEY(user))");
		DatabaseMySql.eseguiAggiornamento("create table " + nomeDB + ".activity7 		(user Char(20), id Char(20), action char(20), updated char(19), PRIMARY KEY(user, id, action))");
	}
	
	public static String getCurrentDB () {
		return currentDB;
	}
	
	private static String currentDB;
}
