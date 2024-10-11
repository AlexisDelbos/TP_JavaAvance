public class TestThread extends Thread {

	public TestThread(String name) {
		super(name);
	}

	public void run() {
		for (int i = 0; i < 1; i++) {
			System.out.println(this.getName());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TestThread t1 = new TestThread("1- 1- 1- 1-");
		TestThread t2 = new TestThread("5----- 5----- 5----- 5-----");
		TestThread t3 = new TestThread("3--- 3--- 3--- 3---");
		TestThread t4 = new TestThread("4---- 4----");
		TestThread t5 = new TestThread("2-- 2-- 2-- 2--");

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}
