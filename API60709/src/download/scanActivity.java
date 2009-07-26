package download;

import com.google.gdata.client.youtube.YouTubeService;

import database.DatabaseMySql;

public class scanActivity {
	
	public scanActivity (YouTubeService myService, String devKey, String nomeDB, String data, int scansioneN) {
		temp = 0;
		while ((n = DatabaseMySql.getCount(nomeDB, "activeList")) != 0) {
			if (n > 20)
				n = 20;
			users = new String[n];
			userTemp = "";
			for (i = 0; i < n; i++) {
				users[i] = DatabaseMySql.extract(nomeDB, "activeList", "user")[0];
				if (i == 0)
					userTemp = users[i++];
				else 
					userTemp = userTemp + "," + users[i++];
			}
			System.out.println("Scansione activity degli utenti " + users);
			if (!API.getActivity(myService, devKey, nomeDB, userTemp, 0, 0, data, scansioneN)) {
				for (i = 0; i < n; i++) {
					if(!DatabaseMySql.contiene(nomeDB, "active" + scansioneN, users[i])) 
						DatabaseMySql.insert(nomeDB, "inactive" + scansioneN, users[i], data);
				}
			}
			if (++temp == 50)
				return;
		}
	}
			
	private static String [] users;
	private static String userTemp;
	private static int n, i, temp;
}
