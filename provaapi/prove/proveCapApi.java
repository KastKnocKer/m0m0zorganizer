package prove;

import java.util.Vector;
import database.*;
import download.*;

public class proveCapApi {

	public static void main(String[] args) {
    	new DatabaseMySql();
    	new OutputTxt();
    	new Contatore();
    	DatabaseMySql.connetti();
    	new API();
    	
		Vector<String[]> vettore;
		String[] user;
		for ( ; (vettore =  DatabaseMySql.eseguiQuery("Select * from utenti.username limit 10")) != null ; ) {
			user = vettore.get(0);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(1);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(2);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(3);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(4);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(5);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(6);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(7);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(8);
			if (API.getUser(user[0]))
				API.getSubscription(user[0]);
			user = vettore.get(9);

		}    
	}
}
