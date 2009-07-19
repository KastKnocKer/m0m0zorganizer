package download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;

public class popularReader {

	public popularReader () {
		popularReaderTool("t", 1);
		popularReaderTool("w", 1);
		popularReaderTool("m", 1);
	}	
	    
	public static void popularReaderTool (String time, int pag) {
		int count = 0;
		int tmp = 24 * (pag - 1);
		System.out.println("\n popularReader reader del " + time);
		String inputLine;
		try {
			metafeedUrl = new URL("http://www.youtube.com/members?&t=" + time + "&g=0&c=0&to=0&nb=0&p=" + pag);
			Contatore.incUrl();
			in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
			
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("<div class=\"channel-short-title\">")) {
					inputLine = in.readLine();
					inputLine = inputLine.substring(19, inputLine.indexOf("\" title"));
					System.out.println(++tmp +" : " + ++count + " Inserimento del popular da controllare: " + inputLine);
					DatabaseMySql.insert("utenti", "popToCheck" , inputLine, Orario.getDataOra(), time);	
					DatabaseMySql.insert("utenti", "popular" , inputLine, Orario.getDataOra(), time);
				} 	
				else if (inputLine.equals("</html>")){
					if (++pag <= 5) { 
						if (count < 24)
							pag--;
						in.close();
						popularReaderTool (time, pag);
						return;
					}
					else {
						in.close();
						return;
					}
				}
				else if (inputLine.contains("Siamo spiacenti per l'interruzione"))
					urlReader.notifyUrlFlood(inputLine, "");  // GESTIRE ANCHE SE E' DIFFICILE CHE SI VERIFICHI 
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
			urlReader.getErrorCode("activity", metafeedUrl, "utenti");
		}
		try {in.close();} catch (IOException e) {}
		return;
	}
	    
	private static URL metafeedUrl;	
	private static BufferedReader in;
}
