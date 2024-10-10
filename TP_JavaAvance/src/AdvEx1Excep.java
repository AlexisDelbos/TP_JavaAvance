import java.util.Date;

public class AdvEx1Excep {

	public static void main(String[] args) {
		Date date = null;
		Date today = new Date();

		try {
			System.out.println(date.getClass().getName());
		} catch (Exception e) {
			System.out.println("Test Erreur");
		}
		System.out.println(today.getClass().getName());
	}
}
