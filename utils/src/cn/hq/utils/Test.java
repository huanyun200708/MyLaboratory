package cn.hq.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) {
		testTimeFormat();

		/*String dir = "H:/V2R1Source/";
		List<String> files =  FileUtil.getAllChildFilesWhithoutSuffix(dir,
				"class;MF;classpath;project;prefs;component;jar;png;svn-base;css;swf;"
				+ "gif;html;jsdtscope;db;entries;format;jpg;cc;asc;md5;sha1;rb;"
				+ "MF;lng;hhc;ico;so;");
		for(int i=0;i<files.size();i++){
			files.set(i, files.get(i).replaceAll("H:\\\\V2R1Source", "https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发"));
		}	
		FileUtil.writeFile(files,"d:/files.txt");*/
	}
	
	private static String selectHigeValueLine(String line, String reg) {
		if(line == null || "".equals(line.trim())){
			return "";
		}else{
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(line);
			boolean isfind = false;
			while(m.find())
			{
				 isfind = true;
				line = m.group(m.groupCount());
			}
			if(!isfind){
				line = "";
				 isfind = false;
			}
			
		}
		return line;
	}
	
	private static void testTimeFormat(){
		Date date = new Date(1485047152261L);
		date = new Date();
		System.out.println(date.getTime());
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		System.out.println(f.format(date));
	}
	
}
