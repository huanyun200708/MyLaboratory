package cn.com.hq.service;


import com.google.gson.JsonObject;


/**
 * 防止跨站脚本注入接口
 * 主要功能是获取可能存在的脚本注入的HTML元素
 * 
 * @author zanglb
 *
 */
public interface XssService {
	
	/**
	 * 转义JAVASCRIPT元素
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	public String escapeHtmlForJS(String str);
	
	/**
	 * 转义HTML元素
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	public String escapeHtmlWithEsapi(String str);
	
	/**
	 * 转义HTML属性
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	public String escapeHtmlAttributeWithEsapi(String str);
	
	/**
	 * 转义URL 
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	public String escapeURLWithEsapi(String str);
	/**
	 * 转义CSS 
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	public String escapeCSSWithEsapi(String str);
	
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
    public String jsonForJavaScript(String jsonData);
	
    /**
	 * 转义Html元素
	 * 
	 * @param str 字符串
	 * @return 转义后的字符串
	 */
	public String responsewriteSpecial(String str , String str2);
	
	/**
	 * 数据处理pif2pia使用。判断pif文件里面的内容是否存在特殊字符，如果存在，则过滤掉
	 * @param value
	 * @return
	 */
	//静态检查注释内容，原因flag为false，后期可能会继续启用
	//public boolean checkIfHaveSpecial(String value);
	
	/**
	 * str需要被转移的字符串
	 * str2要保留的特殊字符，两个特殊字符之间用逗号隔开，比如&,<,>,....
	 */
	public String escapeHtmlForJS(String str , String str2);
	/**
	 * str需要被转移的字符串
	 * str2要保留的特殊字符，两个特殊字符之间用逗号隔开，比如&,<,>,....
	 * str3不能被删除的黑名单中的字符串，两个字符串之间用逗号隔开，比如script,xss,....。
	 */
	public String escapeHtmlForJS(String str , String str2 , String str3);
	
	/**
	 * 用于区分对element对象中用于前台展示的字段进行html转码
	 * @param elementJson
	 */
	public void encodeElementJsonForXss(JsonObject elementJson);
}
