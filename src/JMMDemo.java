
public class JMMDemo {

	static volatile int a,b,x,y;
	
	public static void main(String[] args) {

		while(true){
			a=b=x=y;
			Thread t1=new Thread(){
				public void run(){
					a=1;
					x=b;
				}
			};
			Thread t2=new Thread(){
				public void run(){
					
				}
			};
		}
	}

}
