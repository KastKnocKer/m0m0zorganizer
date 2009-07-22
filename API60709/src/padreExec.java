
import java.io.*;
import database.DatabaseMySql;
import database.OutputTxt;
import download.ethernet;

public class padreExec {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		ProcessBuilder pb = null;

		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"padre\", \"false\")");
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"figlio\", \"false\")");
		boolean flagEth = true; 		    // true eth0 up eth1 down     false eth0 down eth1 up
		ethernet.switchTo("utenti",false); 	// Se ho true sono a eth0 up e switho a eth1 e viceversa	
		DatabaseMySql.eseguiAggiornamento("update utenti.ethernet set flag ='true' where rete='figlio'");
		try {			
			pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/start.sh");
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
				OutputTxt.writeError("Errore exception nel try start del padreExec.");
			} */ 
		}
		catch (IOException e) {
			OutputTxt.writeError("Errore IO nel try start del padreExec.");
		}  
		Process scanner;
		pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanPopular.sh");
		while (DatabaseMySql.getCount("utenti", "popToCheck") != 0) {
			try {
				scanner = pb.start();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				/*			try {
					scanner.waitFor();
				}
				catch (Exception e1) {
					OutputTxt.writeError("Errore exception nel try scanPopular del padreExec.");
				} */
				ethernet.switchTo("utenti", flagEth);
				flagEth = !flagEth;
			} catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try scanPopular del padreExec.");
			}
		}
		pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanUser.sh");
		while (DatabaseMySql.getCount("utenti", "toCheck") != 0) {
			try {
				scanner = pb.start();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				/*		try {
					scanner.waitFor();
				}
				catch (Exception e1) {
					OutputTxt.writeError("Errore exception nel try scanUser del padreExec.");
				} */
				ethernet.switchTo("utenti", flagEth);
				flagEth = !flagEth;
			} catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try scanUser del padreExec.");
			}
		}
	}
}