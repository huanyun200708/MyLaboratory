package cn.com.hq.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * 用于复制request中的InputStream
 * 
 * 
 * */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
    private Map<String , String[]> params = new HashMap<String, String[]>();
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) 
throws IOException {
        super(request);
        this.params.putAll(request.getParameterMap());
        byte[] body  = new byte[1024];
        int len;
        InputStream in  = request.getInputStream();
        while (in!=null && (len = in.read(body)) > -1 ) {  
            baos.write(body, 0, len);  
        }  
        baos.flush();
    }
    
    @Override
    public String getParameter(String name) {
    	//重写getParameter，代表参数从当前类中的map获取
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        JsonElement je = null;
        try
        {
            je = new JsonParser().parse(values[0]);
            if(je==null){
            	return values[0].trim();
            }else if(je.isJsonObject() || je.isJsonArray()){     
            	return handleSpecialcharacter(values[0].trim());
            }else {
            	return values[0].trim();
            }
        }
        catch(Exception e)
        {	if(this.getServletPath().indexOf("exportData_query")>-1 && "graphElements".equals(name)){
        		return handleSpecialcharacter(values[0].trim());
        	}
			return values[0].trim();
        }
        
       
    }
    
    private String  handleSpecialcharacter(String s){
    	String result = s
    			.replaceAll("(?<!\\\\)\"((?<!\\\\)\\\\t+|(?<!\\\\)\\\\r+|(?<!\\\\)\\\\n+|\\s+)+","\"")
				.replaceAll("((?<!\\\\)\\\\t+|(?<!\\\\)\\\\r+|(?<!\\\\)\\\\n+|\\s+)+(?<!\\\\)\"","\"");
    	
    	if(this.getServletPath().indexOf("createElement_cptPre")>-1
    			||this.getServletPath().indexOf("getDataSource_cptPre")>-1
    			||this.getServletPath().indexOf("getSampleForGrap_preview")>-1
    			||this.getServletPath().indexOf("columns_reportResult")>-1
    			||this.getServletPath().indexOf("getGraphData_query")>-1
    			||this.getServletPath().indexOf("exportData_query")>-1
    			||this.getServletPath().indexOf("export_reportResult")>-1
    			||this.getServletPath().indexOf("updateGraphResult_reportResult")>-1
    			||this.getServletPath().indexOf("resultExport_topic")>-1
    			){
    		result = result.replaceAll("&amp;#x5c;r", "&amp;#x5c;&amp;#x5c;r");
    		result = result.replaceAll("&amp;#x5c;n", "&amp;#x5c;&amp;#x5c;n");
    		result = result.replaceAll("&amp;#x5c;b", "&amp;#x5c;&amp;#x5c;b");
    		result = result.replaceAll("&amp;#x5c;t", "&amp;#x5c;&amp;#x5c;t");
    		result = result.replaceAll("&amp;#x5c;f", "&amp;#x5c;&amp;#x5c;f");
    		System.out.println(result);
    		result = result 
    				.replaceAll("(?<!&#x5c;)&#x5c;\\\"","&#x5c;&#x5c;\\\"")
    				.replaceAll("&#x5c;r", "&#x5c;&#x5c;r")
					.replaceAll("&#x5c;n", "&#x5c;&#x5c;n")
					.replaceAll("&#x5c;b", "&#x5c;&#x5c;b")
					.replaceAll("&#x5c;t", "&#x5c;&#x5c;t")
					.replaceAll("&#x5c;f", "&#x5c;&#x5c;f");
    		System.out.println(result);
    	}
    	return result;
    }
 
    public String[] getParameterValues(String name) {//同上
         return params.get(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(),"UTF-8"));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais =new ByteArrayInputStream(baos.toByteArray());//NOPMD
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

}
