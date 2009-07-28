package download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import database.*;

public class urlReader  {
	
	public urlReader () { }
		
	public static boolean userReader (String nomeDB, String tabella, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + " dei " + tabella + " dell' utente " + user + ".");
		return userReader (nomeDB, tabella, user, 0);
	}
	
    public static boolean userReader (String nomeDB, String tabella, String user, int count) {
    	if (!tabella.equals("subscribers") && !tabella.equals("friends")) {
    		System.out.println("Errore utente: Inserire correttamente parametro tabella nel " + tabella + 
    				"Reader dell'utente" + user);
    		System.exit(0);
    	}
    	try {
	    	metafeedUrl = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + tabella + "&start=" + count);
	    	ethernet.checkEthernet(nomeDB);
	    	Contatore.incUrl();
	    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			while ((inputLine = in.readLine()) != null) {
				if (count != 0 && (count == 1008 || count == tot)) {
			    	in.close();
			    	System.out.println("Cap dei " + tabella + " raggiunto per l'utente " + user + ".");
			    	return true;
			    }
				if (inputLine.contains("channel-box-item-count")) {
			    	 inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
			    	 tot = Integer.parseInt(inputLine);    	
			    }
				else if (inputLine.contains("<div class=\"user-thumb")) {
			    	inputLine = in.readLine();
			    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
			    	count++;
			    	DatabaseMySql.insert(nomeDB, tabella , user, inputLine, tot  + "");
			    	DatabaseMySql.inserToCheck(nomeDB, inputLine);
				}
				else if (inputLine.contains("non ha") && count <= 1) {
					in.close();
			    	OutputTxt.writeLog("Errore: L' utente " + user + " non ha aggiunto " + tabella + ".");
			    	return true;
			    }
			    else if (inputLine.contains("Non è")) {
					in.close();
			    	OutputTxt.writeLog("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
			    	DatabaseMySql.insert(nomeDB, "infoReserved", user, tabella);
			    	return true;
			    }
			    else if (inputLine.contains("Questo account è stato")) {
					in.close();
			    	OutputTxt.writeLog("Errore 404: User not found: " + user);
			    	return false;
			    }
			    else if (inputLine.equals("</html>") && count != 0 && count < tot) {
			    	in.close();
			    	System.out.println(tabella + " dell'user " + user + " scaricati fino al num: " + count + ".");
			    	System.out.println("\t\t\tTotale " + tabella + " per l'user " + user + ": " + tot);
			    	userReader(nomeDB, tabella, user, count);
			    	return true;
			    }
				else if (inputLine.contains("is down for") || inputLine.contains("manutenzione")) {
					in.close();
					OutputTxt.writeLog("Youtube down per manutenzione o non al 100%");
					System.out.println("Youtube down per manutenzione o non al 100%. Pausa di  45 secondi.");  
					pausa(nomeDB, 45, user);
					return false;
				}
				else if (inputLine.contains("Siamo spiacenti per l'interruzione")) {
					notifyUrlFlood(nomeDB, inputLine, user);	
					return false;
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
				System.out.println("Errore 500+ : servizio non disponibile al momento.");
				OutputTxt.writeLog("Errore 500+ : servizio non disponibile al momento.");
				DatabaseMySql.insertError(nomeDB, user);
    			return false;
    		}
    	}
    	catch (StringIndexOutOfBoundsException e) {
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore CONOSCIUTO E CONTROLLATO " + tabella + "Reader dell'utente: " + user);	
    		return true;
    		// Pensare come gestire: farei controllo se ci sono amici/subscribers nel db o se no segnalo no amici/sub
    	}
    	try {
    		in.close();
    	} catch (IOException e) {
    		OutputTxt.writeError("Errore IO nella  " + tabella + " reader dell'utente " + user);
    		return true;
    	}
    	System.out.println("Fine del " + tabella + " reader dell'utente " + user);
    	return true;
    }
	
    public static void notifyUrlFlood (String nomeDB, String inputLine, String user) {
			OutputTxt.writeLog("Rete floodata dalle URL.");    // DA RIFAREEEEE
			OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
			OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
			Contatore.setApi(0);
			Contatore.setUrl(0);
			System.out.println("Rete floodata dalle URL.");
			System.out.println("pausa di 30 minuti per flood URL");
			try {
				DatabaseMySql.delete(nomeDB, "profile", "user", user);
	    		DatabaseMySql.delete(nomeDB, "toCheck", "user", user);
				DatabaseMySql.inserToCheck(nomeDB, user, -9999);
				Thread.currentThread();
				Thread.sleep(1800000);	 // pausa 
			}
			catch (InterruptedException e) { 
				e.printStackTrace();
	            OutputTxt.writeException(e.getLocalizedMessage());
	            OutputTxt.writeException("Errore nel notifyFlood dell'utente.");
			}
			return;
    }
    
    public static void pausa(String nomeDB, int sec, String user) {
    	try {
    		System.out.println("Pausa per il DB: " + nomeDB + " di " + sec + " secondi sull'utente " + user);
    		DatabaseMySql.delete(nomeDB, "profile", "user", user);
    		DatabaseMySql.delete(nomeDB, "toCheck", "user", user);
    		tot = DatabaseMySql.getMinPriority();
    		tot = tot + ((-tot) / 10) + 1;
    		System.out.println("Priorità selezionata per l'utente " + user + ": " + tot);
    		DatabaseMySql.inserToCheck(nomeDB, user, tot);
    		Thread.currentThread();
    		Thread.sleep(sec * 1000);	 // pausa(nomeDB, (nomeDB,  di sec secondi
    	}
    	catch (InterruptedException e) { 
    		e.printStackTrace();
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore nella funzione pausa(nomeDB, (nomeDB, .");	
    	}
    }
    
    public static void getVideoUploader (String nomeDB, String videoId, int N) {
		try {
			metafeedUrl = new URL ("http://gdata.youtube.com/feeds/api/videos/" + videoId);
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("<published>"))
					temp1 = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>"));
				if (inputLine.contains("<name>"))
					temp2 = inputLine.substring(inputLine.indexOf("<name>") + 6, inputLine.indexOf("</name>"));
				if (inputLine.contains("numRaters="))
					temp3 = inputLine.substring(inputLine.indexOf("numRaters=") + 11, inputLine.indexOf("rel") - 2);
				if (inputLine.contains("favoriteCount="))
					temp4 = inputLine.substring(inputLine.indexOf("favoriteCount=") + 15, inputLine.indexOf("viewCount") - 2);
			}
			DatabaseMySql.insert(nomeDB, "videoUploadedBy", temp2, videoId, temp1, temp3, temp4);
			temp1 = "";
			temp2 = "";
			temp3 = "";
			temp4 = "";
		} catch (MalformedURLException e) {
			if (!DatabaseMySql.contiene(nomeDB, "videoUploadedBy", "id", videoId))
				DatabaseMySql.insert(nomeDB, "videoToCheck", videoId);
		} catch (IOException e) {
			if (!DatabaseMySql.contiene(nomeDB, "videoUploadedBy", "id", videoId))
				DatabaseMySql.insert(nomeDB, "videoToCheck", videoId);
		}
    }
    
    public static boolean getErrorCode (String nomeDB, String tabella ,URL url ,String user) {
    	String msg;
    	int code;
    	System.out.println("GetErrorCode per il DB: "+ nomeDB + " sui " + tabella  + " dell'utente " + user);
		HttpURLConnection connection;
		try {		
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			connection = (HttpURLConnection) url.openConnection();
			System.out.println((code = connection.getResponseCode()));
			System.out.println(msg = connection.getResponseMessage());
			if (code >= 500) {
				OutputTxt.writeLog("Errore 500+ : servizio non disponibile al momento. Analisi per il DB: "+ nomeDB);
				System.out.println("Errore 500+ : servizio non disponibile al momento. Analisi per il DB: "+ nomeDB);
				if (DatabaseMySql.insertError(nomeDB, user)) { // se ritorna true l'utente non viene ancora bloccato
					tot = DatabaseMySql.getMinPriority(); // con false l'utente viene inserito in .profile come utente bloccato
		    		tot = tot + ((-tot) / 10) + 1;
		    		System.out.println("Priorità selezionata per l'utente " + user + ": " + tot); 
		    		DatabaseMySql.inserToCheck(nomeDB, user, tot);
				}
				return false;
			// Direi di fare una pausa(nomeDB,  e di richiamare la stessa funzione
			}				
			else if (msg.contains("Forbidden") ||
					connection.getResponseMessage().contains("are not public")) {
				DatabaseMySql.insert(nomeDB, "infoReserved", user, tabella);
				System.out.println("Errore 403: Informazione per il DB: "+ nomeDB + " non pubblica: " + tabella + " dell' user " + user);
				OutputTxt.writeLog("Errore 403: Informazione per il DB: "+ nomeDB + " non pubblica: " + tabella + " dell' user " + user);
				return true;
			}
			else if (msg.contains("many")) {
				API.notifyApiFlood(nomeDB, tabella, user);
				return true;
			}	
			else if (code == 404) {
				System.out.println("Errore 404: User not found: " + user);
				OutputTxt.writeLog("Errore 404: per il DB: "+ nomeDB + " User not found: " + user);
				return false; 
			}
			else if (msg.contains("Bad Request")) {
				OutputTxt.writeError("Errore per il DB: "+ nomeDB + " bad request all'url: " + url);
				System.out.println("Errore bad request all'url: " + url);
				return true;
			}	
		} catch (IOException e) { 
			e.printStackTrace();
			OutputTxt.writeException(e.getLocalizedMessage());
	        OutputTxt.writeException("Errore nel getErrorCode dell'utente: " + user);	
	        return true;
		}
		return true;
	}
    
   
    
    private static URL metafeedUrl;
    private static BufferedReader in;
    private static int tot;
    private static String inputLine, temp1 , temp2, temp3, temp4;  
}