package download;
/**
 * @author Monduzzi Mattia 
 * Matr. 25505
 * 
 */
 
import com.google.gdata.client.youtube.*;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

  public class API {
	  
	public API () { myService = new YouTubeService("Tesi", developerKey); }
      
	public static void getUser (String user){
		boolean found = false;

        try {            	
            String profiloUrl = "http://gdata.youtube.com/feeds/api/users/" + user;
            UserProfileEntry profileEntry = myService.getEntry(new URL(profiloUrl), 
           			UserProfileEntry.class);
            
           	System.out.println("Username: " + profileEntry.getUsername());           	
           	userStats = profileEntry.getStatistics();
           	found = true;
           	if(userStats != null) {
           	  System.out.println("Subscriber count: " + userStats.getSubscriberCount());
           	  System.out.println("Visite profilo: " + userStats.getViewCount());
           	  System.out.println("Video visti: " + userStats.getVideoWatchCount());
           	  System.out.println("Ultimo Accesso: " + userStats.getLastWebAccess().toUiString() + "\n");
           	}
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
        catch(ResourceNotFoundException e){
            System.out.println("User not found");
        }
        catch(ServiceException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        if (!found) { // LISTA UTENTI BLOCCATIIIII
        	System.out.println("L' utente " + user + " è da inserire nella lista degli account sospesi.");
        	}
	}

    public static void getUser (String[] user){
    	for (int i = user.length -1; i >= 0; i--)
    		getUser (user[i]);
    }	
	
    // DECIDERE SE INSERIRE UN CHECK SULLA LISTA NERA O SE SFRUTTARE L'ECCEZIONE USER NOT FOUND
    public static void getContacts (String user){
       
        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/contacts?max-results=50");
        	System.out.println("Contatti dell'user " + user + "...\n");
        	friendFeed = myService.getFeed(metafeedUrl, FriendFeed.class);
        	for(FriendEntry friendEntry : friendFeed.getEntries()) {
        		System.out.println("\t" + friendEntry.getTitle().getPlainText());
        		System.out.println("\t" + friendEntry.getPublished());  // Data creazione
        		System.out.println("\t" + friendEntry.getStatus());
        	}  
        	System.out.println("\n");  
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(MalformedURLException e) {
        	e.printStackTrace();
        }
        catch(ServiceException e) {
        	e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
    public static void getContacts (String user, int start){
    	int count = 0;
        
        if (start == 0)		start++;		// OPZIONALE
        try {
              // Get a list of all entries
        	  URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/contacts?max-results=50&" +
        	  		"start-index=" + start);
              System.out.println("Contatti dell'user " + user + "...\n");
              friendFeed = myService.getFeed(metafeedUrl, FriendFeed.class);
              for(FriendEntry friendEntry : friendFeed.getEntries()) {
                 System.out.println("\t" + friendEntry.getTitle().getPlainText());
                 System.out.println("\t" + friendEntry.getPublished());  // Data creazione
                 System.out.println("\t" + friendEntry.getStatus());
                 count++;
              }  
              System.out.println("\n" + "Num contatti di " + user + ": " + count + "\n");  
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(MalformedURLException e) {
        	e.printStackTrace();
        }
        catch(ServiceException e) {
        	e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
    public static void getContacts (String[] user, int start){
    	for (int i = user.length -1; i >= 0; i--)
    		getContacts (user[i], start);
    }
       
    public static void getFavorites (String user){
    	try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50");
        	System.out.println("Preferiti dell'user " + user + "...\n");
        	resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
        	for(VideoEntry videoEntry : resultFeed.getEntries()) {
        		System.out.println("\t" + videoEntry.getTitle().getPlainText());  // Futile solo x capire
                System.out.println("\t" + videoEntry.getId());  // Estrarre ID  --> PARTICOLARE
                System.out.println("\t" + videoEntry.getUpdated());
        	}
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(MalformedURLException e) {
        	e.printStackTrace();
        }
        catch(ServiceException e) {
        	e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }

    public static void getFavorites (String user, int start){
        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50" +
        			"&start-index=" + start);
        	System.out.println("Preferiti dell'user " + user + "...\n");
        	resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
        	for(VideoEntry videoEntry : resultFeed.getEntries()) {
        		System.out.println("\t" + videoEntry.getTitle().getPlainText());  // Futile solo x capire
                System.out.println("\t" + videoEntry.getId());  // Estrarre ID  --> PARTICOLARE
                System.out.println("\t" + videoEntry.getUpdated());
        	}
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(MalformedURLException e) {
        	e.printStackTrace();
        }
        catch(ServiceException e) {
        	e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }
    
    public static void getFavorites (String[] user, int start){
    	for (int i = user.length -1; i >= 0; i--)
    		getFavorites (user[i], start);
    }
       
    public static void getSubscription (String user){
        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50");
        	System.out.println("Subscription dell'user " + user + "...\n");
        	resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
        	List<VideoEntry> entries = resultFeed.getEntries();
        	for(int i=0; i<entries.size(); i++) {
        		VideoEntry entry = entries.get(i);
                System.out.println("\t" + entry.getTitle().getPlainText());
                System.out.println("\t" + entry.getId());  // Estrarre ID
                System.out.println("\t" + entry.getPublished());
        	}
        	System.out.println("\nTotale subscription di " + user + ": " + entries.size() + "\n");
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(MalformedURLException e) {
        	e.printStackTrace();
        }
        catch(ServiceException e) {
        	e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }

    public static void getSubscription (String user, int start){
        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50" +
        			"&start-index=" + start);
        	System.out.println("Subscription dell'user " + user + "...\n");
        	resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
        	List<VideoEntry> entries = resultFeed.getEntries();
        	for(int i=0; i<entries.size(); i++) {
        		VideoEntry entry = entries.get(i);
                System.out.println("\t" + entry.getTitle().getPlainText());
                System.out.println("\t" + entry.getId().substring(entry.getId().lastIndexOf(":") + 1));
                System.out.println("\t" + entry.getPublished());
        	}
        	System.out.println("\nTotale subscription di " + user + ": " + entries.size() + "\n");
        }
        catch(AuthenticationException e) {
        	e.printStackTrace();
        }
        catch(ServiceForbiddenException e) {
        	System.out.println("ERRORE L'USER DEVE ESSERE LOGGATO \n");  // BISOGNA PENSARE A COME 
        }																 // GESTIRE QUESTO ERRORE		
        catch(MalformedURLException e) {
        	e.printStackTrace();
        }
        catch(ServiceException e) {
        	e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
    }

    public static void getSubscription (String[] user, int start){
    	for (int i = user.length -1; i >= 0; i--)
    		getSubscription (user[i], start);
    }

/**
 * Fetches a feed of activities and prints information about them.
 *
 * @param Users The url of the activity feed to print max 20 users.
 */
	public static void getActivityFeed (String users) {
		String feedUrl = "http://gdata.youtube.com/feeds/api/events?author=";
		try {
			activityFeed = myService.getFeed(new URL(feedUrl + users),UserEventFeed.class);
			}
		catch (ServiceException e)			{ e.printStackTrace(); }
		catch (MalformedURLException e) 	{ e.printStackTrace(); } 
		catch (IOException e) 				{ e.printStackTrace(); }
		
		String title = activityFeed.getTitle().getPlainText();
		System.out.println(title);
		
		if (activityFeed.getEntries().size() == 0) {
			System.out.println("This feed contains no entries.");
			return;
		}
		for (UserEventEntry entry : activityFeed.getEntries()) {
			String user = entry.getAuthors().get(0).getName();
			if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_UPLOADED) {
				System.out.println(user + " uploaded a video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
				}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_RATED) {
				System.out.println(user + " rated a video " + entry.getVideoId() +
						" with " + entry.getRating().getValue() + " stars");
				System.out.println(entry.getUpdated());
			    	}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_FAVORITED) {
				System.out.println(user + " favorited a video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_SHARED) {
				System.out.println(user + " shared a video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.VIDEO_COMMENTED) {
				System.out.println(user + " commented on video " + entry.getVideoId());
				System.out.println(entry.getUpdated());
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.USER_SUBSCRIPTION_ADDED) {
				System.out.println(user + " subscribed to the channel of " + entry.getUsername());
				System.out.println(entry.getUpdated());
			}
			else if(entry.getUserEventType() == UserEventEntry.Type.FRIEND_ADDED) {
				System.out.println(user + " friended " + entry.getUsername());
				System.out.println(entry.getUpdated());
			}
		}
		System.out.println();
	}
    
    
    private static YouTubeService myService;
    private static VideoFeed resultFeed;
    private static FriendFeed friendFeed;
    private static YtUserProfileStatistics userStats;
    private static UserEventFeed activityFeed;
    private final String developerKey = "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ";
  }

  