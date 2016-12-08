package cn.com.inspur.hq.model;

import java.util.ArrayList;
import java.util.List;

public class JavaCodeBlock {
	/**方法中所有代码块的集合*/
	List<JavaCodeBlock> javaCodeBlockList = new ArrayList<JavaCodeBlock>();
	/**方法中所有代码的集合*/
	List<CodeLine> javaCodeList = new ArrayList<CodeLine>();
	/**代码块名称*/
	private String wholeCodeBlockName;
	/**代码块是否初始化完成 */
	private boolean isInitFinished = false;
	/**该代码块是否忽略审计日志检查 */
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

	public String getWholeCodeBlockName() {
		return wholeCodeBlockName;
	}

	public void setJavaCodeBlockList(List<JavaCodeBlock> javaCodeBlockList) {
		this.javaCodeBlockList = javaCodeBlockList;
	}

	public void setWholeCodeBlockName(String wholeCodeBlockName) {
		this.wholeCodeBlockName = wholeCodeBlockName;
	}

	public List<CodeLine> getJavaCodeList() {
		return javaCodeList;
	}

	public void setJavaCodeList(List<CodeLine> javaCodeList) {
		this.javaCodeList = javaCodeList;
	}

	public boolean isInitFinished() {
		return isInitFinished;
	}

	public void setInitFinished(boolean isInitFinished) {
		this.isInitFinished = isInitFinished;
	}
	
	public boolean isRecordSuccessOpreateLog() {
		return isRecordSuccessOpreateLog;
	}

	public void setRecordSuccessOpreateLog(boolean isRecordSuccessOpreateLog) {
		this.isRecordSuccessOpreateLog = isRecordSuccessOpreateLog;
	}

	@Override
	public String toString() {
		return wholeCodeBlockName;
	}

	
}
