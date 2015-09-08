
public class DeadLockDemo {

	
	public static void main(String[] args) throws Exception{
		final Object lock1=new Object();
		final Object lock2=new Object();
		
		new Thread("Producer"){
			public void run(){
				while(true){
					synchronized (lock1) {
						synchronized (lock2) {
							System.out.println(getId());
						}
					}
				}
			}
		}.start();
		
		new Thread("Counsumer"){
			public void run(){
				while(true){
					synchronized (lock2) {
						synchronized (lock1) {
							System.out.println(getId());
						}
					}
				}
			}
		}.start();
	}
	
	
}
