package analisi;

import java.util.Vector;

import Database.DatabaseMySql;

public class risultato2 {

	private static Vector<String[]> vector;
	private static float temp, media = 0, varianza = 0;
	private static int buckets = 50, max;
	
	public static void risultato () {
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		//max = Integer.parseInt(DatabaseMySql.eseguiQuery("Select max(subscriber) from totale.numRelation where subscriber<>'reserved'").get(0)[0]);
		max = 30880; // 90-Perc è 30872
		while (max >= buckets) { 
			vector = DatabaseMySql.eseguiQuery("Select berPtion_Si/berPtion_Scan from totale.probRelation where berPtion_Scan<>'0' and user in (Select user from totale.numRelation where subscriber>='" + (buckets - 50)+ "' and subscriber<='" + (buckets + 49)+ "')");
			if (vector.size() != 0) {
				for (int i = 0; i < vector.size(); i++) {
					media += Float.parseFloat(vector.elementAt(i)[0]);					
				}
				media = media / vector.size();
				for (int i = 0; i < vector.size(); i++) {
					temp = Float.parseFloat(vector.elementAt(i)[0]);
					varianza += (temp - media) * (temp - media);
				}
				varianza = varianza / vector.size();
				vector.removeAllElements();
			} else {
				media = 0; 
				varianza = 0;
			}
			System.out.println(buckets + ": media: " + media);
			System.out.println(buckets + ": varianza: " + varianza);
			DatabaseMySql.eseguiAggiornamento("Insert into totale.risultato2Tot values (" + buckets + ", " + media + ", " + varianza + ")");
			buckets += 100;
		}
	}
}

