package prove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gdata.data.spreadsheet.Data;

import database.DatabaseMySql;
import database.Orario;
import database.createRootDB;
import download.urlReader;

public class provesubscription {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new createRootDB();
		String videoId;
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		DatabaseMySql.eseguiAggiornamento("update root.ethernet set flag ='true' where rete='padre'");
		if ((videoId = DatabaseMySql.extract("root", "videoToCheck", "id")[0]) != null)
			urlReader.getVideoUploader(videoId);
	}
}
