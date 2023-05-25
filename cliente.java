package artistaDaStrada;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class cliente extends Thread {
	private int id, tempo;
	private Random rnd = new Random();
	private int tempoMinRitratto=3000;
	private int tempoMaxRitratto=10000;
	private int tempoAttesaClienti=5;
	
	public cliente(int id)
	{
		this.id=id; 
		this.tempo=rnd.ints(tempoMinRitratto,tempoMaxRitratto).findFirst().getAsInt();
	}
	
	public void run() {
		try {
			if(Main.semaphore.tryAcquire(tempoAttesaClienti,TimeUnit.SECONDS)) {
			System.out.println("Il cliente numero "+this.id+" si è seduto");
			try {
			Main.mutex.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("l'artista sta dipingendo il cliente numero "+this.id);
		try {
			Thread.sleep(this.tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Main.mutex.release();
		
		
		System.out.println("L'artista ha completato il dipinto del cliente numero "+this.id);
		Main.semaphore.release();
			}
			else {
				System.out.println("il cliente numero "+this.id+" se nè andato");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
