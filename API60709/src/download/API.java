package download;
 
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
	  
	public API () {}
	 
	public static boolean getUser (YouTubeService myService, String status, String user){
	    try {            	
            metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user);
            Contatore.incApi();
            ethernet.checkEthernet("utenti");
            UserProfileEntry profileEntry = myService.getEntry(metafeedUrl, 
           			UserProfileEntry.class);
            
           	System.out.println("Username: " + profileEntry.getUsername());           	
           	userStats = profileEntry.getStatistics();
           	if(userStats != null) {
           		DatabaseMySql.insert("utenti", "profile", user, status, userStats.getSubscriberCount() + "",
           				userStats.getViewCount() + "", userStats.getVideoWatchCount() + "",
           				userStats.getLastWebAccess().toUiString());
           		return true;
           	}
        }
        catch(AuthenticationException e) { 
    		e.printStackTrace();
        	OutputTxt.writeException(e.getCodeName() + " : " + e.getLocalizedMessage());
        	OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
        	return false;
        } catch(MalformedURLException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
            return false;
        } catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
        	return false;
        } catch(ServiceException e) {
        	notifyApiFlood("profile" , user);
        	return false;
        } catch(IOException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
            return false;
        }
		return false;
	}
	
	public static void getFavorites (YouTubeService myService, String user) {
		getFavorites(myService, user,1, 0);
	}
	
	public static void getFavorites (YouTubeService myService, String user, int count, int giriVuoto) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50" 
			+ "&start-index=" + count);
			Contatore.incApi();
			ethernet.checkEthernet("utenti");
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				countTemp = true;
				if (videoEntry.isDraft()) {
				 	System.out.println(count  + " : RESTRICTED");
				}
				else {					
					
					System.out.println(count + " : Inserimento per l'utente " + user + " del favorites " +
							videoEntry.getMediaGroup().getVideoId() + " : " + 
							videoEntry.getPublished().toString().substring(0,19));
					DatabaseMySql.insert("utenti", "favorites", user , videoEntry.getMediaGroup().getVideoId(), 
							videoEntry.getMediaGroup().getUploader() ,
							videoEntry.getPublished().toString().substring(0,19));
					System.out.println(videoEntry.getMediaGroup().getUploader());
				}
				if (++count == 1001) {
					return;
				}
			}
			int tot;
			if ((tot = videoFeed.getTotalResults()) > 1000)
				tot = 1000;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && tot >= count) {
				System.out.println(tot);
				getFavorites( myService, user, count, giriVuoto);
			}
			else 
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			urlReader.getErrorCode("favorites", metafeedUrl, user);
		} catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
        } catch(ServiceException e) {
        	urlReader.getErrorCode("favorites", metafeedUrl, user);
        }
	}
	
	public static void getVideo (YouTubeService myService, String user) {
		getVideo(myService, user,1, 0);
	}
	
	public static void getVideo (YouTubeService myService, String user, int count, int giriVuoto) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?max-results=50" 
			+ "&start-index=" + count);
			Contatore.incApi();
			ethernet.checkEthernet("utenti");
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				countTemp = true;
				if (videoEntry.isDraft()) {
				 	System.out.println(count  + " : RESTRICTED");
				}
				else {					
					System.out.println(count + " : Inserimento per l'utente " + user + " del video " +
							videoEntry.getMediaGroup().getVideoId() + " : " + 
							videoEntry.getPublished().toString().substring(0,19));
					DatabaseMySql.insert("utenti", "video", user , videoEntry.getMediaGroup().getVideoId(), 
							videoEntry.getPublished().toString().substring(0,19));
				}
				if (++count == 1001) {
					return;
				}
			}
			if ((tot = videoFeed.getTotalResults()) > 1000)
				tot = 1000;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && tot >= count) {
				System.out.println(tot);
				getVideo( myService, user, count, giriVuoto);
			}
			else 
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
        } catch (IOException e) {
			urlReader.getErrorCode("video", metafeedUrl, user);	
		} catch(ServiceException e) {
			urlReader.getErrorCode("video", metafeedUrl, user);
        }
	}
	
	public static boolean getActivity (YouTubeService myService, String user) {
		return getActivity(myService, user,1, 0);
	}
	
	public static boolean getActivity (YouTubeService myService, String user, int count, int giriVuoto) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?&max-results=50&start-index=" + count + "&author=" 
					+ user + "&key=AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ");
			System.out.println(metafeedUrl);
			Contatore.incApi();
			ethernet.checkEthernet("utenti");
			activityFeed = myService.getFeed(metafeedUrl, UserEventFeed.class);
			if (activityFeed.getEntries().size() == 0) {
				System.out.println("This feed contains no entries.");
				return false;
			}
			for (UserEventEntry entry : activityFeed.getEntries()) {
				String userTemp = entry.getAuthors().get(0).getName();
				countTemp = true;
				if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
					DatabaseMySql.insert("utenti", "activity", userTemp, entry.getVideoId(), "uploaded", 
							entry.getUpdated().toString().substring(0, 19));
					System.out.println(count + ": " + userTemp + " uploaded a video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, entry.getVideoId(), "rated", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	System.out.println(count + ": " + userTemp + " rated a video " + entry.getVideoId() +
			          " " + entry.getRating().getValue() + " stars");
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, entry.getVideoId(), "favorited", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	System.out.println(count + ": " + userTemp + " favorited a video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, entry.getUsername(), "subscribed",
			    			entry.getUpdated().toString().substring(0, 19));
			    	System.out.println(count + ": " + userTemp + " subscribed to the channel of " +
			    			entry.getUsername());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, entry.getUsername(), "friended",
			    			entry.getUpdated().toString().substring(0, 19));
			    	System.out.println(count + ": " + userTemp + " friended " + entry.getUsername());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, entry.getVideoId(), "commented",
			    			entry.getUpdated().toString().substring(0, 19));
			    	System.out.println(count + ": " + userTemp + " commented on video " + entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
			    	DatabaseMySql.insert("utenti", "activity", userTemp, entry.getVideoId(), "shared", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	System.out.println(count + ": " + userTemp + " shared a video " + entry.getVideoId());
			    }
				count++;
			  }
			if ((tot = activityFeed.getTotalResults()) > 1000)
				tot = 1000;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && tot >= count) {
				System.out.println(tot);
				getActivity(myService, user, count, giriVuoto);
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
        	return false;
		} catch (IOException e) {
			urlReader.getErrorCode("activity", metafeedUrl, user);
			return false;
		} catch(ServiceException e) {
			urlReader.getErrorCode("activity", metafeedUrl, user);
        	return false;
        }
		if (countTemp)
			return true;
		return false;
		}
	public static void getSubscriptions (YouTubeService myService, String user) {
			getSubscriptions(myService, user, 1,0 );
	}
	
	public static void getSubscriptions (YouTubeService myService, String user, int count, int giriVuoto) {
		String temp;
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50"  
				+ "&start-index=" + count);
			System.out.println(metafeedUrl);
			Contatore.incApi();
		//	ethernet.checkEthernet("utenti");
			feed = myService.getFeed(metafeedUrl, SubscriptionFeed.class);
			for(SubscriptionEntry entry : feed.getEntries()) {
				countTemp = true;
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
				if(!DatabaseMySql.contiene("utenti", "profile", temp))
					DatabaseMySql.inserToCheck("utenti", temp);
				count++;
			}
			if ((tot = feed.getTotalResults()) > 1000)
				tot = 1000;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && tot >= count) {
				System.out.println(tot);
				getSubscriptions(myService, user, count, giriVuoto);	
			}
			else
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
		} catch (IOException e) {
			urlReader.getErrorCode("subscriptions", metafeedUrl, user);
		} catch(ServiceException e) {
			urlReader.getErrorCode("subscriptions", metafeedUrl, user);
        }
	}
	
    public static void notifyApiFlood (String tabella, String user) {
    	OutputTxt.writeLog("Errore 403: Rete floodata dalle API dall'user " + user);    // DA RIFAREEEEE
    	System.out.println("Errore 403: Rete floodata dalle API dall'user " + user);
		OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
		OutputTxt.writeLog("Totale    API: " + Contatore.getTotApi());
		OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
		OutputTxt.writeLog("Totale    URL: " + Contatore.getTotUrl());
		System.out.println("PAUSA di 331 secondi per flood API");
		Contatore.setApi(0);
		Contatore.setUrl(0);
		try {
			DatabaseMySql.delete("utenti", "profile", "user", user);
			DatabaseMySql.inserToCheck("utenti", user, 9999);
			Thread.currentThread();
			Thread.sleep(331000);	 // Pausa di 3 minuti e mezzo
		}
		catch (InterruptedException e) { 
			e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel notifyFlood dell'utente.");
		}
    }
	
    private static YtUserProfileStatistics userStats;
    private static VideoFeed videoFeed;
    private static UserEventFeed activityFeed;
    private static SubscriptionFeed feed;
    private static URL metafeedUrl;
    private static boolean countTemp;
    private static int tot;
  }

  