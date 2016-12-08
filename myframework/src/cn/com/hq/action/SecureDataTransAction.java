package cn.com.hq.action;

import com.inspur.obd.common.key.CodeUtil;

public class SecureDataTransAction extends BaseAction{
	
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
		System.out.println(CodeUtil.aes128Encrypt("你好，欢迎来到开源中国在线工具，这是一个AES加密测试","1234"));
		String s = CodeUtil.aes128Encrypt("你好，欢迎来到开源中国在线工具，这是一个AES加密测试", "1234");
		System.out.println(CodeUtil.aes128Decrypt(s, "1234"));
		responseWriter(CodeUtil.aes128Decrypt(encryptStr, "1234"));
		return null;
	}
	
}
