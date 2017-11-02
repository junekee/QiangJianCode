import java.util.ArrayList;
import java.util.List;

/**
 * List分页测试
 * @author Jandy
 * 2011-6-7 14:50:25
 */
public class Page {

 public static void main(String[] args) throws Exception {
  List<Object> p = new ArrayList<Object>();
  for(int i = 1; i <= 13; i++){
   p.add(i);
   //System.out.println(i+ "-" + p.get(i-1));
  }
  List<Object> result = page(1,6,p);
  for(int i = 0; i < result.size(); i ++){
   System.out.println(result.get(i));
  }
 }

 /**
  * 
  * @param pageNo 当前页码
  * @param pageSize 页数
  * @param list  所有集合
  * @return
  * @throws Exception
  */
 public static List<Object> page(int pageNo, int pageSize, List<Object> list)
			throws Exception {
		List<Object> result = new ArrayList<Object>();
		if (list != null && list.size() > 0) {
			int allCount = list.size();
			int pageCount = (allCount + pageSize - 1) / pageSize;
			System.out.println("总页数："+pageCount);
			if (pageNo >= pageCount) {
				pageNo = pageCount;
			}
			int start = (pageNo - 1) * pageSize;
			int end = pageNo * pageSize;
			if (end >= allCount) {
				end = allCount;
			}
			for (int i = start; i < end; i++) {
				result.add(list.get(i));
			}
		}
		return (result != null && result.size() > 0) ? result : null;
	}
}

