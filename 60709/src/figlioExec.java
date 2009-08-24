import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import download.Contatore;
import download.ethernet;
import download.urlReader;

public class figlioExec {

	public static void main(String[] args) {
		new DatabaseMySql("connessione");
		DatabaseMySql.connetti();
		new Contatore();
		String nomeDB;
		key = new String[6];
		key[0] = "AI39si7ApF_l2ABpTplTnaS_sjxreCpkkQQi4vLAWYxxb1VHsHpeh1HJKlx9t5vi_ngxvSpuIqXQShsNXMWhDNGBjBtNlSIxkg";
		key[1] = "AI39si5XLt78NO1fRB0VaLCqUIXWkZeLDNSITQMvwwo_0scaR2qwzc2FzQTAqNqYBY0mAooL1HM4rl9BNpAefC1jx4PuMYKWsQ";
		key[2] = "AI39si647HsBMmuW7FnWtDwb037yfACgX-FcXaHuMZXfTUH37tw8DawMPmWgbO-CeSIfoJJF5URC7ww52k94Thj_dbH9wFdxNQ";
		key[3] = "AI39si4fzIi01PLvZIYjyHDVpyEKyvUHJAUvG4N9US4g1SYHmmcojgJ-joGo4q3ajF6eLPom3lmUoFw7IpYStDWUoOm29jadMA";
		key[4] = "AI39si7e_IYXZqXB764Zgqll4sJlxizsHT02LAx1yo6CHG-8eaayATP-OGG330hhLj1HUHmjzwU62X7s8WHSe8JpiqpfrfGoGw";
		key[5] = "AI39si64j5tSsH0ZIWV181HS2TS0Fzybri75KOBcQrm6baZ9TtfyZ7IiGPIEZPfuVZS-HK0LDTsNGDHk6Vu_bqObw0nm68VMog";
		DatabaseMySql.eseguiAggiornamento("insert into root.key values (\"figlio\", \"" + key[0] + "\")");
		
		while (true) {
			nomeDB = DatabaseMySql.eseguiQuery("Select nomeDB from root.scansioni where completed='false' limit 1").get(0)[0];
						
			while (DatabaseMySql.contiene("root", "config", "id", "figlio", "status", "off")) {
				ethernet.checkEthernet(nomeDB);
				System.out.println("In attesa della fine dello scanPopular per il DB:" + nomeDB);
				try {Thread.sleep(10000);} catch (InterruptedException e1) {}
			}
			
			if (DatabaseMySql.contiene("root", "config", "id", "figlio", "lista", "user", "status", "on")) {
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
						OutputTxt.writeError(nomeDB,"Errore IO nel try scanUser del figlioExec.");
					}  
				} 
				DatabaseMySql.eseguiAggiornamento("Update root.config set status='off' where id='figlio'");
			}
			else if (DatabaseMySql.contiene("root", "config", "id", "figlio", "lista", "video", "status", "on")) {
				if (DatabaseMySql.getCount("root", "videoToCheck") == 0) {
					System.out.println("In attesa di video da controllare. Scansione attuale: " + nomeDB);
					try { Thread.sleep(1800000); } catch (InterruptedException e) {}
				}
				else if	((videoId = DatabaseMySql.extract("root", "videoToCheck", "id")[0]) != null)
					urlReader.getVideoUploader(videoId);
			}
			ethernet.checkEthernet(nomeDB);
			System.out.println("Pausa..");
			try {Thread.sleep(5000);} catch (InterruptedException e) {}
		}
	}		
	
	private static ProcessBuilder pb = null;
	private static Process scanner;
	private static String[] key;
	private static String videoId;
	private static int n;

	}