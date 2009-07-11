package download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import database.*;

public class urlReader  {
	
	public urlReader () { }
	
	public static boolean userReader (String tabella, String user) {
		Vector<String> userTemp = new Vector<String>();
		return userReader (tabella, user, 0, 0, userTemp);
	}
	
    public static boolean userReader (String tabella, String user, int n, int effettivi, Vector<String> userTemp) {    		
    	System.out.println(tabella + " reader: dell'utente: " + user);
    	boolean flag = false;
    	String inputLine;    	
    	try {
    	metafeedUrl = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + tabella + "&start=" + n);
    	Contatore.incUrl();
    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
    	
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("Siamo spiacenti per l'interruzione"))
				urlReader.checkFlood(inputLine, "","");
			else if (inputLine.contains("channel-box-item-count")) {
				if (n == 0) {
					tot = getCount(inputLine);
				}
			}
			else if (inputLine.contains("<div class=\"user-thumb")) {
		    	flag = true;
		    	inputLine = in.readLine();
		    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
		    	n++;
		    	if (!userTemp.contains(inputLine)) {
		    		userTemp.add(inputLine);
		    		System.out.println(++effettivi +": Inserimento nella tabella " + tabella + 
		    				" della tupla: "+ user + " - " + inputLine);
		    		if (tabella.equals("subscribers"))  { // Dati gli iscritti al mio canale 
		    				// posso anche vederli come loro iscrizioni (subscriptions) a me
			    		System.out.println(effettivi +": Inserimento nella tabella subscription " +
			    				"della tupla: "+ inputLine + " - " + user);
			    		DatabaseMySql.insert("utenti", "subscriptions" , inputLine, user);
		    		}
		    	DatabaseMySql.insert("utenti", tabella , user, inputLine);	
		    	}
		    }  
			else if(tot - n == 0 && n != 0)  {
				in.close();
				return true;
			}
		    else if (inputLine.contains("Non è")) {
				in.close();
		    	OutputTxt.writeLog("Errore 403: Informazione non pubblica: " + tabella + " dell' user " + user);
		    	return false;
		    }
		    else if (inputLine.contains("non ha")) {
				in.close();
		    	OutputTxt.writeLog("Errore: L' utente " + user + " non ha aggiunto amici.");
		    	return false;
		    }
		    else if (inputLine.contains("Questo account è stato")) {
				in.close();
		    	OutputTxt.writeLog("Errore 404: User not found: " + user);
		    	return false;
		    }
		    else if (n == 1008) {
				in.close();
		    	System.out.println("Cap dei " + tabella + " raggiunto per l'utente " + user + ".");
		    	return true;
		    }
		    if (inputLine.equals("</html>")){
		    	if (flag) {
		    		in.close();
		    		userReader(tabella, user, n, effettivi, userTemp);
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
	catch (MalformedURLException e) { 
		e.printStackTrace();
		OutputTxt.writeException(e.getLocalizedMessage()); 
        OutputTxt.writeException("Errore nel " + tabella + "Reader dell'utente: " + user);	
	}
	catch (IOException e) {  
		e.printStackTrace();
		OutputTxt.writeException(e.getLocalizedMessage());
        OutputTxt.writeException("Errore nel " + tabella + "Reader dell'utente: " + user);	
	}
	System.out.println("Fine del " + tabella + " reader dell'utente " + user);
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
			Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>")) {
					in.close();
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI SUSCIRE E BASTA
				}
				else if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					if (n == 0)
						tot = Integer.parseInt(totale);
				}
			    while (inputLine.contains("<entry><id>")) {				    	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry><id>") + 53 );
			    	id = inputLine.substring(0 , inputLine.indexOf("</id>"));
			    	published = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>")-5);
			    	count++;
			    	System.out.println(count-1 + ": Inserimento nella tabella favorites della tupla: " 
			    			+ user + " - "+ id + " - " + published );
			    	DatabaseMySql.insert("utenti", "favorites" , user, id, published);
			    	if (count == 951) {
						in.close();
			    		System.out.println("Cap di favorites raggiunto per l'user: " + user);
			    		return;
			    	}
			    }
			}
			in.close();
			if (count != 1 && (count-1) < tot && count != 951)
				favoritesApiReader (user, count, ++n);
		}catch (IOException e) {
			getErrorCode("favorites", metafeedUrl, user);
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
			Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>"))
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI USCIRE E BASTA
				else if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					if (n == 0)
						tot = Integer.parseInt(totale);
				}
			    while (inputLine.contains("<entry><id>")) {				    	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry><id>") + 53 );
			    	id = inputLine.substring(0 , inputLine.indexOf("</id>"));
			    	published = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>")-5);
			    	count++;
			    	System.out.println(count-1 + ": Inserimento nella tabella video della tupla: " 
			    			+ user + " - "+ id + " - " + published );
			    	DatabaseMySql.insert("utenti", "video" , user, id, published);
			    	if (count == 951) {
			    		System.out.println("Cap dei video raggiunto per l'user: " + user);
			    		in.close();
			    		return;
			    	}
			    }
			}
			in.close();
			if (count != 1 && (count-1) < tot && count != 951) {
				videoApiReader (user, count, ++n);
			}
		}catch (IOException e) {
			OutputTxt.writeLog("Errore api dell'user " + user);
			getErrorCode("videos", metafeedUrl, user);
		}
    }
	
	public static void activityApiReader (String users) {
		activityApiReader (users, 1, 0);
	}
	
	public static void activityApiReader (String users, int count, int n) {
    	System.out.println("\n Activity reader degli utenti: " + users);
    	String inputLine, temp, actionTemp, userTemp, updated, totale, id;    	
      	try {
      		metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?v=2&key=" + developerKey +
      				"&author=" + users + "&max-results=50&start-index=" + count);
      		Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					System.out.println(totale);
					if (n == 0)
						tot = Integer.parseInt(totale);
				}
				while(inputLine.contains("<entry gd")) {
			    	inputLine = inputLine.substring(inputLine.indexOf("updated"));
			    	updated = inputLine.substring(8 , inputLine.indexOf("</updated>") - 5);
			    	if (inputLine.contains("</title><logo>"))
			    		inputLine = inputLine.substring(inputLine.indexOf("</title><logo>") + 8);
			    	inputLine = inputLine.substring(inputLine.indexOf("<title>") + 7);
			    	temp = inputLine.substring(0, inputLine.indexOf("</title>"));
			    	userTemp = temp.substring(0, temp.indexOf(" ")); 
			    	temp = temp.substring(userTemp.length() + 5);
			    	actionTemp = temp.substring(0, temp.indexOf(" "));
			    	temp = temp.substring(actionTemp.length() + 3);
			    	count++;
			    	if (actionTemp.contains("added")) {
			    		id = inputLine.substring(inputLine.indexOf("<yt:username>")+13, inputLine.indexOf("</yt:username>"));
			    		System.out.println(count - 1 + ": " + userTemp + " has " + actionTemp + " a " + temp + ": " + id);
			    		System.out.println(updated);
			    		DatabaseMySql.insert("utenti", "activity" , userTemp, id, actionTemp + " " + temp, updated);
			    	}
			    	else {
			    		id = inputLine.substring(inputLine.indexOf("<yt:videoid>")+12, inputLine.indexOf("</yt:videoid>"));
			    		System.out.println(count - 1 + ": " + userTemp + " has " + actionTemp + " " + id);
			    		System.out.println(updated);
			    		DatabaseMySql.insert("utenti", "activity" , userTemp, id, actionTemp , updated);
			    	}  
			    	if (count == 951) {
			    		in.close();
			    		return;
			    	}
			    }
			}
			in.close();
			if (count != 1 && (count-1) < tot && count != 951) 
				activityApiReader (users, count, ++n);
		}catch (IOException e) {
			OutputTxt.writeLog("Errore nell'activity api degli user " + users);
			getErrorCode("activity", metafeedUrl, "utenti");
		}
    }
	
    public static void checkFlood (String inputLine, String tabella, String user) {
			OutputTxt.writeLog("Rete floodata dalle URL.");    // DA RIFAREEEEE
			OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
			OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
			Contatore.setApi(0);
			Contatore.setUrl(0);
			System.out.println("Rete floodata dalle URL.");
		     try {
		    	// DatabaseMySql.insert("utenti", tabella , user);
		    	// System.out.println(("Reinserisco fra i contatti da controllare: " + user));
		         Thread.currentThread();
		         Thread.sleep(1800000);	 // Pausa di 30 minuti
		         }
		      catch (InterruptedException e) { 
		  		e.printStackTrace();
		    	  OutputTxt.writeException(e.getLocalizedMessage());
		          OutputTxt.writeException("Errore nel checkFlood delle URL");	
		         }
    }
    
    public static void getErrorCode (String tabella ,URL url ,String user) {
		HttpURLConnection connection;
		try {
			String temp;		
			Contatore.incApi();
			connection = (HttpURLConnection) url.openConnection();
			
			if (connection.getResponseCode() >= 500) {
				OutputTxt.writeLog("Errore 500+ : servizio non disponibile al momento.");
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
					OutputTxt.writeLog("Errore 403: Rete floodata dalle API dall'user " + user);
					// Reinserire contatto ed eliminare System.exit
					System.exit(0);
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
	}
    
    public static void popularReader () {
    	popularReader("t", 1);
    	popularReader("w", 1);
    	popularReader("m", 1);
    }
    
    public static void popularReader (String time) {
    	popularReader(time, 1);
    }
    
    public static void popularReader (String time, int pag) {
    		int tmp = 24 * (pag - 1);
        	System.out.println("\n popularReader reader del " + time);
        	String inputLine;
          	try {
          		metafeedUrl = new URL("http://www.youtube.com/members?&t=" + time + "&p=" + pag);
          		Contatore.incUrl();
            	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
            	
          		while ((inputLine = in.readLine()) != null) {
        			if (inputLine.contains("Siamo spiacenti per l'interruzione"))
        				urlReader.checkFlood(inputLine, "","");  // GESTIRE ANCHE SE E' DIFFICILE CHE SI VERIFICHI 
        			else if (inputLine.contains("<div class=\"channel-short-title\">")) {
        		    	inputLine = in.readLine();
        		    	inputLine = inputLine.substring(19, inputLine.indexOf("\" title"));
        		    	tmp++;
        		    	System.out.println(tmp +": Inserimento del popular da controllare: " + inputLine);
        		    	DatabaseMySql.insert("utenti", "popToCheck" , inputLine, Orario.getDataOra(), time);	
        		    }  
        		    if (inputLine.equals("</html>")){
        	        	if (++pag <= 5) { 
        		    		in.close();
            				popularReader (time, pag);
            				return;
        		    	}
        		    	else {
        		    		in.close();
        		    		return;
        		    	}
        		    }
        		}
          		in.close();
        	}
        	catch (MalformedURLException e) {  
        		e.printStackTrace();
        		OutputTxt.writeException(e.getLocalizedMessage());
                OutputTxt.writeException("Errore nel popularReader del tipo " + time + " alla pag: " + pag);	
                } 					
        	catch (IOException e) { 
    			OutputTxt.writeLog("Errore nel download dei canali più popolari del " + time);
    			getErrorCode("activity", metafeedUrl, "utenti");
    		}
    }
    
    private static URL metafeedUrl;
    private static BufferedReader in;
    private static int tot;
    private final static String developerKey = "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ";
}