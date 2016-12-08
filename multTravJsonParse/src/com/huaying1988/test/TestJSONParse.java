package com.huaying1988.test;

import com.huaying1988.multTravJsonParse.JSON;

/**
 * JSON解析器测试类
 * @author huaying1988.com
 *
 */
public class TestJSONParse {
	public static void main(String[]args){
		//String str = "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\",\"key4\":[{\"a1\":\"1\",\"a2\":\"2\",\"a3\":\"3\",\"subChildA\":[{\"suba1\":\"3040\",\"suba2\":\"brebb\",\"suba3\":\"fbre\"},{\"suba1\":\"erbrrt\",\"suba2\":\"be4\",\"suba3\":\"5yh5\"},{\"suba1\":\"g445h\",\"suba2\":\"43th\",\"suba3\":\"r5yj4\"}],\"subChildB\":{\"suY1\":\"30L40\",\"suY2\":\"bre00bb\",\"suY3\":\"fbFGFre\",\"subChildA\":[{\"suba1\":\"3040\",\"suba2\":\"brebb\",\"suba3\":\"fbre\"},{\"suba1\":\"erbrrt\",\"suba2\":\"be4\",\"suba3\":\"5yh5\"},{\"suba1\":\"g445h\",\"suba2\":\"43th\",\"suba3\":\"r5yj4\"}]}},{\"a1\":\"s\",\"a2\":\"D\",\"a3\":\"F\"},{\"a1\":\"Q\",\"a2\":\"R\",\"a3\":\"T\"}],\"key5\":[{\"b1\":\"11\",\"b2\":\"21\",\"b3\":\"31\"},{\"b1\":\"3er\",\"b2\":\"3gt\",\"b3\":\"y7u\"},{\"b1\":\"H\",\"b2\":\"Y\",\"b3\":\"R\"}],\"key6\":\"uuid\",\"key7\":{\"vx1\":\"HwH\",\"vx2\":\"YrY\",\"vx3\":\"ReR\"}}";
		String str = "{\n\ta:[1,-23333,-0.3,0.17,5.2,\"\\u82B1\\u6979~\"],\n\tb:[\"a\tbc\",\"12  3\",\"4,5\\\"6\",{\n\t\t\t\t\tx:1,\n\t\t\t\t\ty:\"cc\\ncc\"\n\t\t\t\t},4.56],\n\t\"text\":\"I'm OK~\",\n\t\"1-2\":234,\n\tmybool:false,\n\tmynull:null,\n\tmyreal:true\n}\n";
		System.out.println(str);
		//JSON解析
		Object  o = JSON.parse(str);
		//使用第三方包fastjson用于对比前后结果
		String out = com.alibaba.fastjson.JSON.toJSONString(o);
		System.out.println(out);
	}
}
