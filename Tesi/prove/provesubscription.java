package prove;
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
    	
    	new API();
    	new urlReader();
    	
    //	API.getUser("animegamer3");
    // 	API.getContacts("animegamer3");
   	//	API.getSubscription("animegamer3");
    	urlReader.favoritesReader("Staffgrillo");
    	urlReader.favoritesReader("Covy1986");
    //	urlReader.favoritesReader("BarackObamadotcom");
    //	urlReader.favoritesReader("hereforyou13");
    	urlReader.favoritesReader("domenico70b");
    	urlReader.favoritesReader("animegamer3");
    	
    //	API.getActivityFeed("animegamer3");

	}

}
