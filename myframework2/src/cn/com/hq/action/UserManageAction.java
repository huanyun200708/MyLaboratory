package cn.com.hq.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import cn.com.hq.entity.Account;
import cn.com.hq.service.UserService;
import cn.com.hq.util.JsonUtils;
import com.opensymphony.xwork2.ActionSupport;

public class UserManageAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String passWord;
	private UserService userService;
	private String as;
	public String execute() throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		Account a1 = new Account();
		a1.setName("a1");
		Account a2 = new Account();
		a2.setName("a2");
		accounts.add(a1);
		accounts.add(a2);
		as = JsonUtils.toJson(accounts);
		ServletActionContext.getRequest().setAttribute("myname", "u1");
		//ServletActionContext.getRequest().setAttribute("accounts", JsonUtils.toJson(accounts));
		ServletActionContext.getRequest().setAttribute("a1", JsonUtils.toJson(a1));
		return "success";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public UserService getUserService() {
		return userService;
	}


	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}

}
