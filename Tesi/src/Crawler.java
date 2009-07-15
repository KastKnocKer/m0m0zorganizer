import download.*;

public class Crawler {

	public static void main(String[] args) {

		new Scan();
		//urlReader.popularReader();
		//Runtime.getRuntime().gc();
		//Scan.popularScan();
		//Runtime.getRuntime().gc();
		Scan.toCheck();
		Runtime.getRuntime().gc();
		Scan.inactive();
		Runtime.getRuntime().gc();
		// Scansione completa
	}
}
