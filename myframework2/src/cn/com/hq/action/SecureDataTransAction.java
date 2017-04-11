package cn.com.hq.action;

public class SecureDataTransAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String encryptStr = "";
	
	public String getEncryptStr() {
		return encryptStr;
	}
	public void setEncryptStr(String encryptStr) {
		this.encryptStr = encryptStr;
	}
	@Override
	public String execute() throws Exception {
		return "success";
	}
	
	public String decrypt() {
		responseWriter("你好，欢迎来到开源中国在线工具，这是一个AES加密测试");
		return null;
	}
	
}
