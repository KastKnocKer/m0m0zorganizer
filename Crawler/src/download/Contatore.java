package download;

public class Contatore {
	
	public Contatore () {
		Api = 0;
		Url = 0;
		totApi = 0;
		totUrl = 0;
		//	capApi = 5000;
		//	capUrl = 5000;
	}
	
	public static void incApi () {	// Ritorna true se ha incrementato con successo
		Api++;
		totApi++;
		System.out.println("\t\t\t\t Richiesta Api parziali: " + Api + "\t Totale Api num: " + totApi);


	}

	public static void incUrl () {
		Url++;
		totUrl++;		
		System.out.println("\t\t\t\t Richiesta Url parziali: " + Url + "\t Totale Url num: " + totUrl);
	}
	
	public static String getApi () {
		return "" + Api;
	}	
	
	public static String getUrl () {
		return "" + Url;
	}	
	
	public static String getTotApi () {
		return "" + totApi;
	}	
	
	public static String getTotUrl () {
		return "" + totUrl;
	}	
	
	public static void setApi (int num) {
		Contatore.Api = num;
	}
	
	public static void setUrl (int num) {
		Contatore.Url = num;
	}
	
	private static int Api;
	private static int Url;
	private static int totApi;
	private static int totUrl;
}
