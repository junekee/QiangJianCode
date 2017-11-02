import java.util.Date;


public class FirstThread {

	public static void main(String[] args){
		System.out.println("===========1========");
		new  Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println(new Date().toString());
			}}).start();
		System.out.println("===========2========");
	}
}
