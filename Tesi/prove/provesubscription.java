package prove;

import download.API;

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
    //	getUser("topakkami");
    	API.getUser("momoz1987");
    //	API.getContacts(prova ,1 );
    	API.getActivityFeed("momoz1987,Covy1986,lucamengoni,BarackObamadotcom");
    //	API.getSubscription(prova ,1 );
    //	API.getSubscription(prova ,1 );
    //	getFavorites(prova, 1 );
    //	getSubscription(prova,1 );

	}

}
