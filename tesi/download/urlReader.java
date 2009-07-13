package download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    	System.out.println(tabella + " reader: dell'utente: " + user);
    	String inputLine;    	
    	try {
    	metafeedUrl = new URL("http://www.youtube.com/profile?user=" + user + "&view=" + tabella + "&start=" + count);
    	Contatore.incUrl();
    	System.out.println(metafeedUrl);
    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
    	
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("Siamo spiacenti per l'interruzione"))
				urlReader.checkFlood(inputLine, "","");
			else if (inputLine.contains("is down for") || inputLine.contains("manutenzione")) {
				OutputTxt.writeLog("Youtube down per manutenzione o non al 100%");
				pausa(600);
				// REINSERIRE IL CONTATTO DA CONTROLLARE 
				// DIREI DI CHIUDERE IL BUFFER E DI PROSEGUIRE CON IL PROX UTENTE
			}
			else if (inputLine.contains("non ha") && count <= 1) {
				in.close();
		    	OutputTxt.writeLog("Errore: L' utente " + user + " non ha aggiunto amici.");
		    	return;
		    }
			else if (inputLine.contains("channel-box-item-count")) {
				tot = getCount(inputLine);
			}
			else if (inputLine.contains("<div class=\"user-thumb")) {
		    	inputLine = in.readLine();
		    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
		    	count++;
		    	if (tabella.equals("subscribers"))  { // Dati gli iscritti al mio canale 
		    		// posso anche vederli come loro iscrizioni (subscriptions) a me
		    		if (DatabaseMySql.insert("utenti", "subscriptions" , inputLine, user, ""))
		    			System.out.println(count + " : " + ++effettivi + ": Inserimento nella tabella " + tabella + 
			    				" della tupla: "+ user + " - " + inputLine);
		    		if(DatabaseMySql.insert("utenti", tabella , user, inputLine, ""))
		    			System.out.println(count + ": Inserimento nella tabella subscription " +
			    				"della tupla: "+ inputLine + " - " + user);
		    	}
		    	else
		    		if (DatabaseMySql.insert("utenti", tabella , user, inputLine))
		    			System.out.println(count + " : " + ++effettivi + ": Inserimento nella tabella " + tabella + 
			    				" della tupla: "+ user + " - " + inputLine);
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
		    if (inputLine.equals("</html>") && count != 0 && count < tot) {
		    		in.close();
		    		userReader(tabella, user, count, effettivi);
		    		return;
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
    	catch (StringIndexOutOfBoundsException e) {  
    		e.printStackTrace();
    		OutputTxt.writeException(e.getLocalizedMessage());
    		OutputTxt.writeException("Errore nel " + tabella + "Reader dell'utente: " + user);	
    	}
    	System.out.println("Fine del " + tabella + " reader dell'utente " + user);
    	return;
    }
    
    public static int getCount (String inputLine) {
    	 inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
    	 return Integer.parseInt(inputLine);

    }
    
    public static void favoritesApiReader (String user) {
    	favoritesApiReader(user , 0 , 0);
    }
    
    public static void favoritesApiReader (String user, int count, int n) {
    	System.out.println("\nFavorites reader dell'utente: " + user);
    	String inputLine, id, published, totale;
    	
      	try {
      		if(count == 950)
      			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50" +
					"&start-index=" + count);
      		else 
      			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50" +
    					"&start-index=" + (count + 1));
			Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>")) {
					in.close();
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI SUSCIRE E BASTA
				}
				else if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					tot = Integer.parseInt(totale);
				}
			    while (inputLine.contains("<entry")) {				    	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry") + 53 );
			    	id = inputLine.substring(0 , inputLine.indexOf("</id>"));
			    	published = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>")-5);
			    	count++;
			    	System.out.println(count + ": Inserimento nella tabella favorites della tupla: " 
			    			+ user + " - "+ id + " - " + published );
			    	DatabaseMySql.insert("utenti", "favorites" , user, id, published);
			    	if (count == 1000) {
						in.close();
			    		System.out.println("Cap di favorites raggiunto per l'user: " + user);
			    		return;
			    	}
			    }
			}
			in.close();
			if (count != 0 && count < tot)
				favoritesApiReader (user, count, ++n);
		}catch (IOException e) {
			getErrorCode("favorites", metafeedUrl, user);
		}
		catch (StringIndexOutOfBoundsException e) {  
			e.printStackTrace();
			OutputTxt.writeException(e.getLocalizedMessage());
	        OutputTxt.writeException("Errore nel favoritesApiReader dell'utente: " + user);	
		}
    }
    
	public static void videoApiReader (String user) {
		videoApiReader (user, 0, 0);
	}
	
	public static void videoApiReader (String user, int count, int n) {
    	System.out.println("\nVideo reader dell'utente: " + user);
    	String inputLine, id, published, totale;    	
      	try {
      		if(count == 950)
      			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?max-results=50" +
    					"&start-index=" + count );
      		else 
      			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?max-results=50" +
					"&start-index=" + (count + 1));
			Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>"))
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI USCIRE E BASTA
				else if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					tot = Integer.parseInt(totale);
				}
			    while (inputLine.contains("<entry")) {				    	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry") + 53 );
			    	id = inputLine.substring(0 , inputLine.indexOf("</id>"));
			    	published = inputLine.substring(inputLine.indexOf("<published>") + 11, inputLine.indexOf("</published>")-5);
			    	count++;
			    	System.out.println(count + ": Inserimento nella tabella video della tupla: " 
			    			+ user + " - "+ id + " - " + published );
			    	DatabaseMySql.insert("utenti", "video" , user, id, published);
			    	if (count == 1000) {
			    		System.out.println("Cap dei video raggiunto per l'user: " + user);
			    		in.close();
			    		return;
			    	}
			    }
			}
			in.close();
			if (count != 0 && count < tot) {
				videoApiReader (user, count, ++n);
			}
		}catch (IOException e) {
			OutputTxt.writeLog("Errore api dell'user " + user);
			getErrorCode("videos", metafeedUrl, user);
		}
		catch (StringIndexOutOfBoundsException e) {  
			e.printStackTrace();
			OutputTxt.writeException(e.getLocalizedMessage());
	        OutputTxt.writeException("Errore nel videoApiReader dell'utente: " + user);	
		}
    }
	
	public static void activityApiReader (String users) {
		activityApiReader (users, 0, 0);
	}
	
	public static void activityApiReader (String users, int count, int n) {
    	System.out.println("\n Activity reader degli utenti: " + users);
    	String inputLine, temp, actionTemp, userTemp, updated, totale, id;    	
      	try {
      		metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?v=2&key=" + developerKey +
      				"&author=" + users + "&max-results=50&start-index=" + (count + 1));
      		Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("<openSearch:total")) {
					totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
					tot = Integer.parseInt(totale);
				}
				while(inputLine.contains("<entry")) {
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
			    		System.out.println(count + ": " + userTemp + " has " + actionTemp + " a " + temp
			    				+ ": " + id + " - " + updated);
			    		DatabaseMySql.insert("utenti", "activity" , userTemp, id, actionTemp + " " + temp, updated);
			    	}
			    	else {
			    		id = inputLine.substring(inputLine.indexOf("<yt:videoid>")+12, inputLine.indexOf("</yt:videoid>"));
			    		System.out.println(count - 1 + ": " + userTemp + " has " + actionTemp + ": " + 
			    				id + " - " + updated);
			    		DatabaseMySql.insert("utenti", "activity" , userTemp, id, actionTemp , updated);
			    	}  
			    	if (count == 950) {
			    		in.close();
			    		return;
			    	}
			    }
				if (count == 0) 
					System.out.println("Errore: no activity per l'utente " + users);
			}
			in.close();
			if (count != 0 && count < tot) 
				activityApiReader (users, count, ++n);
		}catch (IOException e) {
			OutputTxt.writeLog("Errore nell'activityApiReader degli user " + users);
			getErrorCode("activity", metafeedUrl, "utenti");
		}
		catch (StringIndexOutOfBoundsException e) {  
			e.printStackTrace();
			OutputTxt.writeException(e.getLocalizedMessage());
	        OutputTxt.writeException("Errore nel activityApiReader dell'utente: " + users);	
		}
    }
	
	public static void subscriptionsApiReader (String user) {
		subscriptionsApiReader (user, 0, 0);
	}
	
	public static void subscriptionsApiReader (String user, int count, int n) {
    	System.out.println("\nSubscriptions reader dell'utente: " + user);
    	String inputLine, temp, subTemp = null, pubTemp = null, totale;    	
      	try {
      		if (count == 950)
    			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50" +
    					"&start-index=" + count);
      		else
    			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50" +
    					"&start-index=" + (count + 1));
			Contatore.incApi();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("</openSearch:itemsPerPage></feed>")) {
					in.close();
					return; // DA SISTEMARE QUANDO SI PARLERÀ DEL DATABASE MA DIREI DI USCIRE E BASTA
				}
				totale = inputLine.substring(inputLine.indexOf("totalResults")+ 13, inputLine.indexOf("</openSearch"));
				tot = Integer.parseInt(totale);
				
				while (inputLine.contains("<entry")) {	
			    	inputLine = inputLine.substring(inputLine.indexOf("<entry") + 2);
			    	temp = inputLine.substring(0 , inputLine.indexOf("</entry"));
			    	if (temp.contains("matching")){
			    		if (++count == 1000) {
			    			System.out.println("Cap dei video raggiunto per l'user: " + user);
				    		in.close();
				    		return;
			    		}
			    		else 
			    			continue;
			    	}
			    	else {
			    		temp = inputLine.substring(inputLine.indexOf("<published>"),
			    				inputLine.indexOf("</entry>"));
			    		pubTemp = temp.substring(11 , temp.indexOf("</published>") - 10);
			    		temp = temp.substring(temp.indexOf("<yt:username>") + 13);
			    		subTemp = temp.substring(0, temp.indexOf("</yt:username>"));
			    		count++;
			    	}
			    	System.out.println(count + ": Inserimento nella tabella subscriptions della tupla: " 
			    			+ user + " - "+ subTemp + " - " + pubTemp );
					DatabaseMySql.insert("utenti", "subscriptions" , user, subTemp, pubTemp);
					DatabaseMySql.insert("utenti", "subscribers" , subTemp, user, pubTemp);
					if (count == 1000) {
						System.out.println("Cap dei video raggiunto per l'user: " + user);
						in.close();
						return;
					}	
			    }
			}
			in.close();
			if (count != 0 && count < tot) 
				subscriptionsApiReader (user, count, ++n);	
			}catch (IOException e) {
				getErrorCode("subscriptions", metafeedUrl, user);
			}
			catch (StringIndexOutOfBoundsException e) {  
				e.printStackTrace();
				OutputTxt.writeException(e.getLocalizedMessage());
				OutputTxt.writeException("Errore nel subscriptionApiReader dell'utente: " + user);	
			}
		}
	
    public static void checkFlood (String inputLine, String tabella, String user) {
			OutputTxt.writeLog("Rete floodata dalle URL.");    // DA RIFAREEEEE
			OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
			OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
			Contatore.setApi(0);
			Contatore.setUrl(0);
			System.out.println("Rete floodata dalle URL.");
			pausa(1800);

    }
    
    public static void pausa(int sec) {
	     try {
		    	// DatabaseMySql.insert("utenti", tabella , user);
		    	// System.out.println(("Reinserisco fra i contatti da controllare: " + user));
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
			
			if (connection.getResponseCode() >= 500) {
				Contatore.incUrl();
				in = new BufferedReader(new InputStreamReader(new URL("http://www.youtube.com").openStream()));
				while ((temp = in.readLine()) != null) {
					if (temp.contains("is down for") || temp.contains("manutenzione")) {
						OutputTxt.writeLog("Youtube down per manutenzione o non al 100%");
						pausa(600);
						// REINSERIRE CONTATTO 
						return;
					}
				}
				in.close();
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
    		String[] temp;
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
        		    	if(DatabaseMySql.contiene(inputLine, "popToCheck")) {
        		    		temp = DatabaseMySql.eseguiExtractUser("utenti", "popToCheck", "user", inputLine);
        		    		DatabaseMySql.insert("utenti", "popToCheck" , inputLine, Orario.getDataOra(), temp[2] + "-" + time);	
        		    	}
        		    	else 
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