package prove;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

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
    	
    	new Scan();
    	
        Socket soc = new java.net.Socket();
        try {
            soc.connect(new InetSocketAddress("www.google.it", 80));
            System.out.println(API.getUser("active", "Staffgrillo"));
        } catch (IOException e) {
            e.printStackTrace();
        }     	    	
        try {
			soc.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    /*	for (;;) {
    		API.getUser("animegamer3");
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
        	API.getUser("PaceAmoreEmpatia");
    	} */
    	
    	//API.getUser("staffgrillo");
    	//API.getActivityFeed("staffgrillo");


    	
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
