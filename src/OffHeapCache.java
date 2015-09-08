import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;


public class OffHeapCache {

	public static int capacity=10*1024*1024;
	
	public static ByteBuffer buf=ByteBuffer.allocateDirect(capacity);
	
	public static Map<String,BufferObjectPoint> bufferPointMap=new HashMap<String, BufferObjectPoint>();
	
	private static int size=0;
	
	public static void put(String key,Object obj) throws Exception{

		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ObjectOutputStream out=new ObjectOutputStream(bos);
		out.writeObject(obj);
		byte[] bytes=bos.toByteArray();
		
		BufferObjectPoint point=new BufferObjectPoint();
		point.position=size;
		point.size=bytes.length;
		
		buf.position(size);
		//buf.limit(capacity);
		buf.put(bytes);
		
		point.limit=buf.position();
		bufferPointMap.put(key, point);
		size+=point.size;
	}
	
	public static Object get(String key) throws Exception{
		BufferObjectPoint point=bufferPointMap.get(key);
		int position=point.position;
		int limit=point.limit;
		int size=point.size;

		byte[] bytes=new byte[size];
		buf.position(position);
		buf.limit(limit);
		
		buf.get(bytes);
		
		ByteArrayInputStream bis=new ByteArrayInputStream(bytes);
		ObjectInputStream ois=new ObjectInputStream(bis);
		Object obj=ois.readObject();
		return obj;
	}
	
	
	public static void main(String[] arg) throws Exception{
		Student student1=new Student();
		student1.id="1werw";
		student1.name="aaa";
		
		Student student2=new Student();
		student2.id="2werew";
		student2.name="bbb";
		
		Map<String ,String> map=new HashMap<String,String>();
		map.put("name", "zhbron");
		map.put("address", "shanghai");
		
		
		OffHeapCache.put("aaa", student1);
		OffHeapCache.put("bbb", student2);
		OffHeapCache.put("map", map);
		
		Map<String,String> cacheMap=(Map<String,String>)OffHeapCache.get("map");
		for(String key:cacheMap.keySet()){
			System.out.println(key+":"+cacheMap.get(key));
		}
	}
}

class Student implements Serializable{
	public String id;
	public String name;
}

class BufferObjectPoint{
	public int position;
	public int limit;
	public int size;
}
