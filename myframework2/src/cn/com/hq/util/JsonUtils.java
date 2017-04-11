package cn.com.hq.util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * json 转换工具
 * 
 * @author 杨伟俊 2013-04-08 16:33
 * 
 */
public class JsonUtils
{
    
	  /**
     * java对象转字符串
     * 
     * @param <T>
     *            泛型占位符
     * @param obj
     *            T
     * @return String
     */
    public static <T> String toJson(T obj)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }
    
    /**
     * java对象转字符串
     * 
     * @param obj
     *            被转对象
     * @param dateFormatter
     *            自定义日期格式
     * @param <T> <T>
     * @return String
     */
    public static <T> String toJson(T obj, String dateFormatter)
    {
        Gson gson = new GsonBuilder().setDateFormat(dateFormatter).enableComplexMapKeySerialization().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }
    
    /**
     * <p>
     * json字符串转java对象
     * </p>
     * 
     * <p>
     * 例子1: 假设发序列化普通类Person
     * </p>
     * <p>
     * <code>
     *  JsonUtils.fromJson("{id:1,name:'a'}", Person.class);
     * </code>
     * </p>
     * 
     * <p>
     * 例子2: 假设反序列化List&lt;Person&gt;类
     * </p>
     * <p>
     * <code>
     *  JsonUtils.fromJson("[{id:1,name:'a'},{id:2,name:'b'}]", new TypeToken&lt;List&lt;Person&gt;&gt;(){}.getType());
     * </code>
     * </p>
     * 
     * @param <T>
     *            泛型占位符
     * @param json
     *            String 字符串
     * @param type
     *            Type
     * @return T 转换后的结果对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Type type)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Object obj = gson.fromJson(json, type);
        return (T)obj;
    }
    
    /**
     * json字符串转java对象
     * 
     * @param json
     *            json 数据串
     * @param type
     *            Gson type 类型
     * @param dateFormat
     *            日期格式
     * @param <T> <T>
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Type type, String dateFormat)
    {
        Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
        Object obj = gson.fromJson(json, type);
        return (T)obj;
    }
    
    /**
     * json 转 map
     * 
     * @param json
     *            json 数据串
     * @return Map
     */
    public static Map<String, String> json2Map(String json)
    {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, Object>>()
        {
        }.getType());
        return map;
    }
    
    /**
     * 解析JSON为原始JsonElement
     * 
     * @param json
     *            json串
     * @return JsonElement
     */
    public static JsonElement fromJson(String json)
    {
        return new JsonParser().parse(json);
    }
}

