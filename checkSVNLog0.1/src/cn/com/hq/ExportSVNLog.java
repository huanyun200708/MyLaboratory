package cn.com.hq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.hq.model.OperateFile;
import cn.com.hq.model.Record;

public class ExportSVNLog {
	public static final boolean ISCHECKDELETE = true;
	public static final boolean ISCHECKMODIFY = false;
	public static final boolean ISCHECKADD = true;
	/**
	 * @param svnpath : https://10.110.2.10/obd/ACROSSV2R1C00/03-产品研发/02-Trunk/AcrossPM-installpackage
	 * @param befordayNum : 当前天之前多少天
	 * */
	public static void CheckShellScript(String svnpath,int befordayNum) {
		Runtime rt = Runtime.getRuntime();
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = new Date();
			String today = format2.format(date);
			Calendar   calendar   =   new   GregorianCalendar(); 
		     calendar.setTime(date); 
		     calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - befordayNum);
		     date=calendar.getTime();   //这个时间就是日期往前减一天的结果
		     String yesterday =  format2.format(date);
		     Process p = rt.exec("svn log  -r {"+yesterday+"}:{"+today+"}  -v " + svnpath);
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			printResult(reader,yesterday);
			p.waitFor();
			is.close();
			reader.close();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printResult(BufferedReader reader, String yesterday) throws IOException, ParseException{
		boolean isCheckOk = true;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String line;
		Record tempRecord = new Record();
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if("".equals(line)){
				continue;
			}
//System.out.println(line);
			if("------------------------------------------------------------------------".equals(line)
					&& tempRecord.getAuthorName() == null){
				tempRecord.setIsfinish(false);
			}
			
			if(!tempRecord.isIsfinish()){
				String authorName = isNeededLine(line, "\\| ([^\\d{4}\\-\\d{2}\\-\\d{2}]*) \\|");
				String changeTimeStr = isNeededLine(line, "\\| (\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}\\:\\d{2}\\:\\d{2}).* \\|");
				if(authorName != null && !"".equals(authorName)){
					tempRecord.setAuthorName(authorName);
				}
				if(changeTimeStr != null && !"".equals(changeTimeStr)){
					Date changeTime = format1.parse(changeTimeStr);
					tempRecord.setChangeTime(changeTime);
				}
				String operateFileStr =  isNeededLine(line, "^[AMD] .*");
				if(operateFileStr != null && !"".equals(operateFileStr)){
					OperateFile operateFile = new OperateFile();
					operateFile.setOprateType(line.substring(0, 1));
					operateFile.setFilePath(line.substring(2));
					tempRecord.getOperateFiles().add(operateFile);
					if(ISCHECKADD && "A".equals(operateFile.getOprateType())){
						System.out.println("!!!! WARNING ADD: ");
						System.out.println("               " + tempRecord.getAuthorName() + " ADD a shell script at  "
								+ format1.format(tempRecord.getChangeTime()));
						System.out.println("               scriptPath: " + operateFile.getFilePath());
						isCheckOk = false;
					}
					if(ISCHECKMODIFY && "M".equals(operateFile.getOprateType())){
						System.out.println("!!!! WARNING MODIFY: ");
						System.out.println("               " + tempRecord.getAuthorName() + " MODIFY a shell script at  "
								+ format1.format(tempRecord.getChangeTime()));
						System.out.println("               scriptPath: " + operateFile.getFilePath());
						isCheckOk = false;
					}
					if(ISCHECKDELETE && "D".equals(operateFile.getOprateType())){
						System.out.println("!!!! WARNING DELETE: ");
						System.out.println("               " + tempRecord.getAuthorName() + " DELETE a shell script at  "
								+ format1.format(tempRecord.getChangeTime()));
						System.out.println("               scriptPath: " + operateFile.getFilePath());
						isCheckOk = false;
					}
				}
			}
			
			if("------------------------------------------------------------------------".equals(line)
					&& tempRecord.getAuthorName() != null){
			//	tempRecord.setIsfinish(true);
			}
		}
		if(isCheckOk){
			System.out.println("yesterday " +yesterday + " shell script add check OK.  ");
		}
	}
	
	private Record createOneRecord(String line){
		return null;
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
}
