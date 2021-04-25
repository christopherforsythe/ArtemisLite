package game;

public class EndGame implements Runnable {

	@Override
	public void run() {

		try {
			
			System.err.println("Ending game in... ");

			for (int count = 5; count >= 0; count--) {
				Thread.sleep(1000);
				System.err.println(count);
			}
			
		} catch (InterruptedException continueGame) {
			
		}
		
	}

}
