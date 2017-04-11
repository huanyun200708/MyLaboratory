package cn.com.hq.serviceimpl;

import java.util.Map.Entry;
import java.util.Set;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;

import cn.com.hq.service.XssService;
import cn.com.hq.util.BeanUtils;
import cn.com.hq.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;


/**
 * 防止跨站脚本注入实现类
 * 主要功能是获取可能存在的脚本注入的HTML元素
 * 
 * @author zanglb
 *
 */
public class XssServiceImpl implements XssService {

	
	@Override
	public String escapeHtmlForJS(String str) {
		String result=str;
		if(!StringUtil.isEmpty(result))
    	{
			result = this.jsonForJavaScript(result);
			
    	}
        else
        {
            // 若result为null或者空串，则返回空
            // 防止jsp页面上当String unit = null时，通过var unit = '<%=unit%>';转换将其转换为了'null',而使页面回显失败
            return "";
        }
		
    	return result;
	}
	/**
	 * 转义HTML元素
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	@Override
	public String escapeHtmlWithEsapi(String str)
	{
		String result=str;
		if(!StringUtil.isEmpty(result))
    	{
			result = ESAPI.encoder().encodeForHTML(str);
    	}
		
    	return result;
	}
	
	/**
	 * 转义HTML属性
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	@Override
	public String escapeHtmlAttributeWithEsapi(String str)
	{
		String result=str;
		if(!StringUtil.isEmpty(result))
    	{
			result = ESAPI.encoder().encodeForHTMLAttribute(str);
    	}
		
    	return result;
	}
	
	/**
	 * 转义URL 
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	@Override
	public String escapeURLWithEsapi(String str)
	{
		String result=str;
		if(!StringUtil.isEmpty(result))
    	{
			try {
				result = ESAPI.encoder().encodeForURL(str);
			} catch (EncodingException e) {
				System.out.println("COMMON_XSS escapeURLWithEsapi EncodingException!");
			}
    	}
		
    	return result;
	}
	/**
	 * 转义CSS 
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	@Override
	public String escapeCSSWithEsapi(String str)
	{
		String result=str;
		if(!StringUtil.isEmpty(result))
    	{
			result = ESAPI.encoder().encodeForCSS(str);		
    	}
		
    	return result;
	}
    /**
     * 
     * JSON数据防跨站注入
     * (功能详细描述)
     * @author       f00165112
     * @see          相关函数，对于重要的函数建议注释
     * @since        Across PM V100R002C20, 2015-8-15
     * @param jsonData
     * @return
     */
    public String jsonForJavaScript(String jsonData)
    {
    	String result = jsonData;
        JsonElement je = null;
        try
        {
            je = new JsonParser().parse(result);
        }
        catch(JsonSyntaxException e)
        {	
				return jsonData.replace("&", "&amp;")
						.replace("<", "&lt;").replace(">", "&gt;")
						.replace("'", "&#x27;").replace("\"", "&quot;")
						.replace("/", "&#x2F;").replace("(" , "&#x28;")
	                    .replace(")", "&#x29;")
                        .replace("\\", "&#x5c;");
        }
        if(je.isJsonObject()){        	
        	je = dealJsonElement(je);
        }else if(je.isJsonArray()){
        	je = dealJsonArray((JsonArray) je);
        }else {
        	if(result.startsWith("\"")&&result.endsWith("\"")){
        		result = result.substring(1, result.length()-1);
        		result = result.replace("&", "&amp;")
    					.replace("<", "&lt;").replace(">", "&gt;")
    					.replace("'", "&#x27;").replace("\"", "&quot;")
    					.replace("/", "&#x2F;").replace("(" , "&#x28;")
                        .replace(")", "&#x29;")
                        .replace("\\", "&#x5c;");
        		return "\"" + result + "\"";
        	}
        	return result.replace("&", "&amp;")
					.replace("<", "&lt;").replace(">", "&gt;")
					.replace("'", "&#x27;").replace("\"", "&quot;")
					.replace("/", "&#x2F;").replace("(" , "&#x28;")
                    .replace(")", "&#x29;")
     				.replace("\\", "&#x5c;");
        }
        if(je==null){
            return "";
        }
        return je.toString();
    }
    /**
     * 转译HTML字符
     * @param str1 要被转译的字符串
     * str2 str1中不能被转译的特殊字符
     * 
     */
	@Override
	public String responsewriteSpecial(String str1, String strPt2) {
		String resultPt = str1;
    	
    	if(!StringUtil.isEmpty(resultPt))
    	{
    		if(null == strPt2){
    			resultPt = resultPt.replace("&", "&amp;")
    					.replace("<", "&lt;").replace(">", "&gt;")
    					.replace("'", "&#x27;").replace("\"", "&quot;")
    					.replace("/", "&#x2F;").replace("(" , "&#x28;")
                        .replace(")", "&#x29;")
         				.replace("\\", "&#x5c;");
    		}else{
	    		if(strPt2.indexOf("&")<0){
	    			resultPt = resultPt.replace("&", "&amp;");
	    		}
	    		if(strPt2.indexOf("<")<0){
	    			resultPt = resultPt.replace("<", "&lt;");
	    		}
	    		if(strPt2.indexOf(">")<0){
	    			resultPt = resultPt.replace(">", "&gt;");
	    		}
	    		if(strPt2.indexOf("'")<0){
	    			resultPt = resultPt.replace("'", "&#x27;");
	    		}
	    		if(strPt2.indexOf("/")<0){
	    			resultPt = resultPt.replace("/", "&#x2F;");
	    		}
	    		if(strPt2.indexOf("\"")<0){
	    			resultPt = resultPt.replace("\"", "&quot;");
	    		}
	    		if(strPt2.indexOf("(")<0){
	    			resultPt = resultPt.replace("(", "&#x28;");
	    		}
	    		if(strPt2.indexOf(")")<0){
	    			resultPt = resultPt.replace(")", "&#x29;");
	    		}
	    		if(strPt2.indexOf("\\")<0){
	    			resultPt = resultPt.replace("\\", "&#x5c;");
	    		}
    		}
    	}
    	
    	return resultPt;
	}
    /**
     * 
     * json数据递归处理
     * (功能详细描述)
     * @author       f00165112
     * @see          相关函数，对于重要的函数建议注释
     * @since        Across PM V100R002C20, 2015-8-15
     * @param je
     */
    private JsonElement dealJsonElement(JsonElement je)
    {
        JsonObject jo =  je.getAsJsonObject();
        Set<Entry<String, JsonElement>> jEntryset = jo.entrySet();
        try 
        {
        	
        	for(Entry<String, JsonElement> entry : jEntryset)
        	{
        		JsonElement sje = entry.getValue();
        		
        		if (sje.isJsonNull())
        		{
        			continue;
        		}
        		if(sje.isJsonObject())
        		{
        			sje = dealJsonElement(sje);
        			entry.setValue(sje);
        		}
        		else if (sje.isJsonArray())
        		{
        			sje = dealJsonArray((JsonArray)sje);
        			entry.setValue(sje);
        		}
        		else
        		{
        			JsonPrimitive jp =  sje.getAsJsonPrimitive();
	                if(jp.isString())
	                {
	                	String reslutstr = jp.getAsString().replace("&", "&amp;")
	        					.replace("<", "&lt;").replace(">", "&gt;")
	        					.replace("'", "&#x27;").replace("\"", "&quot;")
	        					.replace("/", "&#x2F;").replace("(" , "&#x28;")
	                            .replace(")", "&#x29;")
	             				.replace("\\", "&#x5c;");
	                	reslutstr = reslutstr.replace("\\\n", "");
	                    jp = new JsonPrimitive(reslutstr);
	                }
        			
        			entry.setValue(jp);
        		}
        	}
        }
        catch (Exception e)
        {
        	System.out.println("dealJsonElement error ");
        }
        return je;
    }
    
