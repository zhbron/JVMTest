import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchDemo {

	public static List<File> list=new ArrayList<File>();
	
	public static ExecutorService pool=Executors.newFixedThreadPool(5);
	
	public static void main1(String[] args){
		long start=System.currentTimeMillis();
		singleThreadSearch();
		long end=System.currentTimeMillis();
		System.out.println("Search Total Time : "+(end-start)+"ms");
	}
	
	
	public static void main(String[] args) throws Exception{
		System.out.println(Runtime.getRuntime().availableProcessors());
		final long start=System.currentTimeMillis();
		final String searchName="README.txt";
		multipleThreadSearch(searchName);
		pool.shutdown();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Search Total Time : "+(System.currentTimeMillis()-start)+"ms");
			}
		}));
	}
	
	public static void singleThreadSearch() {
		// TODO Auto-generated method stub
		SearchDemo sd=new SearchDemo();
		File file=new File("c:\\");
		sd.search(file,"README.txt");
	}
	
	public static void multipleThreadSearch(final String searchName){
		final SearchDemo sd=new SearchDemo();
		File file=new File("c:\\");
		File[] files=file.listFiles();
		for(int i=0;i<files.length;i++){
			final File f=files[i];
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					sd.search(f,searchName);
				}
			}).start();*/
			pool.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					sd.search(f,searchName);
				}
			});
		}	
	}
	
	public void search(File file,String searchName){
		if(file.isFile()){
			String fileName=file.getName();
			if(searchName.equalsIgnoreCase(fileName)){
				System.out.println(file.getAbsolutePath());
			}
		}
		else if(file.isDirectory()){
			File[] files=file.listFiles();
			if(files!=null && files.length>0){
				for(File f:files){
					search(f,searchName);
				}
			}
		}
	}

}
