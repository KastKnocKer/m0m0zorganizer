package scansioni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import download.Contatore;

public class figlio  extends Thread {
	
	public static void run (String nomeDB) {
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		new Contatore();
		//String nomeDB = args[0];
		key = new String[6];
		key[0] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[1] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[2] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		key[3] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[4] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[5] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		
		DatabaseMySql.eseguiAggiornamento("insert into root.key values (\"figlio\", \"" + key[0] + "\")");
		
		while (DatabaseMySql.contiene(nomeDB, "ethernet", "rete", "figlio", "flag", "false")) {
			try {Thread.sleep(5000); System.out.println("In attesa della fine dello scanPopular per il DB: " + nomeDB);} catch (InterruptedException e1) {}
			}
		
		pb = new ProcessBuilder ("java", "crawlerUser" , "figlio", nomeDB); 
		while (DatabaseMySql.getCount(nomeDB, "toCheck") != 0 && Orario.getDataOra().compareTo(
				DatabaseMySql.eseguiQuery("Select fine from root.scansioni where nomeDB='" + 
						nomeDB + "' and lista='user'").get(0)[0]) < 0) {
			try {
				OutputTxt.writeLog(nomeDB, "Figlio: processo scanUser per il DB: " + nomeDB);
				scanner = pb.start();
				OutputTxt.writeLog(nomeDB, "Figlio: partito scanUser per il DB: " + nomeDB);
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				} 
				if (++n == 6) 
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update root.key set devKey='" + key[n] + "' where crawler='figlio'");
				OutputTxt.writeLog(nomeDB, "Figlio: Popular scansionati   totale: " + DatabaseMySql.getCount("" + nomeDB + "", "profile"));
				OutputTxt.writeLog(nomeDB, "Figlio: Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
				OutputTxt.writeLog(nomeDB, "Figlio: Popular scansionati   corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
			} catch (IOException e) {
				OutputTxt.writeError(nomeDB, "Errore IO nel try scanUser del figlioExec.");
			}  
		} 
		DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag ='false' where rete='figlio'");		
	}	
		
	private static ProcessBuilder pb = null;
	private static Process scanner;
	private static String[] key;
	private static int n;
}
