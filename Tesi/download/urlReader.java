package download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import database.*;

public class urlReader  {
	
	public urlReader () { }
	
	public static boolean userReader (String tabella, String user) {
		return userReader (tabella, user, 0);
	}
	
    public static boolean userReader (String tabella, String user, int n) {
    	System.out.println("User reader: " + tabella + " dell'utente: " + user);
    	boolean flag = false;
    	String inputLine;
    	
    	try {
    	metafeedUrl = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + tabella + "&start=" + n);
    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("channel-box-item-count")) {
				if (n == 0) {
					tot = getCount(inputLine);
				}
			}
			else if (inputLine.contains("<div class=\"user-thumb-xlarge\"><div>")) {
		    	flag = true;
		    	inputLine = in.readLine();
		    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
		    	n++;
		    	// IMPOSTARE CORRETTAMENTE INSERT IN BASE ALLA LISTA, DIREI CON UNA SERIE DI IF
		    	System.out.println("Inserimento nella tabella " + tabella + " della tupla: " +
		    			 user + " + " + inputLine);
		    	OutputTxt.writeLog("Inserimento nella tabella " + tabella + " della tupla: " 
		    			+ user + " + " + inputLine);
		    	DatabaseMySql.insert("utenti", tabella , user, inputLine);	
		    }  // AGGIUNGERE I VARI CONTROLLI
		    else if (inputLine.contains("Questo account è stato") || inputLine.contains("Non è")) {
		    	OutputTxt.writeLog("Lista " + tabella + " non visualizzabile per l'utente " + user);
		    	in.close();
		    	return false;
		    }
		    else if (n == 1008) {
		    	System.out.println("Cap dei " + tabella + " raggiunto per l'utente " + user + ".");
		    	OutputTxt.writeLog("Cap dei " + tabella + " raggiunto per l'utente " + user + ".");
		    	in.close();
		    	return true;
		    }
		    if (inputLine.equals("</html>") && (tot - n) != 0){
		    	if (flag) {
		    		in.close();
		    		userReader(tabella, user, n);
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
	System.out.println("Fine di userReader di " + tabella + " dell'utente " + user);
	return true;
    }
    
    public static int getCount (String inputLine) {
    	 inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
    	 return Integer.parseInt(inputLine);

    }
    
    public static void favoritesApiReader (String user) {
    	favoritesApiReader(user , 1 , 0);
    }
    
    public static void favoritesApiReader (String user, int count, int n) {
    	System.out.println("\nFavorites reader dell'utente: " + user);
    	String inputLine, id, published, totale;
    	
      	try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50" +
					"&start-index=" + count);
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>"))
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI SUSCIRE E BASTA
				else if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					if (n == 0)
						tot = Integer.parseInt(totale);
				}
			    while (inputLine.contains("<entry><id>")) {				    	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry><id>") + 53 );
			    	id = inputLine.substring(0 , inputLine.indexOf("</id>"));
			    	published = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>"));
			    	count++;
			    	OutputTxt.writeLog("Inserimento nella tabella favorites della tupla: " 
			    			+ user + " - "+ id + " -  " + published );
			    	System.out.println(count-1 + ": Inserimento nella tabella favorites della tupla: " 
			    			+ user + " - "+ id + " -  " + published );
			    	if (count == 951) {
			    		OutputTxt.writeLog("Cap di favorites raggiunto per l'user: " + user);
			    		System.out.println("Cap di favorites raggiunto per l'user: " + user);
			    		return;
			    	}
			    }
			}
			if (count != 1 && count < tot && count != 951)
				favoritesApiReader (user, count, ++n);
		}catch (IOException e) {
			System.out.println("I preferiti dell'utente " + user + " non sono pubblici.\n");
		}
    }
    
	public static void videoApiReader (String user) {
		videoApiReader (user, 1, 0);
	}
	
	public static void videoApiReader (String user, int count, int n) {
    	System.out.println("\nVideo reader dell'utente: " + user);
    	String inputLine, id, published, totale;    	
      	try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?max-results=50" +
					"&start-index=" + count);
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>"))
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI SUSCIRE E BASTA
				else if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					if (n == 0)
						tot = Integer.parseInt(totale);
				}
			    while (inputLine.contains("<entry><id>")) {				    	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry><id>") + 53 );
			    	id = inputLine.substring(0 , inputLine.indexOf("</id>"));
			    	published = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>"));
			    	count++;
			    	OutputTxt.writeLog("Inserimento nella tabella video della tupla: " 
			    			+ user + " - "+ id + " -  " + published );
			    	System.out.println(count-1 + ": Inserimento nella tabella video della tupla: " 
			    			+ user + " - "+ id + " -  " + published );
			    	if (count == 951) {
			    		OutputTxt.writeLog("Cap dei video raggiunto per l'user: " + user);
			    		System.out.println("Cap dei video raggiunto per l'user: " + user);
			    		return;
			    	}
			    }
			}
			if (count != 1 && count < tot && count != 951) 
				videoApiReader (user, count, ++n);
		}catch (IOException e) {
			System.out.println("I video dell'utente " + user + " non sono pubblici.\n");
		}
    }
    
    public static void checkFlood (String inputLine, String tabella, String user) {
		if (inputLine.contains("Siamo spiacenti per l'interruzione")) {
			OutputTxt.writeLog("Rete floodata.");    // DA RIFAREEEEE
			System.out.println("Rete floodata.");
		     try {
		    	 DatabaseMySql.insert("utenti", tabella , user);
		    	 System.out.println(("Reinserisco fra i contatti da controllare: " + user));
		         Thread.currentThread();
		         Thread.sleep(1800000);	 // Pausa di 30 minuti
		         }
		      catch (InterruptedException e) {
		         e.printStackTrace();
		         }
		}
    }
    
    private static URL metafeedUrl;
    private static BufferedReader in;
    private static int tot;
}