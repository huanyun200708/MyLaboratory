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

public class HandleJavaFileUnfindSuccessOpreLog implements HandleJavaFile {

	@Override
	/**
	 * 检查方法中的代码是不是记录成功审计日志的情况
	 * 
	 * */
	public List<String> checkJavaFile(JavaFile javaFile) {
		List<String> errorStrings = new ArrayList<String>(); 
		for(JavaMethod javaMethod : javaFile.getJavaMethodList()){
			checkJavaMethod(javaMethod);
			if(!javaMethod.isRecordSuccessOpreateLog()){
				errorStrings.add(javaMethod.getWholeMethodName() + " unfound success operateLog!");
			}
		}
		return errorStrings;
	
	}
	/**
	 * 检查方法中的代码是不是记录成功审计日志的情况
	 * 
	 * */
	public void checkJavaMethod(JavaMethod javaMethod) {
if("public void exportBusinessGroup()".equals(javaMethod.getWholeMethodName())){
	System.out.print("");
}		
		if(javaMethod.isIgnoreCheck()){
			javaMethod.setRecordSuccessOpreateLog(true);
			return;
		}
		
		if(checkJavaCodeBlock(javaMethod.getJavaCodeList())){
			javaMethod.setRecordSuccessOpreateLog(true);
			return;
		}
		
		for(JavaCodeBlock javaCodeBlock : javaMethod.getJavaCodeBlockList()){
			checkJavaCodeBlock(javaCodeBlock);
			if(javaCodeBlock.isRecordSuccessOpreateLog()){
				javaMethod.setRecordSuccessOpreateLog(true);
			}
		}
	}

	public void checkJavaCodeBlock(JavaCodeBlock javaCodeBlock) {
		if(checkJavaCodeBlock(javaCodeBlock.getJavaCodeList())){
			javaCodeBlock.setRecordSuccessOpreateLog(true);
			return;
		}
		for(JavaCodeBlock subJavaCodeBlock : javaCodeBlock.getJavaCodeBlockList()){
			checkJavaCodeBlock(subJavaCodeBlock);
			if(subJavaCodeBlock.isRecordSuccessOpreateLog()){
				javaCodeBlock.setRecordSuccessOpreateLog(true);
				return;
			}
		}
	}
	
	public boolean checkJavaCodeBlock(List<CodeLine> lines){
		boolean result = false;
		for(CodeLine line: lines){
			//如果发现审计日志记录语句
			if(line.getCodeValue().matches(".*BaseAction\\.SUCCESS.*")
					||line.getCodeValue().matches(".*\\.addOperationLog\\(.*\\\"success\\\".*")
					||line.getCodeValue().matches(".*OperationLogResult\\.SUCCESS.*")
					
					){
				result = true;
			}
		}
		return result;
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
