
import scansioni.padre;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;
import database.createDatabase;

public class padreExec {

	public static void main(String[] args) {
		new DatabaseMySql("utenti"); // DA CONFIGURARE
		DatabaseMySql.connetti();	// Connessione al database
		OutputTxt.writeLog("Inizio scansione");
		String nomeDB;
		nomeDB = "utenti";
		
		new createDatabase(nomeDB);		
		padre.scansioneCompleta(nomeDB);
		new Orario();
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
	}
}