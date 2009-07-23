package download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import database.*;

public class urlReader  {
	
	public urlReader () { }
	
	public static void userReader (String tabella, String user) {
		userReader (tabella, user, 0);
	}
	
    public static void userReader (String tabella, String user, int count) {
    	if (!tabella.equals("subscribers") && !tabella.equals("friends")) {
    		System.out.println("Errore utente: Inserire correttamente parametro tabella nel " + tabella + 
    				"Reader dell'utente" + user);
    		System.exit(0);
    	}  	
    	try {
	    	metafeedUrl = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + tabella + "&start=" + count);
	    	ethernet.checkEthernet("utenti");
	    	Contatore.incUrl();
	    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			while ((inputLine = in.readLine()) != null) {
				if (count != 0 && (count == 1008 || count == tot)) {
			    	in.close();
			    	System.out.println("Cap dei " + tabella + " raggiunto per l'utente " + user + ".");
			    	return;
			    }
				if (inputLine.contains("channel-box-item-count")) {
			    	 inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
			    	 tot = Integer.parseInt(inputLine);    	
			    }
				else if (inputLine.contains("<div class=\"user-thumb")) {
			    	inputLine = in.readLine();
			    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
			    	count++;
			    	DatabaseMySql.insert("utenti", tabella , user, inputLine, tot  + "");
			    	DatabaseMySql.inserToCheck("utenti", inputLine);
				}
				else if (inputLine.contains("non ha") && count <= 1) {
					in.close();
			    	OutputTxt.writeLog("Errore: L' utente " + user + " non ha aggiunto " + tabella + ".");
			    	return;
			    }
			    else if (inputLine.contains("Non è")) {
					in.close();
			    	OutputTxt.writeLog("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
			    	DatabaseMySql.insert("utenti", "infoReserved", user, tabella);
			    	return;
			    }
			    else if (inputLine.contains("Questo account è stato")) {
					in.close();
			    	OutputTxt.writeLog("Errore 404: User not found: " + user);
			    	return;
			    }
			    else if (inputLine.equals("</html>") && count != 0 && count < tot) {
			    	in.close();
			    	System.out.println(tabella + " dell'user " + user + " scaricati fino al num: " + count + ".");
			    	System.out.println("\t\t\tTotale " + tabella + " per l'user " + user + ": " + tot);
			    	userReader(tabella, user, count);
			    	return;
			    }
				else if (inputLine.contains("is down for") || inputLine.contains("manutenzione")) {
					in.close();
					OutputTxt.writeLog("Youtube down per manutenzione o non al 100%");
					System.out.println("Youtube down per manutenzione o non al 100%. Pausa 1 minuto.");  
					pausa(60, user);
					return;
				}
				else if (inputLine.contains("Siamo spiacenti per l'interruzione"))
					notifyUrlFlood(inputLine, user);				
			}
    	}
    	catch (MalformedURLException e) { 
    		e.printStackTrace();
    		OutputTxt.writeException(e.getLocalizedMessage()); 
    		OutputTxt.writeException("Errore nel " + tabella + "Reader dell'utente: " + user);	
    	}
    	catch (IOException e) {  
    		if(e.getMessage().contains("Server returned HTTP response code: 50")) {
				System.out.println("Errore 500+ : servizio non disponibile al momento.. Pausa 5 minuti.");
				OutputTxt.writeLog("Errore 500+ : servizio non disponibile al momento.");
    			pausa(300, user);
    			return;
    		}
    	}
    	catch (StringIndexOutOfBoundsException e) {
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore CONOSCIUTO E CONTROLLATO " + tabella + "Reader dell'utente: " + user);	
    		// Pensare come gestire: farei controllo se ci sono amici/subscribers nel db o se no segnalo no amici/sub
    	}
    	try {
    		in.close();
    	} catch (IOException e) {
    		OutputTxt.writeError("Errore IO nella  " + tabella + " reader dell'utente " + user);
    	}
    	System.out.println("Fine del " + tabella + " reader dell'utente " + user);
    	return;
    }
	
    public static void notifyUrlFlood (String inputLine, String user) {
			OutputTxt.writeLog("Rete floodata dalle URL.");    // DA RIFAREEEEE
			OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
			OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
			Contatore.setApi(0);
			Contatore.setUrl(0);
			System.out.println("Rete floodata dalle URL.");
			System.out.println("PAUSA di 30 minuti per flood URL");
			try {
				DatabaseMySql.delete("utenti", "profile", "user", user);
				DatabaseMySql.inserToCheck("utenti", user, -9999);
				Thread.currentThread();
				Thread.sleep(1800000);	 // Pausa di 3 minuti e mezzo
			}
			catch (InterruptedException e) { 
				e.printStackTrace();
	            OutputTxt.writeException(e.getLocalizedMessage());
	            OutputTxt.writeException("Errore nel notifyFlood dell'utente.");
			}
			return;
    }
    
    public static void pausa(int sec, String user) {
    	try {
    		System.out.println("Pausa di " + sec + " secondi sull'utente " + user);
    		DatabaseMySql.delete("utenti", "profile", "user", user);
    		DatabaseMySql.inserToCheck("utenti", user, DatabaseMySql.getMinPriority() + 3);
    		Thread.currentThread();
    		Thread.sleep(sec * 1000);	 // Pausa di sec secondi
    	}
    	catch (InterruptedException e) { 
    		e.printStackTrace();
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore nella funzione pausa.");	
    	}
    }
    
    public static void getErrorCode (String tabella ,URL url ,String user) {
    	String msg;
    	int code;
    	System.out.println("GetErrorCode sui " + tabella  + " dell'utente " + user);
		HttpURLConnection connection;
		try {		
			ethernet.checkEthernet("utenti");
			Contatore.incApi();
			connection = (HttpURLConnection) url.openConnection();
			System.out.println((code = connection.getResponseCode()));
			System.out.println(msg = connection.getResponseMessage());
			if (code >= 500) {
				OutputTxt.writeLog("Errore 500+ : servizio non disponibile al momento.");
				System.out.println("Errore 500+ : servizio non disponibile al momento.. Pausa 5 minuti.");
				pausa(300, user);
				return;
			// Direi di fare una pausa e di richiamare la stessa funzione
			}				
			else if (msg.contains("Forbidden") ||
					connection.getResponseMessage().contains("are not public")) {
				DatabaseMySql.insert("utenti", "infoReserved", user, tabella);
				System.out.println("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
				OutputTxt.writeLog("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
				return;
			}
			else if (msg.contains("many")) {
				API.notifyApiFlood(tabella, user);
				return;
			}	
			else if (code == 404) {
				System.out.println("Errore 404: User not found: " + user);
				OutputTxt.writeLog("Errore 404: User not found: " + user);
				return; 
			}
			else if (msg.contains("Bad Request")) {
				OutputTxt.writeError("Errore bad request all'url: " + url);
				System.out.println("Errore bad request all'url: " + url);
				return;
			}	
		} catch (IOException e) { 
			e.printStackTrace();
			OutputTxt.writeException(e.getLocalizedMessage());
	        OutputTxt.writeException("Errore nel getErrorCode dell'utente: " + user);	
		}
		return;
	}
    
   
    
    private static URL metafeedUrl;
    private static BufferedReader in;
    private static int tot;
    private static String inputLine;  
}