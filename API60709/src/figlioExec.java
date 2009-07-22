

import java.io.*;

import database.DatabaseMySql;
import database.OutputTxt;

public class figlioExec {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		ProcessBuilder pb = null;
		try {
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {e.printStackTrace();}
			
			pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanPopular.sh");
			Process starter = pb.start ();
			BufferedReader in = new BufferedReader(	new InputStreamReader(starter.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
	/*		try	{
				starter.waitFor ();
			}
			catch (Exception e)	{
				OutputTxt.writeError("Errore exception nel try scanPopular del figlioExec.");
			} */
		}
		catch (IOException e) {
			OutputTxt.writeError("Errore IO nel try scanPopular del figlioExec.");
		}  
		Process scanner;
		pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanUser.sh");
		while (DatabaseMySql.getCount("utenti", "toCheck") != 0) {
			try {
				scanner = pb.start();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				/*	try {
					scanner.waitFor();
				}
				catch (Exception e1) {
					OutputTxt.writeError("Errore exception nel try scanUser del figlioExec.");
				} */
			} catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try scanUserdel figlioExec.");
			}
		}
	}
}