package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;

public class scanCorrupted {

	public static void controlloError (YouTubeService myService, String devKey, String nomeDB) {
		while (DatabaseMySql.getCount(nomeDB, "copyCorrupted") != 0) {
			user = DatabaseMySql.extract(nomeDB, "copyCorrupted", "user");
			if (user[1].equals("favorites")) {
				DatabaseMySql.eseguiAggiornamento("Delete from utenti.infoCorrupted where user='" + user[0] + 
				"' and tabella='favorites'");
				API.getFavorites(myService, devKey, nomeDB, user[0]);
			}
			else if (user[1].equals("subscriptions")) {
				DatabaseMySql.eseguiAggiornamento("Delete from utenti.infoCorrupted where user='" + user[0] + 
						"' and tabella='subscriptions'");
				API.getSubscriptions(myService, devKey, nomeDB, user[0]);
			}
			else if (user[1].equals("activity")) {
				DatabaseMySql.eseguiAggiornamento("Delete from utenti.profile where user='" + user[0] + "'");
				if (API.getActivity(myService, devKey, nomeDB, user[0])) {	// Ha activityFeed? 
					if (API.getUser(myService, devKey, "active", nomeDB, user[0])) { 	// E' un utente sospeso?  No --> active
						API.getSubscriptions(myService, devKey, nomeDB, user[0]);
						API.getFavorites(myService, devKey, nomeDB, user[0]);
						urlReader.userReader(nomeDB, "subscribers", user[0]);	
						urlReader.userReader(nomeDB, "friends", user[0]);
						API.getVideo(myService, devKey, nomeDB, user[0]);
					}
				}
			}
			else if (user[1].equals("profile")) {
				DatabaseMySql.eseguiAggiornamento("Delete from utenti.profile where user='" + user[0] + "'");
				if (API.getActivity(myService, devKey, nomeDB, user[0])) {	// Ha activityFeed? 
					if (API.getUser(myService, devKey, "active", nomeDB, user[0])) { 	// E' un utente sospeso?  No --> active
						API.getSubscriptions(myService, devKey, nomeDB, user[0]);
						API.getFavorites(myService, devKey, nomeDB, user[0]);
						urlReader.userReader(nomeDB, "subscribers", user[0]);	
						urlReader.userReader(nomeDB, "friends", user[0]);
						API.getVideo(myService, devKey, nomeDB, user[0]);;
					}
				}
			}			
			else if (user[1].equals("friends")) {
				DatabaseMySql.eseguiAggiornamento("Delete from utenti.infoCorrupted where user='" + user[0] + 
				"' and tabella='friends'");
				urlReader.userReader(nomeDB, "friends" , user[0]);
			}
			else if (user[1].equals("subscribers")) {
				DatabaseMySql.eseguiAggiornamento("Delete from utenti.infoCorrupted where user='" + user[0] + 
				"' and tabella='subscribers'");
				urlReader.userReader(nomeDB, "subscribers" , user[0]);
			}
			if (!DatabaseMySql.contiene(nomeDB, "infoCorrupted", "user", user[0], "error", "error500+")) {
				DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".profile set status='*active* where user='"
						+ user[0]	+ "'");
			}
		}
	}
	
	static private String[] user;
}
