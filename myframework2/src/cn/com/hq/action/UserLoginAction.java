package cn.com.hq.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import cn.com.hq.entity.Account;
import cn.com.hq.service.UserService;
import cn.com.hq.serviceimpl.UserServiceimpl;
import cn.com.hq.serviceimpl.XssServiceImpl;
import cn.com.hq.util.JsonUtils;

public class UserLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceimpl();
	
	public static void main(String[] args) {
		System.out.println(XssServiceImpl.escapeHtmlForString("{\"name\":\"a&amp;#x5c;1\",\"age\":0}"));
	}
	public String execute() throws Exception {
		return "success";
	}
	public UserService getUserService() {
		return userService;
	}

	public void login(){
		HttpServletRequest reguest= super.getRequest();
		String data = reguest.getParameter("data");
		System.out.println(data);
		responseWriter(data + " login success!");
	}

	public void queryData(){
		HttpServletRequest reguest= super.getRequest();
		String requestuser = reguest.getParameter("user");
		String description = reguest.getParameter("description");
		description = XssServiceImpl.escapeHtmlForString(description);
		String handlerequestuser = requestuser;//.replaceAll("&#x5c;","&#x5c;&#x5c;");
		String user = XssServiceImpl.escapeHtmlForString(handlerequestuser);
		Account a1 = new Account();
		try {
			System.out.println(user);
			a1 = JsonUtils.fromJson(user, Account.class);
			a1.setDescription(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String aJsonStr = JsonUtils.toJson(a1);
		//aJsonStr = "{\"name\":\"a1\",\"age\":0}";
		responseWriter(aJsonStr);
	}

	public void queryDataFromDBByName(){
		HttpServletRequest reguest= super.getRequest();
		String name = reguest.getParameter("name");
		List<Account> accountList = userService.queryAccountByName(name);
		if(accountList != null){
			responseWriter( JsonUtils.toJson(accountList.get(0)));
		}else{
			responseWriter("{\"isSuccess\":false,\"message\":\"result is null\"}");
		}		
	}

	public void savaOrUpdateAccount(){
		 HttpServletRequest reguest= super.getRequest();
		String address = XssServiceImpl.escapeHtmlForString(reguest.getParameter("address"));
		String user = XssServiceImpl.escapeHtmlForString(reguest.getParameter("user"));
		//Account a1 = new Account();
		try {
			//a1 = JsonUtils.fromJson(user, Account.class);
			//a1.setAccountid(Math.round(Math.random()*100000)+"");
			//a1.setPassWord(Math.round(Math.random()*100000)+"");
			responseWriter("{\"success\":true,\"message\":\""+address+"\"}");
			//userService.createAccount(a1);
			//userService.savaOrUpdateAccount(a1);
		} catch (Exception e) {
			e.printStackTrace();
			responseWriter("{\"success\":false,\"message\":\"result is null\"}");
		}
		responseWriter("{\"success\":true,\"message\":\"savaAccount success\"}");
	}

}
