package cn.hq.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
	
	public static void onlyCopeFile(String readFileStr,String writeFileStr){
		
		 Path readFilePath = FileSystems.getDefault().getPath(readFileStr);
		 Path writeFilePath = FileSystems.getDefault().getPath(writeFileStr);
		 
		 whenFileNotExistCreate(writeFilePath);

		 Charset charset = Charset.forName("ISO-8859-1");
		 BufferedReader reader = null;
		 BufferedWriter writer = null;
		 try {
			 
			 reader = Files.newBufferedReader(readFilePath, charset);
			 writer = Files.newBufferedWriter(writeFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
	         String line = null;
	         while ((line = reader.readLine()) != null) {
	        	 if(!"".equals(line)){
	        		 writer.write(line +"\r\n");
	        	 }
	         }
	         
	         
	     } catch (IOException e) {
	         System.err.println(e);
	     }finally{
	    	 closeReader(reader);
	    	 closeWriter(writer);
	    }
		 
		 System.out.println("success");
	}
	
	public static void changeFileAllLine(String readFileStr,String writeFileStr,String str){
		
		 Path readFilePath = FileSystems.getDefault().getPath(readFileStr);
		 Path writeFilePath = FileSystems.getDefault().getPath(writeFileStr);
		 
		 whenFileNotExistCreate(writeFilePath);

		 Charset charset = Charset.forName("ISO-8859-1");
		 BufferedReader reader = null;
		 BufferedWriter writer = null;
		 try {
			 
			 reader = Files.newBufferedReader(readFilePath, charset);
			 writer = Files.newBufferedWriter(writeFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
	         String line = null;
	         while ((line = reader.readLine()) != null) {
	        	 if(!"".equals(line)){
	        		 if(line.indexOf(str)>-1){
	        			 writer.write(line +"		AAAA		BBBB" +"\r\n");
	        		 }else{
	        			 writer.write(line +"\r\n");
	        		 }
	        		
	        	 }
	         }
	         
	         
	     } catch (IOException e) {
	         System.err.println(e);
	     }finally{
	    	 closeReader(reader);
	    	 closeWriter(writer);
	    }
		 
		 System.out.println("success");
	}
	/**
	 * 从一个文件查询出满足正则要求的字符的出现次数
	 * 
	 * */
	public static void countStrformFlieTimes(String fileStr,String resultFileStr,String regex){
		
		 Path readFilePath = FileSystems.getDefault().getPath(fileStr);
		 Path writeFilePath = FileSystems.getDefault().getPath(resultFileStr);
		 
		 whenFileNotExistCreate(writeFilePath);

		 Charset charset = Charset.forName("ISO-8859-1");
		 BufferedReader reader = null;
		 BufferedWriter writer = null;
		 try {
			 
			 reader = Files.newBufferedReader(readFilePath, charset);
			 writer = Files.newBufferedWriter(writeFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
	         String line = null;
	         int lineNo = 0;
	         while ((line = reader.readLine()) != null) {
	        	 lineNo ++ ;
	        	 if(!"".equals(line)){
	        		 if(line == null || "".equals(line.trim())){
	        				continue;
	        			}else{
	        				Pattern p = Pattern.compile(regex);
	        				Matcher m = p.matcher(line);
	        				int i = 0;
	        				while(m.find())
	        				{
	        					i ++ ;
	        					writer.write("line" + lineNo + ":" + i  + "\r\n");
	        				}
	        			}
	        	 }
	         }
	         
	         
	     } catch (IOException e) {
	         System.err.println(e);
	     }finally{
	    	 closeReader(reader);
	    	 closeWriter(writer);
	    }
		 
		 System.out.println("success");
	}
	/**
	 * 从一个文件挑取满足正则要求的行，输出到输出文件
	 * 
	 * */
	public static void copeFileThroughRegex(String readFilePathStr,String writeFilePathStr,String reg){
		
		 Path readFilePath = FileSystems.getDefault().getPath(readFilePathStr);
		 Path writeFilePath = FileSystems.getDefault().getPath(writeFilePathStr);
		 
		 whenFileNotExistCreate(writeFilePath);
		 
		 Charset charset = Charset.forName("ISO-8859-1");
		 BufferedReader reader = null;
		 BufferedWriter writer = null;
		 try {
			 reader = Files.newBufferedReader(readFilePath, charset);
			 writer = Files.newBufferedWriter(writeFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
	         String line = null;
	         while ((line = reader.readLine()) != null) {
	        	 line = selectHigeValueLine(line,reg);
	        	 if(!"".equals(line)){
	        		 writer.write(line +"\r\n");
	        	 }
	         }
	         
	     } catch (IOException e) {
	         e.printStackTrace();
	     }finally{
	    	 closeReader(reader);
	    	 closeWriter(writer);
	    }
		 
		 System.out.println("success");
	}
	

	public static void whenFileNotExistCreate(Path filePath){
		 
		 if(Files.notExists(filePath,new LinkOption[] { LinkOption.NOFOLLOW_LINKS })){
			 try {
				Files.createFile(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}
	
	public static void closeReader(BufferedReader reader){
		 try {
    		 if(reader != null){
    			 reader.close();
    		 }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void closeWriter(BufferedWriter writer){
		 try {
    		 if(writer != null){
    			writer.flush();
 				writer.close();
    		 }
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从文件1中找出不在文件2中的行，输出到结果文件
	 * 
	 * */
	public static void exportDifferentFormFiles(String file1,String file2,String exporfile){

		 Path readFilePath1 = FileSystems.getDefault().getPath(file1);
		 Path readFilePath2 = FileSystems.getDefault().getPath(file2);
		 Path exporfilePath = FileSystems.getDefault().getPath(exporfile);
		 
		 whenFileNotExistCreate(exporfilePath);

		 Charset charset = Charset.forName("ISO-8859-1");
		 BufferedReader reader1 = null;
		 
		 BufferedWriter writer = null;
		 try {
			
			 reader1 = Files.newBufferedReader(readFilePath1, charset);
			
			 writer = Files.newBufferedWriter(exporfilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
	         String line1 = null;
	         while ((line1 = reader1.readLine()) != null) {
	        	 if(!"".equals(line1)){
	        		 String line2 = null;
	        		 boolean isSameStr = false;
	        		 BufferedReader reader2 = Files.newBufferedReader(readFilePath2, charset);
	        		 while ((line2 = reader2.readLine()) != null) {
	    	        	 if(!"".equals(line2)){
	    	        		 if(line1.equals(line2)){
	    	        			 isSameStr = true;
	    	        			 break;
	    	        		 }
	    	        	 }
	    	         }
	        		 
	        		 if(!isSameStr){
	        			 writer.write(line1 +"\r\n");
	        		 }
	        		 closeReader(reader2);
	        	 }
	         }
	         
	         
	     } catch (IOException e) {
	         System.err.println(e);
	     }finally{
	    	 closeReader(reader1);
	    	 closeWriter(writer);
	    }
		 
		 System.out.println("success");
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

	
	
	/**
	 * 
	 * 
	 * 
	 * */
	public static Map<String, ArrayList<String>> getRequestUrlAndParameters(String line){
		String regex = "/.*\\.do\\?";
		Map<String, ArrayList<String>> urlMap = new HashMap<String, ArrayList<String>>(10);
		if(line == null || "".equals(line.trim())){
			return null;
		}else{
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(line);
			while(m.find())
			{
				String[] parameters = line.replaceAll(regex, "").split("&");
				List<String> parameterList =  Arrays.asList(parameters);
				ArrayList<String> parameterArrayList =  new ArrayList<String>();
				for(String s : parameterList){
					parameterArrayList.add(s);
				}
				urlMap.put(m.group(m.groupCount()), parameterArrayList);
				return urlMap;
			}
		}
		return null;
	}
	
	public static ArrayList<String> removeDumpElement(ArrayList<String> list){
		ArrayList<String> newList = new ArrayList<String>();
		Map<String, String> urlMap = new HashMap<String, String>(10);
		
		for(String s : list){
			urlMap.put(s, "");
		}
		for(String s : urlMap.keySet()){
			newList.add(s);
		}
		return newList;
	}
	
	public static ArrayList<String> getAllChildFilesWhithoutSuffix(String dir, String filterSuffix){
		ArrayList<String> newList = new ArrayList<String>();
		try {
			File file = new File(dir);
	        file = file.getAbsoluteFile();
			if (file.exists() && file.isDirectory())
	        {
	            String[] filelist = file.list();
	            for (int i = 0; i < filelist.length; i++)
	            {
	                File delfile = new File(dir + File.separator
	                        + filelist[i]);
	                if (!delfile.isDirectory() )
	                {
	                	if(filterSuffix== null || "".equals(filterSuffix)
	    	            		){
	    	        		newList.add(delfile.getCanonicalPath());
	    	        	}else {
	    	        		String fileSuffix = delfile.getName().substring(delfile.getName().lastIndexOf('.')+1);
	    	        		String [] suffixs = filterSuffix.split(";");
	    	        		List<String> suffixList = Arrays.asList(suffixs);
	    	        		if(!suffixList.contains(fileSuffix.toLowerCase())){
	    	        			newList.add(delfile.getCanonicalPath());
	    	        		}
	    	        	}
	                }
	                else if (delfile.isDirectory())
	                {
	                	newList.addAll(getAllChildFilesWhithoutSuffix(dir + File.separator + filelist[i], filterSuffix));
	                }
	            }
	        }else if(file.exists() && !file.isDirectory() ){
	        	if(filterSuffix== null || "".equals(filterSuffix)
	            		){
	        		newList.add(file.getCanonicalPath());
	        	}else  {
	        		String fileSuffix = file.getName().substring(file.getName().lastIndexOf('.')+1);
	        		String [] suffixs = filterSuffix.split(";");
	        		List<String> suffixList = Arrays.asList(suffixs);
	        		if(!suffixList.contains(fileSuffix.toLowerCase())){
	        			newList.add(file.getCanonicalPath());
	        		}
	        	}
	        	
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return newList;
		}
		
		return newList;
	}
	
	public static ArrayList<String> getAllChildFilesWhithSuffix(String dir, String filterSuffix){
		ArrayList<String> newList = new ArrayList<String>();
		try {
			File file = new File(dir);
	        file = file.getAbsoluteFile();
			if (file.exists() && file.isDirectory())
	        {
	            String[] filelist = file.list();
	            for (int i = 0; i < filelist.length; i++)
	            {
	                File delfile = new File(dir + File.separator
	                        + filelist[i]);
	                if (!delfile.isDirectory() )
	                {
	                	if(filterSuffix== null || "".equals(filterSuffix)
	    	            		){
	    	        		newList.add(delfile.getCanonicalPath());
	    	        	}else {
	    	        		String fileSuffix = delfile.getName().substring(delfile.getName().lastIndexOf('.')+1);
	    	        		String [] suffixs = filterSuffix.split(";");
	    	        		List<String> suffixList = Arrays.asList(suffixs);
	    	        		if(suffixList.contains(fileSuffix.toLowerCase())){
	    	        			newList.add(delfile.getCanonicalPath());
	    	        		}
	    	        	}
	                }
	                else if (delfile.isDirectory())
	                {
	                	newList.addAll(getAllChildFilesWhithSuffix(dir + File.separator + filelist[i], filterSuffix));
	                }
	            }
	        }else if(file.exists() && !file.isDirectory() ){
	        	if(filterSuffix== null || "".equals(filterSuffix)
	            		){
	        		newList.add(file.getCanonicalPath());
	        	}else  {
	        		String fileSuffix = file.getName().substring(file.getName().lastIndexOf('.')+1);
	        		String [] suffixs = filterSuffix.split(";");
	        		List<String> suffixList = Arrays.asList(suffixs);
	        		if(!suffixList.contains(fileSuffix.toLowerCase())){
	        			newList.add(file.getCanonicalPath());
	        		}
	        	}
	        	
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return newList;
		}
		
		return newList;
	}
	
	public static void writeFile(List<String> lines,String filePath){
		Path writeFilePath = FileSystems.getDefault().getPath(filePath);
		 
		 whenFileNotExistCreate(writeFilePath);

		 Charset charset = Charset.forName("utf-8");
		 BufferedWriter writer = null;
		 try {
			 writer = Files.newBufferedWriter(writeFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
	         for(String line : lines){
	        	 if(!"".equals(line)){
	        		 writer.write(line +"\r\n");
	        	 }
	         }
	     } catch (IOException e) {
	         System.err.println(e);
	     }finally{
	    	 closeWriter(writer);
	    }
		 System.out.println("success");
	}
	
	public static String readFileAsLine(String dir) {
		File f = new File(dir);
		 Path readFilePath = FileSystems.getDefault().getPath(dir);
		 Charset charset = Charset.forName("utf-8");
		 BufferedReader reader = null;
//		 test code do not delete
//		if(dir.contains("validateReportElement.xml")){
//			System.out.println();
//		}
		if (!f.exists() || f.isDirectory()) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		try {
			 reader = Files.newBufferedReader(readFilePath, charset);
			 String line = null;
			 while ((line = reader.readLine()) != null) {
	        	 if(!"".equals(line)){
	        		 result.append(line + "\r\n");
	        	 }
	         }
		} catch (IOException e) {
		} finally {
			 try {
	    		 if(reader != null){
	    			 reader.close();
	    		 }
			} catch (IOException e) {
			}
		}
		return result.toString();
	}
}
