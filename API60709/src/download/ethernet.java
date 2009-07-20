package download;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ethernet {
	
	public ethernet () {}
	
	public static void switchTo (boolean flag) {		
		try {
			if (flag) {
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/swith_to_eth1.sh");
				System.out.println("Switch from eth0 to eth1");
			}
			else {
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/swith_to_eth0.sh");
				System.out.println("Switch from eth1 to eth0");
			}
			scanner = pb.start ();
			BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
			try {Thread.sleep(7500);} catch (InterruptedException e1) {}
			try	{scanner.waitFor ();} catch (Exception e) {System.out.println(e);}
		}
		catch (IOException e) {
			System.out.println(e);
		}  
	
	}
	static ProcessBuilder pb;
	static Process scanner;
}
