package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.OutputTxt;

public class scanActivity {
	
	public scanActivity (YouTubeService myService, String devKey, String nomeDB, int scansioneN) {
		try {
			try {
				error = Integer.parseInt(DatabaseMySql.eseguiQuery("Select MAX(priority) from " + nomeDB + ".activeList").get(0)[0]);
			} catch (NullPointerException e) { error = 0;}
			temp = 0;
			while (DatabaseMySql.getCount(nomeDB, "activeList") != 0) {
				n = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".activeList where data = (" +
						"select min(data) from " + nomeDB + ".activeList)").get(0)[0]);
				if (n > 20)
					n = 20;
				users = new String[n][3];
				userTemp = "";
				for (i = 0; i < n; i++) {
					users[i] = DatabaseMySql.extractActiveList(nomeDB, "user");
					if (i == 0)
						userTemp = users[0][1];
					else 
						userTemp = userTemp + "," + users[i][1];
				}
				//System.out.println("Scansione activity degli utenti " + userTemp);
				data = DatabaseMySql.eseguiQuery("Select min(dataScan) from " + nomeDB + ".activeList").get(0)[0];
				if (API.getActivity(myService, devKey, nomeDB, userTemp, 0, 0, data, scansioneN)) {
					for (i = 0; i < n; i++) {
						if(!DatabaseMySql.contiene(nomeDB, "active" + scansioneN, "user", users[i][1])) 
							DatabaseMySql.insert(nomeDB, "inactive" + scansioneN, users[i][1], data);
					}
				}
				else  {
					for (i = 0; i < n; i++) 
						DatabaseMySql.insert(nomeDB, "activeList", error, users[i][1], users[i][2]);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {e.printStackTrace();}
					OutputTxt.writeError("ERRORE 500 per users" + userTemp);
				}
				if (++temp == 1)
					return;
			}
		} catch (NullPointerException e) {System.out.println("Lista activeList terminata.");}
	}
			
	private static String [][] users;
	private static String userTemp, data;
	private static int n, i, temp, error;
}
