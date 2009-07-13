package download;
/**
 * @author Monduzzi Mattia 
 * Matr. 25505
 * 
 */
 
import com.google.gdata.client.youtube.*;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.*;

import database.DatabaseMySql;
import database.OutputTxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

  public class API {
	  
	public API () { myService = new YouTubeService("Tesi", developerKey); }
      
	
	public static boolean getUser (String user){
	    try {            	
            String profiloUrl = "http://gdata.youtube.com/feeds/api/users/" + user;
            Contatore.incApi();
            UserProfileEntry profileEntry = myService.getEntry(new URL(profiloUrl), 
           			UserProfileEntry.class);
            
           	System.out.println("Username: " + profileEntry.getUsername());           	
           	userStats = profileEntry.getStatistics();
           	if(userStats != null) {
           		DatabaseMySql.insert("utenti", "active", user,  userStats.getSubscriberCount(),
           				userStats.getViewCount(), userStats.getVideoWatchCount(),
           				userStats.getLastWebAccess().toUiString());
           		System.out.println("Subscriber count: " + userStats.getSubscriberCount());
           		System.out.println("Visite profilo: " + userStats.getViewCount());
           		System.out.println("Video visti: " + userStats.getVideoWatchCount());
           		System.out.println("Ultimo Accesso: " + userStats.getLastWebAccess().toUiString() + "\n");
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
        }
        catch(IOException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
            return false;
        }
        return false;
	}
	          
    public static void getSubscriptionOld (String user){
    	getSubscription (user, 1);
    }

    public static void getSubscription (String user, int count){
        try {
        	metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50" +
        			"&start-index=" + (count));
        	System.out.println("\nSubscription dell'user " + user + "...");
        	Contatore.incApi();
        	videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
        	
        	String subTemp, pubTemp, temp;
        	for(VideoEntry videoEntry : videoFeed.getEntries()) {
        		subTemp = videoEntry.getTitle().getPlainText();
        		if (subTemp.contains("Favorites"))
        				subTemp =subTemp.substring(subTemp.indexOf("of :") + 5);
        		else if (subTemp.contains("matching")) {  // Pensare se analizzare
        			if (++count == 1001)
                    	return;
        			else 
        				continue;
        		}
        		else if (subTemp.contains("published")) 
        				subTemp =subTemp.substring(subTemp.indexOf("by :") + 5);
        		else { 	// CASO DEGLI ERRORI
        			subTemp = videoEntry.getId();
        			subTemp = subTemp.substring(subTemp.lastIndexOf(":") + 1);
        			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions/" +
        					subTemp);
        			Contatore.incApi();
        	    	in = new BufferedReader(new InputStreamReader(metafeedUrl.openStream()));
        	    	while ((temp = in.readLine()) != null) {
        			if (temp.contains("<yt:username>"))
        				subTemp = temp.substring(temp.indexOf("<yt:username>") + 13, temp.indexOf("</yt:username>"));
        	    	}
        	    	in.close();
        		}
        		pubTemp = videoEntry.getPublished().toString();
        		pubTemp = pubTemp.substring(0, 19);
        		DatabaseMySql.insert("utenti", "subscriptions", user, subTemp, pubTemp);
                if (++count == 1001)
                	return;
        	}
        	if ((count) % 50 == 0 && count != 1 )   // Check se è divisibile per 50
            	getSubscription (user, count);
        }
        catch(AuthenticationException e) { 
    		e.printStackTrace();
        	OutputTxt.writeException(e.getCodeName() + " : " + e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getSubscriptions dell'utente: " + user);
        }
        catch(ResourceNotFoundException e) {    	
        	OutputTxt.writeLog("Errore 404: User " + user + " not found.");
        }		
        catch(MalformedURLException e) { 
    		e.printStackTrace();
        	OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getSubscriptions dell'utente: " + user);
        }
        catch(IOException e) { 
    		e.printStackTrace();
        	OutputTxt.writeException( e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getSubscriptions dell'utente: " + user);
        }
        catch (ServiceException e) {
        	urlReader.getErrorCode("subscriptions", metafeedUrl, user);
		}
    }
    
/**
 * Fetches a feed of activities and prints information about them.
 *
 * @param Users The url of the activity feed to print max 20 users.
 */
	public static void getActivityFeedOld (String users) {
		int count = 0;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?v=2&author=" + users + "&max-results=50&start-index=56");
			Contatore.incApi();
			activityFeed = myService.getFeed(metafeedUrl,UserEventFeed.class);
			}
		catch (ServiceException e)			{ // Al posto del return pensare al timer
			OutputTxt.writeLog("Errore 403: Rete floodata dalle API getActivityFeed.");
			return;
		}
		catch (MalformedURLException e) 	{ 
			e.printStackTrace(); 
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getActivityFeed dell'utente: " + users);
		}
		catch (IOException e) 				{ // Qui direi di lasciare il return
			OutputTxt.writeLog("Errore 404.");
			return;
		}
				
		if (activityFeed.getEntries().size() == 0) {
			System.out.println("This feed contains no entries.");
			return;
		}
		for (UserEventEntry entry : activityFeed.getEntries()) {
			String userActivity = entry.getAuthors().get(0).getName();
			if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
				System.out.println(userActivity + " uploaded a video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
				count++;
				}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
				System.out.println(userActivity + " rated a video " + entry.getVideoId() +
						" with " + entry.getRating().getValue() + " stars");
				System.out.println(entry.getUpdated());
				count++;
			    	}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
				System.out.println(userActivity + " favorited a video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
				count++;
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
				System.out.println(userActivity + " shared a video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
				count++;
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
				System.out.println(userActivity + " commented on video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
				count++;
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
				System.out.println(userActivity + " subscribed to the channel of " + entry.getUsername());
				System.out.println(entry.getUpdated());
				count++;
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
				System.out.println(userActivity + " friended " + entry.getUsername());
				System.out.println(entry.getUpdated());
				count++;
			}
		}
		System.out.println("\n" + count);
	}
	
    public static void notifyFlood (String tabella, String user) {
    	OutputTxt.writeLog("Errore 403: Rete floodata dalle API dall'user " + user);    // DA RIFAREEEEE
		OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
		OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
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
		
    }
	
    
    private static YouTubeService myService;
    private static VideoFeed videoFeed;
    private static YtUserProfileStatistics userStats;
    private static UserEventFeed activityFeed;
    private static URL metafeedUrl;
    private static BufferedReader in;
    private final static String developerKey = "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ";
  }

  