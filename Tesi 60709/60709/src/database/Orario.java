package database;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Orario extends GregorianCalendar {

	public Orario () {
		super();
		Orologio = this;
	}

	public static String getOra () {
		ora = getOre() + ":" + getMinuti() + ":" + getSecondi();
		return ora;
	}
	
	public static String getData () {
		data = (Orologio.get(Calendar.YEAR)) + "-" + getMeseMod(0) + "-" + getGiornoMod(0);
		return data;
	}
	
	public static String getSecondi () {
		data = Orologio.get(Calendar.SECOND) + "";
		if (Orologio.get(Calendar.SECOND) < 10)
			data = "0" + data;
		return data;
	}
	
	public static String getMinuti () {
		data = Orologio.get(Calendar.MINUTE) + "";
		if (Orologio.get(Calendar.MINUTE) < 10)
			data = "0" + data;
		return data;
	}	
	
	public static String getOre () {
		if (Orologio.get(Calendar.AM_PM) == 0) {
			data = Orologio.get(Calendar.HOUR) + "";
			if (Orologio.get(Calendar.HOUR) < 10)
				data = "0" + data;
		}
		else 
			data = (Orologio.get(Calendar.HOUR) + 12) + "";
		return data;
	}
	
	public static String getGiornoMod (int mod) {
		if (mod != 0) {
			if((Orologio.get(Calendar.DATE) - mod) < 0)
				data = "01";
			else {
				data = (Orologio.get(Calendar.DATE) - mod) + "";
				if (Orologio.get(Calendar.DATE) -mod < 10)			
					data = "0" + data;
			}
		}				
		else {
			data = (Orologio.get(Calendar.DATE) - mod) + "";
			if (Orologio.get(Calendar.DATE) < 10)			
				data = "0" + data;
		}			
		return data;
	}
	public static String getMeseMod (int mod) {
		if (mod != 0) {
			if(Orologio.get(Calendar.MONTH) - ++mod == 0)
				data = "12";
			else if (Orologio.get(Calendar.MONTH) - mod == -1)
				data = "11";
			else {
				data = (Orologio.get(Calendar.MONTH) - mod) + "";
				if (Orologio.get(Calendar.MONTH) - mod < 10)			
					data = "0" + data;
			}
		}	
		else {  // CONTROLLARE SOLO SE IL PORTATILE SBAGLIA SOLO IL PORTATILE 
			data = (Orologio.get(Calendar.MONTH) + 1) + ""; // CORRETTO SOTTO GETMESEMODNORMALE()
			if ((Orologio.get(Calendar.MONTH) + 1) < 10)			
				data = "0" + data;
		}			
		return data;
	}
	
	public static String getMeseModNORMALRE (int mod) {
		if (mod != 0) {
			if(Orologio.get(Calendar.MONTH) - mod < 0)
				data = "01";
			else {
				data = (Orologio.get(Calendar.MONTH) - mod) + "";
				if (Orologio.get(Calendar.MONTH) - mod < 10)			
					data = "0" + data;
			}
		}	
		else {  // CONTROLLARE SOLO SE IL PORTATILE SBAGLIA SOLO IL PORTATILE
			data = Orologio.get(Calendar.MONTH) + "";
			if (Orologio.get(Calendar.MONTH) < 10)			
				data = "0" + data;
		}			
		return data;
	}
	
	public static String getDataOra () {
		return  Orario.getData() + "T" + Orario.getOra();
	}
	
	private static String ora;
	private static String data;
	private static Orario Orologio;
	private static final long serialVersionUID = 1L;
}

