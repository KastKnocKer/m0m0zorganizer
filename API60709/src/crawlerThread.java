
public class crawlerThread implements Runnable {

	public crawlerThread () {
		Thread ct = Thread.currentThread();
		ct.setName("Thread principale");
		Thread t = new Thread(this, "Thread figlio");
		System.out.println("Thread attuale: " + ct);
		System.out.println("Thread creato: " + t);
		t.start();
		new padreExec();
		try {
			t.join();
		} catch (InterruptedException e) {
			System.out.println("principale interrotto");
		}
		System.out.println("Usicta crawlerPadre");
	}
	
	public void run() {
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {e.printStackTrace();}
		new figlioExec();
		System.out.println("Usicta crawlerFiglio");
	}
	
	
	public static void main(String args[]) {
		new crawlerThread();
    }
 }