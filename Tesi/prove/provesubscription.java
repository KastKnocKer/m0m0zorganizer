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
    	urlReader.favoritesReader("Staffgrillo"); //NO
    	urlReader.favoritesReader("Covy1986"); //52
    	urlReader.favoritesReader("BarackObamadotcom"); // 9
    	urlReader.favoritesReader("hereforyou13"); // 100
    	urlReader.favoritesReader("domenico70b"); // 19
    	urlReader.favoritesReader("animegamer3"); // 305
    	urlReader.favoritesReader("Temari21");  // 142
    	urlReader.favoritesReader("isotta11"); // 91
    	urlReader.favoritesReader("melindawong88"); // 311
    	urlReader.favoritesReader("PaceAmoreEmpatia"); // 81
    	
    	
    //	API.getActivityFeed("animegamer3");

	}

}
