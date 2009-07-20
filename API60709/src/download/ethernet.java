package download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import database.DatabaseMySql;


public class ethernet {
	
	public ethernet () {}
	
	public static void switchTo (String nomeDB, boolean flag) {		
		try {
			if (flag) {
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/switch_to_eth1.sh");
				System.out.println("Switch from eth0 to eth1");
			}
			else {
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/switch_to_eth0.sh");
				System.out.println("Switch from eth1 to eth0");
			}
			DatabaseMySql.delete(nomeDB, "ethernet", "rete", "eth");
			DatabaseMySql.insert(nomeDB, "ethernet", "false");
			try {Thread.sleep(250);} catch (InterruptedException e2) {}
			scanner = pb.start ();
			BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
			try {
				Thread.sleep(7500);
				DatabaseMySql.delete(nomeDB, "ethernet", "rete", "eth");
				DatabaseMySql.insert(nomeDB, "ethernet", "true");
			} catch (InterruptedException e1) {}
			try	{scanner.waitFor ();} catch (Exception e) {System.out.println(e);}
		}
		catch (IOException e) {
			System.out.println(e);
		}  
	
	}
	
	public static void checkEthernet (String nomeDB) {
		if (DatabaseMySql.eseguiQuery("Select flag from " + nomeDB + ".ethernet").get(0)[0].contains("true"))
			return;
		else {
			try {
				System.out.println("Ethernet switching..Attendere..");
				Thread.sleep(7500);	
				return;
			} catch (InterruptedException e1) {}
		}
	}
		
	static ProcessBuilder pb;
	static Process scanner;
	static boolean flag;
}
