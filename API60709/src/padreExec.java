
import java.io.*;
import database.DatabaseMySql;
import database.OutputTxt;
import download.ethernet;

public class padreExec {

	public static void main(String[] args) {
		int n = 0;
		new DatabaseMySql();		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		ProcessBuilder pb = null;

		String[] key = new String[6];
		key[0] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[1] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[2] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		key[3] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[4] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[5] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		DatabaseMySql.eseguiAggiornamento("insert into utenti.key values (\"padre\", \"" + key[0] + "\")");
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"padre\", \"false\")");
		DatabaseMySql.eseguiAggiornamento("insert into utenti.ethernet values (\"figlio\", \"false\")");
		boolean flagEth = false; 		    // true eth0 up eth1 down     false eth0 down eth1 up
		ethernet.switchTo("utenti",true); 	// Se ho true sono a eth0 up e switho a eth1 e viceversa	
		DatabaseMySql.eseguiAggiornamento("update utenti.ethernet set flag ='true' where rete='figlio'");
		try {			
			pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/start.sh");
			Process starter = pb.start ();
			BufferedReader in = new BufferedReader(	new InputStreamReader(starter.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
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
				if (++n == 6)
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update utenti.key set devKey='" + key[n] + "' where crawler='padre'");
				ethernet.switchTo("utenti", flagEth);
				flagEth = !flagEth;
				OutputTxt.writeLog("Popular scansionati   totale: " + DatabaseMySql.getCount("utenti", "profile"));
				OutputTxt.writeLog("Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Popular scansionati inattivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='inactive'").get(0)[0]);
				OutputTxt.writeLog("Popular scansionati bloccati: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='blocked'").get(0)[0]);
				
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
				if (++n == 6) 
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update utenti.key set devKey='" + key[n] + "' where crawler='padre'");
				ethernet.switchTo("utenti", flagEth);
				flagEth = !flagEth;
				OutputTxt.writeLog("Popular scansionati   totale: " + DatabaseMySql.getCount("utenti", "profile"));
				OutputTxt.writeLog("Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Popular scansionati inattivi: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='inactive'").get(0)[0]);
				OutputTxt.writeLog("Popular scansionati bloccati: " + DatabaseMySql.eseguiQuery("Select count(*) from utenti.profile where status='blocked'").get(0)[0]);
			} catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try scanUser del padreExec.");
			}  
		} 
	}
}