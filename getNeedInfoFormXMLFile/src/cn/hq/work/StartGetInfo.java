package cn.hq.work;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.hq.utils.FileUtil;

public class StartGetInfo {
	public static void main(String[] args) {
		String filePath = "H:/Inspur/luna/V2R1_new/AcrossPM-Web/src/com/inspur/pmv5/core/utils/conf";
		Map<String,String> fileContents = getAllFileContent(filePath);
		List<String> messages = getMessages(fileContents, "");
		FileUtil.writeFile(messages, "D:/code file/java/a.txt");
	}
	private static Map<String,String> getAllFileContent(String filePath){
		Map<String,String> fileContents = new HashMap<String, String>(); 
		ArrayList<String> chileFiles  = FileUtil.getAllChildFilesWhithSuffix(filePath, "xml");
		for(String f : chileFiles){
			fileContents.put(f, FileUtil.readFileAsLine(f));
		}
		return fileContents;
	}
	
	private static List<String> getMessages( Map<String,String> fileContents,String queryStr){
		List<String> messages = new ArrayList<String>();
		 SAXReader reader = new SAXReader();
		 ByteArrayInputStream in = null;
	     Document doc = null;
	    for(Entry<String,String> e : fileContents.entrySet()){
	    	try
	        {
	 if(e.getKey().contains("getResGroupInfoAction_validate.xml")){
		 System.out.println();
		 
	 }
	        	in = new ByteArrayInputStream(e.getValue().getBytes("UTF-8"));
	            reader.setIncludeExternalDTDDeclarations(false);
	            reader.setIncludeInternalDTDDeclarations(false);
	            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); 
	        	doc = reader.read(in);
	            Element root = doc.getRootElement();
	            List<Element> fieldList = root.elements("field");
	            //遍历所有field节点
	            for (Element element : fieldList)
	            {
	            	 List<Element> validatorList = element.elements("field-validator");
	            	 boolean isHavaregex = false;//是否参数配置了正则校验
	            	//遍历所有validator节点
	            	for(Element el : validatorList){
	            		Attribute validatorAttribute = el.attribute("type");
	            		if("regex".equals(validatorAttribute.getText())
	            				||"customValidator".equals(validatorAttribute.getText())
	            				||"singleEnum".equals(validatorAttribute.getText())
	            		){
	            			if(el.attribute("value")!=null
	            					&&("common_name".equals(el.attribute("value").getText())
			            				||"common_code".equals(el.attribute("value").getText())
			            				||"common_query".equals(el.attribute("value").getText())
	            				     )
	            			){
	            				System.out.println("COMMON_REGEX "+e.getKey() + "----" + element.attribute("name").getText());
	            				messages.add("COMMON_REGEX "+e.getKey() + "----" + element.attribute("name").getText() + "");
	            			}
	            			isHavaregex = true;
	            		}
	            	}
	            	if(!isHavaregex){
	            		System.out.println("NONE_REGEX "+e.getKey() + "----" + element.attribute("name").getText());
	            		messages.add("NONE_REGEX "+e.getKey() + "----" + element.attribute("name").getText() + "");
	            	}
	            }
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
	        }finally{
	        	if(in!=null){
	    			try {
	    				in.close();
	    			} catch (IOException eex) {
	    				in = null;
	    			}
	    		}
	        }
	    }
		 
		return messages;
	}
}
