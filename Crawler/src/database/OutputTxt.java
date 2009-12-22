package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputTxt {
	
	public static void writeLog (String nomeDB, String check) {
		try {			
			logWriter = new BufferedWriter(new FileWriter("./" + nomeDB + "Log", true));
			//System.out.println(Orario.getDataOra() + ": " + check);
			logWriter.write(Orario.getDataOra() + ": " + check);
			logWriter.newLine();			
			logWriter.flush();
			logWriter.close();
		} catch (IOException e) {
			System.out.println("Errore nel writeLog");
			e.printStackTrace();
				}
		return;
	}	
	
	public static void writeError (String nomeDB, String check) {
		try {			
			logWriter = new BufferedWriter(new FileWriter("./" + nomeDB + "Error", true));
			System.out.println(Orario.getDataOra() + ": " + check);
			logWriter.write(Orario.getDataOra() + ": " + check);
			logWriter.newLine();			
			logWriter.flush();
			logWriter.close();
		} catch (IOException e) {
			System.out.println("Errore nel writeError");
			e.printStackTrace();
		}
		return;
	}
	
	public static void writeException (String nomeDB, String check) {
		try {			
			logWriter = new BufferedWriter(new FileWriter("./" + nomeDB + "Exception", true));
			logWriter.write(Orario.getDataOra() + ": " + check);
			logWriter.newLine();			
			logWriter.flush();
			logWriter.close();
		} catch (IOException e) {
			System.out.println("Errore nel writeLog");
			e.printStackTrace();
		}
		return;
	}
	
	private static BufferedWriter logWriter;
}	