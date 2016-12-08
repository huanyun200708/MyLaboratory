package cn.com.inspur.hq.imp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import cn.com.inspur.hq.CheckOperateLog;
import cn.com.inspur.hq.itf.HandleJavaFormateFile;

public class HandleJavaFormateFileAndFindAllLogCodeImp implements HandleJavaFormateFile {

	@Override
	public void startHandleJavaFormateFile(File fileDir) {
		String path = CheckOperateLog.class.getResource("/").getPath();
		Path FormateJavaFileWriteFilePath = FileSystems.getDefault().getPath(path.substring(1)+"exportAllLog.txt");
		CheckOperateLog.whenFileNotExistCreate(FormateJavaFileWriteFilePath);
		BufferedWriter FormateJavaFileWrite = null;
		try {
			FormateJavaFileWrite = Files.newBufferedWriter(FormateJavaFileWriteFilePath, Charset.forName("utf-8"), StandardOpenOption.TRUNCATE_EXISTING);
			if (fileDir.isDirectory()){
				List<String> strList =HandleJavaFormateFileDir(fileDir);;
        		for(int ii=0;ii<strList.size();ii++){
        			FormateJavaFileWrite.write(strList.get(ii) + "\r\n");
        		}
			}else{
				List<String> strList = HandleJavaFormateFile(fileDir);
        		for(int ii=0;ii<strList.size();ii++){
        			FormateJavaFileWrite.write(strList.get(ii) + "\r\n");
        		}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		CheckOperateLog.closeWriter(FormateJavaFileWrite);
	}
	
	private List<String> HandleJavaFormateFileDir(File fileDir){
		 List<String> strList = new ArrayList<String>();
		 String[] children = fileDir.list();
         for (int i=0; i<children.length; i++) {
         	File dir = new File(fileDir.toString() + "/" + children[i]);
         	if (dir.isDirectory()){
         		strList.addAll(HandleJavaFormateFileDir(dir)) ;
         	}else if(dir.toString().matches(".*\\.java\\s*")){
         		strList.addAll(HandleJavaFormateFile(dir)) ;
         	}
         }
		return strList;
	} 
	
	private List<String> HandleJavaFormateFile(File file){
		 List<String> strList = new ArrayList<String>();
		 BufferedReader javaSourceCodeFileReader = null;
		 Charset charset = Charset.forName("utf-8");
		 String line = "";
		 try {
			Path javaSourceCodeFilePath = new CheckOperateLog().exportFormateJavaFile(file.getCanonicalPath());
			javaSourceCodeFileReader = Files.newBufferedReader(javaSourceCodeFilePath, charset);
			while ((line = javaSourceCodeFileReader.readLine()) != null) {
				if(line != null || !"".equals(line)){
					if(isNeededLine(line)){
						strList.add(file.getName() + "	" +line);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		 CheckOperateLog.closeReader(javaSourceCodeFileReader);
		 return strList;
	}

	@Override
	public boolean isNeededLine(String line) {
		if(line.matches(".*\\.error\\(.*")
				||line.matches(".*\\.info\\(.*")
				||line.matches(".*\\.debug\\(.*")
				){
			return true;
		}
		return false;
	}

}
