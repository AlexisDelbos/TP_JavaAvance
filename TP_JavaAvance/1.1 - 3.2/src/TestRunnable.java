public class TestRunnable implements Runnable {

	private static int lineNumber = 1;

	private static final String[] symbols = { "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*" };

	@Override
	public void run() {
		displayPattern();
	}

	private synchronized void displayPattern() {
		if (lineNumber <= symbols.length) {
			String symbol = symbols[lineNumber - 1];
			StringBuilder pattern = new StringBuilder();

			pattern.append(symbol);
			for (int i = 0; i < lineNumber; i++) {
				pattern.append('*');
			}
			pattern.append(symbol);

			System.out.println(pattern.toString());

			lineNumber++;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new TestRunnable());
			thread.start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
