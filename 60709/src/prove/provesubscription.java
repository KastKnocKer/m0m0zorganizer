package prove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import com.google.gdata.data.spreadsheet.Data;

import database.DatabaseMySql;
import database.Orario;
import database.createRootDB;
import download.popularReader;
import download.urlReader;

public class provesubscription {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new createRootDB();
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		new popularReader("prima");
	}
}
