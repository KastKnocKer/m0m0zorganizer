package database;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Orario extends GregorianCalendar {

	public Orario () {
		super();
	
		String AMPM = "PM";
		
		if (this.get(Calendar.AM_PM) == 0)
			AMPM = "AM";
		ora = this.get(Calendar.HOUR) + ":" + this.get(Calendar.MINUTE) +				
			  ":" + this.get(Calendar.SECOND) + " " + AMPM;
		data = this.get(Calendar.DATE) + "/" + this.get(Calendar.MONTH) + "/" + this.get(Calendar.YEAR);
	}

	public  static String getOra () {
		return ora;
	}
	
	public  static String getData () {
		return data;
	}
	
	public static String getDataOra () {
		return  data + " - " + ora + ": ";
	}
	
	private static String ora;
	private static String data;
}