    private JsonElement dealJsonArray(JsonArray jsonArr)
    {
    	int size = jsonArr.size();
    	JsonArray newjsonArr = new JsonArray();
    	for(int i=0;i<size;i++)
    	{
    		JsonElement itemJson = jsonArr.get(i);
    		String strjson = jsonForJavaScript(itemJson.toString());
    		itemJson = new JsonParser().parse(strjson);
    		newjsonArr.add(itemJson);
    	}
    	return newjsonArr;
    }
    
    private static String escapeJsonStrHtmlForString(String html)
    {
    	if(!StringUtil.isEmpty(html)){
            html = html.replaceAll("&(lt;)+", "<")
                    .replaceAll("(&gt;)+", ">")
                    .replaceAll("&(#x27;)+", "'")
                    .replaceAll("&(quot;)+", "\\\\\"")
                    .replaceAll("&(#x2F;)+", "/")
                    .replaceAll("&(amp;)+", "&")
                    .replaceAll("&(#x28;)+", "(")
                    .replaceAll("&(#x29;)+", ")")
                    .replaceAll("&(#x5c;)+", "\\\\\\\\");
            if(html.indexOf("&lt;")>-1 || 
                    html.indexOf("&lt;")>-1 || 
                    html.indexOf("&gt;")>-1 || 
                    html.indexOf("&#x27;")>-1 || 
                    html.indexOf("&quot;")>-1 || 
                    html.indexOf("&#x2F;")>-1 || 
                    html.indexOf("&amp;")>-1 ||
                    html.indexOf("&#x28;")>-1 ||
                    html.indexOf("&#x29;")>-1||
                    html.indexOf("&#x5c;")>-1
                    ){
                html = escapeJsonStrHtmlForString(html);
            }
        }
    	return html;
    }
    private static String escapeNormalStrHtmlForString(String html)
    {
    	if(!StringUtil.isEmpty(html)){
            html = html.replaceAll("&(lt;)+", "<")
                    .replaceAll("(&gt;)+", ">")
                    .replaceAll("&(#x27;)+", "'")
                    .replaceAll("&(quot;)+", "\"")
                    .replaceAll("&(#x2F;)+", "/")
                    .replaceAll("&(amp;)+", "&")
                    .replaceAll("&(#x28;)+", "(")
                    .replaceAll("&(#x29;)+", ")")
                    .replaceAll("&(#x5c;)+", "\\\\");
            if(html.indexOf("&lt;")>-1 || 
                    html.indexOf("&lt;")>-1 || 
                    html.indexOf("&gt;")>-1 || 
                    html.indexOf("&#x27;")>-1 || 
                    html.indexOf("&quot;")>-1 || 
                    html.indexOf("&#x2F;")>-1 || 
                    html.indexOf("&amp;")>-1 ||
                    html.indexOf("&#x28;")>-1 ||
                    html.indexOf("&#x29;")>-1||
                    html.indexOf("&#x5c;")>-1
                    ){
                html = escapeNormalStrHtmlForString(html);
            }
        }
    	return html;
    }
    
