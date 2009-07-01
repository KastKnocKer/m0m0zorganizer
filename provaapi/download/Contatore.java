package download;

import database.OutputTxt;

public class Contatore {
	
	public Contatore () {
		Api = 0;
		Url = 0;
		capApi = 999999999;
		capUrl = 2500;
	}
	
	public static boolean incApi () {	// Ritorna true se ha incrementato con successo
		Api++;
		OutputTxt.writeLog("\t\t\t\t\t\t Richiesta Api num: " + Api);
		System.out.println("\t Richiesta Api num: " + Api);
		if (checkApi()) 				// Ritorna false ha raggiunto il cap e quindi nn incrementa
			return true;
		return false;
	}

	public static boolean incUrl () {	// Ritorna true se ha incrementato con successo
		if (checkUrl()) {				// Ritorna false ha raggiunto il cap e quindi nn incrementa
			Url++;
			return true;
		}
		return false;
	}
	
	public static void decApi () {
		Api--;
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
	
	public static void setCapApi (int cap) {
		Contatore.capApi = cap;
	}
	
	public static void setCapUrl (int cap) {
		Contatore.capUrl = cap;
	}
	

	private static int Api;
	private static int Url;
	private static int capApi;
	private static int capUrl;
}
