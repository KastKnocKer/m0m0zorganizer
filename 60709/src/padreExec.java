
import scansioni.padre;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import database.createDatabase;

public class padreExec {

	public static void main(String[] args) {
		new DatabaseMySql("root"); // DA CONFIGURARE
		DatabaseMySql.connetti();	// Connessione al database
		OutputTxt.writeLog("Inizio scansione");
		String nomeDB;
		if (args[0] == null) {
			System.out.println("Inserire il nome del db per la scansione.");
			System.exit(0);
		}
		nomeDB = args[0];
		
		new createDatabase(nomeDB);		
		padre.scansioneCompleta(nomeDB);
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
	}
}