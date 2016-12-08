package cn.com.inspur.hq.work;

import java.io.File;

import cn.com.inspur.hq.imp.HandleJavaFileUnfindSuccessOpreLog;
import cn.com.inspur.hq.imp.HandleJavaFileUnfindUnneededOerateLogMethod;
import cn.com.inspur.hq.imp.HandleJavaFileUnfundOpreLog;
import cn.com.inspur.hq.itf.HandleJavaFile;

public class StartCheckOperateLog {
	public static void main(String[] args) {
		String path = "H:/V2R1Source/";
		//String path = "E:/工作/项目/03 ACROSSPM/V2R1SourceCode02-Trunk/AcrossPM-Web/src/com/inspur/pmv5/platform/action/";
		//String path = "E:/工作/项目/03 ACROSSPM/V2R1SourceCode02-Trunk/SelfManagement/Self-Mgt/src/com/inspur/self/action/";
		
		HandleJavaFile handler = new HandleJavaFileUnfundOpreLog();
		//HandleJavaFile handler = new HandleJavaFileUnfindUnneededOerateLogMethod();
		handler.startHandleJavaFile(new File(path));
	}
}
