package prove;

import scansioni.padre;
import database.DatabaseMySql;
import database.Orario;
import database.createRootDB;
import download.popularReader;


public class provevideo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DatabaseMySql("root");
		DatabaseMySql.connetti();
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='" + Orario.getDataOra() + 
				"' where nomeDB='prima' and lista='popular'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='" + Orario.getDataOra(7) + 
				"' where nomeDB='prima' and lista='popular'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='" + Orario.getDataOra(7,11) + 
				"' where nomeDB='prima' and lista='user'");
	}

}
