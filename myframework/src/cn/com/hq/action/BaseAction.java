package cn.com.hq.action;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.com.hq.service.XssService;
import cn.com.hq.serviceimpl.XssServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	public XssService xssService = new XssServiceImpl();
	public void responseWriter(String jsonPtString) {
		 PrintWriter out = null;
	        try
	        {
	            out = getResponse().getWriter();
	            if (jsonPtString != null)
	            {
	            	out.write(xssService.jsonForJavaScript(jsonPtString));
	            }
	            out.flush();
	        }
	        catch (Exception e)
	        {
	            StringWriter trace = new StringWriter();
	        }
	        finally
	        {
	        	try {
	        		if (out != null)
	                {
	                    out.close();
	                }
				} catch (Exception e2) {
					StringWriter trace = new StringWriter();
				}
	        }

	}
	
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
}
