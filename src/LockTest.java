import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LockTest {

	private static Lock lock=new ReentrantLock();
	
	private static BlockingQueue<String> queue=new ArrayBlockingQueue<String>(10);
	
	public static void main(String[] args) {
		
		
		
		Thread t1=new Thread(){
			public void run() {
				String name="zhanghaibo";
				while(true){
					output(name);
				}
			};
		};
		
		Thread t2=new Thread(){
			public void run() {
				String name="baojun";
				while(true){
					output(name);
				}
			};
		};
		
		t1.start();
		t2.start();
	}
	
	public static void output(String name){
		int lenth=name.length();
		lock.lock();
		try{
			for(int i=0;i<lenth;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		finally{
			lock.unlock();
		}
	}

}
