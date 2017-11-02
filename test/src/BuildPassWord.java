
 
import java.security.MessageDigest;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
  
public class BuildPassWord {  
  
    public static List<Map<String, String>> lists = new ArrayList<Map<String, String>>();  
    public static String[] chars = "0123456789abcdefghijklmnopkrstuvwxyzABCDEFGHIJKLMNOPKRSTUVWXYZ!@#$%^&*()_+-=`~?><,.".split("");  
    public static int size = chars.length;  
      
    public static void main(String[] args){  
        generatePass(4);  
    }  
      
    public static void generatePass(int len){  
        Map<String, String> item = new HashMap<String, String>();  
        double count = 0;  
        for(double index = 0; index < Math.pow(chars.length, len); index++){  
            String pass = getNumPass(index);  
            
            System.out.println(pass);
            //item.put("pass", pass);  
            //item.put("md5", md5(pass));  
            count++;  
            synchronized(lists){  
                //lists.add(item);  
                  
            }  
        }  
        System.out.println(count);  
    }  
      
    public static String getNumPass(double num){  
          
        int len = 1;  
        int pos = 0;  
        if(num % size == 0){  
            pos = 1;  
        }  
          
        while(Math.pow(size, len) < num){  
            len += 1;  
        }  
          
        len += pos;  
  
          
          
        int[] arr = new int[len];  
          
        int count =  len - 1;  
        while(num >= size){  
            arr[count] = (int) (num % size);  
            num = Math.floor(num/size);  
            count--;  
        }  
          
          
        arr[count] = (int)num;  
          
        String result = "";  
        for(int i = 0; i < len; i++){  
            result += chars[arr[i]];  
        }  
          
        return result;  
    }  
      
    public static String md5(String s){  
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};         
        try {  
            byte[] btInput = s.getBytes();  
            // 获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            // 使用指定的字节更新摘要  
            mdInst.update(btInput);  
            // 获得密文  
            byte[] md = mdInst.digest();  
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }         
    }  
      
    public static class SavePass extends Thread{  
        public void run(){  
            while(true){  
                  
                while(BuildPassWord.lists.size() > 0){  
                    Map<String, String> item = BuildPassWord.lists.get(0);  
                    synchronized(BuildPassWord.lists){  
                    	BuildPassWord.lists.remove(0);  
                    }  
                      
                    System.out.println(item.get("md5") + "=" + item.get("pass"));  
                }  
            }  
        }  
    }  
}  


  class SearchTool {
    static int m=1;
    static void search(File a,String x) throws IOException{//在文件a中的每行中查找x
        Scanner scan = new Scanner(a,"gbk");
        int k = 0;
        while(true){    
            if(scan.hasNext()==false) break;
            String s = scan.nextLine();
            k++;
            if(s.contains(x)){
                String ss =m +".文件:"+ a.getPath() + " 第" + k + "行 \n  内容：" + s;
                System.out.println(ss);
                m++;
            }
        } 
        Scanner scan1 = new Scanner(a,"utf-8");
        int k1 = 0;
        while(true){    
            if(scan1.hasNext()==false) break;
            String s1 = scan1.nextLine();            
            k1++;
            if(s1.contains(x)){
                String ss1 =m +".文件:"+ a.getPath() + " 第" + k1 + "行 \n  内容：" + s1;
                System.out.println(ss1);
                m++;
            }
        } 
    }
    static void f(File a,String s)throws IOException{//在a下所有文件中查找含有s的行
        
        File[] ff = a.listFiles();
        if(ff==null) return;
        for(File it : ff){
            if(it.isFile()){//若a是文件，直接查找
                search(it,s);
            }
            if(it.isDirectory()){//若a是目录，则对其目录下的目录或文件继续查找
                f(it,s);
            }
        }        
    }

    public static void main(String[] args)throws IOException {
        f(new File("d:\\"),"对象");

    }

}