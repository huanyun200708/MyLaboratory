package com.huaying1988.multTravJsonParse;

import com.huaying1988.multTravJsonParse.syntax.StateMachine;

/**
 * JSON解析器的总入口,该JSON解析器为一个多次遍历解析器（结构清晰，但效率上多有折损），实现较为简单，以弄清原理为目标
 * @author huaying1988.com
 *
 */
public class JSON {
	/**
	 * 解析json字符串返回List/Map的嵌套结构或值（true、false、number、null）
	 * @param str 要解析的json字符串
	 * @return 解析的结果对象
	 */
	public static Object parse(String str) {
		if (str == null || "".equals(str.trim()))
			return null;
		else
			return (new StateMachine(str)).parse();
	}
}
