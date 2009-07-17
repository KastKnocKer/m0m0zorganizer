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
import com.google.gdata.util.ServiceForbiddenException;
import database.DatabaseMySql;
import database.OutputTxt;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

  public class API {
	  
	public API () { 
		myService = new YouTubeService("Tesi", "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ"); 
		}	
	 
	public static boolean getUser (String tabella, String user){
	    try {            	
            metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user);
            Contatore.incApi();
            UserProfileEntry profileEntry = myService.getEntry(metafeedUrl, 
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
	
	public static void getFavorites (String user) {
		getFavorites(user,1, 0, 50);
	}
	
	public static void getFavorites (String user, int count, int tot, int max) {	
		try {
			if  (tot != 0 && (tot - count) < 50) {
				max = tot - count + 1;
			}
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=" + max 
			+ "&start-index=" + count);
			Contatore.incApi();
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				if (videoEntry.isDraft()) {
				 	System.out.println(count  + " : RESTRICTED");
				}
				else {					
					System.out.println(count + " : Inserimento per l'utente " + user + "del favorites " +
							videoEntry.getMediaGroup().getVideoId() + " : " + 
							videoEntry.getPublished().toString().substring(0,19));
					DatabaseMySql.insert("utenti", "favorites", user , videoEntry.getMediaGroup().getVideoId(), 
							videoEntry.getPublished().toString().substring(0,19));
				}
				if (++count == 1001) {
					return;
				}
			}
			if ((tot = videoFeed.getTotalResults()) > count)
				getFavorites(user,count, tot, max);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			urlReader.getErrorCode("favorites", metafeedUrl, user);
		} catch (ServiceForbiddenException e) {
			urlReader.getErrorCode("favorites", metafeedUrl, user);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public static void getVideo (String user) {
		getVideo(user,1, 0, 50);
	}
	
	public static void getVideo (String user, int count, int tot, int max) {	
		try {
			if  (tot != 0 && (tot - count) < 50)
				max = tot - count + 1;
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?max-results=" + max 
			+ "&start-index=" + count);
			Contatore.incApi();
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				if (videoEntry.isDraft()) {
				 	System.out.println(count  + " : RESTRICTED");
				}
				else {					
					System.out.println(count + " : Inserimento per l'utente " + user + "del favorites  " +
							videoEntry.getMediaGroup().getVideoId() + " : " + 
							videoEntry.getPublished().toString().substring(0,19));
					DatabaseMySql.insert("utenti", "video", user , videoEntry.getMediaGroup().getVideoId(), 
							videoEntry.getPublished().toString().substring(0,19));
				}
				if (++count == 50001) {
					return;
				}
			}
			if ((tot = videoFeed.getTotalResults()) > count)
				getVideo(user,count, tot, max);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			urlReader.getErrorCode("video", metafeedUrl, user);
		} catch (ServiceForbiddenException e) {
			urlReader.getErrorCode("video", metafeedUrl, user);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public static void getActivity (String user) {
		getActivity(user,1, 0, 50);
	}
	
	public static void getActivity (String user, int count, int tot, int max) {
		try {
			if  (tot != 0 && (tot - count) < 50)
				max = tot - count + 1;
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?&max-results=" + max + "&start-index=" + count + "&author=" 
					+ user + "&key=AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ");
			System.out.println(metafeedUrl);
			Contatore.incApi();
			activityFeed = myService.getFeed(metafeedUrl, UserEventFeed.class);
			if (activityFeed.getEntries().size() == 0) {
				System.out.println("This feed contains no entries.");
				return;
			}
			for (UserEventEntry entry : activityFeed.getEntries()) {
				String userTemp = entry.getAuthors().get(0).getName();
				count++;
				if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
					DatabaseMySql.insert("utenti", "activity", userTemp, "uploaded", entry.getVideoId());
					System.out.println(count + ": " + userTemp + " uploaded a video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, "rated", entry.getVideoId());
			    	System.out.println(count + ": " + userTemp + " rated a video " + entry.getVideoId() +
			          " " + entry.getRating().getValue() + " stars");
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, "favorited", entry.getVideoId());
			    	System.out.println(count + ": " + userTemp + " favorited a video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, "shared", entry.getVideoId());
			    	System.out.println(count + ": " + userTemp + " shared a video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, "commented", entry.getVideoId());
			    	System.out.println(count + ": " + userTemp + " commented on video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, "subscribed", entry.getUsername());
			    	System.out.println(count + ": " + userTemp + " subscribed to the channel of " +
			    			entry.getUsername());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, "friended", entry.getUsername());
			    	System.out.println(count + ": " + userTemp + " friended " + entry.getUsername());
			    }
			  }
			if ((tot = activityFeed.getTotalResults()) > count)
				getActivity(user, count, tot, max);	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			urlReader.getErrorCode("activity", metafeedUrl, "utenti");
		} catch (ServiceException e) {
			urlReader.getErrorCode("activity", metafeedUrl, "utenti");
		}
	}
	public static void getSubscriptions (String user) {
			getSubscriptions(user, 1, 0, 50);
	}
	
	public static void getSubscriptions (String user, int count, int tot, int max) {
		String temp;
		if  (tot != 0 && (tot - count) < 50)
			max = tot - count + 1;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=" + max 
				+ "&start-index=" + count);
			Contatore.incApi();
			feed = myService.getFeed(metafeedUrl, SubscriptionFeed.class);
			for(SubscriptionEntry entry : feed.getEntries()) {
				temp = entry.getTitle().getPlainText();
				if (temp.contains("Videos published by"))
					temp = temp.substring(22);
				else if (temp.contains("Favorites of"))
					temp = temp.substring(15);
				else {
					count++;
					continue;
				}
				System.out.println(count + " : Inserimento per l'utente " + user + "della subscritions a" +
						temp + " : " + entry.getPublished().toString().substring(0,19));
				DatabaseMySql.insert("utenti","subscriptions",user, temp, entry.getPublished().toString().substring(0,19));
				System.out.println(temp + " : " + entry.getPublished().toString().substring(0,19));
				DatabaseMySql.inserToCheck("utenti", user);
				count++;
			}
			if ((tot = feed.getTotalResults()) > count)
				getSubscriptions(user, count, tot, max);	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			urlReader.getErrorCode("activity", metafeedUrl, "utenti");
		} catch (ServiceException e) {
			urlReader.getErrorCode("activity", metafeedUrl, "utenti");
		}
	}
	
    public static void notifyFlood (String tabella, String user) {
    	OutputTxt.writeLog("Errore 403: Rete floodata dalle API dall'user " + user);    // DA RIFAREEEEE
    	System.out.println("Errore 403: Rete floodata dalle API dall'user " + user);
		OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
		OutputTxt.writeLog("Totale    API: " + Contatore.getTotApi());
		OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
		OutputTxt.writeLog("Totale    URL: " + Contatore.getTotUrl());
		
		Contatore.setApi(0);
		Contatore.setUrl(0);
		try {
			DatabaseMySql.inserToCheck("utenti", user);
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
    private static VideoFeed videoFeed;
    private static UserEventFeed activityFeed;
    private static SubscriptionFeed feed;
    private static URL metafeedUrl;
  }

  