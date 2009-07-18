package download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import database.*;

public class urlReader  {
	
	public urlReader () { }
	
	public static void userReader (String tabella, String user) {
		userReader (tabella, user, 0, 0);
	}
	
    public static void userReader (String tabella, String user, int count, int effettivi) {
    	if (!tabella.equals("subscribers") && !tabella.equals("friends")) {
    		System.out.println("Errore utente: Inserire correttamente parametro tabella nel " + tabella + 
    				"Reader dell'utente" + user);
    		System.exit(0);
    	}  	
    	try {
	    	metafeedUrl = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + tabella + "&start=" + count);
	    	Contatore.incUrl();
	    	System.out.println(metafeedUrl);
	    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
	    	
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("Siamo spiacenti per l'interruzione"))
					urlReader.checkFlood(inputLine, "","");
				else if (inputLine.contains("is down for") || inputLine.contains("manutenzione")) {
					in.close();
					OutputTxt.writeLog("Youtube down per manutenzione o non al 100%");   
					pausa(600, user);
					return;
				}
				else if (inputLine.contains("non ha") && count <= 1) {
					in.close();
			    	OutputTxt.writeLog("Errore: L' utente " + user + " non ha aggiunto " + tabella + ".");
			    	return;
			    }
				else if (inputLine.contains("channel-box-item-count")) {
			    	 inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
			    	 tot = Integer.parseInt(inputLine);
				}
				else if (inputLine.contains("<div class=\"user-thumb")) {
			    	inputLine = in.readLine();
			    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
			    	count++;
			    	if (DatabaseMySql.insert("utenti", tabella , user, inputLine))
			    		System.out.println(count + " : " + ++effettivi + ": Inserimento nella tabella " + tabella + 
			    				" della tupla: "+ user + " - " + inputLine);
			    		DatabaseMySql.inserToCheck("utenti", inputLine);	    	
			    }
			    else if (inputLine.contains("Non è")) {
					in.close();
			    	OutputTxt.writeLog("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
			    	return;
			    }
			    else if (inputLine.contains("Questo account è stato")) {
					in.close();
			    	OutputTxt.writeLog("Errore 404: User not found: " + user);
			    	return;
			    }
			    else if (count != 0 && (count == 1008 || count == tot)) {
			    	in.close();
			    	System.out.println("Cap dei " + tabella + " raggiunto per l'utente " + user + ".");
			    	return;
			    }
			    else if (inputLine.equals("</html>") && count != 0 && count < tot) {
			    	in.close();
			    	userReader(tabella, user, count, effettivi);
			    	return;
			    }
			}
    	}
    	catch (MalformedURLException e) { 
    		e.printStackTrace();
    		OutputTxt.writeException(e.getLocalizedMessage()); 
    		OutputTxt.writeException("Errore nel " + tabella + "Reader dell'utente: " + user);	
    	}
    	catch (IOException e) {  
    		if(e.getMessage().contains("Server returned HTTP response code: 50")) {
    			getErrorCode(tabella, metafeedUrl, user);
    		}
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore nel " + tabella + "Reader dell'utente: " + user);	
    	}
    	catch (StringIndexOutOfBoundsException e) {
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore CONOSCIUTO E CONTROLLATO " + tabella + "Reader dell'utente: " + user);	
    		// Pensare come gestire: farei controllo se ci sono amici/subscribers nel db o se no segnalo no amici/sub
    	}
    	try {in.close();} catch (IOException e) {}
    	System.out.println("Fine del " + tabella + " reader dell'utente " + user);
    	return;
    }
	
    public static void checkFlood (String inputLine, String tabella, String user) {
			OutputTxt.writeLog("Rete floodata dalle URL.");    // DA RIFAREEEEE
			OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
			OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
			Contatore.setApi(0);
			Contatore.setUrl(0);
			System.out.println("Rete floodata dalle URL.");
			Runtime.getRuntime().gc();
			pausa(1800, user);
			return;
    }
    
    public static void pausa(int sec, String user) {
    	try {
    		DatabaseMySql.eseguiExtractUser("utenti", "active", "user", user);
    		DatabaseMySql.inserToCheck("utenti", user, 50);
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
		HttpURLConnection connection;
		try {
			String temp;		
			Contatore.incApi();
			connection = (HttpURLConnection) url.openConnection();
			System.out.println(connection.getResponseCode());
			if (connection.getResponseCode() == 503)
				OutputTxt.writeLog("Errore 503 : servizio non disponibile al momento.");
			else if (connection.getResponseCode() >= 500) {
				Contatore.incUrl();
				in = new BufferedReader(new InputStreamReader(new URL("http://www.youtube.com").openStream()));
				while ((temp = in.readLine()) != null) {
					if (temp.contains("is down for") || temp.contains("manutenzione")) {
						in.close();
						OutputTxt.writeLog("Youtube down per manutenzione o non al 100%");
						DatabaseMySql.inserToCheck("utenti", user, 9999);
						pausa(600, user);
						return;
					}
				}
				in.close();
				DatabaseMySql.inserToCheck("utenti", user);
				OutputTxt.writeLog("Errore 500+ : servizio non disponibile al momento.");
				OutputTxt.writeLog("Errore 500+ : user " + user + " reinserito in toCheck.");
			// Direi di fare una pausa e di richiamare la stessa funzione
			}
				
			if (connection.getErrorStream() != null)
				in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			while ((temp = in.readLine()) != null) {
				if (temp.contains("must be logged") || temp.contains("are not public")  ) {
					in.close();
					OutputTxt.writeLog("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
					return;
				}
				else if (temp.contains("many")) {
					in.close();
					DatabaseMySql.inserToCheck("utenti", user, 9999);
					API.notifyFlood(tabella, user);
					return;
				}	
				else if (temp.contains("not found")) {
					in.close();
					OutputTxt.writeLog("Errore 404: User not found: " + user);
					return; 
				}
			}
		} catch (IOException e) { 
			e.printStackTrace();
			OutputTxt.writeException(e.getLocalizedMessage());
	        OutputTxt.writeException("Errore nel getErrorCode dell'utente: " + user);	
		}
		try {in.close();} catch (IOException e) {}
		return;
	}
    
   
    
    private static URL metafeedUrl;
    private static BufferedReader in;
    private static int tot;
    private static String inputLine;  
}