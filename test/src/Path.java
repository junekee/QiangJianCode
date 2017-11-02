
public class Path {

	public static void main(String[] args){
		new Path().path();
	}
	public void path(){
		System.out.println(this.getClass().getClassLoader().getResource("").getPath().toString()); 
//		£½£½/D:/workspace/strutsTest/WebRoot/WEB-INF/classes/ 
		System.out.println(this.getClass().getResource("").getPath().toString()); 
//		£½£½/D:/workspace/strutsTest/WebRoot/WEB-INF/classes/bl/
		System.out.println(this.getClass().getResourceAsStream("/"));
	}
}
