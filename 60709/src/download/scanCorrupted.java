package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;

public class scanCorrupted {

	public scanCorrupted (YouTubeService myService, String devKey, String nomeDB) {
		int count = 0;
		while (DatabaseMySql.getCount(nomeDB, "corruptedList") != 0) {
			System.out.println(++count);
			user = DatabaseMySql.extractCorrupted(nomeDB);
			if (user[1].equals("favorites")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
				"' and tabella='favorites'");
				API.getFavorites(myService, devKey, nomeDB, user[0]);
			}
			else if (user[1].equals("subscriptions")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
						"' and tabella='subscriptions'");
				API.getSubscriptions(myService, devKey, nomeDB, user[0]);
			}
			else if (user[1].equals("activity")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
				"' and tabella='activity'");
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".profile where user='" + user[0] + "'");
				if (API.getActivity(myService, devKey, nomeDB, user[0])) {	// Ha activityFeed?
					count++;
					if (API.getUser(myService, devKey, "active", nomeDB, user[0])) { 	// E' un utente sospeso?  No --> active
						count++;
						API.getSubscriptions(myService, devKey, nomeDB, user[0]);
						count++;
						urlReader.userReader(nomeDB, "subscribers", user[0]);
						count++;
						API.getFavorites(myService, devKey, nomeDB, user[0]);
						count++;
						urlReader.userReader(nomeDB, "friends", user[0]);
						count++;
						API.getVideo(myService, devKey, nomeDB, user[0]);;
						count++;
					}
				}
				else 
					API.getUser(myService, devKey, "inactive", nomeDB, user[0]);
			}
			else if (user[1].equals("profile")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
				"' and tabella='profile'");
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".profile where user='" + user[0] + "'");
				if (API.getActivity(myService, devKey, nomeDB, user[0])) {	// Ha activityFeed?
					count++;
					if (API.getUser(myService, devKey, "active", nomeDB, user[0])) { 	// E' un utente sospeso?  No --> active
						count++;
						API.getSubscriptions(myService, devKey, nomeDB, user[0]);
						count++;
						urlReader.userReader(nomeDB, "subscribers", user[0]);
						count++;
						API.getFavorites(myService, devKey, nomeDB, user[0]);
						count++;
						urlReader.userReader(nomeDB, "friends", user[0]);
						count++;
						API.getVideo(myService, devKey, nomeDB, user[0]);;
						count++;
					}
				}
				else 
					API.getUser(myService, devKey, "inactive", nomeDB, user[0]);
			}
			else if (user[1].equals("friends")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
				"' and tabella='friends'");
				urlReader.userReader(nomeDB, "friends" , user[0]);
			}
			else if (user[1].equals("subscribers")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
				"' and tabella='subscribers'");
				urlReader.userReader(nomeDB, "subscribers" , user[0]);
			}
			if (!DatabaseMySql.contiene(nomeDB, "infoCorrupted", "user", user[0])) {
				DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".profile set status='*active*' where user='"
						+ user[0]	+ "'");
			}
			if (count == 100)
				return;
		}
	}
	
	static private String[] user;
}
