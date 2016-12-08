package cn.com.hq.work;

import java.util.ArrayList;
import java.util.List;

import cn.hq.utils.FileUtil;


public class TestMain {

	public static void main(String[] args) {
		//String svnstr1 = "svn log  -r {2016-08-06}:{2016-08-08}  -v https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发/02-Trunk/";
		String svnstr2 = "svn diff -r 1019:12356  https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发/02-Trunk/AcrossPM-Encrypt/src/com/inspur/obd/common/key/Normaliser.java";
		String dir = "H:/V2R1Source/AcrossPM-EAM";
		List<String> reulstList = new ArrayList<String>();
		List<String> files =  FileUtil.getAllChildFilesWhithoutSuffix(dir,
				"class;MF;classpath;project;prefs;component;jar;png;svn-base;css;swf;"
				+ "gif;html;jsdtscope;db;entries;format;jpg;cc;asc;md5;sha1;rb;"
				+ "MF;lng;hhc;ico;so;");
		for(int i=0;i<files.size();i++){
			String svnstr1 = "svn log  -r {2016-08-06}:{2016-08-09}  -v " + files.get(i).replaceAll("H:\\\\V2R1Source", "https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发");
			List<String> results = ExportSVNLog.getSVNcmdResult(svnstr1);
			reulstList.add(files.get(i).replaceAll("H:\\\\V2R1Source", "https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发"));
			reulstList.addAll(results);
		}	
		FileUtil.writeFile(reulstList,"d:/files.txt");
	}

}
