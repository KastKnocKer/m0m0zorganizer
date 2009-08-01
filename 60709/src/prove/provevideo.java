package prove;

import scansioni.padre;
import database.DatabaseMySql;
import database.createRootDB;


public class provevideo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	new createRootDB();
		new DatabaseMySql("root");
		DatabaseMySql.connetti();
		padre.scansioneCompleta("prima");
		padre.scansioneVeloce(1, "prima", "oggi");
		padre.scansioneVeloce(2, "prima", "oggi");
		padre.scansioneVeloce(3, "prima", "oggi");
		padre.scansioneVeloce(4, "prima", "oggi");
		padre.scansioneVeloce(5, "prima", "oggi");		

		padre.scansioneCompleta("seconda");
		padre.scansioneVeloce(1, "seconda", "oggi");
		padre.scansioneVeloce(2, "seconda", "oggi");
		padre.scansioneVeloce(3, "seconda", "oggi");
		padre.scansioneVeloce(4, "seconda", "oggi");
		padre.scansioneVeloce(5, "seconda", "oggi");
		DatabaseMySql.Disconnetti();
	}

}
