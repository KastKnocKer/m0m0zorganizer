
import java.io.*;
import database.DatabaseMySql;
import database.OutputTxt;
import download.Contatore;
import download.ethernet;
import download.popularReader;

public class padreExec {

	public static void main(String[] args) {
		int n = 0;
		new DatabaseMySql("utenti"); // DA CONFIGURARE
		DatabaseMySql.connetti();	// Connessione al database
		ProcessBuilder pb = null;
		Process scanner;
		
		OutputTxt.writeLog("Inizio scansione");
		
		String nomeDB;
		nomeDB = "utenti";  // Momentaneo
		final String[] key = new String[6];
		key[0] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[1] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[2] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		key[3] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[4] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[5] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".key values (\"padre\", \"" + key[0] + "\")");
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".ethernet values (\"padre\", \"false\")");
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".ethernet values (\"figlio\", \"false\")");
		boolean flagEth = false; 		    // true eth0 up eth1 down     false eth0 down eth1 up
		ethernet.switchTo(nomeDB, true); 	// Se ho true sono a eth0 up e switho a eth1 e viceversa
		
		new popularReader(nomeDB);
		
		//temporaneo per le prove a casa
		DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag ='true' where rete='padre'");
		DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag ='true' where rete='figlio'");
		
		pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanPopular.sh");
		while (DatabaseMySql.getCount(nomeDB, "popToCheck") != 0) {
			try {			
				OutputTxt.writeLog("Padre: processo scanPopular per il DB: DA CONFIGURARE");
				scanner = pb.start ();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				if (++n == 6)
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='padre'");
				ethernet.switchTo(nomeDB, flagEth);
				flagEth = !flagEth;
				OutputTxt.writeLog("Padre: Popular scansionati   totale: " + DatabaseMySql.getCount(nomeDB, "profile"));
				OutputTxt.writeLog("Padre: Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Padre: Richieste API per il processo: " + Contatore.getTotApi());
				OutputTxt.writeLog("Padre: Richieste URL per il processo: " + Contatore.getTotUrl());
			}
			catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try start del padreExec.");
			}  
		}
		pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanUser.sh"); 
		while (DatabaseMySql.getCount(nomeDB, "toCheck") != 0) {
			try {
				OutputTxt.writeLog("Padre: processo scanUser per il DB: DA CONFIGURARE");
				scanner = pb.start();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				} 
				if (++n == 6) 
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='padre'");
				ethernet.switchTo(nomeDB, flagEth);
				flagEth = !flagEth;
				OutputTxt.writeLog("Padre: Popular scansionati   totale: " + DatabaseMySql.getCount(nomeDB, "profile"));
				OutputTxt.writeLog("Padre: Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Padre: Richieste API per il processo: " + Contatore.getTotApi());
				OutputTxt.writeLog("Padre: Richieste URL per il processo: " + Contatore.getTotUrl());
			} catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try scanUser del padreExec.");
			}  
		}  
	}
}