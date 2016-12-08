import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.hq.entity.Account;
import cn.com.hq.util.JsonUtils;


public class TestSyso {

	public static void main(String[] args) {
		//{"name":"a2","description":"a\"2da\"}fd\r\n\\"}
	String str = "{\"name\":\"a2\",\"description\":\"a\\\"2da\"}fd\\\\r\\\\n\\\\\"}";
	checkJsonStr(str);
	/*try {
		System.out.println(str);
		Account a1 = JsonUtils.fromJson(str, Account.class);
		System.out.println(a1.getDescription());
	} catch (Exception e) {
		e.printStackTrace();
	}*/
	}
	public static void testRegex(){
		String regex = "^[\u2E80-\uFE4F\\w\\_\\-\\(\\)\\.\\/\\#\\$\\%\\~\\@\\s\\:\\=\\,\\;]*$";
		
		String line = "<>";
		if(line == null || "".equals(line.trim())){
		}else{
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(line);
			boolean isfind = false;
			while(m.find())
			{
				 isfind = true;
				line = m.group(m.groupCount());
				System.out.println(line);
			}
		}
	}
	
	public static void checkJsonStr(String str){
		System.out.println(str);
		System.out.println(str.indexOf("\""));
		System.out.println(str.charAt(str.indexOf("\"")));
		System.out.println(str.indexOf("\"", str.indexOf("\"")+1));
	}
}
