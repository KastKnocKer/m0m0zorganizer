

import java.io.*;

import database.DatabaseMySql;

public class tryExec {


	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		ProcessBuilder pb = null;
	/*	try {
			pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/start.sh");
			Process starter = pb.start ();
			//Process p = Runtime.getRuntime().exec("amulecmd --command='set bwlimit up 22'"Â«Â»);
			BufferedReader in = new BufferedReader(	new InputStreamReader(starter.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
			try	{
				starter.waitFor ();
			}
			catch (Exception e)	{
				System.out.println(e);
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}  */
		Process scanner;
	/*	pb.command("/home/m0m0z/Scrivania/tesina_exec/scanPopular.sh");
		while (DatabaseMySql.getCount("utenti", "popToCheck") != 0) {
				try {
					scanner = pb.start();
					BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
					String line = null;
					while ((line = in.readLine()) != null)	{
						System.out.println(line);
					}
					try {
						scanner.waitFor();
					}
					catch (Exception e1) {
						e1.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		} */
		pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanUser.sh");
		while (DatabaseMySql.getCount("utenti", "toCheck") != 0) {
			try {
				scanner = pb.start();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				try {
					scanner.waitFor();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	}
}