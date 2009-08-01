package prove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gdata.data.spreadsheet.Data;

import database.Orario;
import database.createRootDB;

public class provesubscription {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//new createRootDB();
		ProcessBuilder pb = new ProcessBuilder ("java", "bin.conteggiodelcazzo"); 
		try {
			Process process = pb.start();
			System.out.println("Here is the standard output of the command:\n");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = stdInput.readLine()) != null) {
				System.out.println(line);
			}
 
			System.out.println("Here is the error output of the command:\n");
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = stdError.readLine()) != null) {
				System.out.println(line);
			}
			process.waitFor();
			System.out.println(process.exitValue());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
}
