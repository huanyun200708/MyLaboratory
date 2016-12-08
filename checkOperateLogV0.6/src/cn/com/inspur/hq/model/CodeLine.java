package cn.com.inspur.hq.model;

public class CodeLine {
	private String codeValue = "";
	private boolean isCodeLineEnd = false;

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public boolean isCodeLineEnd() {
		return isCodeLineEnd;
	}

	public void setCodeLineEnd(boolean isCodeLineEnd) {
		this.isCodeLineEnd = isCodeLineEnd;
	}

	@Override
	public String toString() {
		return codeValue;
	}
	
	

}
