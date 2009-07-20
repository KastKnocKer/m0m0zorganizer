import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ethernet {
	
	public ethernet () {}
	
	public static void switchTo (boolean flag) {		
		try {
			if (flag)
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/swith_to_eth1.sh");
			else 
				pb = new ProcessBuilder ("/home/m0m0z/Scrivania/tesina_exec/swith_to_eth0.sh");
			scanner = pb.start ();
			BufferedReader in = new BufferedReader(	new InputStreamReader(scanner.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null)	{
				System.out.println(line);
			}
			try	{
				scanner.waitFor ();
			}
			catch (Exception e)	{
				System.out.println(e);
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}  
	
	}
	static ProcessBuilder pb;
	static Process scanner;
}
