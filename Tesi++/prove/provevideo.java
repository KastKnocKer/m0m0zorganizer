package prove;

import database.*;
import download.*;

public class provevideo {

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
    	
    	new Scan();


		for ( ; ; ) {
			String[] user;
			user = DatabaseMySql.extract("utenti", "username", "user"); 
    		System.out.println(user[0]);
    		System.out.println(user[1]);
    		System.out.println(user[2]);
    		System.out.println(user[3]);
    		System.out.println(user[4]);
			//urlReader.videoApiReader(user[0]);
		}
    	
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
   	    API.getUser("animegamer3");
    	API.getUser("Staffgrillo"); 
    	API.getUser("Covy1986"); 
    	API.getUser("BarackObamadotcom");
    	API.getUser("hereforyou13"); 
    	API.getUser("domenico70b"); 
    	API.getUser("animegamer3"); 
    	API.getUser("Temari21");  
    	API.getUser("isotta11"); 
    	API.getUser("melindawong88"); 
    	API.getUser("PaceAmoreEmpatia");  */
    	
    	
    //	API.getActivityFeed("animegamer3");

	}

}