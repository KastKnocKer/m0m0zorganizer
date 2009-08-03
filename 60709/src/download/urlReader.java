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
			    	DatabaseMySql.insert(nomeDB, tabella , user, inputLine, tot);
			    	DatabaseMySql.inserToCheck(nomeDB, inputLine);
				}
				else if (inputLine.contains("non ha") && count <= 1) {
					in.close();
			    	OutputTxt.writeLog(nomeDB, "Errore: L' utente " + user + " non ha aggiunto " + tabella + ".");
			    	return true;
			    }
			    else if (inputLine.contains("Non è")) {
					in.close();
			    	OutputTxt.writeLog(nomeDB, "Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
			    	DatabaseMySql.insert(nomeDB, "infoCorrupted", user, tabella, "Reserved");
			    	DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".profile set status='corrupted' where user='" + user + "'");
			    	return true;
			    }
			    else if (inputLine.contains("Questo account è stato")) {
					in.close();
			    	OutputTxt.writeLog(nomeDB, "Errore 404: User not found: " + user);
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
					OutputTxt.writeLog(nomeDB, "Youtube down per manutenzione o non al 100%");
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
    		OutputTxt.writeException(nomeDB, e.getLocalizedMessage()); 
    		OutputTxt.writeException(nomeDB, "Errore nel " + tabella + "Reader dell'utente: " + user);	
    	}
    	catch (IOException e) {  
    		if(e.getMessage().contains("Server returned HTTP response code: 50")) {
				System.out.println("Errore 500+ : servizio non disponibile al momento.");
				OutputTxt.writeLog(nomeDB, "Errore 500+ : servizio non disponibile al momento sulla tabella " + tabella +
						" dell'utente " + user);
				DatabaseMySql.insert500error(nomeDB, tabella, user);
    			return false;
    		}
    	}
    	catch (StringIndexOutOfBoundsException e) {
    		OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
    		OutputTxt.writeException(nomeDB, "Errore CONOSCIUTO E CONTROLLATO " + tabella + "Reader dell'utente: " + user);	
    		return true;
    		// Pensare come gestire: farei controllo se ci sono amici/subscribers nel db o se no segnalo no amici/sub
    	}
    	try {
    		in.close();
    	} catch (IOException e) {
    		OutputTxt.writeError(nomeDB, "Errore IO nella  " + tabella + " reader dell'utente " + user);
    		return true;
    	}
    	System.out.println("Fine del " + tabella + " reader dell'utente " + user);
    	return true;
    }
	
    public static void notifyUrlFlood (String nomeDB, String inputLine, String user) {
			OutputTxt.writeLog(nomeDB, "Rete floodata dalle URL.");    // DA RIFAREEEEE
			OutputTxt.writeLog(nomeDB, "Richieste API: " + Contatore.getApi());
			OutputTxt.writeLog(nomeDB, "Richieste URL: " + Contatore.getUrl());
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
	            OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
	            OutputTxt.writeException(nomeDB, "Errore nel notifyFlood dell'utente.");
			}
			return;
    }
    
    public static void pausa(String nomeDB, int sec, String user) {
    	try {
    		System.out.println("Pausa per il DB: " + nomeDB + " di " + sec + " secondi sull'utente " + user);
    		DatabaseMySql.delete(nomeDB, "profile", "user", user);
    		DatabaseMySql.delete(nomeDB, "toCheck", "user", user);
    		//if (DatabaseMySql.contiene(nomeDB, "popular", "user", user))
    		//	tot = DatabaseMySql.getMinPriority(nomeDB) + 1;
    		//else {
    			tot = DatabaseMySql.getMinPriority(nomeDB);
    			tot = tot + ((-tot) / 10) + 1;
    		//	}
    		System.out.println("Priorità selezionata per l'utente " + user + ": " + tot);
    		DatabaseMySql.inserToCheck(nomeDB, user, tot);
    		Thread.currentThread();
    		Thread.sleep(sec * 1000);	 // pausa(nomeDB, (nomeDB,  di sec secondi
    	}
    	catch (InterruptedException e) { 
    		e.printStackTrace();
    		OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
    		OutputTxt.writeException(nomeDB, "Errore nella funzione pausa(nomeDB, (nomeDB, .");	
    	}
    }
    
    public static void getVideoUploader (String videoId) {
		try {
			temp1 = "";
			temp2 = "";
			temp3 = "";
			temp4 = "";
			System.out.println("ANALISI per il video: " + videoId);
			metafeedUrl = new URL ("http://gdata.youtube.com/feeds/api/videos/" + videoId);
			ethernet.checkEthernet("root");
			Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("<published>"))
					temp1 = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>") - 5);
				if (inputLine.contains("<name>"))
					temp2 = inputLine.substring(inputLine.indexOf("<name>") + 6, inputLine.indexOf("</name>"));
				if (inputLine.contains("numRaters=")) {
					inputLine = inputLine.substring(inputLine.indexOf("numRaters=") + 11);
					temp3 = inputLine.substring(0, inputLine.indexOf("'"));
				}
				if (inputLine.contains("favoriteCount=")) {
					temp5 = inputLine;
					temp5 = temp5.substring(temp5.indexOf("viewCount=") + 11);
					temp5 = temp5.substring(0, temp5.indexOf("'/"));
					temp4 = inputLine.substring(inputLine.indexOf("favoriteCount=") + 15, inputLine.indexOf("viewCount") - 2);
					
				}
			}
			DatabaseMySql.insert("root", "videoUploadedBy", temp2, videoId);
			DatabaseMySql.insert("root", "videoChecked", temp2, videoId, temp1, temp3, temp4, temp5);
			temp1 = "";
			temp2 = "";
			temp3 = "";
			temp4 = "";
		} catch (MalformedURLException e) {
			if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", videoId))
				DatabaseMySql.insert("root", "videoToCheck", videoId);
		} catch (IOException e) {
			if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", videoId))
				DatabaseMySql.insert("root", "videoToCheck", videoId);
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
				OutputTxt.writeLog(nomeDB, "Errore 500+ : servizio non disponibile al momento. Analisi per il DB: "+ nomeDB + "dell'utente " + user);
				System.out.println("Errore 500+ : servizio non disponibile al momento. Analisi per il DB: "+ nomeDB + "dell'utente " + user);
				DatabaseMySql.insert500error(nomeDB, tabella, user);
				return false;
				}
			else if (msg.contains("Forbidden") ||
					connection.getResponseMessage().contains("are not public")) {
				DatabaseMySql.insert(nomeDB, "infoCorrupted", user, tabella, "Reserved");
				if (DatabaseMySql.contiene(nomeDB, "profile", "user", user))
					DatabaseMySql.eseguiAggiornamento("Update " + nomeDB + ".profile set status='corrupted' where user='" + user + "'");
				else {
					DatabaseMySql.insert(nomeDB , "profile", user, "corrupted", Orario.getDataOra(), 0, 0, 0, "profreserv");
				}
				System.out.println("Errore 403: Informazione per il DB: "+ nomeDB + " non pubblica: " + tabella + " dell' user " + user);
				OutputTxt.writeLog(nomeDB, "Errore 403: Informazione per il DB: "+ nomeDB + " non pubblica: " + tabella + " dell' user " + user);
				return false;
			}
			else if (msg.contains("many")) {
				API.notifyApiFlood(nomeDB, tabella, user);
				return false;
			}	
			else if (code == 404) {
				System.out.println("Errore 404: User not found: " + user);
				OutputTxt.writeLog(nomeDB, "Errore 404: per il DB: "+ nomeDB + " User not found: " + user);
				return false; 
			}
			else if (msg.contains("Bad Request")) {
				OutputTxt.writeError(nomeDB, "Errore per il DB: "+ nomeDB + " bad request all'url: " + url);
				System.out.println("Errore bad request all'url: " + url);
				return false;
			}	
		} catch (IOException e) { 
			e.printStackTrace();
			OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
	        OutputTxt.writeException(nomeDB, "Errore nel getErrorCode dell'utente: " + user);	
	        return true;
		}
		return true;
	}
    
   
    
    private static URL metafeedUrl;
    private static BufferedReader in;
    private static int tot;
    private static String inputLine, temp1 , temp2, temp3, temp4, temp5;  
}