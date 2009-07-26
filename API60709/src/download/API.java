package download;
 
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

  public class API {
	  
	public API () {}
	 
	public static boolean getUser (YouTubeService myService, String devKey, String status, String nomeDB, String user){
	    try {   
	    	System.out.println("\nANALISI per il DB: "+ nomeDB + "  del profilo dell' utente " + user + ".");
            metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "?v=2&key=" + devKey);
            ethernet.checkEthernet(nomeDB);
            Contatore.incApi();
            UserProfileEntry profileEntry = myService.getEntry(metafeedUrl, 
           			UserProfileEntry.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");            
           	System.out.println("Profilo scaricato per l'utente: " + profileEntry.getUsername());           	
           	userStats = profileEntry.getStatistics();
           	if(userStats != null) {
           		new Orario();
           		DatabaseMySql.insert(nomeDB, "profile", user, status, Orario.getDataOra(), userStats.getSubscriberCount() + "",
           				userStats.getViewCount() + "", userStats.getVideoWatchCount() + "",
           				userStats.getLastWebAccess().toString().substring(0, 19));
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
        	notifyApiFlood(nomeDB, "profile" , user);
        	return false;
        } catch(IOException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(e.getLocalizedMessage());
            OutputTxt.writeException("Errore nel getUser dell'utente: " + user);
            return false;
        }
		return false;
	}
	
	public static void getFavorites (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + " dei favorites dell' utente " + user + ".");
		getFavorites(myService, devKey, nomeDB, user,1, 0, 0);
	}
	
	public static void getFavorites (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, int maxCount) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?&key=" + devKey +
					"&max-results=50&start-index=" + count );
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			tot = videoFeed.getTotalResults();
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				countTemp = true;
				if (!videoEntry.isDraft()) {		
					stringTemp =videoEntry.getMediaGroup().getUploader();
					DatabaseMySql.insert(nomeDB, "favorites", user , videoEntry.getMediaGroup().getVideoId(), 
							stringTemp , videoEntry.getPublished().toString().substring(0,19), tot + "");
					DatabaseMySql.inserToCheck(nomeDB, stringTemp);
				}
				if (++count == 1001)
					return;
			}
			System.out.println("Favorites dell'user " + user + " scaricati fino al num: " + count + ".");
			if (tot > 1000)
				tot = 951;
			if (count > 951) {
				count= 951;
				maxCount++;
			}				
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot >= count) {

				System.out.println("\t\t\tTotale favorites per l'user " + user + ": " + tot);
				getFavorites(myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
			}
			else 
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			urlReader.getErrorCode(nomeDB, "favorites", metafeedUrl, user);
		} catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
        } catch(ServiceException e) {
        	urlReader.getErrorCode(nomeDB, "favorites", metafeedUrl, user);
        }
	}
	
	public static void getVideo (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  dei video dell' utente " + user + ".");
		getVideo(myService, devKey, nomeDB, user, 1, 0, 0);
	}
	
	public static void getVideo (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, int maxCount) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?&key=" + devKey +
					"&max-results=50&start-index=" + count);
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			tot = videoFeed.getTotalResults();
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				countTemp = true;
				if (!videoEntry.isDraft()) {				
					DatabaseMySql.insert(nomeDB, "video", user , videoEntry.getMediaGroup().getVideoId(), 
							videoEntry.getPublished().toString().substring(0,19), tot + "");
				}
				if (++count == 1001) {
					return;
				}
			}
			System.out.println("Video dell'user " + user + " scaricati fino al num: " + count + ".");
			if (tot > 1000)
				tot = 951;
			if (count > 951) {
				count= 951;
				maxCount++;
			}				
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot >= count) {

				System.out.println("\t\t\tTotale video per l'user " + user + ": " + tot);
				getVideo(myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
			}
			else 
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
        } catch (IOException e) {
			urlReader.getErrorCode(nomeDB, "video", metafeedUrl, user);	
		} catch(ServiceException e) {
			urlReader.getErrorCode(nomeDB, "video", metafeedUrl, user);
        }
	}
	
	public static boolean getActivity (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  delle activity dell' utente " + user + ".");
		return getActivity(myService, devKey, nomeDB, user, 1, 0);
	}
	
	public static boolean getActivity (YouTubeService myService, String devKey,String nomeDB, String user, int count, int giriVuoto) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?v=2&max-results=50&start-index=" + count + "&author=" 
					+ user + "&key=" + devKey);
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			activityFeed = myService.getFeed(metafeedUrl, UserEventFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			if (activityFeed.getEntries().size() == 0) {
				System.out.println("This feed contains no entries.");
				return false;
			}
			for (UserEventEntry entry : activityFeed.getEntries()) {
				stringTemp = entry.getAuthors().get(0).getName();
				countTemp = true;
				if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
					DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "uploaded", 
							entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "rated", 
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "favorited", 
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getUsername(), "subscribed",
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getUsername(), "friended",
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "commented",
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "shared", 
			    			entry.getUpdated().toString().substring(0, 19));
			    }
				count++;
			  }
			System.out.println("Activity dell'user " + user + " scaricati fino al num: " + count + ".");
			if ((tot = activityFeed.getTotalResults()) > 1000)
				tot = 951;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && tot >= count) {
				System.out.println("\t\t\tTotale activity per l'user " + user + ": " + tot);
				getActivity(myService, devKey, nomeDB, user, count, giriVuoto);
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
        	return false;
		} catch (IOException e) {
			urlReader.getErrorCode(nomeDB, "activity", metafeedUrl, user);
			return false;
		} catch(ServiceException e) {
			urlReader.getErrorCode(nomeDB, "activity", metafeedUrl, user);
        	return false;
        }
		if (countTemp)
			return true;
		return false;
		}
	
	public static boolean getActivity (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, String data, int N) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  delle activity dell' utente " + user + ".");
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?v=2&published-min=" + data + 
					".000Z&max-results=50&start-index=" + count + "&author=" + user + "&key=" + devKey);
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			activityFeed = myService.getFeed(metafeedUrl, UserEventFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			if (activityFeed.getEntries().size() == 0) {
				System.out.println("Tutti questi: " + user);
				return false;
			}
			for (UserEventEntry entry : activityFeed.getEntries()) {
				stringTemp = entry.getAuthors().get(0).getName();
				countTemp = true;
				if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
					DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "uploaded", 
							entry.getUpdated().toString().substring(0, 19));
					DatabaseMySql.insert(nomeDB, "video" + N, stringTemp, entry.getVideoId(), "" , 
							entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "rated", 
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "favorited", 
			    			entry.getUpdated().toString().substring(0, 19));
				    	DatabaseMySql.insert(nomeDB, "favorites" + N, stringTemp, entry.getVideoId(), urlReader.getFavoritesFeed(nomeDB, "favorites" + N, user, entry.getVideoId()));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getUsername(), "subscribed",
			    			entry.getUpdated().toString().substring(0, 19));
			    	DatabaseMySql.insert(nomeDB, "subscriptions" + N, stringTemp, entry.getUsername(), entry.getUpdated().toString().substring(0, 19), "");
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getUsername(), "friended",
			    			entry.getUpdated().toString().substring(0, 19));
			     	DatabaseMySql.insert(nomeDB, "friends" + N, stringTemp, entry.getUsername() , "");
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "commented",
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "shared", 
			    			entry.getUpdated().toString().substring(0, 19));
			    }
				count++;
			  }
			System.out.println("Activity dell'user " + user + " scaricati fino al num: " + count + ".");
			if ((tot = activityFeed.getTotalResults()) > 1000)
				tot = 951;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && tot >= count) {
				System.out.println("\t\t\tTotale activity gliuser " + user + ": " + tot);
				getActivity(myService, devKey, nomeDB, user, count, giriVuoto, data, N);
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
        	return false;
		} catch (IOException e) {
			urlReader.getErrorCode(nomeDB, "activity" + N, metafeedUrl, user);
			return false;
		} catch(ServiceException e) {
			urlReader.getErrorCode(nomeDB, "activity" + N, metafeedUrl, user);
        	return false;
        }
		if (countTemp)
			return true;
		return false;
		}
	
	
	public static void getSubscriptions (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  delle subscriptions dell' utente " + user + ".");
			getSubscriptions(myService, devKey, nomeDB, user, 1, 0, 0);
	}
	
	public static void getSubscriptions (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, int maxCount) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?key=" + devKey +
					"&max-results=50&start-index=" + count);
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			feed = myService.getFeed(metafeedUrl, SubscriptionFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			tot = feed.getTotalResults();
			for(SubscriptionEntry entry : feed.getEntries()) {
				countTemp = true;
				stringTemp = entry.getTitle().getPlainText();
				if (stringTemp.contains("Videos published by"))
					stringTemp = stringTemp.substring(22);
				else if (stringTemp.contains("Favorites of"))
					stringTemp = stringTemp.substring(15);
				else {
					count++;
					continue;
				}
				DatabaseMySql.insert(nomeDB,"subscriptions",user, stringTemp, 
						entry.getPublished().toString().substring(0,19), tot + "");
				DatabaseMySql.inserToCheck(nomeDB, stringTemp);
				count++;
			}
			System.out.println("Subscriptions dell'user " + user + " scaricati fino al num: " + count + ".");
			if (tot > 1000)
				tot = 951;
			if (count > 951) {
				count= 951;
				maxCount++;
			}				
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot >= count) {

				System.out.println("\t\t\tTotale subscriptions per l'user " + user + ": " + tot);
				getSubscriptions(myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
			}
			else
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
        	OutputTxt.writeLog("Errore 404: User not found: " + user);
		} catch (IOException e) {
			urlReader.getErrorCode(nomeDB, "subscriptions", metafeedUrl, user);
		} catch(ServiceException e) {
			urlReader.getErrorCode(nomeDB, "subscriptions", metafeedUrl, user);
        }
	}
	
    public static void notifyApiFlood (String nomeDB, String tabella, String user) {
    	OutputTxt.writeLog("Errore 403: Rete floodata dalle API per il DB: "+ nomeDB + " dall'user " + user);    // DA RIFAREEEEE
    	System.out.println("Errore 403: Rete floodata dalle API per il DB: "+ nomeDB + " dall'user " + user);
		OutputTxt.writeLog("Richieste API: " + Contatore.getApi());
		OutputTxt.writeLog("\t\t\t\t\tTotale    API: " + Contatore.getTotApi());
		OutputTxt.writeLog("Richieste URL: " + Contatore.getUrl());
		OutputTxt.writeLog("\t\t\t\t\tTotale    URL: " + Contatore.getTotUrl());
		System.out.println("PAUSA di 331 secondi per flood API");
		Contatore.setApi(0);
		Contatore.setUrl(0);
		try {
			DatabaseMySql.delete(nomeDB, "profile", "user", user);
    		DatabaseMySql.delete(nomeDB, "toCheck", "user", user);
			DatabaseMySql.inserToCheck(nomeDB, user, -9999);
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
    private static String stringTemp;
  }

  