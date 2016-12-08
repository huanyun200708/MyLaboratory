package cn.com.inspur.hq.itf;

import java.io.File;
import java.util.List;
import cn.com.inspur.hq.model.JavaFile;

public interface HandleJavaFile {
	List<String> checkJavaFile(JavaFile javaFile);
	void startHandleJavaFile(File fileDir);
}
