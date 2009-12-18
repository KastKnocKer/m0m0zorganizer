package analisi;

import java.util.Vector;

import Database.DatabaseMySql;

public class Probabilita_Rel {

	static String user, relazionato;
	static String status;
	static Vector<String[]> vector;
	static int tot=0, si=0, no=0, scansionati=0, nonScansionati=0;
	
	public static void main(String[] args) {
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		while ((user = DatabaseMySql.extract("totale", "profileTemp", "user")[0]) != null) {
			amicizia_iscrizione();
			amicizia_iscrittoAme();
			subscription_subscriber();
			subscriber_subscription();
			subscriber_friend();
			subscription_friend();
		}
		System.out.println("Totale: 			" + tot);
		System.out.println("Scansionati:		" + scansionati);
		System.out.println("Non scansionati: 	" + nonScansionati);
	}
	
	// Siamo amici, sono anche iscritto al suo canale?
	public static void amicizia_iscrizione () {
		tot=0; 
		si=0; 
		scansionati=0; 
		vector = DatabaseMySql.eseguiQuery("Select friend from totale.friends where user='" + user +"'");
		while (vector.size() != 0) {
			tot++;
			relazionato = vector.get(0)[0];
			if (DatabaseMySql.contiene("totale", "profileAttivi", "user", relazionato)) {
				scansionati++;
				if (DatabaseMySql.contiene("totale", "subscriptions", "user", user, "subscription", relazionato))
					si++; // Vedo nelle mie iscrizioni se sono iscritto al suo canale
				else if (DatabaseMySql.contiene("totale", "subscribers", "user", relazionato, "subscriber", user))
					si++; // Vedo dai suoi iscritti se sono iscritto al suo canale
			}
			vector.removeElementAt(0);
		}
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set friendPtion_Si='" +
				si + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set friendPtion_Scan='" +
				scansionati + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set friendPtion_Tot='" +
				tot + "' where user='" + user + "'");
		System.out.println("Utente: " + user + " ha " + si + " friendPtion su " + scansionati);			
	}
	
	// Siamo amici, lui è iscritto al mio canale?
	public static void amicizia_iscrittoAme () {
		tot=0; 
		si=0; 
		scansionati=0; 
		vector = DatabaseMySql.eseguiQuery("Select friend from totale.friends where user='" + user +"'");
		while (vector.size() != 0) {
			tot++;
			relazionato = vector.get(0)[0];
			if (DatabaseMySql.contiene("totale", "profileAttivi", "user", relazionato)) {
				scansionati++;
				if (DatabaseMySql.contiene("totale", "subscriptions", "user", relazionato, "subscription", user))
					si++; // Vedo nelle sue iscrizioni se è iscritto al mio canale
				else if (DatabaseMySql.contiene("totale", "subscribers", "user", user, "subscriber", relazionato))
					si++; // Vedo dai miei iscritti se è iscritto al mio canale
			}
			vector.removeElementAt(0);
		}
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set friendBer_Si='" +
				si + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set friendBer_Scan='" +
				scansionati + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set friendBer_Tot='" +
				tot + "' where user='" + user + "'");
		System.out.println("Utente: " + user + " ha " + si + " friendBer su " + scansionati);			
	}
	
	// Io sono iscritto a lui, lui è iscritto a me?
	public static void subscription_subscriber () {
		tot=0; 
		si=0; 
		scansionati=0;
		vector = DatabaseMySql.eseguiQuery("Select subscription from totale.subscriptions where user='" + user +"'");
		while (vector.size() != 0) {
			tot++;
			relazionato = vector.get(0)[0];
			if (DatabaseMySql.contiene("totale", "profileAttivi", "user", relazionato)) {
				scansionati++;
				if (DatabaseMySql.contiene("totale", "subscriptions", "user", relazionato, "subscription", user))
					si++; // Vedo nelle sue iscrizioni se è iscritto al mio canale
				else if (DatabaseMySql.contiene("totale", "subscribers", "user", user, "subscriber", relazionato))
					si++; // Vedo dai miei iscritti se è iscritto al mio canale
			}
			vector.removeElementAt(0);
		}
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set ptionBer_Si='" +
				si + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set ptionBer_Scan='" +
				scansionati + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set ptionBer_Tot='" +
				tot + "' where user='" + user + "'");
		System.out.println("Utente: " + user + " ha " + si + " ptionBer su " + scansionati);				
	}
	
	// Y sono iscritti ad X, X è iscritto agli Y?
	public static void subscriber_subscription () {
		tot=0; 
		si=0; 
		scansionati=0;
		vector = DatabaseMySql.eseguiQuery("Select subscriber from totale.subscribers where user='" + user +"'");
		while (vector.size() != 0) {
			tot++;
			relazionato = vector.get(0)[0];
			if (DatabaseMySql.contiene("totale", "profileAttivi", "user", relazionato)) {
				scansionati++;
				if (DatabaseMySql.contiene("totale", "subscriptions", "user", user, "subscription", relazionato))
					si++; // Vedo nelle sue iscrizioni se è iscritto al mio canale
				else if (DatabaseMySql.contiene("totale", "subscribers", "user", relazionato, "subscriber", user))
					si++; // Vedo dai miei iscritti se è iscritto al mio canale
			}
			vector.removeElementAt(0);
		}
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set berPtion_Si='" +
				si + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set berPtion_Scan='" +
				scansionati + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set berPtion_Tot='" +
				tot + "' where user='" + user + "'");
		System.out.println("Utente: " + user + " ha " + si + " berPtion su " + scansionati);				
	}
	
	// Y sono iscritti ad X, X è amico degli Y?
	public static void subscriber_friend () {
		tot=0; 
		si=0; 
		scansionati=0;
		vector = DatabaseMySql.eseguiQuery("Select subscriber from totale.subscribers where user='" + user +"'");
		while (vector.size() != 0) {
			tot++;
			relazionato = vector.get(0)[0];
			if (DatabaseMySql.contiene("totale", "profileAttivi", "user", relazionato)) {
				scansionati++;
				if (DatabaseMySql.contiene("totale", "friends", "user", relazionato, "friend", user))
					si++; // Vedo dai suoi amici se siamo legati
				else if (DatabaseMySql.contiene("totale", "friends", "user", user, "friend", relazionato))
					si++; // Vedo dai miei amici se siamo legati
			}
			vector.removeElementAt(0);
		}
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set berFriend_Si='" +
				si + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set berFriend_Scan='" +
				scansionati + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set berFriend_Tot='" +
				tot + "' where user='" + user + "'");
		System.out.println("Utente: " + user + " ha " + si + " berFriend su " + scansionati);				
	}
	
	// Se sono iscritto, sono anche amico?
	public static void subscription_friend () {
		tot=0; 
		si=0; 
		scansionati=0;
		vector = DatabaseMySql.eseguiQuery("Select subscription from totale.subscriptions where user='" + user +"'");
		while (vector.size() != 0) {
			tot++;
			relazionato = vector.get(0)[0];
			if (DatabaseMySql.contiene("totale", "profileAttivi", "user", relazionato)) {
				scansionati++;
				if (DatabaseMySql.contiene("totale", "friends", "user", relazionato, "friend", user))
					si++; // Vedo dai suoi amici se siamo legati
				else if (DatabaseMySql.contiene("totale", "friends", "user", user, "friend", relazionato))
					si++; // Vedo dai miei amici se siamo legati
			}
			vector.removeElementAt(0);
		}
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set ptionFriend_Si='" +
				si + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set ptionFriend_Scan='" +
				scansionati + "' where user='" + user + "'");
		DatabaseMySql.eseguiAggiornamento("Update totale.probRelation set ptionFriend_Tot='" +
				tot + "' where user='" + user + "'");
		System.out.println("Utente: " + user + " ha " + si + " ptionFriend su " + scansionati);				
	}
}
