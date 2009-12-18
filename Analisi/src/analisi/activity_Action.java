package analisi;

import java.util.Vector;

import Database.DatabaseMySql;

public class activity_Action {

	private static String[] start;
	private static Vector<String[]> vector;
	private static String uploaderStart;
	private static int n,z ;
	private static int[][] count;
			
	public static void action ()  {
		count = new int[3][3];
		start = new String[5];
		z = 0;
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		while ((start = DatabaseMySql.extract("totale", "activityTemp3", "num")) != null) {
			for (int x = 0; x < 3; x++)
				for (int y = 0; y < 3; y++)
					count[x][y] = 0;
			
			if(start[3].equals("rated"))
				n = 0;
			else if(start[3].equals("favorited"))
				n = 1;
			else if(start[3].equals("commented"))
				n = 2;			
			System.out.println("Analisi action: " + ++z);
			
			uploaderStart = DatabaseMySql.eseguiQuery("Select user from root.videoUploadedBy where id='" + start[2] + "'").get(0)[0];
			if (uploaderStart.equals("*VideoRimosso*"))
				continue;
			vector = DatabaseMySql.eseguiQuery("Select user, id, action, updated from totale.activity " +
					"where user='" + start[1] + "' and id='" + uploaderStart + "'");
			while (vector.size() != 0) {
				if (vector.elementAt(0)[3].compareTo(start[4]) > 0) {
					count[n][2]++;
					if (vector.elementAt(0)[2].equals("friended"))
						count[n][0]++;
					else if (vector.elementAt(0)[2].equals("subscribed"))
						count[n][1]++;
				}
				vector.removeElementAt(0);
			}
			vector = DatabaseMySql.eseguiQuery("Select user, id, action, updated from totale.activity " +
					"where user='" + uploaderStart + "' and id='" + start[1] + "' and action='friended'");
			while (vector.size() != 0) {
				if (vector.elementAt(0)[3].compareTo(start[4]) > 0) {
					count[n][2]++;
					if (vector.elementAt(0)[2].equals("friended"))
						count[n][0]++;
				}
				vector.removeElementAt(0);
			}
			DatabaseMySql.eseguiAggiornamento("Insert into totale.activityAction2 values ('" + start[1] + "', '" + start[2] + "', '" + start[3] + "', '" + start[4] + "', '" + uploaderStart + "'" +
					", " + count[0][0] + ", " + count[0][1] + ", " + count[0][2] + 
					", " + count[1][0] + ", " + count[1][1] + ", " + count[1][2] +
					", " + count[2][0] + ", " + count[2][1] + ", " + count[2][2] + ")");
			if (DatabaseMySql.contiene("root", "config", "id", "figlio", "status", "off")) {
				System.out.println("Blocco utente");
				System.exit(0);
			}
		}
	}
}
