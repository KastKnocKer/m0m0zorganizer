import scansioni.padre;
import database.DatabaseMySql;
import database.Orario;
import database.OutputTxt;

// Processo Padre
public class padreExec {

	public static void main(String[] args) {
		new DatabaseMySql("connessione"); 
		DatabaseMySql.connetti();	// Connessione al database
		String nomeDB;
		nomeDB = "prima";		
		OutputTxt.writeLog(nomeDB, "Inizio scansione per il DB: " + nomeDB);
		padre.scansioneCompleta(nomeDB);
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(3, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(4, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(5, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(6, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(7, nomeDB, Orario.getDataOra());
		nomeDB = "seconda";		
		OutputTxt.writeLog(nomeDB, "Inizio scansione per il DB: " + nomeDB);
		padre.scansioneCompleta(nomeDB);
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(3, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(4, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(5, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(6, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(7, nomeDB, Orario.getDataOra());
		nomeDB = "terza";		
		OutputTxt.writeLog(nomeDB, "Inizio scansione per il DB: " + nomeDB);
		padre.scansioneCompleta(nomeDB);
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(3, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(4, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(5, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(6, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(7, nomeDB, Orario.getDataOra());
		nomeDB = "quarta";		
		OutputTxt.writeLog(nomeDB, "Inizio scansione per il DB: " + nomeDB);
		padre.scansioneCompleta(nomeDB);
		padre.scansioneVeloce(1, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(2, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(3, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(4, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(5, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(6, nomeDB, Orario.getDataOra());
		padre.scansioneVeloce(7, nomeDB, Orario.getDataOra());
		nomeDB = "quinta";		
		OutputTxt.writeLog(nomeDB, "Inizio scansione per il DB: " + nomeDB);
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
