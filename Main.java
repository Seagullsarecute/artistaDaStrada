package artistaDaStrada;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static Semaphore semaphore = new Semaphore(4);
	public static Semaphore mutex= new Semaphore(1);
	public static int clienti, time_arrival;
	private static Random ran= new Random();
	public static int tempoMinArrivoClienti=2000;
	public static int tempoMaxArrivoClienti=4000;
	
	public static void main(String []args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Numero di clienti: ");
		clienti = Integer.parseInt(in.readLine());
		
		for(int i=1;i<=clienti;i++)
		{
			time_arrival= ran.ints(tempoMinArrivoClienti,tempoMaxArrivoClienti).findFirst().getAsInt();
			try {
				Thread.sleep(time_arrival);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cliente c= new cliente(i);
			c.start();
			System.out.println("è arrivato il cliente numero "+i);
		}
		
		
	}
}
