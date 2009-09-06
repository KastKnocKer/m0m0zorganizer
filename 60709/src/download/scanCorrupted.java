package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.Orario;

public class scanCorrupted {

	public scanCorrupted (YouTubeService myService, String devKey, String nomeDB) {
		while (DatabaseMySql.getCount(nomeDB, "corruptedList") != 0) {
			user = DatabaseMySql.extractCorrupted(nomeDB);
			System.out.println(++count);
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
					if (API.getUser(myService, devKey, "active", nomeDB, user[0])) { 	// E' un utente sospeso?  No --> active
						API.getSubscriptions(myService, devKey, nomeDB, user[0]);
						urlReader.userReader(nomeDB, "subscribers", user[0]);
						API.getFavorites(myService, devKey, nomeDB, user[0]);
						urlReader.userReader(nomeDB, "friends", user[0]);
						API.getVideo(myService, devKey, nomeDB, user[0]);;
					}
				}
				else {
					API.getUser(myService, devKey, "inactive", nomeDB, user[0]);
				}
			}
			else if (user[1].equals("profile")) {
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".infoCorrupted where user='" + user[0] + 
					"' and tabella='profile'");
				DatabaseMySql.eseguiAggiornamento("Delete from " + nomeDB + ".profile where user='" + user[0] + "'");
				if (API.getActivity(myService, devKey, nomeDB, user[0])) {	// Ha activityFeed?
					if (API.getUser(myService, devKey, "active", nomeDB, user[0])) { 	// E' un utente sospeso?  No --> active
						API.getSubscriptions(myService, devKey, nomeDB, user[0]);
						urlReader.userReader(nomeDB, "subscribers", user[0]);
						API.getFavorites(myService, devKey, nomeDB, user[0]);
						urlReader.userReader(nomeDB, "friends", user[0]);
						API.getVideo(myService, devKey, nomeDB, user[0]);;
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
				DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".profile set lastacc='" + Orario.getDataOra() + "' where user='"
						+ user[0]	+ "'");
			}
			if (count == 200)
				return;
			System.out.println("COUNT ARRIVATO A : " + count);
		}
	}
	
	static private String[] user;
	static int count;
}
