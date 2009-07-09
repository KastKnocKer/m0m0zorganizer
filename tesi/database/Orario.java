package database;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Orario extends GregorianCalendar {

	public Orario () {
		super();
		Orologio = this;
	}

	public  static String getOra () {
		String AMPM = "PM";
		if (Orologio.get(Calendar.AM_PM) == 0)
			AMPM = "AM";
		ora = Orologio.get(Calendar.HOUR) + ":" + Orologio.get(Calendar.MINUTE) +				
			  ":" + Orologio.get(Calendar.SECOND) + " " + AMPM;
		return ora;
	}
	
	public  static String getData () {
		data = Orologio.get(Calendar.DATE) + "/" + Orologio.get(Calendar.MONTH) + "/" + Orologio.get(Calendar.YEAR);
		return data;
	}
	
	public static String getDataOra () {
		return  Orario.getData() + " - " + Orario.getOra() + ": ";
	}
	
	private static String ora;
	private static String data;
	private static Orario Orologio;
}

