
public class Qm {
	public static void main(String[] args){
//		System.out.println((75*2)%(75*2));
//		System.out.println((75*2)/(75*2));
//		System.out.println((75*2+1)%(75*2));
//		System.out.println((75*2+1)/(75*2));
//		System.out.println((75*2-1)%(75*2));
//		System.out.println((75*2-1)/(75*2));
		int c = 75*2+1;
		int count = 0;
		if(c %(75*2)==0&&c/(75*2)>0){
			count=c/(75*2);
		}else{
			count=c/(75*2)+1;
		}
		System.out.println(count);
	}
}
