package analisi;

import java.util.Vector;

import Database.DatabaseMySql;

public class activity_Relation {
	
	private static String[] start;
	private static Vector<String[]> vector;
//	private static String uploaderStart;
	private static int n, z;
	private static int[][] count;
			
	public static void relation () {
		count = new int[2][4];
		start = new String[5];
		z = 0;
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		while ((start = DatabaseMySql.extract("totale", "activityTemp2", "num")) != null) {
			for (int x = 0; x < 2; x++)
				for (int y = 0; y < 4; y++)
					count[x][y] = 0;
			System.out.println("Analisi relation: " + ++z);
			if (start[3].equals("friended"))
				n = 0;
			else if (start[3].equals("subscribed"))
				n = 1;
			vector = DatabaseMySql.eseguiQuery("Select user, id, action, updated from totale.activity where user='" + start[1] + "' " +
					"and (action='commented' or action='favorited' or action='rated')");
			while (vector.size() != 0) {
				if(DatabaseMySql.contiene("root", "videoUploadedBy", "user", start[2], "id", vector.elementAt(0)[1])) {
					if (vector.elementAt(0)[3].compareTo(start[4]) > 0) {
						count[n][3]++;
						if (vector.elementAt(0)[2].equals("commented"))
							count[n][0]++;
						else if (vector.elementAt(0)[2].equals("rated"))
							count[n][1]++;
						else if (vector.elementAt(0)[2].equals("favorited"))
							count[n][2]++;
					}
				}
				vector.removeElementAt(0);
			}
			DatabaseMySql.eseguiAggiornamento("Insert into totale.activityRelation2 values ('" + start[1] + "', '" + start[2] + "', '" + start[3] + "', '" + start[4] + "'" +
					", " + count[0][0] + ", " + count[0][1] + ", " + count[0][2] + ", " + count[0][3] + 
					", " + count[1][0] + ", " + count[1][1] + ", " + count[1][2] + ", " + count[1][3] + ")");
			if (DatabaseMySql.contiene("root", "config", "id", "figlio", "status", "off")) {
				System.out.println("Blocco utente");
				System.exit(0);
			}
		}
	}
}
