package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;

public class scanActivity {
	
	public scanActivity (YouTubeService myService, String devKey, String nomeDB, int scansioneN) {
		try {
			temp = 0;
			while ((count = DatabaseMySql.getCount(nomeDB, "activeList")) != 0) {
				userTemp = "";
				data = DatabaseMySql.eseguiQuery("Select min(data) from " + nomeDB + ".activeList").get(0)[0];
				if(DatabaseMySql.contiene(nomeDB, "activeList", "priority", "0")) {
					n = Integer.parseInt(DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".activeList " +
							"where data ='" + data + "'").get(0)[0]);
					if (n > 20)
						n = 20;
				}	
				else if (count >= 400)
					n = 20;	
				else if (count < 400)
					n = count / 50;	
				else if (count < 250)
					n = 2;		
				else 
					n = 1;
				users = new String[n][3];
				for (i = 0; i < n; i++) {
					users[i] = DatabaseMySql.extractActiveList(nomeDB, "user");
					System.out.println((i + 1) + ": " +users[i][1]);
					if (i == 0)
						userTemp = users[0][1];
					else 
						userTemp = userTemp + "," + users[i][1];
				}
				//System.out.println("Scansione activity degli utenti " + userTemp);
				if (API.getActivity(myService, devKey, nomeDB, userTemp, 0, 0, data, scansioneN)) {
					for (i = 0; i < n; i++) {
						if(!DatabaseMySql.contiene(nomeDB, "active" + scansioneN, "user", users[i][1])) 
							DatabaseMySql.insert(nomeDB, "inactive" + scansioneN, users[i][1], Orario.getDataOra());
					}
				}
				else  {
					if (n == 1) {
						DatabaseMySql.insert(nomeDB, "corrupted" + scansioneN, users[0][1], Orario.getDataOra());
					}
					else {
						for (i = 0; i < n; i++) 
							DatabaseMySql.insert(nomeDB, "activeList", "1" , users[i][1], users[i][2]);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {e.printStackTrace();}
						OutputTxt.writeError(nomeDB, "ERRORE 500 per users" + userTemp);
					}
				}
				System.out.println("RICHIESTA ACTIVITY NUMERO: " + temp);
				if (++temp == 150) {					
					return;
				}
			}
		} catch (NullPointerException e) {System.out.println("Lista activeList terminata.");}
	}
			
	private static String [][] users;
	private static String userTemp, data;
	private static int n, i, temp, count;
}
