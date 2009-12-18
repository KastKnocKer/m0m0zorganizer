package analisi;

import java.util.Vector;
import Database.DatabaseMySql;

public class risultato3 {

	private static int buckets = 50, max;
	private static Vector<String[]> vector;
	private static float media = 0,temp, varianza = 0;
	
	public static void risultato() {
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();	// Connessione al database
		max = 17070; // 90-Perc vicini
		while (buckets <= max) {
		System.out.println("Esecuzione del gruppo vicini num: " + buckets);
			vector = DatabaseMySql.eseguiQuery("Select (friendPtion_Si + friendBer_Si + ptionBer_Si + ptionFriend_Si + berPtion_Si + berFriend_Si)/(friendPtion_Tot + friendBer_Tot + ptionBer_Tot + ptionFriend_Tot + berPtion_Tot + berFriend_Tot) from totale.probRelation where friendPtion_Tot<>'0' and friendBer_Tot<>'0' and ptionBer_Tot<>'0' and ptionFriend_Tot<>'0' and berPtion_Tot<>'0' and berFriend_Tot<>'0' and user in (Select user from totale.numRelation where friend<>'reserved' and subscriber<>'reserved' and subscription<>'reserved' and friend+subscriber+subscription>='" + (buckets - 50) + "' and friend+subscriber+subscription<='" + (buckets + 49) + "')");
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
			} else {
				media = 0;
				varianza = 0;
			}
			System.out.println(buckets + ": media: " + media);
			System.out.println(buckets + ": varianza: " + varianza);
			DatabaseMySql.eseguiAggiornamento("Insert into totale.risultato3Tot values (" + buckets + ", " + media + ", " + varianza + ")");
			buckets += 100;
		}
	}
}