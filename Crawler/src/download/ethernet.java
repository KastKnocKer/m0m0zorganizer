package download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import database.DatabaseMySql;
import database.OutputTxt;


public class ethernet {
	
	public ethernet () {}
	
	public static void switchTo (boolean flag, String nomeDB) {
		int n = 0;
		try {
			System.out.println("Avvio switching ethernet..Attendere..");
			DatabaseMySql.eseguiAggiornamento("update root.ethernet set flag ='false' where rete='padre'");
			while (DatabaseMySql.contiene("root", "ethernet", "rete", "figlio", "flag", "true")) { 
				try {	
					if (++n == 3600) {
						DatabaseMySql.eseguiAggiornamento("update root.ethernet set flag ='false' where rete='figlio'");
					}
					System.out.println(n + ": Attesa segnale dal processo figlio..Attendere..");
					Thread.sleep(1000);	
				} catch (InterruptedException e2) {
					OutputTxt.writeError(nomeDB, "Errore di interrupt nel primo timer dello switchEthernet");
				} 
			} 
			if (flag) {
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina/switch_to_eth1.sh");
				System.out.println("Switch from eth0 to eth1");
				OutputTxt.writeLog(nomeDB, "Switch from eth0 to eth1");
			}
			else {
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina/switch_to_eth0.sh");
				System.out.println("Switch from eth1 to eth0");
				OutputTxt.writeLog(nomeDB, "Switch from eth1 to eth0");
			}			
			scanner = pb.start ();
			BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
		} catch (IOException e) { OutputTxt.writeError(nomeDB, "Errore di IO nello switchEthernet");
		}
		try {
			Thread.sleep(7500);  // Sistemare se sono a casa 7500 se sono in uni 3500
			DatabaseMySql.eseguiAggiornamento("update root.ethernet set flag ='true' where rete='padre'");
		} catch (InterruptedException e1) {
			OutputTxt.writeError(nomeDB, "Errore di interrupt nel secondo timer dello switchEthernet");
		}
	}
			
	
	public static void checkEthernet (String nomeDB) {
		try {
			if (DatabaseMySql.contiene("root", "ethernet", "rete", "padre", "flag", "true"))
				return;
			else {
				DatabaseMySql.eseguiAggiornamento("update root.ethernet set flag ='false' where rete='figlio'");
				while (DatabaseMySql.contiene("root", "ethernet", "rete", "padre", "flag", "false")) {
					try {
						System.out.println("Ethernet switching..Attendere..");
						Thread.sleep(1000);	
					} catch (InterruptedException e1) { 
						OutputTxt.writeError(nomeDB, "Errore di interrupt nel timer del checkEthernet");}
				}
				DatabaseMySql.eseguiAggiornamento("update root.ethernet set flag ='true' where rete='figlio'");
			} 
		} catch (ArrayIndexOutOfBoundsException e) { 
			try {Thread.sleep(1000);} catch (InterruptedException e1) {
				OutputTxt.writeError(nomeDB, "Errore di interrupt nel timer del checkEthernet");
			}
			System.out.println("Possibile switching ethernet..Controllo in corso..");
			checkEthernet(nomeDB);			
		}
	}
		
	static ProcessBuilder pb;
	static Process scanner;
	static boolean flag;
}
