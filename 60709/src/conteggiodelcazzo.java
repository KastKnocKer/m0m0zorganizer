
public class conteggiodelcazzo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;
		for (;;) {
			System.out.println(i++);
			try {
				Thread.sleep(1100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
