//Rhenan Abachioni Gomes SP3069184
//Exercício de Threads - Sleep e Join
package exercicio1;

public class ClasseRunnable implements Runnable{

	@Override
	public void run() {
		System.out.println("Nome da thread: " + Thread.currentThread().getName());
		int ct=0;
		while(ct<100) {
			ct++;
			System.out.println(ct);
		}	
		System.out.println("Espere um pouco...");
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Thread em execução: " + Thread.currentThread().getName());
		Runnable rn = new ClasseRunnable();
		Thread thread1 = new Thread(rn);
		thread1.start();
		thread1.sleep(5000);
		Thread thread2 = new NovaThread();
		thread2.start();
		System.out.println("Join aparecerá em breve...");
		Thread thread3 = new Thread(() -> System.out.println("Outra Thread: " + Thread.currentThread().getName()));
		thread3.start();
		thread3.join();
		thread1.join();
		System.out.println("Testando Join e sleep!");
	}
}
