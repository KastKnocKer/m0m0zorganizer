package prove;
import java.util.GregorianCalendar;

import database.*;
import download.*;

public class provesubscription {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	String[] prova = new String[6]; 	
    	prova[0] = "momoz1987";
    	prova[1] = "scimmiaubriaca";
    	prova[2] = "hereforyou13";
    	prova[3] = "domenico70b";
    	prova[4] = "BarackObamadotcom";
    	prova[5] = "enfry1983";
    	
    	new DatabaseMySql();
    	new OutputTxt();
    	DatabaseMySql.connetti();
    	new API();
    	new urlReader();
    	
    	//API.getUser("hereforyou13");
    	//urlReader.userReader("friends", "hereforyou13");
    	//urlReader.userReader("subscribers", "hereforyou13");
   	    API.getSubscription("enfry1983");
  
    	
    	//urlReader.videoApiReader("hereforyou13");
    	//urlReader.favoritesApiReader("hereforyou13");
    	
  /*  	urlReader.userReader("subscribers" , "staffgrillo");
    	urlReader.userReader("friends" , "staffgrillo");
    	urlReader.userReader("subscribers" , "hereforyou13");
    	urlReader.userReader("friends" , "hereforyou13");
    	urlReader.userReader("subscribers" , "scimmiaubriaca");
    	urlReader.userReader("friends" , "scimmiaubriaca"); */
    /*	API.getUser("animegamer3");
     	API.getContacts("animegamer3");
   	    API.getSubscription("animegamer3");
    	API.getSubscription("Staffgrillo"); 
    	API.getSubscription("Covy1986"); 
    	API.getSubscription("BarackObamadotcom");
    	API.getSubscription("hereforyou13"); 
    	API.getSubscription("domenico70b"); 
    	API.getSubscription("animegamer3"); 
    	API.getSubscription("Temari21");  
    	API.getSubscription("isotta11"); 
    	API.getSubscription("melindawong88"); 
    	API.getSubscription("PaceAmoreEmpatia");  */
    	
    	
    //	API.getActivityFeed("animegamer3");

	}

}
