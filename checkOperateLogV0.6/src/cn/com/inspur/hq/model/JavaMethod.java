package cn.com.inspur.hq.model;

import java.util.ArrayList;
import java.util.List;

public class JavaMethod {
	/**方法中所有代码块的集合*/
	List<JavaCodeBlock> javaCodeBlockList = new ArrayList<JavaCodeBlock>();
	/**方法中所有代码的集合*/
	List<CodeLine> javaCodeList = new ArrayList<CodeLine>();
	/**方法名称*/
	private String wholeMethodName;
	/**方法初始化是否完成 */
	private boolean isInitFinished = false;
	/**方法名称是否完成 */
	private boolean isInitNameFinished = false;
	/**该方法是否忽略审计日志检查 */
	private boolean isIgnoreCheck = false;
	/**该方法是否记录成功日志 */
	private boolean isRecordSuccessOpreateLog = false;
	public boolean isIgnoreCheck() {
		return isIgnoreCheck;
	}

	public void setIgnoreCheck(boolean isIgnoreCheck) {
		this.isIgnoreCheck = isIgnoreCheck;
	}

	public List<JavaCodeBlock> getJavaCodeBlockList() {
		return javaCodeBlockList;
	}

	public List<CodeLine> getJavaCodeList() {
		return javaCodeList;
	}

	public String getWholeMethodName() {
		return wholeMethodName;
	}

	public void setJavaCodeBlockList(List<JavaCodeBlock> javaCodeBlockList) {
		this.javaCodeBlockList = javaCodeBlockList;
	}

	public void setJavaCodeList(List<CodeLine> javaCodeList) {
		this.javaCodeList = javaCodeList;
	}

	public void setWholeMethodName(String wholeMethodName) {
		this.wholeMethodName = wholeMethodName;
	}

	public boolean isInitFinished() {
		return isInitFinished;
	}

	public void setInitFinished(boolean isInitFinished) {
		this.isInitFinished = isInitFinished;
	}

	public boolean isInitNameFinished() {
		return isInitNameFinished;
	}

	public void setInitNameFinished(boolean isInitNameFinished) {
		this.isInitNameFinished = isInitNameFinished;
	}
	
	

	public boolean isRecordSuccessOpreateLog() {
		return isRecordSuccessOpreateLog;
	}

	public void setRecordSuccessOpreateLog(boolean isRecordSuccessOpreateLog) {
		this.isRecordSuccessOpreateLog = isRecordSuccessOpreateLog;
	}

	@Override
	public String toString() {
		return this.wholeMethodName;
	}


	
}
