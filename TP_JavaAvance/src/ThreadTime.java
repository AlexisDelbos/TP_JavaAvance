import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ThreadTime {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Horloge Digitale");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 100);

		JLabel timeLabel = new JLabel();
		timeLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

		frame.add(timeLabel);
		frame.setVisible(true);

		Thread thread = new Thread(new MonRunnable(1000, timeLabel));
		thread.start();
	}

	// Classe Runnable pour mettre à jour l'heure
	private static class MonRunnable implements Runnable {

		private long delai;
		private JLabel label;

		public MonRunnable(long delai, JLabel label) {
			this.delai = delai;
			this.label = label;
		}

		@Override
		public void run() {

			DateFormat df = new SimpleDateFormat("HH:mm:ss");

			while (true) {
				try {

					String currentTime = df.format(new Date());
					label.setText(currentTime);

					Thread.sleep(delai);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
