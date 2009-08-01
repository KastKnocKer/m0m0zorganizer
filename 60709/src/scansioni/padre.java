package scansioni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import download.Contatore;
import download.ethernet;
import download.popularReader;

public class padre {	
	
	public static void scansioneCompleta (String nomeDB) {
		new Contatore();
		key = new String[6];
		key[0] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[1] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[2] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		key[3] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[4] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[5] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".key values (\"padre\", \"" + key[0] + "\")");
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".ethernet values (\"padre\", \"false\")");
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".ethernet values (\"figlio\", \"false\")");
		flagEth = true; 		    // true eth0 up eth1 down     false eth0 down eth1 up
		ethernet.switchTo(nomeDB, false); 	// Se ho true sono a eth0 up e switho a eth1 e viceversa
		
		if(DatabaseMySql.contiene("root", "scansioni", "nomeDB", nomeDB, "lista", "popular", "completed", "false")) {
			DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='" + Orario.getDataOra()  + "' where nomeDB='" + nomeDB + "' and lista='popular'");
			DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='"   + Orario.getDataOra()  + "' where nomeDB='" + nomeDB + "' and lista='popular'");
			DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='"   + Orario.getDataOra(6, 12, 0) + "' where nomeDB='" + nomeDB + "' and lista='user'");
						
			new popularReader(nomeDB);		
			// Blocca il figlio fino alla fine del popularReader
			//DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag ='true' where rete='figlio'");
			System.out.println("Nome del database per la scansione " + nomeDB);
			pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanPopular.sh" , "padre", nomeDB);
			while (DatabaseMySql.getCount(nomeDB, "popToCheck") != 0) {
				try {			
					OutputTxt.writeLog("Padre: processo scanPopular per il DB: " + nomeDB + ".");
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
					OutputTxt.writeLog("Padre: Popular scansionati    totale: " + DatabaseMySql.getCount(nomeDB, "profile"));
					OutputTxt.writeLog("Padre: Popular scansionati    attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
					OutputTxt.writeLog("Padre: Popular scansionati corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
	
				}
				catch (IOException e) {
					OutputTxt.writeError("Errore IO nel try start del padreExec.");
				}  
			}
		}
		
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set completed='true' where nomeDB='" + nomeDB + "' and " + "lista='popular'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='"   + Orario.getDataOra() + "' where nomeDB='" + nomeDB + "' and lista='popular'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='" + Orario.getDataOra() + "' where nomeDB='" + nomeDB + "' and lista='user'");
		if(DatabaseMySql.contiene("root", "scansioni", "nomeDB", nomeDB, "lista", "user", "completed", "false")) {
			pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanUser.sh" , "padre", nomeDB); 
			while (DatabaseMySql.getCount(nomeDB, "toCheck") != 0 && Orario.getDataOra().compareTo(
					DatabaseMySql.eseguiQuery("Select fine from root.scansioni where nomeDB='" + 
							nomeDB + "' and lista='user'").get(0)[0]) < 0) {
				try {
					OutputTxt.writeLog("Padre: processo scanUser per il DB: " + nomeDB + ".");
					scanner = pb.start();
					BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
					String line = null;
					while ((line = in.readLine()) != null)	{
						System.out.println(line);
					} 
					if (++n == 6) 
						n = 0;
					DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='padre'");
					//DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag='false' where rete='figlio'"); 
					ethernet.switchTo(nomeDB, flagEth);
					flagEth = !flagEth;
					OutputTxt.writeLog("Padre: User scansionati    totale: " + DatabaseMySql.getCount("" + nomeDB + "", "profile"));
					OutputTxt.writeLog("Padre: User scansionati    attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
					OutputTxt.writeLog("Padre: User scansionati corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
				} catch (IOException e) {
					OutputTxt.writeError("Errore IO nel try scanUser del padreExec.");
				}  
			} 
		}
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set completed='true' where nomeDB='" + nomeDB + "' and " +
		"lista='user'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='"   + Orario.getDataOra() + "' where nomeDB='" + nomeDB + "' and lista='user'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='" + Orario.getDataOra() + "' where nomeDB='" + nomeDB + "' and lista='corrupted'");
		
		if(DatabaseMySql.contiene("root", "scansioni", "nomeDB", nomeDB, "lista", "corrupted", "completed", "false")) {
			if (DatabaseMySql.getCount(nomeDB, "corruptedList") == 0)
				DatabaseMySql.copyCorrupted(nomeDB);
			pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanCorrupted.sh" , "padre", nomeDB); 
			while (DatabaseMySql.getCount(nomeDB, "corruptedList") != 0) { // && getCount(nomeDB, "profile*ACTIVE*) < CAP)
				try {
					OutputTxt.writeLog("Padre: processo scanCorrupted per il DB: " + nomeDB + ".");
					scanner = pb.start();
					BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
					String line = null;
					while ((line = in.readLine()) != null)	{
						System.out.println(line);
					} 
					if (++n == 6) 
						n = 0;
					DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='padre'");
				//	DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag='false' where rete='figlio'"); 
					ethernet.switchTo(nomeDB, flagEth);
					flagEth = !flagEth;
					OutputTxt.writeLog("Padre: User corrupted 				    totale: " + DatabaseMySql.getCount("" + nomeDB + "", "profile"));
					OutputTxt.writeLog("Padre: User corrupted ri-scansionati    attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
					OutputTxt.writeLog("Padre: User corrupted ri-scansionati corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
				} catch (IOException e) {
					OutputTxt.writeError("Errore IO nel try scanUser del padreExec.");
				}  
			}  
		}
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='"   + Orario.getDataOra() + "' where nomeDB='" + nomeDB + "' and lista='corrupted'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set completed='true' where nomeDB='" + nomeDB + "' and " +
		"lista='corrupted'");
		// flag per dire al figlio se partire o no
	}
	
	public static void scansioneVeloce (int scansioneN, String nomeDB, String data) {
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='"   + Orario.getDataOra() + 		 "' where nomeDB='" + nomeDB + "' and lista='veloce" + scansioneN + "'");
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set fine='"   + Orario.getDataOra(1, 0 , 0) + 	 "' where nomeDB='" + nomeDB + "' and lista='veloce" + scansioneN + "'");
		if (scansioneN != 7)
			DatabaseMySql.eseguiAggiornamento("Update root.scansioni set inizio='"   + Orario.getDataOra(1, 0 , 1) + "' where nomeDB='" + nomeDB + "' and lista='veloce" + (scansioneN + 1)+ "'");
		if(DatabaseMySql.contiene("root", "scansioni", "nomeDB", nomeDB, "lista", "veloce" + scansioneN, "completed", "true")) 
			return;
		while (Orario.getDataOra().compareTo(DatabaseMySql.eseguiQuery("Select inizio from root.scansioni where nomeDB='" + 
							nomeDB + "' and lista='veloce" + scansioneN + "'").get(0)[0]) < 0) {
			try {
				System.out.println("Attesa del momento corretto per iniziare la scansione veloce " + scansioneN);
				OutputTxt.writeLog("Attesa del momento corretto per iniziare la scansione veloce " + scansioneN);
				Thread.sleep(3600000);
			} catch (InterruptedException e) {}
		}
		// Copia degli utenti attivi per avere una lista per le scansioni veloci
		if (DatabaseMySql.getCount(nomeDB, "activeList") == 0) {
			DatabaseMySql.copyAttivi(nomeDB);
		}
		
		key = new String[6];
		key[0] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[1] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[2] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		key[3] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[4] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[5] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		
		// Messo perchè non penso di utilizzare il figlio nelle scansioni veloci.
		DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".ethernet set flag ='false' where rete='figlio'");
		pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanActivity.sh" , "padre" , nomeDB, scansioneN + "");
		while (DatabaseMySql.getCount(nomeDB, "activeList") != 0 && Orario.getDataOra().compareTo(DatabaseMySql.eseguiQuery("Select fine from root.scansioni where nomeDB='" + 
				nomeDB + "' and lista='veloce" + scansioneN + "'").get(0)[0]) < 0) {
			try {			
				OutputTxt.writeLog("Padre: processo scanActivity per il DB: " + nomeDB + ".");
				scanner = pb.start ();
				BufferedReader in = new BufferedReader(new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				if (++n == 6)
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='padre'");
				ethernet.switchTo(nomeDB, flagEth);
			    flagEth = !flagEth;
				OutputTxt.writeLog("Padre: User scansionati nelle actvity    totale: " + DatabaseMySql.getCount("" + nomeDB + "", "profile"));
				OutputTxt.writeLog("Padre: User scansionati nelle actvity    attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Padre: User scansionati nelle actvity corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
			}
			catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try start del padreExec.");
			}  
		}	
		DatabaseMySql.eseguiAggiornamento("Update root.scansioni set completed='true' where nomeDB='" + nomeDB + "' and " +
		"lista='veloce" + scansioneN + "'");	
	}
	
	private static ProcessBuilder pb = null;
	private static Process scanner;
	private static boolean flagEth;
	private static String[] key;
	private static int n;

}
