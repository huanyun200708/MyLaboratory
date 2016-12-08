package cn.com.inspur.hq.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.inspur.hq.CheckOperateLog;
import cn.com.inspur.hq.itf.HandleJavaFile;
import cn.com.inspur.hq.model.JavaFile;
import cn.com.inspur.hq.model.JavaMethod;

public class HandleJavaFileUnfindUnneededOerateLogMethod implements HandleJavaFile {

	@Override
	/**
	 * 检查方法中的代码是不是记录成功审计日志的情况
	 * 
	 * */
	public List<String> checkJavaFile(JavaFile javaFile) {
		List<String> errorStrings = new ArrayList<String>(); 
		for(JavaMethod javaMethod : javaFile.getJavaMethodList()){
			if(javaMethod.isIgnoreCheck()){
				errorStrings.add(javaMethod.getWholeMethodName() + " unneeded  operateLog!");
			}
		}
		return errorStrings;
	
	}
	@Override
	public void startHandleJavaFile(File fileDir) {
		if (fileDir.isDirectory()) {
			new CheckOperateLog().checkOperateLogByDir(fileDir,this);
        }else if(fileDir.toString().matches(".*Action\\.java\\s*")){
        	try {
        		new CheckOperateLog().startCheckOperateLog(fileDir.getCanonicalPath(),this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

}
