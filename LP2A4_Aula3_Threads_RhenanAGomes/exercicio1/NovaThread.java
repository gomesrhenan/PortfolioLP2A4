package exercicio1;

public class NovaThread extends Thread{
	public void run() {
		System.out.println("Nova thread: " + this.getName());
		System.out.println("ABCD");
		int ct = -1;
		while(ct>-100) {
			ct--;
			System.out.println(ct);
		}
	}
}
