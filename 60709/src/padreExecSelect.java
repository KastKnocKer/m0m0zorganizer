
import scansioni.padre;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import database.createDatabase;

public class padreExecSelect {

	public static void main(String[] args) {
		new DatabaseMySql("connessione"); 
		DatabaseMySql.connetti();	// Connessione al database
		OutputTxt.writeLog("Inizio scansione");
		String nomeDB;
		if (args[0] == null) {
			System.out.println("Inserire il nome del db per la scansione.");
			System.exit(0);
		}
		nomeDB = args[0];
		
			
		padre.scansioneCompleta(nomeDB);
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(3, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(4, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(5, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(6, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(7, nomeDB, Orario.getDataOra());
	}
}