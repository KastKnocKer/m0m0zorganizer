package download;

public class Contatore {
	
	public Contatore () {
		Api = 0;
		Url = 0;
		totApi = 0;
		totUrl = 0;
		capApi = 5000;
		capUrl = 5000;
	}
	
	public static void incApi () {	// Ritorna true se ha incrementato con successo
		Api++;
		System.out.println("\t Richiesta Api num: " + Api);
		totApi++;
		System.out.println("\t Totale Api num: " + totApi);

	}

	public static void incUrl () {
		Url++;
		System.out.println("\t Richiesta Url num: " + Url);
		totUrl++;
		System.out.println("\t Totale Url num: " + totUrl);

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
	
	public static void setApi (int num) {
		Contatore.Api = num;
	}
	
	public static void setUrl (int num) {
		Contatore.Url = num;
	}
	
	public static boolean checkCompleteScan () {
		if (capUrl - 84 >= Url)
			return true;
		return false;
	}
	

	private static int Api;
	private static int Url;
	private static int totApi;
	private static int totUrl;
	private static int capApi;
	private static int capUrl;
}
