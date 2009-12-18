package analisi;

import java.util.Vector;

import Database.DatabaseMySql;

public class activity_Upload {

		private static String[] id;
		private static Vector<String[]> activity;
		private static int n;
		private static int[] stat;
				
		public static void main(String[] args) {
			stat = new int[12];
			n = 0;
			new DatabaseMySql("root");		// Definisco il database per tutto il programma
			DatabaseMySql.connetti();	// Connessione al database
			while ((id = DatabaseMySql.extract("totale", "activityTemp", "id")) != null) {
				for (int i = 0; i < stat.length; i++)
					stat[i] = 0;
				System.out.println(++n + ": Analisi id: " + id[0]);
				activity = DatabaseMySql.eseguiQuery("Select * from totale.activity where id ='" + id[0] + "'");
				DatabaseMySql.eseguiAggiornamento("Update totale.activityUpload set totalAction='" + (activity.size() - 1) + "' where id='" + id[0] + "'");
				while (activity.size() != 0) {		
					System.out.println("Action: " + activity.elementAt(0)[2]);
					if (activity.elementAt(0)[2].equals("uploaded")) {
							System.out.println("Salto per uploaded");
					}
					else if (activity.elementAt(0)[2].equals("commented")) {
						if (DatabaseMySql.contiene("totale", "friends", "user", id[1] , "friend", activity.elementAt(0)[0]))
							stat[0]++;
						else if (DatabaseMySql.contiene("totale", "friends", "user", activity.elementAt(0)[0] , "friend", id[1]))
							stat[0]++;
						else if (DatabaseMySql.contiene("totale", "subscribers", "user", id[1] , "subscriber", activity.elementAt(0)[0]))
							stat[3]++;
						else if (DatabaseMySql.contiene("totale", "subscriptions", "user", activity.elementAt(0)[0] , "subscription", id[1]))
							stat[3]++;
						else if (DatabaseMySql.contiene("totale", "subscriptions", "user", id[1] , "subscription", activity.elementAt(0)[0]))
							stat[6]++;
						else if (DatabaseMySql.contiene("totale", "subscribers", "user", activity.elementAt(0)[0] , "subscriber", id[1]))
							stat[6]++;
						else
							stat[9]++;
					}
					else if (activity.elementAt(0)[2].equals("rated")) {
						if (DatabaseMySql.contiene("totale", "friends", "user", id[1] , "friend", activity.elementAt(0)[0]))
							stat[1]++;
						else if (DatabaseMySql.contiene("totale", "friends", "user", activity.elementAt(0)[0] , "friend", id[1]))
							stat[1]++;
						else if (DatabaseMySql.contiene("totale", "subscribers", "user", id[1] , "subscriber", activity.elementAt(0)[0]))
							stat[4]++;
						else if (DatabaseMySql.contiene("totale", "subscriptions", "user", activity.elementAt(0)[0] , "subscription", id[1]))
							stat[4]++;
						else if (DatabaseMySql.contiene("totale", "subscriptions", "user", id[1] , "subscription", activity.elementAt(0)[0]))
							stat[7]++;
						else if (DatabaseMySql.contiene("totale", "subscribers", "user", activity.elementAt(0)[0] , "subscriber", id[1]))
							stat[7]++;
						else
							stat[10]++;
					}
					else if (activity.elementAt(0)[2].equals("favorited")) {
						if (DatabaseMySql.contiene("totale", "friends", "user", id[1] , "friend", activity.elementAt(0)[0]))
							stat[2]++;
						else if (DatabaseMySql.contiene("totale", "friends", "user", activity.elementAt(0)[0] , "friend", id[1]))
							stat[2]++;
						else if (DatabaseMySql.contiene("totale", "subscribers", "user", id[1] , "subscriber", activity.elementAt(0)[0]))
							stat[5]++;
						else if (DatabaseMySql.contiene("totale", "subscriptions", "user", activity.elementAt(0)[0] , "subscription", id[1]))
							stat[5]++;
						else if (DatabaseMySql.contiene("totale", "subscriptions", "user", id[1] , "subscription", activity.elementAt(0)[0]))
							stat[8]++;
						else if (DatabaseMySql.contiene("totale", "subscribers", "user", activity.elementAt(0)[0] , "subscriber", id[1]))
							stat[8]++;
						else
							stat[11]++;
					}
					activity.removeElementAt(0);
				}
				DatabaseMySql.eseguiAggiornamento("Update totale.activityUpload set commentiFriend='" + stat[0] + 
						"' , rateFriend='" + stat[1] +
						"' , favoriteFriend='" + stat[2] +
						"' , commentiSubscriber='" + stat[3] +
						"' , rateSubscriber='" + stat[4] +
						"' , favoriteSubscriber='" + stat[5] +
						"' , commentiSubscription='" + stat[6] +
						"' , rateSubscription='" + stat[7] +
						"' , favoriteSubscription='" + stat[8] +
						"' , commentiNoRel='" + stat[9] +
						"' , rateNoRel='" + stat[10] +
						"' , favoriteNoRel='" + stat[11] +
						"' where id ='" + id[0] + "'");
				if (DatabaseMySql.contiene("root", "config", "id", "figlio", "status", "off")) {
					System.out.println("Blocco utente");
					System.exit(0);
				}
			}
		}
}
