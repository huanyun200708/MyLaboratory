package cn.com.hq.work;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.com.hq.start.Start;


public class ExportSVNLog {
	private static Logger logger = Logger.getLogger(ExportSVNLog.class); 
	public Map<String ,String >getAllSVNFileandversion(String path){
		Map<String ,String > resultMap = new HashMap<String ,String>();
		List<String> results = getSVNcmdResult("svn ls " + path);
		for(String file : results){
			if(isFileIgnoreCheck(file)){
				continue;
			}
			if(file.trim().endsWith("/")){
				resultMap.putAll(getAllSVNFileandversionByFileDir(path + file.trim()));
			}else{
				resultMap.putAll(getAllSVNFileandversionByFile(path + file.trim()));
			}
		}
		return resultMap;
		
	}
	
	/**
	 * 锟矫碉拷锟斤拷锟叫碉拷锟斤拷锟斤拷锟斤拷锟叫斤拷锟斤拷锟�
	 * */
	public static List<String> getSVNcmdResult(String cmd){
		List<String> results = new ArrayList<String>(); 
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(cmd);
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if("".equals(line)){
					continue;
				}
				results.add(line);
			}
			p.waitFor();
			is.close();
			reader.close();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public Map<String ,String >getAllSVNFileandversionByFileDir(String path){
		Map<String ,String > resultMap = new HashMap<String ,String>();
		
		List<String> results = getSVNcmdResult("svn ls " + path);
		
		for(String file : results){
			if(isFileIgnoreCheck(file)){
				continue;
			}
			if(file.trim().endsWith("/")){
				resultMap.putAll(getAllSVNFileandversionByFileDir(path + file.trim()));
			}else{
				resultMap.putAll(getAllSVNFileandversionByFile(path + file.trim()));
			}
		}
		
		return resultMap;
	}
	
	public Map<String ,String >getAllSVNFileandversionByFile(String path){
		logger.info("get vesion " + path);
		Map<String ,String > resultMap = new HashMap<String ,String>();
		List<String> results = getSVNcmdResult("svn info " + path);
		
		for(String s : results){
			//Revision: 14946
			String needStr = isNeededLine(s.trim(),"Revision\\:\\s+(\\d+)");
			if(!"".equals(needStr)){
				resultMap.put(path, needStr);
			}
			
		}
		
		return resultMap;
	}
	
	private static String isNeededLine(String line, String reg) {
		if (line == null || "".equals(line.trim())) {
			return "";
		} else {
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(line);
			boolean isfind = false;
			while (m.find()) {
				isfind = true;
				line = m.group(m.groupCount());
			}
			if (!isfind) {
				line = "";
				isfind = false;
			}
		}
		return line;
	}
	
	public boolean isFileIgnoreCheck(String fileName){
		return( fileName.endsWith(".project")
		||fileName.endsWith(".classpath")
		||fileName.endsWith(".settings")
		||fileName.endsWith(".jar")
		||fileName.endsWith(".jar")
		);
	}
}
