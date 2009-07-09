package download;

import database.OutputTxt;

public class Contatore {
	
	public Contatore () {
		Api = 0;
		Url = 0;
		capApi = 999999999;
		capUrl = 999999999;
	}
	
	public static void incApi () {	// Ritorna true se ha incrementato con successo
		Api++;
		System.out.println("\t Richiesta Api num: " + Api);

	}

	public static void incUrl () {
		Url++;
		System.out.println("\t Richiesta Url num: " + Url);

	}
	
	public static void decApi () {
		Api--;
	}	
	
	public static String getApi () {
		return "" + Api;
	}	
	
	public static String getUrl () {
		return "" + Url;
	}	
	
	public static void decUrl () {
		Url++;
	}
	
	public static boolean checkApi () {   	// Ritornano true se NON è ancora arrivato al cap
		if (Api == capApi)
			return false;
		return true;
	}
	
	public static boolean checkUrl () {		// Ritornano true se NON è ancora arrivato al cap
		if (Url == capUrl)
			return false;
		return true;
	}
	
	public static void setApi (int cap) {
		Contatore.Api = cap;
	}
	
	public static void setUrl (int cap) {
		Contatore.Url = cap;
	}
	

	private static int Api;
	private static int Url;
	private static int capApi;
	private static int capUrl;
}
