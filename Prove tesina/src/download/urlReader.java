package download;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import database.*;


public class urlReader  {
	
	public urlReader () { }
	
    public static boolean friendReader (String user, int n, ConnessioneMySql db) {
    	System.out.println("FRIEND READER " + user);
    	
    	boolean flag = false;
    	
    	try {
		URL urlFriend = new URL("http://www.youtube.com/profile?user=" + user + "&view=friends&start=" + n);
		BufferedReader in = new BufferedReader(	new InputStreamReader(urlFriend.openStream()));
		
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
		    if (inputLine.contains("<div class=\"user-thumb-xlarge\"><div>")) {
		    	flag = true;
		    	inputLine = in.readLine();
		    	inputLine = inputLine.substring(15, inputLine.indexOf("\" onmousedown"));
		    	n++;
		    	if (!(ConnessioneMySql.contiene( inputLine , "username") || ConnessioneMySql.contiene(inputLine , "bloccati"))) {
		    		ConnessioneMySql.eseguiAggiornamento("insert into utenti.contatti values (\"" + inputLine + "\")");	
		    		System.out.println(n + ": inserimento di " + inputLine);
		    	}
		    }
		    else if (inputLine.contains("Questo account è stato")) {
		    	flag = true;
		    	System.out.println("Errore: account sospeso o bloccato.");
		    	return false;
		    }
		    else if (n == 1008) {
		    	System.out.println("Errore: Cap per utente raggiunto.");
		    	return true;
		    }
		    if (inputLine.equals("</html>")){
		    	if (flag) {
		    		in.close();
		    		friendReader(user, n, db);
		    		return true;
		    	}
		    	else 
		    		return false;
		    }

		}
	}
	catch (StringIndexOutOfBoundsException e) { System.out.println("Errore");}
	catch (MalformedURLException e) { e.printStackTrace(); } 
	catch (IOException e) { e.printStackTrace(); }
	System.out.println("Fine di friendReader dell'user " + user);
	return true;
    }
    
    public static boolean friendCount (String user, ConnessioneMySql db) {
    	System.out.println("FRIEND COUNT " + user);
    	try {
		URL urlFriend = new URL("http://www.youtube.com/profile?user=" + user + "&view=friends");
		BufferedReader in = new BufferedReader(	new InputStreamReader(urlFriend.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
		    if (inputLine.contains("channel-box-item-count")) {
		    		inputLine = inputLine.substring(inputLine.indexOf(">") + 1, inputLine.indexOf("</span>"));
		    		ConnessioneMySql.eseguiAggiornamento("insert into utenti.username values (\"" + user + "\" , \"" + inputLine + "\")");
			    	System.out.println("insert into utenti.username values (\"" + user + "\" , \"" + inputLine + "\")");
			    	System.out.println(user + ": " + inputLine);
			    	return true;
		    }
		    if (inputLine.contains("Questo account è stato")) {
		    	System.out.println("Errore: account sospeso.");
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
				    		ConnessioneMySql.eseguiAggiornamento("insert into utenti.username values (\"" + user + "\" , \"**" + inputLine + "**\")");
				    		return true;
						}
					}
				}
				catch (IOException e) {	return false; }
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