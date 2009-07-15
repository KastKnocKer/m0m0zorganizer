package download;
/**
 * @author Monduzzi Mattia 
 * Matr. 25505
 * 
 */
 
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import database.DatabaseMySql;
import database.OutputTxt;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

  public class API {
	  
	public API () { myService = new YouTubeService("Tesi", "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ"); } 
	
	public static boolean getUser (String tabella, String user){
	    try {            	
            String profiloUrl = "http://gdata.youtube.com/feeds/api/users/" + user;
            Contatore.incApi();
            UserProfileEntry profileEntry = myService.getEntry(new URL(profiloUrl), 
           			UserProfileEntry.class);
            
           	System.out.println("Username: " + profileEntry.getUsername());           	
           	userStats = profileEntry.getStatistics();
           	if(userStats != null) {
           		DatabaseMySql.insert("utenti", tabella, user,  userStats.getSubscriberCount(),
           				userStats.getViewCount(), userStats.getVideoWatchCount(),
           				userStats.getLastWebAccess().toUiString());
           		return true;
           	}
        }
        catch(AuthenticationException e) { 
    		e.printStackTrace();
        	OutputTxt.writeException(e.getCodeName() + " : " + e.getLocalizedMessage());
        	OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
        	return false;
        }
        catch(MalformedURLException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
            return false;
        }
        catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
        	return false;
        }
        catch(ServiceException e) {
        	notifyFlood("profile" , user);
        	return false;
        }
        catch(IOException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
            return false;
        }
        return false;
	}
	          
    public static void notifyFlood (String tabella, String user) {
    	OutputTxt.writeLog("Errore 403: Rete floodata dalle API dall'user " + user);    // DA RIFAREEEEE
		OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
		OutputTxt.writeLog("Totale    API: " + Contatore.getTotApi());
		OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
		OutputTxt.writeLog("Totale    URL: " + Contatore.getTotUrl());
		
		Contatore.setApi(0);
		Contatore.setUrl(0);
		try {
			//REINSERIRE CONTATTO NEL DB
			Thread.currentThread();
			Thread.sleep(331000);	 // Pausa di 4 minutiù
			OutputTxt.writeLog("Check ora");
		}
		catch (InterruptedException e) { 
			e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel notifyFlood dell'utente.");
		}
		Runtime.getRuntime().gc();
		return;
    }
	
    
    private static YouTubeService myService;
    private static YtUserProfileStatistics userStats;
  }

  