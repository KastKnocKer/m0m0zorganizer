

import java.io.*;

import database.DatabaseMySql;

public class padreExec {

	public static void main(String[] args) {
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		ProcessBuilder pb = null;
		
		boolean flagEth = true;  // true eth0 up eth1 down     false eth0 down eth1 up
		ethernet.switchTo(false); // Se ho true sono a eth0 up e switho a eth1 e viceversa
		try {
			pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/start.sh");
			Process starter = pb.start ();
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
				try {
					scanner.waitFor();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
				ethernet.switchTo(flagEth);
				flagEth = !flagEth;
			} catch (IOException e) {
				e.printStackTrace();
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
				try {
					scanner.waitFor();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
				ethernet.switchTo(flagEth);
				flagEth = !flagEth;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}