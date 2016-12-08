package cn.com.inspur.hq.work;

import java.io.File;

import cn.com.inspur.hq.imp.HandleJavaFormateFileAndFindAllLogCodeImp;
import cn.com.inspur.hq.imp.HandleJavaFormateFileAndFindAllOpreLogCodeImp;
import cn.com.inspur.hq.itf.HandleJavaFormateFile;

public class StartGetAllLogCode {
	public static void main(String[] args) {
		String path = "H:/V2R1Source";
		HandleJavaFormateFile handler = new HandleJavaFormateFileAndFindAllLogCodeImp();
		handler.startHandleJavaFormateFile(new File(path));
	}
	
	
}
