package download;
 
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import java.io.IOException;
import java.net.HttpURLConnection;
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
           		DatabaseMySql.insert(nomeDB, "profile", user, status, Orario.getDataOra(), userStats.getSubscriberCount(),
           				userStats.getViewCount(), userStats.getVideoWatchCount(),
           				userStats.getLastWebAccess().toString().substring(0, 19));
           		return true;
           	}
        }
        catch(AuthenticationException e) { 
    		e.printStackTrace();
        	OutputTxt.writeException(nomeDB, e.getCodeName() + " : " + e.getLocalizedMessage());
        	OutputTxt.writeException(nomeDB, "Errore nel getUser dell'utente: " + user);
        	return false;
        } catch(MalformedURLException e) { 
    		e.printStackTrace();
            OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
            OutputTxt.writeException(nomeDB, "Errore nel getUser dell'utente: " + user);
            return false;
        } catch(ResourceNotFoundException e){
        	DatabaseMySql.insert(nomeDB , "profile", user, "blocked", Orario.getDataOra(), 0, 0, 0, "block");
        	OutputTxt.writeLog(nomeDB, "Errore 404: User not found: " + user);
        	return false;
        } catch(ServiceException e) {
        	urlReader.getErrorCode(nomeDB, "profile", metafeedUrl, user);
        	return false;
        } catch(IOException e) { 
        	urlReader.getErrorCode(nomeDB, "profile", metafeedUrl, user);
    		e.printStackTrace();
            OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
            OutputTxt.writeException(nomeDB, "Errore nel getUser dell'utente: " + user);
            return false;
        }
		return false;
	}
	
	public static boolean getFavorites (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + " dei favorites dell' utente " + user + ".");
		return getFavorites( myService, devKey, nomeDB, user,1, 0, 0);
	}
	
	public static boolean getFavorites (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, int maxCount) {
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?&key=" + devKey +
					"&max-results=50&start-index=" + count );
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			if (count == 1 ) {
				tot = videoFeed.getTotalResults();
				DatabaseMySql.insert(nomeDB, "numFavorites", user, tot , Orario.getDataOra());
			}
			for (VideoEntry videoEntry : videoFeed.getEntries() ) {
				countTemp = true;
				if (!videoEntry.isDraft()) {		
					stringTemp = videoEntry.getMediaGroup().getUploader();
					DatabaseMySql.insert(nomeDB, "favorites", user , videoEntry.getMediaGroup().getVideoId(), 
							stringTemp , videoEntry.getPublished().toString().substring(0,19));
					DatabaseMySql.inserToCheck(nomeDB, stringTemp);
					DatabaseMySql.insert("root", "videoUploadedBy", stringTemp, videoEntry.getMediaGroup().getVideoId());
				}
				count++;
			}
			System.out.println("Favorites dell'user " + user + " scaricati fino al num: " + count + ".");
			if (count > 950 && count < tot) {
				count= 950;
				maxCount++;
			}					
			if (tot > 1000)
				tot = 951;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot > count) {
				System.out.println("\t\t\tTotale favorites per l'user " + user + ": " + tot);
				if (tot == 951 && count == 950)
					count++;
				getFavorites( myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
				return true;
			}
			else 
				return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			return urlReader.getErrorCode(nomeDB, "favorites", metafeedUrl, user);
		} catch(ResourceNotFoundException e){
        	OutputTxt.writeLog(nomeDB, "Errore 404: User not found: " + user);
        	return false;
        } catch(ServiceException e) {
        	return urlReader.getErrorCode(nomeDB, "favorites", metafeedUrl, user);
        }
        return true;
	}
		
	public static boolean getVideo (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  dei video dell' utente " + user + ".");
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/uploads?&key=" + devKey);
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			videoFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			tot = videoFeed.getTotalResults();
			DatabaseMySql.insert(nomeDB, "numVideo", user, tot, Orario.getDataOra());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
			OutputTxt.writeLog(nomeDB, "Errore 404: User not found: " + user);
			return false;
        } catch (IOException e) {
			return urlReader.getErrorCode(nomeDB, "video", metafeedUrl, user);	
		} catch(ServiceException e) {
			return urlReader.getErrorCode(nomeDB, "video", metafeedUrl, user);
        }
		return true;
	}
	
	public static void getCompleteVideo (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  dei video dell' utente " + user + ".");
		getCompleteVideo( myService, devKey, nomeDB, user, 1, 0, 0);
	}
	// SISTEMARE MAX COUNT COME NEGLI ALTRI
	public static void getCompleteVideo (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, int maxCount) {
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
			if (count > 950) {
				count= 951;
				maxCount++;
			}				
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot >= count) {
				System.out.println("\t\t\tTotale video per l'user " + user + ": " + tot);
				getCompleteVideo( myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
			}
			else 
				return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
        	OutputTxt.writeLog(nomeDB, "Errore 404: User not found: " + user);
        } catch (IOException e) {
			urlReader.getErrorCode(nomeDB, "video", metafeedUrl, user);	
		} catch(ServiceException e) {
			urlReader.getErrorCode(nomeDB, "video", metafeedUrl, user);
        }
	}
	
	public static boolean getActivity (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  delle activity dell' utente " + user + ".");
		return getActivity( myService, devKey, nomeDB, user, 1, 0, 0);
	}
	
	public static boolean getActivity (YouTubeService myService, String devKey,String nomeDB, String user, int count, int giriVuoto, int maxCount) {
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
					DatabaseMySql.insert("root", "videoUploadedBy" , stringTemp, entry.getVideoId()); 
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "rated", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "favorited", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
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
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
			    	DatabaseMySql.insert(nomeDB, "activity", stringTemp, entry.getVideoId(), "shared", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
				count++;
			  }
			System.out.println("Activity dell'user " + user + " scaricati fino al num: " + count + ".");
			tot = activityFeed.getTotalResults();
			if (count > 950 && count < tot) {
				count= 950;
				maxCount++;
			}					
			if (tot > 1000)
				tot = 951;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot > count) {
				System.out.println("\t\t\tTotale activity per l'user " + user + ": " + tot);
				if (tot == 951 && count == 950)
					count++;
				getActivity( myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
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
		return getActivity ( myService, devKey, nomeDB, user, count, giriVuoto, data, N, 0);
	}
	
	public static boolean getActivity (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, String data, int N, int maxCount) {
		System.out.println("ANALISI numero " + N + " per il DB: "+ nomeDB + "  delle activity dell' utente " + user + ".");
		countTemp = false;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/events?v=2&published-min=" + data + 
					".000Z&max-results=50&start-index=" + (count + 1) + "&author=" + user + "&key=" + devKey);
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			activityFeed = myService.getFeed(metafeedUrl, UserEventFeed.class);
	    	System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tPacchetto arrivato.");
			if (activityFeed.getEntries().size() == 0) {
				System.out.println("Tutti questi: " + user);
			}
			for (UserEventEntry entry : activityFeed.getEntries()) {
				count++;
				stringTemp = entry.getAuthors().get(0).getName();
				DatabaseMySql.insert(nomeDB, "active" + N, stringTemp, Orario.getDataOra());
				System.out.println(count + ": Attività riscontrata per l'utente " + stringTemp);
				countTemp = true;
				if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
					DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "uploaded", 
							entry.getUpdated().toString().substring(0, 19));
					DatabaseMySql.insert("root", "videoUploadedBy" , stringTemp, entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "rated", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "favorited", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getUsername(), "subscribed",
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getUsername(), "friended",
			    			entry.getUpdated().toString().substring(0, 19));
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "commented",
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
			    else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
			    	DatabaseMySql.insert(nomeDB, "activity" + N, stringTemp, entry.getVideoId(), "shared", 
			    			entry.getUpdated().toString().substring(0, 19));
			    	if (!DatabaseMySql.contiene("root", "videoUploadedBy", "id", entry.getVideoId()))
			    		DatabaseMySql.insert("root", "videoToCheck", entry.getVideoId());
			    }
			  }
			System.out.println("Activity " + N + " dell'user " + user + " scaricati fino al num: " + count + ".");
			tot = activityFeed.getTotalResults();
			OutputTxt.writeException(nomeDB, tot + "");
			if (count > 950 && count < tot) {
				count= 950;
				maxCount++;
			}					
			if (tot > 1000)
				tot = 951;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot > count) {
				System.out.println("\t\t\tTotale activity per l'user " + user + ": " + tot);
				if (tot == 951 && count == 950)
					count++;
				System.out.println(count);
				getActivity ( myService, devKey, nomeDB, user, count, giriVuoto, data, N, 0);
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
        	return false;
		} catch (IOException e) {
			activityGetErrorCode(nomeDB, "activity" + N, metafeedUrl, user);
			return false;
		} catch(ServiceException e) {
			activityGetErrorCode(nomeDB, "activity" + N, metafeedUrl, user);
        	return false;
        }
		return true;
	}
	
	
	public static boolean getSubscriptions (YouTubeService myService, String devKey, String nomeDB, String user) {
		System.out.println("ANALISI per il DB: "+ nomeDB + "  delle subscriptions dell' utente " + user + ".");
		return getSubscriptions(myService, devKey, nomeDB, user, 1, 0, 0);
	}
	
	public static boolean getSubscriptions (YouTubeService myService, String devKey, String nomeDB, String user, int count, int giriVuoto, int maxCount) {
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
						entry.getPublished().toString().substring(0,19), tot);
				DatabaseMySql.inserToCheck(nomeDB, stringTemp);
				count++;
			}
			System.out.println("Subscriptions dell'user " + user + " scaricati fino al num: " + count + ".");
			if (count > 950 && count < tot) {
				count= 950;
				maxCount++;
			}					
			if (tot > 1000)
				tot = 951;
			if (!countTemp)
				giriVuoto++;
			if (giriVuoto < 2 && maxCount < 2 && tot > count) {
				System.out.println("\t\t\tTotale subscriptions per l'user " + user + ": " + tot);
				if (tot == 951 && count == 950)
					count++;
				getSubscriptions(myService, devKey, nomeDB, user, count, giriVuoto, maxCount);
			}
			else
				return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch(ResourceNotFoundException e){
        	OutputTxt.writeLog(nomeDB, "Errore 404: User not found: " + user);
        	return false;
		} catch (IOException e) {
			return urlReader.getErrorCode(nomeDB, "subscriptions", metafeedUrl, user);
		} catch(ServiceException e) {
			return urlReader.getErrorCode(nomeDB, "subscriptions", metafeedUrl, user);
        }
		return true;
	}
	
    public static void notifyApiFlood (String nomeDB, String tabella, String user) {
    	OutputTxt.writeLog(nomeDB, "Errore 403: Rete floodata dalle API per il DB: "+ nomeDB + " dall'user " + user);    // DA RIFAREEEEE
    	System.out.println("Errore 403: Rete floodata dalle API per il DB: "+ nomeDB + " dall'user " + user);
		OutputTxt.writeLog(nomeDB, "Richieste API: " + Contatore.getApi());
		OutputTxt.writeLog(nomeDB, "\t\t\t\t\tTotale    API: " + Contatore.getTotApi());
		OutputTxt.writeLog(nomeDB, "Richieste URL: " + Contatore.getUrl());
		OutputTxt.writeLog(nomeDB, "\t\t\t\t\tTotale    URL: " + Contatore.getTotUrl());
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
            OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
            OutputTxt.writeException(nomeDB, "Errore nel notifyFlood dell'utente.");
		}
    }
    
    public static void activityGetErrorCode (String nomeDB, String tabella ,URL url ,String user) {
    	String msg;
    	int code;
    	System.out.println("ActivityGetErrorCode per il DB: "+ nomeDB + " sui " + tabella  + " dell'utente " + user);
		HttpURLConnection connection;
		try {		
			ethernet.checkEthernet(nomeDB);
			Contatore.incApi();
			connection = (HttpURLConnection) url.openConnection();
			System.out.println((code = connection.getResponseCode()));
			System.out.println(msg = connection.getResponseMessage());
			if (code >= 500) {
				OutputTxt.writeLog(nomeDB, "Errore 500+ : servizio non disponibile al momento. Analisi activity per il DB: "+ nomeDB);
				System.out.println("Errore 500+ : servizio non disponibile al momento. Analisi activity per il DB: "+ nomeDB);
				return;
			}				
			else if (msg.contains("many")) {
		    	OutputTxt.writeLog(nomeDB, "Errore 403: Rete floodata dalle SOLE activity per il DB: "+ nomeDB + " dall'user " + user);    // DA RIFAREEEEE
		    	System.out.println("Errore 403: Rete floodata dalle SOLE activity per il DB: "+ nomeDB + " dall'user " + user);
				OutputTxt.writeLog(nomeDB, "Richieste API: " + Contatore.getApi());
				OutputTxt.writeLog(nomeDB, "\t\t\t\t\tTotale    API: " + Contatore.getTotApi());
				OutputTxt.writeLog(nomeDB, "Richieste URL: " + Contatore.getUrl());
				OutputTxt.writeLog(nomeDB, "\t\t\t\t\tTotale    URL: " + Contatore.getTotUrl());
				System.out.println("PAUSA di 331 secondi per flood API");
				Contatore.setApi(0);
				Contatore.setUrl(0);
				try {Thread.currentThread();Thread.sleep(331000);}
				catch (InterruptedException e) { 
					e.printStackTrace();
		            OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
		            OutputTxt.writeException(nomeDB, "Errore nel activityNotifyFlood dell'utente.");
				}
				return;
			}	
			else if (msg.contains("Bad Request")) {
				OutputTxt.writeError(nomeDB, "Errore per il DB: "+ nomeDB + " bad request all'url: " + url);
				System.out.println("Errore bad request all'url: " + url);
				return;
			}	
		} catch (IOException e) { 
			e.printStackTrace();
			OutputTxt.writeException(nomeDB, e.getLocalizedMessage());
	        OutputTxt.writeException(nomeDB, "Errore nel activityGetErrorCode dell'utente: " + user);	
		}
		return;
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

  