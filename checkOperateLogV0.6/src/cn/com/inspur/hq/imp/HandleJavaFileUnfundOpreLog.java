package cn.com.inspur.hq.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.inspur.hq.CheckOperateLog;
import cn.com.inspur.hq.itf.HandleJavaFile;
import cn.com.inspur.hq.model.CodeLine;
import cn.com.inspur.hq.model.JavaCodeBlock;
import cn.com.inspur.hq.model.JavaFile;
import cn.com.inspur.hq.model.JavaMethod;

public class HandleJavaFileUnfundOpreLog implements HandleJavaFile {

	@Override
	/**
	 * 检查方法中的代码是不是记录审计日志的情况
	 * 
	 * */
	public List<String> checkJavaFile(JavaFile javaFile) {
		List<String> errorStrings = new ArrayList<String>(); 
		for(JavaMethod javaMethod : javaFile.getJavaMethodList()){
			if(!javaMethod.isIgnoreCheck()){
				errorStrings.addAll(checkJavaMethod(javaMethod));
			}
		}
		return errorStrings;
	
	}
	/**
	 * 检查方法中的代码是不是存在没有记录审计日志的情况
	 * 
	 * */
	public List<String> checkJavaMethod(JavaMethod javaMethod) {


		List<String> errorStrings = new ArrayList<String>(); 
		if(javaMethod.isIgnoreCheck()){
			return errorStrings; 
		}
		boolean isHaveOperateLogLine = false;
		for(CodeLine line: javaMethod.getJavaCodeList()){
			//如果发现审计日志记录语句
			if(line.getCodeValue().matches(".*\\.addOperationLog\\(.*")){
				isHaveOperateLogLine = true;
			}
		}
		
		for(JavaCodeBlock javaCodeBlock : javaMethod.getJavaCodeBlockList()){
			
			for(String s : checkJavaCodeBlock(javaCodeBlock)){
				errorStrings.add(javaMethod.getWholeMethodName()+ "	" + s);
			}
		}
//		if(javaMethod.getWholeMethodName().contains("public void importExclusionData()")){
//			System.out.println();
//		}
		if(!isHaveOperateLogLine){
			boolean idNotError = false;
			for(String s : errorStrings){
				if(s.contains("METHOD HAD SUCCESS OPERLOG")){
					idNotError = true;
					errorStrings.remove(s);
					break;
				}
			}
			if(!idNotError){
				errorStrings.add(javaMethod.getWholeMethodName() + "	need record operation log.");
			}
			
		}
		
		
		return errorStrings;
	
	}

	public List<String> checkJavaCodeBlock(JavaCodeBlock javaCodeBlock) {
		List<String> errorStrings = new ArrayList<String>(); 
		
		if(javaCodeBlock.isIgnoreCheck()){
			return errorStrings; 
		}
		
		boolean isHaveOperateLogLine = false;
		boolean isNeedOperateLogLine = false;
		boolean isHaveSuccessOperateLogLine = false;
		for(CodeLine line: javaCodeBlock.getJavaCodeList()){
			//如果发现审计日志记录语句recordOperationLog
			if(line.getCodeValue().matches(".*\\.addOperationLog\\(.*")
					||line.getCodeValue().matches(".*\\recordOperationLog\\(.*")){
				isHaveOperateLogLine = true;
			}
			if(line.getCodeValue().matches(".*BaseAction\\.SUCCESS.*")
					||line.getCodeValue().matches(".*\\.addOperationLog\\(.*\\\"success\\\".*")
					||line.getCodeValue().matches(".*OperationLogResult\\.SUCCESS.*")
					
					){
				isHaveSuccessOperateLogLine = true;
			}
			/**
			 * 以下情况需要记录审计日志
			 * 1.retrun语句
			 * 2.抛出参数异常
			 * 3.session失效语句
			 * */
			if(line.getCodeValue().matches("^\\s*return\\s*[^\\w]*;\\s*$")
//					||line.getCodeValue().matches("^\\s*throw\\s*new\\s*ParamValidateException\\(\\s*\\)\\s*;\\s*$")
//					||line.getCodeValue().matches("^\\s*request\\.getSession\\(\\s*\\)\\s*\\.invalidate\\(\\s*\\)\\s*;\\s*;\\s*$")
					){
				isNeedOperateLogLine = true;
			}
		}
		//捕获异常时需要记录审计日志
		if(javaCodeBlock.getWholeCodeBlockName().matches("^\\s*catch\\s*\\(.*Exception.*\\).*$")){
			isNeedOperateLogLine = true;
		}
		
		if(isNeedOperateLogLine && !isHaveOperateLogLine){
			errorStrings.add(javaCodeBlock.getWholeCodeBlockName() + "	need record operation log.");
		}
		
		for(JavaCodeBlock subJavaCodeBlock : javaCodeBlock.getJavaCodeBlockList()){
			errorStrings.addAll(checkJavaCodeBlock(subJavaCodeBlock));
		}
		if(isHaveSuccessOperateLogLine){
			errorStrings.add("METHOD HAD SUCCESS OPERLOG");
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
