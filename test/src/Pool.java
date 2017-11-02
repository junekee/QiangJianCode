import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Pool {
	public static void main(String[] args) { 
		while(true){
			
		
		BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
         String str ="";
		try {
			str = strin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
			SendDataToWBThread st=	new SendDataToWBThread();
			st.setStr(str+".....");
			single.getsingle().execut(st);
//			single.getsingle().shutdown();
		}
		}  
	
	public void cachedThreadPool() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		cachedThreadPool.execute(new SendDataToWBThread());
		cachedThreadPool.execute(new SendDataToWBThread());
		cachedThreadPool.execute(new SendDataToWBThread());
	}
	
	public void singleThreadExecutor() {

		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		 
		for (int i = 0; i < 10; i++) {
			final int index = i;
			
			SendDataToWBThread st=	new SendDataToWBThread();
			st.setStr(index+"");
			singleThreadExecutor.execute(st);
		}
	}
	
	
}
 class SendDataToWBThread  extends Thread{
	 String str;
	 public synchronized void run() {
		 try {
			this.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(this.getStr()+"    "+this.getId());
	 }
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
 }
 
 class single{
	 
		private volatile static single single;

		public static single getsingle() {
			if (single == null) {
				synchronized (single.class) {
					if (single == null) {
						single= new single();
					}
				}
			}
			System.out.println(single.toString());
			return single;
		}
	 
		private volatile static ExecutorService singleThreadExecutor;

		public static ExecutorService getExecutorService() {
			if (singleThreadExecutor == null) {
				synchronized (ExecutorService.class) {
					if (singleThreadExecutor == null) {
						singleThreadExecutor = Executors.newSingleThreadExecutor();
					}
				}
			}
			System.out.println(singleThreadExecutor.toString());
			return singleThreadExecutor;
		}
		public void execut(Runnable run){
			getExecutorService().execute(run);
		}
		public void shutdown(){
			getExecutorService().shutdown();
		}
 }