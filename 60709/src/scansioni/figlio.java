package scansioni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import database.DatabaseMySql;
import database.OutputTxt;
import download.Contatore;

public class figlio {
	
	public static void scansioneCompleta (String nomeDB) {
		new Contatore();
		key = new String[6];
		key[0] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[1] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[2] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		key[3] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[4] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[5] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		
		DatabaseMySql.eseguiAggiornamento("insert into " + nomeDB + ".key values (\"figlio\", \"" + key[0] + "\")");
		
		while (DatabaseMySql.eseguiQuery("Select flag from " + nomeDB + ".ethernet where crawler='figlio'").get(0)[0].equals("false")) {
			try {Thread.sleep(5000); System.out.println("In attesa della fine di popularReader.");} catch (InterruptedException e1) {}
			}
		
		pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanPopular.sh" , "figlio");
		while (DatabaseMySql.getCount("" + nomeDB + "", "popToCheck") != 0) {
			try {			
				OutputTxt.writeLog("Figlio: processo scanPopular per il DB: DA CONFIGURARE");
				scanner = pb.start ();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				if (++n == 6)
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='figlio'");
				OutputTxt.writeLog("Figlio: Popular scansionati   totale: " + DatabaseMySql.getCount("" + nomeDB + "", "profile"));
				OutputTxt.writeLog("Figlio: Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Figlio: Popular scansionati   corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
				OutputTxt.writeLog("Figlio: Richieste API per il processo: " + Contatore.getTotApi());
				OutputTxt.writeLog("Figlio: Richieste URL per il processo: " + Contatore.getTotUrl());
			}
			catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try start del figlioExec.");
			}  
		}
		pb.command ("/home/m0m0z/Scrivania/tesina_exec/scanUser.sh" , "figlio"); 
		while (DatabaseMySql.getCount("" + nomeDB + "", "toCheck") != 0) { // && getCount(nomeDB, "profile*ACTIVE*) < CAP)
			try {
				OutputTxt.writeLog("Figlio: processo scanUser per il DB: DA CONFIGURARE");
				scanner = pb.start();
				BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				} 
				if (++n == 6) 
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='figlio'");
				OutputTxt.writeLog("Figlio: Popular scansionati   totale: " + DatabaseMySql.getCount("" + nomeDB + "", "profile"));
				OutputTxt.writeLog("Figlio: Popular scansionati   attivi: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='active'").get(0)[0]);
				OutputTxt.writeLog("Figlio: Popular scansionati   corrupted: " + DatabaseMySql.eseguiQuery("Select count(*) from " + nomeDB + ".profile where status='corrupted'").get(0)[0]);
				OutputTxt.writeLog("Figlio: Richieste API per il processo: " + Contatore.getTotApi());
				OutputTxt.writeLog("Figlio: Richieste URL per il processo: " + Contatore.getTotUrl());
			} catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try scanUser del figlioExec.");
			}  
		} 
	}	
	
	/*
	public static void scansioneVeloce (int scansioneN, String nomeDB, String data, int n) {
		key = new String[6];
		key[0] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[1] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[2] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		key[3] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[4] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[5] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		
		pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/scanActivity.sh" , "padre" , data + ".000Z", scansioneN + "");
		while (DatabaseMySql.getCount(nomeDB, "activeList") != 0) {
			try {			
				OutputTxt.writeLog("Figlio: processo scanActivity per il DB: DA CONFIGURARE");
				scanner = pb.start ();
				BufferedReader in = new BufferedReader(new InputStreamReader(scanner.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null)	{
					System.out.println(line);
				}
				if (++n == 6)
					n = 0;
				DatabaseMySql.eseguiAggiornamento("update " + nomeDB + ".key set devKey='" + key[n] + "' where crawler='figlio'");
				OutputTxt.writeLog("Figlio: Attivi   scansionati   totale: " + DatabaseMySql.getCount(nomeDB, "active" + scansioneN));
				OutputTxt.writeLog("Figlio: Inattivi scansionati   totale: " + DatabaseMySql.getCount(nomeDB, "inactive" + scansioneN));
				OutputTxt.writeLog("Figlio: Richieste API per il processo: " + Contatore.getTotApi());
			}
			catch (IOException e) {
				OutputTxt.writeError("Errore IO nel try start del padreExec.");
			}  
		}		
	} */
	
	private static ProcessBuilder pb = null;
	private static Process scanner;
	private static String[] key;
	private static int n;
}
