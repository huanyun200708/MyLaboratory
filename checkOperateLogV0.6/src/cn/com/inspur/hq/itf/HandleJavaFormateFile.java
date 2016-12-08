package cn.com.inspur.hq.itf;

import java.io.File;

public interface HandleJavaFormateFile {
	void startHandleJavaFormateFile(File fileDir);
	boolean isNeededLine(String line);
}

