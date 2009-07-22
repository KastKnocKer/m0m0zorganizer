package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputTxt {
	
	public OutputTxt () {}
	/*
	// Da automatizzareeeeeeeee
	public  void writeFile(Vector<String[]> user) {
		BufferedWriter writer; // il writer che userò per scrivere le informazioni
		String[] record;

		try {
			// apro il buffered writer
			writer = new BufferedWriter(new FileWriter( File, false));
			writer.write("Lista bloccati");
			writer.newLine();
			// Scrivo le informazioni 
			for(int i=0; i<user.size(); i++){
				record = user.get(i);
				System.out.println("Scrittura di "+ record[0]);
				writer.write(record[0]);	
			//	System.out.println("Scrittura di "+ record[0] + " : " + record[1]);
			//	writer.write(record[0] + " : " + record[1]);	
				writer.newLine();
			}
			writer.write("FINE");
			writer.newLine();
		
			// chiudo il file
			writer.flush();
			writer.close();
			}
		catch (IOException e) {
			System.out.println("Eccezione durante un'operazione di Output sul file: " + File );
			}
	} */
	
	public static void writeLog (String check) {
		try {			
			logWriter = new BufferedWriter(new FileWriter("./Log", true));
			new Orario();
			//System.out.println(Orario.getDataOra() + ": " + check);
			logWriter.write(Orario.getDataOra() + ": " + check);
			logWriter.newLine();			
			logWriter.flush();
			logWriter.close();
		} catch (IOException e) {
			System.out.println("Errore nel writeLog");
			e.printStackTrace();
		}
		return;
	}
	
	public static void writeError (String check) {
		try {			
			logWriter = new BufferedWriter(new FileWriter("./Error", true));
			new Orario();
			System.out.println(Orario.getDataOra() + ": " + check);
			logWriter.write(Orario.getDataOra() + ": " + check);
			logWriter.newLine();			
			logWriter.flush();
			logWriter.close();
		} catch (IOException e) {
			System.out.println("Errore nel writeError");
			e.printStackTrace();
		}
		return;
	}
	 
	public static void writeException (String check) {
		try {			
			logWriter = new BufferedWriter(new FileWriter("./Exception", true));
			new Orario();
			logWriter.write(Orario.getDataOra() + ": " + check);
			logWriter.newLine();			
			logWriter.flush();
			logWriter.close();
		} catch (IOException e) {
			System.out.println("Errore nel writeLog");
			e.printStackTrace();
		}
		return;
	}
	
	/*
	// Read dal file + analisi statistica del numero degli amici
	public static String[] readFileCount () {
		String[][] utenti;
		String stringa;
		Vector<String> vector = new Vector<String>();
		
		BufferedReader reader = null; // il reader che user� sul file
		try {
			// apro il reader e svuoto la tabella
			reader = new BufferedReader(new FileReader (File));			
			reader.readLine();
			while ((stringa = reader.readLine()) != null) {
					if (!stringa.equals("FINE"))
						vector.add(stringa);
			}
			reader.close();
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		
		utenti = new String[vector.size()][2];
		int j = 0, n = 0;
		int mille = 0, cinquecento = 0, duecentocinquanta = 0, cento = 0, cinquanta = 0, uno = 0;
		for (int i = 0; i < utenti.length; i++ ) {
			n++;
			stringa = vector.get(i);
			// utenti[i][0] = stringa.substring(0 , stringa.indexOf(":"));
			utenti[i][1] = stringa.substring(stringa.indexOf(":") + 2 , stringa.length());
			if (utenti[i][1].contains("*")) {
				utenti[i][1] = utenti[i][1].substring(2, utenti[i][1].lastIndexOf("*") - 1); 
			}
			j = Integer.parseInt(utenti[i][1]);
			if (j > 999) {
				mille++; 
				continue;				
			}
			else if (j > 499) {
				cinquecento++; 
				continue;				
			}
			else if (j > 249) {
				duecentocinquanta++; 
				continue;				
			}
			else if (j > 99) {
				cento++; 
				continue;				
			}
			else if (j > 49) {
				cinquanta++; 
				continue;				
			}
			else 
				uno++;	
			}
		
		System.out.println ("Con almeno 1000 utenti: " + mille);
		System.out.println ("Con almeno  500 utenti: " + cinquecento);
		System.out.println ("Con almeno  250 utenti: " + duecentocinquanta);
		System.out.println ("Con almeno  100 utenti: " + cento);
		System.out.println ("Con almeno   50 utenti: " + cinquanta);
		System.out.println ("Con almeno    1 utenti: " + uno);
		System.out.println ("Contatti controllati " + n);
		
		return null;
	}	
	
	public static void readFileInsertDB (String File, String nomeDB, String lista) {
		 
		 	String stringa;			
			BufferedReader reader = null; // il reader che userò sul file
			try {
				// apro il reader e svuoto la tabella
				reader = new BufferedReader(new FileReader (File));			
				reader.readLine();
				while ((stringa = reader.readLine()) != null) { // Eventualmente inserire check su un
							DatabaseMySql.insert(nomeDB , lista , stringa); // terminatore FINE FILE
				}
				reader.close();
			}
			catch (FileNotFoundException e) {}
			catch (IOException e) {}
		}		
	 
	public void setFile (String File) {
		 OutputTxt.File = File;
	 }
	 */
	private static BufferedWriter logWriter;
}