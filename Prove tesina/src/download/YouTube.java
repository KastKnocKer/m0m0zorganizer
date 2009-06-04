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

  public class YouTube {
    
	public static void main(String[] args) {
    	String[] prova = new String[6]; 	
    	prova[0] = "momoz1987";
    	prova[1] = "scimmiaubriaca";
    	prova[2] = "hereforyou13";
    	prova[3] = "domenico70b";
    	prova[4] = "BarackObamadotcom";
    	prova[5] = "enfry1983";
    	
    	getContacts(prova [0], 1);
    //	getUser("topakkami");
    //  getUser("momoz1987");
    	getContacts(prova ,1 );
    //	getFavorites(prova, 1 );
    //	getSubscription(prova,1 );
  }
    
	public static void getUser (String user){
		boolean found = false;
		YouTubeService myService = new YouTubeService("Tesi");

        try {            	
            String profiloUrl = "http://gdata.youtube.com/feeds/api/users/" + user;
            UserProfileEntry profileEntry = myService.getEntry(new URL(profiloUrl), 
           			UserProfileEntry.class);
            
           	System.out.println("Username: " + profileEntry.getUsername());           	
           	YtUserProfileStatistics stats = profileEntry.getStatistics();
           	found = true;
           	if(stats != null) {
           	  System.out.println("Subscriber count: " + stats.getSubscriberCount());
           	  System.out.println("Visite profilo: " + stats.getViewCount());
           	  System.out.println("Video visti: " + stats.getVideoWatchCount());
           	  System.out.println("Ultimo Accesso: " + stats.getLastWebAccess().toUiString() + "\n");
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
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("Tesi");
       
        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/contacts?max-results=50");
        	System.out.println("Contatti dell'user " + user + "...\n");
        	FriendFeed friendFeed = myService.getFeed(metafeedUrl, FriendFeed.class);
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
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("Tesi");
        
        if (start == 0)		start++;		// OPZIONALE
        try {
              // Get a list of all entries
        	  URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/contacts?max-results=50&" +
        	  		"start-index=" + start);
              System.out.println("Contatti dell'user " + user + "...\n");
              FriendFeed friendFeed = myService.getFeed(metafeedUrl, FriendFeed.class);
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
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("Tesi");

        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50");
        	System.out.println("Preferiti dell'user " + user + "...\n");
        	VideoFeed resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
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
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("Tesi");

        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/favorites?max-results=50" +
        			"&start-index=" + start);
        	System.out.println("Preferiti dell'user " + user + "...\n");
        	VideoFeed resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
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
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("Tesi");

        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50");
        	System.out.println("Subscription dell'user " + user + "...\n");
        	VideoFeed resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
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
        // Create a new YouTube service
        YouTubeService myService = new YouTubeService("Tesi");

        try {
        	// Get a list of all entries
        	URL metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/" + user + "/subscriptions?max-results=50" +
        			"&start-index=" + start);
        	System.out.println("Subscription dell'user " + user + "...\n");
        	VideoFeed resultFeed = myService.getFeed(metafeedUrl, VideoFeed.class);
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

  
    public static void getSubscription (String[] user, int start){
    	for (int i = user.length -1; i >= 0; i--)
    		getSubscription (user[i], start);
    }
  }

  