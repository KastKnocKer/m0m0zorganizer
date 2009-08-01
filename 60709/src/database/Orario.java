package database;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Orario extends GregorianCalendar {
	
	public Orario () {
		super();
		Orologio = this;
	}
	
	public static String getDataOra() {
		return getDataOra(0, 0, 0);
	}
	
	public static String getDataOra(int giorno) {
		return getDataOra(giorno, 0 , 0);
	}
	
	public static String getDataOra(int giorno, int ora, int minuto) {
		if (minuto > 59) {
			OutputTxt.writeError("Il max incremento dei minuti è di 59");
			minuto = 59;
		}
		if (ora > 23) {
			OutputTxt.writeError("Il max incremento delle ore è di 23");
			ora = 23;
		}
		if (giorno > 31) {
			OutputTxt.writeError("Il max incremento dei giorni è di 31");
			giorno = 31;
		}
		new Orario();
		riporto = 0;
		// SECONDI
		input = Orologio.get(Calendar.SECOND);
		data = input + "";
		if (input < 10)
			data = "0" + data;
		// MINUTI
		input = Orologio.get(Calendar.MINUTE) + minuto;
		if (input > 59) {
			riporto = 1;
			input = input % 60;
		}
		data = input + ":" + data;
		if (input < 10)
			data = "0" + data;
		// ORE
		if (Orologio.get(Calendar.AM_PM) == 0) {
			input = Orologio.get(Calendar.HOUR) + riporto + ora;
			riporto = 0;
			if (input < 10) {
				data = "T0" + input + ":" + data;
			}
			if (input > 23) {
				input = input % 24;
				riporto = 1;
				data = "T" + input + ":" + data;
			}
		}
		else {
			input = Orologio.get(Calendar.HOUR) + 12 + riporto + ora;
			riporto = 0;
			if (input > 23) {
				input = input % 24;
				riporto = 1;
			}
			data = "T" +input + ":" + data;
		}
		
		input = Orologio.get(Calendar.DATE) + riporto + giorno;
		riporto = 0;
		switch (Orologio.get(Calendar.MONTH) + 1) {
	        case 1:  	if (input > 31) {riporto = 1; input = input % 31; }  break;
	        case 2:  	if (input > 28) {riporto = 1; input = input % 28; }  break; 
	        case 3:  	if (input > 31) {riporto = 1; input = input % 31; }  break; 
	        case 4:  	if (input > 30) {riporto = 1; input = input % 30; }  break; 
	        case 5:  	if (input > 31) {riporto = 1; input = input % 31; }  break; 
	        case 6:  	if (input > 30) {riporto = 1; input = input % 30; }  break; 
	        case 7:  	if (input > 31) {riporto = 1; input = input % 31; }  break; 
	        case 8: 	if (input > 31) {riporto = 1; input = input % 31; }  break; 
	        case 9:  	if (input > 30) {riporto = 1; input = input % 30; }  break; 
	        case 10: 	if (input > 31) {riporto = 1; input = input % 31; }  break; 
	        case 11: 	if (input > 30) {riporto = 1; input = input % 30; }  break; 
	        case 12: 	if (input > 31) {riporto = 1; input = input % 31; }  break; 
	        default: break;
		}
		data = input + data;
		// MESE
		input = Orologio.get(Calendar.MONTH) + 1 + riporto;
		riporto = 0;
		if (input > 12) {
			input = 1;
			riporto = 1;			
		}
		data = input + "-" + data;
		
		input = Orologio.get(Calendar.YEAR) + riporto;
		return  input + "-" + data;		
	}
	
	private static String data;
	private static int input, riporto;
	private static Orario Orologio;
	private static final long serialVersionUID = 1L;
}