    public static String escapeHtmlForString(String html)
    {
        String jsonStr = escapeJsonStrHtmlForString(html);
        JsonElement je = null;
        try
        {
            je = new JsonParser().parse(jsonStr);
        }
        catch(JsonSyntaxException e)
        {	
			return escapeNormalStrHtmlForString(html);
        }
        if(je.isJsonObject() || je.isJsonArray()){        	
        	return jsonStr;
        }else {
        	return escapeNormalStrHtmlForString(html);
        }
    }
    
	@Override
	public String escapeHtmlForJS(String str, String str2) {
		
		return null;
	}

	@Override
	public String escapeHtmlForJS(String str, String str2, String str3) {
		
		return null;
	}
	
	public static String encode8HtmlCharacters(String str)
	{
		if(!StringUtil.isEmpty(str)){			
			str = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
					 .replaceAll(">", "&gt;").replaceAll("'", "&#x27;")
					 .replaceAll("\"", "&quot;").replaceAll("/", "&#x2F;")
					 .replaceAll("\\(", "&#x28;").replaceAll("\\)", "&#x29;")
      				.replace("\\", "&#x5c;");
			
		}
		return str;
	}
	
	public static String encodeXmlCharacters(String str)
	{
		if(!StringUtil.isEmpty(str)){			
			str = str.replaceAll("&amp;", "&").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"")
					 .replaceAll("&#x27;", "'").replaceAll("&#x28;", "\\(").replaceAll("&#x29;", "\\)").replaceAll("&#x2F;", "/")
					 .replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;")
					 .replaceAll("'", "&#x27;").replaceAll("\\(", "&#x28;").replaceAll("\\)", "&#x29;").replaceAll("/", "&#x2F;")
					 .replace("\\", "&#x5c;");
		}
		return str;
	}
	
	/**
	 * 用于区分对element对象中用于前台展示的字段进行html转码
	 */
	@Override
	public void encodeElementJsonForXss(JsonObject elementJson)
	{
		encodeJsonObjByMemberName(elementJson, "elementName");
		encodeJsonObjByMemberName(elementJson, "elementStyleId");
		if(!BeanUtils.isEmpty(elementJson.get("indicatorList"))
				&& !"null".equals(elementJson.get("indicatorList").toString()) 
				&& !BeanUtils.isEmpty(elementJson.getAsJsonArray("indicatorList")))
		{
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("indicatorList"),"indicatorName");
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("indicatorList"),"indicatorAlias");
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("indicatorList"),"indicatorSetName");
		}
		if(!BeanUtils.isEmpty(elementJson.get("moEntityList"))
				&& !"null".equals(elementJson.get("moEntityList").toString()) 
				&& !BeanUtils.isEmpty(elementJson.getAsJsonArray("moEntityList")))
		{
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("moEntityList"),"objName");
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("moEntityList"),"objCode");
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("moEntityList"),"objShortName");
		}
		if(!BeanUtils.isEmpty(elementJson.get("attributeList"))
				&& !"null".equals(elementJson.get("attributeList").toString()) 
				&& !BeanUtils.isEmpty(elementJson.getAsJsonArray("attributeList")))
		{
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("attributeList"),"attributeName");
		}
		if(!BeanUtils.isEmpty(elementJson.get("series"))
				&& !"null".equals(elementJson.get("series").toString()) 
				&& !BeanUtils.isEmpty(elementJson.getAsJsonArray("series")))
		{
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("series"), "indicatorAlias");
			encodeJsonArrayByMemberName(elementJson.getAsJsonArray("series"), "seriesname");
		}
	}
	/**
	 * 处理JsonObject
	 * @param jsonObject
	 * @param memberName
	 */
	private void encodeJsonObjByMemberName(JsonObject jsonObject, String memberName)
	{
		Gson gson = new GsonBuilder().create();
		if (jsonObject.has(memberName) && !BeanUtils.isEmpty(jsonObject.get(memberName)))
		{
			String memberValue = encode8HtmlCharacters(jsonObject.get(memberName).getAsString());
			jsonObject.remove(memberName);
			jsonObject.add(memberName, gson.toJsonTree(memberValue));
		}
	}
	/**
	 * 处理JsonArray中的JsonObject列表
	 * @param jsonArray
	 * @param memberName
	 */
	private void encodeJsonArrayByMemberName(JsonArray jsonArray, String memberName)
	{
		Gson gson = new GsonBuilder().create();
		if(!BeanUtils.isEmpty(jsonArray))
		{			
			for (JsonElement jsonElement : jsonArray)
			{	
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.has(memberName) && !BeanUtils.isEmpty(jsonObject.get(memberName)))
				{
					String memberValue = encode8HtmlCharacters(jsonObject.get(memberName).getAsString());
					jsonObject.remove(memberName);
					jsonObject.add(memberName, gson.toJsonTree(memberValue));
				}
			}
		}
	}
}
