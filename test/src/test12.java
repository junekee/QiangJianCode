import net.sf.json.JSONObject;

import org.eredlab.g4.ccl.datastructure.impl.BaseDto;


public class test12 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String jsonString = "{\"id\":\"\",\"tableid\":\"1d5b362e-b059-4933-8c79-4da52319c6f2\",\"filepath\":\"\uploaddata\2017\03\20\b11420a3-37a3-4f0c-8ccb-abc33cc514e8.pdf\",\"filename\":\"23\",\"isupload\":\"2\",\"issign\":\"undefined\"}";
		System.out.println(\"--"+jsonString);
		JSONObject jb = JSONObject.fromObject(jsonString);
		dto = (BaseDto) JSONObject.toBean(jb, BaseDto.class);

	}

}
