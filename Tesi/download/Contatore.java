package download;

import database.OutputTxt;

public class Contatore {
	
	public Contatore () {
		Api = 0;
		Url = 0;
		capApi = 601;
		capUrl = 2500;
	}
	
	public static void incApi () {	
		Api++;
		OutputTxt.writeLog("Richiesta Api num: " + Api);
		System.out.println("\t Richiesta Api num: " + Api);

	}

	public static void incUrl () {
			Url++;
	}
	
	public static void decApi () {
		Api--;
	}	
	
	public static void decUrl () {
		Url++;
	}
	
	public static boolean checkCompleteScan () {
		if (checkScanApi() && checkScanUrl())
			return true;
		return false;
	}
	
	public static boolean checkScanApi() {
		if (capApi-Api >= completeScanApi)
			return true;
		return false;
	}
	
	public static boolean checkScanUrl() {
		if (capUrl-Url >= completeScanUrl)
			return true;
		return false;
	}
	
	public static void setCapApi (int cap) {
		Contatore.capApi = cap;
	}
	
	public static void setCapUrl (int cap) {
		Contatore.capUrl = cap;
	}
	
	public static void setScanApi (int scan) {
		Contatore.completeScanApi = scan;
	}
	
	public static void setScanUrl (int scan) {
		Contatore.completeScanUrl = scan;
	}
	

	private static int Api;
	private static int Url;
	private static int capApi;
	private static int capUrl;
	private static int completeScanApi = 61;
	private static int completeScanUrl = 85;
}
