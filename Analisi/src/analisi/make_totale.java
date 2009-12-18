package analisi;

import Database.DatabaseMySql;

public class make_totale {

	public static void main(String[] args) {
		
		new DatabaseMySql("root");		// Definisco il database per tutto il programma
		DatabaseMySql.connetti();
		
		DatabaseMySql.eseguiAggiornamento("insert into totale.profileTemp (user) (select user from prima.profile) UNION (select user from seconda.profile) UNION (select user from terza.profile) UNION (select user from quarta.profile) UNION (select user from quinta.profile)");
	/*	System.out.println("Activity");	
		DatabaseMySql.eseguiAggiornamento("insert into totale.activity (user, id, action, updated) (select user, id, action, updated from prima.activity) UNION (select user, id, action, updated from prima.activity1) UNION (select user, id, action, updated from prima.activity2) UNION (select user, id, action, updated from prima.activity3) UNION (select user, id, action, updated from prima.activity4) UNION (select user, id, action, updated from prima.activity5) UNION (select user, id, action, updated from prima.activity6) UNION (select user, id, action, updated from prima.activity7) UNION (select user, id, action, updated from seconda.activity) UNION (select user, id, action, updated from seconda.activity1) UNION (select user, id, action, updated from seconda.activity2) UNION (select user, id, action, updated from seconda.activity3) UNION (select user, id, action, updated from seconda.activity4) UNION(select user, id, action, updated from seconda.activity5) UNION (select user, id, action, updated from seconda.activity6) UNION (select user, id, action, updated from seconda.activity7) UNION (select user, id, action, updated from terza.activity) UNION (select user, id, action, updated from terza.activity1) UNION (select user, id, action, updated from terza.activity2) UNION (select user, id, action, updated from terza.activity3) UNION (select user, id, action, updated from terza.activity4) UNION (select user, id, action, updated from terza.activity5) UNION (select user, id, action, updated from terza.activity6) UNION (select user, id, action, updated from terza.activity7) UNION (select user, id, action, updated from quarta.activity) UNION (select user, id, action, updated from quarta.activity1) UNION (select user, id, action, updated from quarta.activity2) UNION (select user, id, action, updated from quarta.activity3) UNION (select user, id, action, updated from quarta.activity4) UNION (select user, id, action, updated from quarta.activity5) UNION (select user, id, action, updated from quarta.activity6) UNION (select user, id, action, updated from quarta.activity7) UNION (select user, id, action, updated from quinta.activity) UNION (select user, id, action, updated from quinta.activity1) UNION (select user, id, action, updated from quinta.activity2) UNION (select user, id, action, updated from quinta.activity3) UNION (select user, id, action, updated from quinta.activity4) UNION (select user, id, action, updated from quinta.activity5) UNION (select user, id, action, updated from quinta.activity6) UNION (select user, id, action, updated from quinta.activity7)");
		System.out.println("Favoriti");
		DatabaseMySql.eseguiAggiornamento("insert into totale.favorites (user, id, uploader, published) (select user, id, uploader, published from prima.favorites) UNION (select user, id, uploader, published from seconda.favorites) UNION (select user, id, uploader, published from terza.favorites) UNION (select user, id, uploader, published from quarta.favorites) UNION (select user, id, uploader, published from quinta.favorites)");
		System.out.println("Friend");
		DatabaseMySql.eseguiAggiornamento("insert into totale.friends (user, friend) (select user, friend from prima.friends) UNION (select user, friend from seconda.friends) UNION (select user, friend from terza.friends) UNION (select user, friend from quarta.friends) UNION (select user, friend from quinta.friends)");
		System.out.println("Subscriptions");
		//DatabaseMySql.eseguiAggiornamento("insert into totale.subscriptions (user, subscription, published) (select user, subscription, published from prima.subscriptions) UNION (select user, subscription, published from seconda.subscriptions) UNION (select user, subscription, published from terza.subscriptions) UNION (select user, subscription, published from quarta.subscriptions) UNION (select user, subscription, published from quinta.subscriptions)");
		System.out.println("Subscribers");
		DatabaseMySql.eseguiAggiornamento("insert into totale.subscribers (user, subscriber) (select user, subscriber from prima.subscribers) UNION (select user, subscriber from seconda.subscribers) UNION (select user, subscriber from terza.subscribers) UNION (select user, subscriber from quarta.subscribers) UNION (select user, subscriber from quinta.subscribers)");
		*/
	}
}
