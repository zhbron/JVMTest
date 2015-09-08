import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.io.File;
import java.util.*;

public class ForkJoinDemo {

	public static void main(String[] args) throws Exception{
		long start=System.currentTimeMillis();
		File file=new File("c:\\");
		String searchKey="README.txt";
		ForkJoinPool pool=new ForkJoinPool(10);
		Future<List<File>> result=pool.submit(new SearchTask(file,searchKey));
		List<File> r=result.get();
		long end=System.currentTimeMillis();
		System.out.println("Total :"+r.size());
		for(File f:r){
			System.out.println(f.getAbsolutePath());
		}
		System.out.println("Search Total Time : "+(end-start)+"ms");
	}

}


class SearchTask extends RecursiveTask<List<File>>{

	private File file;
	private String searchKey;
	
	private static final long serialVersionUID = 1L;
	
	public SearchTask(File file,String searchKey){
		this.file=file;
		this.searchKey=searchKey;
	}

	@Override
	protected List<File> compute() {
		List<File> list=new ArrayList<File>();
		File[] files=this.file.listFiles();
		List<SearchTask> taskList=new ArrayList<>();
		if(files!=null && files.length>0){
			List<SearchTask> all=new ArrayList<>();
			for(File f:files){
				if(f.isDirectory()){
					SearchTask task=new SearchTask(f, searchKey);
					task.fork();
					//List<File> l=task.join();
					//list.addAll(l);
				}
				else{
					if(searchKey.equalsIgnoreCase(f.getName())){
						list.add(f);
					}
				}
			}
			
		}
		List<SearchTask> tl=(List<SearchTask> )invokeAll(taskList);
		for(SearchTask t:tl){
			try {
				list.addAll(t.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
}
