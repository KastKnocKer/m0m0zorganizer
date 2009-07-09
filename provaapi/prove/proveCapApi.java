package prove;

import database.*;
import download.*;

public class proveCapApi {

	public static void main(String[] args) {
    	new DatabaseMySql();
    	new OutputTxt();
    	new Contatore();
    	DatabaseMySql.connetti();
    	new API();
    	new urlReader();
    	
    	/*
    	for ( ; ; ) {
			API.getUser("Covy1986");
			urlReader.userReader("friends", "Covy1986");
			urlReader.userReader("subscribers", "Covy1986");
			urlReader.favoritesApiReader("Covy1986");
			urlReader.videoApiReader("Covy1986");
			API.getSubscription("Covy1986");
    		
    		API.getUser("momoz1987");
    		urlReader.userReader("friends", "momoz1987");
    		urlReader.userReader("subscribers", "momoz1987");
    		urlReader.favoritesApiReader("momoz1987");
    		urlReader.videoApiReader("momoz1987");
    		API.getSubscription("momoz1987");
			
			API.getUser("hereforyou13");
			urlReader.userReader("friends", "hereforyou13");
			urlReader.userReader("subscribers", "hereforyou13");
    		urlReader.favoritesApiReader("hereforyou13");
    		urlReader.videoApiReader("hereforyou13");
			API.getSubscription("hereforyou13");
			
			API.getUser("animegamer3");
			urlReader.userReader("friends", "animegamer3");
			urlReader.userReader("subscribers", "animegamer3");
			urlReader.favoritesApiReader("animegamer3");
			urlReader.videoApiReader("animegamer3");
			API.getSubscription("animegamer3");
			
			API.getUser("domenico70b");
			urlReader.userReader("friends", "domenico70b");
			urlReader.userReader("subscribers", "domenico70b");
			urlReader.favoritesApiReader("domenico70b");
			urlReader.videoApiReader("domenico70b");
			API.getSubscription("domenico70b");
			
			API.getUser("barackobamadotcom");
			urlReader.userReader("friends", "barackobamadotcom");
			urlReader.userReader("subscribers", "barackobamadotcom");
			urlReader.favoritesApiReader("barackobamadotcom");
			urlReader.videoApiReader("barackobamadotcom");			
			API.getSubscription("domenico70b"); 
			
    	}*/
	}    
}
