package cn.com.hq.start;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import cn.com.hq.util.FileUtils;
import cn.com.hq.work.ExportSVNLog;

public class Start {
	private static Logger logger = Logger.getLogger(Start.class); 
	public static void main(String[] args) {
		String path = Start.class.getResource("/").getPath();
		String standerFilePath = path.substring(1)+"standerFile.txt";
		String svnpath = "https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发/02-Trunk/";
		BufferedWriter writer = FileUtils.getFileWriter(standerFilePath);
		logger.info("start getAllSVNFileandversion......");
		List<String> rmap = new ExportSVNLog().getSVNcmdResult("svn log -r {2016-07-20}:{2016-07-21} " + svnpath);
		for(String s : rmap){
			try {
				writer.write(s + "\r\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		logger.info("end getAllSVNFileandversion.");
		FileUtils.closeWriter(writer);
	}
}
