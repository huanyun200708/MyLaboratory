package cn.com.inspur.hq.model;

import java.util.ArrayList;
import java.util.List;

public class JavaFile {
	/**所有方法的集合 */
	private List<JavaMethod> javaMethodList = new ArrayList<JavaMethod>(10);
	/**类名 */
	private String className;

	public List<JavaMethod> getJavaMethodList() {
		return javaMethodList;
	}

	public void setJavaMethodList(List<JavaMethod> javaMethodList) {
		this.javaMethodList = javaMethodList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	

	@Override
	public String toString() {
		return "JavaFile [javaMethodList=" + javaMethodList + ", className="
				+ className + "]";
	}
	


}
