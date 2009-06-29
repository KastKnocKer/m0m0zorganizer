package download;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import database.*;


public class urlReader  {
	
	public urlReader () { }
	
	public static boolean userReader (String lista, String user) {
		return userReader (lista, user, 0);
	}
	
    public static boolean userReader (String lista, String user, int n) {
    	System.out.println("User reader: " + lista + " dell'utente: " + user);
    	boolean flag = false;
    	String inputLine;
    	
    	try {
		URL urlFriend = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + lista + "&start=" + n);
		BufferedReader in = new BufferedReader(	new InputStreamReader(urlFriend.openStream()));
		
		while ((inputLine = in.readLine()) != null) {
		    if (inputLine.contains("<div class=\"user-thumb-xlarge\"><div>")) {
		    	flag = true;
		    	inputLine = in.readLine();
		    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
		    	n++;
		    	// IMPOSTARE CORRETTAMENTE INSERT IN BASE ALLA LISTA, DIREI CON UNA SERIE DI IF
		    	if (!(DatabaseMySql.contiene( inputLine , "username") || DatabaseMySql.contiene(inputLine , "bloccati"))) {	
		    		DatabaseMySql.insert("utenti", "contatti", inputLine);	
		    		System.out.println(n + ": inserimento nei contatti di " + inputLine);
		    	}
		    }  // AGGIUNGERE I VARI CONTROLLI
		    else if (inputLine.contains("Questo account è stato")) {
		    	System.out.println("Errore: account sospeso o bloccato.");
		    	in.close();
		    	return false;
		    }
		    else if (n == 1008) {
		    	System.out.println("Errore: Cap per utente raggiunto.");
		    	in.close();
		    	return true;
		    }
		    if (inputLine.equals("</html>")){
		    	if (flag) {
		    		in.close();
		    		userReader(lista, user, n);
		    		return true;
		    	}
		    	else {
		    		in.close();
		    		return false;
		    	}
		    }

		}
		in.close();
	}
	catch (StringIndexOutOfBoundsException e) { System.out.println("Errore");}
	catch (MalformedURLException e) { e.printStackTrace(); } 
	catch (IOException e) { e.printStackTrace(); }
	System.out.println("Fine di userReader dell'utente " + user);
	return true;
    }
    
    public static boolean friendCount (String user) {
    	System.out.println("FRIEND COUNT " + user);
    	try {
		URL urlFriend = new URL("http://www.youtube.com/profile?user=" + user + "&view=friends");
		BufferedReader in = new BufferedReader(	new InputStreamReader(urlFriend.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("Siamo spiacenti per l'interruzione")) {
				
				String controllo;				
				String orario = "PM";
				Calendar calendar = new GregorianCalendar();
				if (calendar.get(Calendar.AM_PM) == 0)
					orario = "AM";
				controllo = "Rete floodata. Ora: " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + 
				":" + calendar.get(Calendar.SECOND) + " " + orario;
				OutputTxt File = new OutputTxt();
				File.writeLog(controllo);    // DA RIFAREEEEE
				System.out.println(controllo);
			     try {
			    	 DatabaseMySql.insert("utenti", "contatti", user);
			    	 System.out.println(("Reinserisco fra i contatti da controllare: " + user));
			         Thread.currentThread().sleep(1800000);	 // Pausa di 30 minuti
			         in.close();
			         return true;
			         }
			       catch (InterruptedException e) {
			         e.printStackTrace();
			         }
			}
		    if (inputLine.contains("channel-box-item-count")) {
		    		inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
		    		DatabaseMySql.insert("utenti", "username", user, inputLine);
		        	System.out.println("insert into utenti.username values (\"" + user + "\" , \"" + inputLine + "\")");
			    	System.out.println(user + ": " + inputLine);
			    	in.close();
			    	return true;
		    }
		    if (inputLine.contains("Questo account è stato")) {
		    	System.out.println("Errore: account sospeso.");
		    	in.close();
		    	return false; 
		    }
		    if (inputLine.contains("Non è disponibile")) {
		    	urlFriend = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/contacts");
		    	in.close();
				try {
					in = new BufferedReader(new InputStreamReader(urlFriend.openStream()));
					while ((inputLine = in.readLine()) != null) {
						if (inputLine.contains("<openSearch:totalResults>")) {
				    		inputLine = inputLine.substring(inputLine.indexOf("totalResults>") + 13, inputLine.indexOf("</openSearch:"));
				    		System.out.println(" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + inputLine);
				    		DatabaseMySql.insert("utenti", "username", user, "**" +  inputLine + "**");
				    		in.close();
				    		return true;
						}
					}
				}
				catch (IOException e) {	
					in.close();
					return false; }
		    }
		}
	}
	catch (StringIndexOutOfBoundsException e) { System.out.println("Errore");}
	catch (MalformedURLException e) { e.printStackTrace(); } 
	catch (IOException e) { e.printStackTrace(); }
	System.out.println("Fine di friendCount dell'user " + user);
	return false;
    }
}