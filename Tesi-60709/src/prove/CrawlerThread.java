package prove;

import download.Scan;
import download.urlReader;

public class CrawlerThread extends Thread {
	
	public CrawlerThread () {
		Thread ct = Thread.currentThread();
		ct.setName("Crawler principale");
		Thread t = new Thread(this, "Crawler figlio");
		System.out.println("Thread attuale: " + ct);
		System.out.println("Thread creato: " + t);
		
		new Scan();
		urlReader.popularReader();
		Runtime.getRuntime().gc();
		Scan.popularScan();
		Runtime.getRuntime().gc();
		
		t.start();

		Scan.toCheck();
		Runtime.getRuntime().gc();
		System.out.println("uscita Thread principale");
	}
	
	public void run() {
		System.out.println("Avvio Thread FIGLIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!");
		System.out.println("Avvio Thread FIGLIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!");
		System.out.println("Avvio Thread FIGLIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!");
		System.out.println("Avvio Thread FIGLIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!");
		System.out.println("Avvio Thread FIGLIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!");
		
		new Scan();
		Scan.toCheck();
	}
	
	public static void main(String args[]) {
		new CrawlerThread();
	}
}