package cn.com.inspur.hq;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.com.inspur.hq.itf.HandleJavaFile;
import cn.com.inspur.hq.model.CodeLine;
import cn.com.inspur.hq.model.JavaCodeBlock;
import cn.com.inspur.hq.model.JavaFile;
import cn.com.inspur.hq.model.JavaMethod;
/**
 * 用于检验一个类中审计日志的记录情况
 * 思路：
 * 1.将一个类对应的java源码文件进行格式化，去掉注释和双引号内的内容防止干扰解析；一句完整的代码合并到一行；‘}’符号单独成行，‘{’符号合并到方法、类、条件语句声明语句那一行
 * 2.类中的每个方法分别解析成对象；
 * 3.方法中的每个条件语句、try、catch包裹的语句看作是一个代码块，分别解析成对象；
 * 4.如果代码块中又包含条件语句、try、catch包裹的语句,再将这些语句分别解析成代码块
 * 5.解析完一个类后，所以代码都应该是属于某个方法或者某个代码块
 * 6.分析每个方法，如果方法中的代码行没有出现记录审计日志的语句，则认为该方法漏记录审计日志。除非该方法被标识不用检查审计日志记录情况
 * 7.分析每个代码块中的代码行，如果出现retrun语句、2.抛出参数异常、session失效语句或者捕获异常，并且没有出现记录审计日志的语句，则认为该代码块漏记录审计日志
 * 
 * */
public class CheckOperateLog {
	/**不检查审计日志的标识 */
	private static final String UNNEEDOPERALOG = "UNNEEDOPERALOG";
	/**不检查审计日志的标识 */
	private static final String JAVAFILENAMESUFFIX = "Action";
	/**类方法名是否初始化完成 */
	private boolean isClassNameEnd = false;
	/**是否所有方法初始化完毕 */
	private boolean isFindAllMethodEnd = false;
	/**当前正在初始化的方法名称 */
	private String theCurrentMethodName = "";
	/**解析java文件转换的java对象 */
	private JavaFile javaFile = new JavaFile();
	/**所有方法的集合 */
	private Map<String, JavaMethod> methodMap = new HashMap<String, JavaMethod>();
	/**代码块暂存集合 */
	private List<JavaCodeBlock> tempJavaCodeBlockList = new ArrayList<JavaCodeBlock>();
	/**当前解析代码行的前一行，用于查看方法或者代码块是否需要忽略审计日志检查 */
	private String preLine = "";
	/**在检查前是否对结果文件进行了清空 */
	private static boolean isCleanResultFile = false;
	/**在检查前是否对结果文件进行了清空 */
	private static int lineNumber = 0;
	private static Logger logger = Logger.getLogger(CheckOperateLog.class);  
	/**
	 *  检查一个java类对应的java文件源码中是否记录审计日志的入口
	 * @author hq
	 * @param filePath java源码文件绝对路径
	 * 
	 * */
	public void startCheckOperateLog(String filePath, HandleJavaFile handleJavaFile ){
		Path formateJavaFilePath = exportFormateJavaFile(filePath);
		instanceJavaFile(formateJavaFilePath);
		getCheckJavaFileResult(javaFile,handleJavaFile);
		initParamenters();
	}
	/**
	 *  处理完一个action类后还原初始变量
	 * @author hq
	 * 
	 * */
	public void initParamenters(){
		//处理完一个action类后还原初始变量
				isClassNameEnd = false;
				javaFile = new JavaFile();
				 methodMap = new HashMap<String, JavaMethod>();
				tempJavaCodeBlockList = new ArrayList<JavaCodeBlock>();
				isFindAllMethodEnd = false;
				lineNumber = 0;
				theCurrentMethodName = "";
	}
	
	/**
	 *  递归检查一个文件路径中的所有Action类
	 * @author hq
	 * @param dir 文件路径
	 * 
	 * */
	public void checkOperateLogByDir(File dir,HandleJavaFile handleJavaFile){
		if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
            	File fileDir = new File(dir.toString() + "/" + children[i]);
            	if (fileDir.isDirectory()){
            		checkOperateLogByDir(fileDir,handleJavaFile);
            	}else if(fileDir.toString().matches(".*" + JAVAFILENAMESUFFIX + "\\.java\\s*")){
                	try {
                		logger.debug("currrent file name : " + isNeededLine(fileDir.toString(),"\\\\\\w+\\\\\\w+\\.java"));  	
System.out.println("currrent file name : " + isNeededLine(fileDir.toString(),"\\\\\\w+\\\\\\w+\\.java"));
        				startCheckOperateLog(fileDir.getCanonicalPath(),handleJavaFile);
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
                }
            }
        }
	}
	
	
	
	/**
	 *  导出检查结果到checkJavaFileResult.txt
	 * @author hq
	 * @param javaFile java源码文件转换成的java对象
	 * @return
	 * */
	private void getCheckJavaFileResult(JavaFile javaFile , HandleJavaFile handleJavaFile) {
		
		String path = CheckOperateLog.class.getResource("/").getPath();
		Charset charset = Charset.forName("utf-8");
		Path writeFilePath = FileSystems.getDefault().getPath(path.substring(1)+"checkJavaFileResult.txt");
		BufferedWriter writer = null;
		whenFileNotExistCreate(writeFilePath);
		if(!isCleanResultFile){
			writeFilePath.toFile().delete();
			whenFileNotExistCreate(writeFilePath);
			isCleanResultFile = true;
		}
		try {
			writer = Files.newBufferedWriter(writeFilePath, charset, StandardOpenOption.APPEND);
			//List<String> errorStrings = javaFile.checkCode();
			List<String> errorStrings = handleJavaFile.checkJavaFile(javaFile);
	 		for(String s : errorStrings){
	 			writer.write(javaFile.getClassName() + "	" + s + "\r\n");
	 		}
	 		
		} catch (IOException e) {
		}finally{
			closeWriter(writer);
		}
 		
	}

	/**
	 *  初始化java源码文件对应的java对象
	 * @author hq
	 * @param formateJavaFilePath java文件的绝对地址
	 * @return
	 * */
	public JavaFile instanceJavaFile(Path formateJavaFilePath) {

		Charset charset = Charset.forName("utf-8");
		BufferedReader reader = null;

		try {
			reader = Files.newBufferedReader(formateJavaFilePath, charset);
			List<String> lineBuffer = new ArrayList<String>();
			String line = null;
			while ((line = reader.readLine()) != null) {
/***********************************************
 * ***********************
 * 调试代码行
 * ***********************
 * *******************************/
// TODO:调试代码行
if(line.contains("private String checkIndicator")){
System.out.print("");	
}				
				 ++ lineNumber;
				if (null != line && !"".equals(line.replaceAll("\\s", ""))) {
					if (!isClassNameEnd) {
						instanceClassName(line, lineBuffer);
						continue;
					}

					if (!isFindAllMethodEnd) {
						instanceJavaMethod(line, lineBuffer);
					}
				}
			}
			
			for(Entry<String, JavaMethod> e : methodMap.entrySet()){
				javaFile.getJavaMethodList().add(e.getValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader(reader);
		}
		return javaFile;
	}
	/**
	 *  初始化类名
	 * @author hq
	 * @param line 当前代码行
	 * @param lineBuffer 缓存代码行集合
	 * @return
	 * */
	public void instanceClassName(String line, List<String> lineBuffer) {
		/**发现public关键字或者缓存字符串列表里面已经缓存了内容 */
		if (line.matches("^\\s*public\\s*.*$")) { 
			/**如果是完整的类声明语句 */
			if (line.matches("^\\s*public\\s+class\\s+.*\\w+\\s*\\{+\\s*$")) {
				javaFile.setClassName(isNeededLine(line,"^\\s*public\\s+class\\s+(\\w+)\\s*.*\\{+\\s*$"));
				isClassNameEnd = true;
			}
		}
	}
	/**
	 *  初始化方法名
	 * @author hq
	 * @param line 当前代码行
	 * @param lineBuffer 缓存代码行集合
	 * @return
	 * */
	public void instanceJavaMethod(String line, List<String> lineBuffer) {
/***********************************************
 * ***********************
 * 调试方法名
 * ***********************
 * *******************************/
// TODO:调试代码行
if("private void recordOperLog(String userID, String userName".equals(theCurrentMethodName)){
	System.out.print("");
}
		JavaMethod tempJavaMethod = methodMap.get(theCurrentMethodName);
		if (tempJavaMethod == null) {
			tempJavaMethod = new JavaMethod();
		}
		if (!tempJavaMethod.isInitFinished() || lineBuffer.size() > 0) {// 如果一个方法还没有初始化完
			if (!tempJavaMethod.isInitNameFinished()) {
				instanceJavaMethodName(line, lineBuffer);
			} else {
				instanceJavaMethodCode(line, lineBuffer);
			}
		}
		/**如果发现内部类的声明语句，说明所有的方法已经初始化完成 */
		if (line.matches("^\\s*class\\s*.*$")) {
			isFindAllMethodEnd = true;
		}
		
		preLine = line;

	}
	
	/**
	 *  初始化方法名
	 * @author hq
	 * @param line 当前代码行
	 * @param lineBuffer 缓存代码行集合
	 * @return
	 * */
	
	public void instanceJavaMethodName(String line, List<String> lineBuffer) {
		
		if (line.matches("^\\s*public\\s*.*$")) {// 发现public关键字或者缓存字符串列表里面已经缓存了内容
			if (line.matches("^\\s*public[\\s\\w]*\\([^\\(\\)]*\\)\\s*\\{\\s*$")) {// 如果是完整的方法声明字符串
				JavaMethod javaMethod = new JavaMethod();
				String wholeMethodName = isNeededLine(line,"^\\s*(public[\\s\\w]*\\([^\\(\\)]*\\))\\s*\\{\\s*$");
				javaMethod.setWholeMethodName(wholeMethodName);
				javaMethod.setInitFinished(false);
				javaMethod.setInitNameFinished(true);
				
				if(preLine.matches(".*" + UNNEEDOPERALOG + ".*")){
					javaMethod.setIgnoreCheck(true);
				}
				
				theCurrentMethodName = javaMethod.getWholeMethodName();
				methodMap.put(wholeMethodName, javaMethod);
			}
		}
		
	}
	/**
	 *  解析方法中的代码
	 * @author hq
	 * @param line 当前代码行
	 * @param lineBuffer 缓存代码行集合
	 * @return
	 * */
	private void instanceJavaMethodCode(String line, List<String> lineBuffer) {
		
		JavaMethod tempJavaMethod = methodMap.get(theCurrentMethodName);
		JavaCodeBlock javaCodeBlock = getTheLastUnEndCodeBlock(tempJavaCodeBlockList);
		if (!"".equals(line) && !tempJavaMethod.isInitFinished()) {
			if(isCodeBlockLineStart(line)){
				if (line.matches(".*\\{\\s*$")) {
					if(javaCodeBlock == null){
						javaCodeBlock = new JavaCodeBlock();
						javaCodeBlock.setWholeCodeBlockName(line);
						tempJavaCodeBlockList.add(javaCodeBlock);
					}else{
						JavaCodeBlock subCodeBlock = new JavaCodeBlock();
						subCodeBlock.setWholeCodeBlockName(line);
						javaCodeBlock.getJavaCodeBlockList().add(subCodeBlock);
					}
					if(preLine.matches(".*" + UNNEEDOPERALOG + ".*")){
						javaCodeBlock.setIgnoreCheck(true);
					}
				}
			}else if (line.matches("\\s*\\}\\s*$")) {
				if(javaCodeBlock != null){
					javaCodeBlock.setInitFinished(true);
				}
				//如果方法中的代码块全部分析完成
				if(javaCodeBlock == null && tempJavaCodeBlockList.size() > 0){
					for(JavaCodeBlock jb : tempJavaCodeBlockList){
						tempJavaMethod.getJavaCodeBlockList().add(jb);
					}
					tempJavaCodeBlockList.clear();
				}
				
			}
			else if (line.matches(".*;\\s*$")) {
				CodeLine codeLine = new CodeLine();
				codeLine.setCodeValue(line);
				codeLine.setCodeLineEnd(true);
				if(javaCodeBlock != null && !javaCodeBlock.isInitFinished()){
					javaCodeBlock.getJavaCodeList().add(codeLine);
				}else{
					tempJavaMethod.getJavaCodeList().add(codeLine);
				}
			} 
			
		}

		if (line.matches("^\\s*public\\s*.*$") || line.matches("^\\s*private\\s*.*$")) {
			tempJavaMethod.setInitFinished(true);
			for(JavaCodeBlock jb : tempJavaCodeBlockList){
				tempJavaMethod.getJavaCodeBlockList().add(jb);
			}
			tempJavaCodeBlockList.clear();
			theCurrentMethodName = "中文方法";
			instanceJavaMethod(line, lineBuffer);
		}
	}
	/**
	 *  将缓存代码行集合转换成字符串
	 * @author hq
	 * @param lineBuffer 缓存代码行集合
	 * @return
	 * */
	private String lingBufferToString(List<String> lineBuffer) {
		String result = "";
		int n = 0;
		for (String l : lineBuffer) {
			++n;
			if(n >= 1){
				if("{".equals(l.trim())){
					l = l.trim();
				}
				result = result + l;
				
			}
			
		}
		String[] ss = result.split("[^\\s]+");
		String result2 = "";
		//保留第一个空白字符
		if(ss.length > 0){
			result2 = ss[0];
			result = result2 +result.replaceAll("\\r\\n", "回车").replaceAll("\\s+", " ").replaceAll("回车", "\r\n");
		}
		return result;
	}

	/**
	 *  清除一行代码中的注入内容和双引号中的内容
	 * @author hq
	 * @param 
	 * @return
	 * */
	private String cleanLine(String line) {
		//debugLine(line, "templateNameList.add(templateName);");
		if ((line.matches("^\\s*\\/\\*.*$") 
				||line.matches("^\\s*\\@.*$")
				|| line.matches("^\\s*\\*.*$")
				|| line.matches("^\\s*\\/\\/.*$"))
				&& !line.matches(".*" + UNNEEDOPERALOG + ".*")) {
			return "";
		} else {
			if(!line.matches(".*" + UNNEEDOPERALOG + ".*")){
				String tempLine = line.replaceAll("(\\\\\\\\)+", "\\\\")
						.replaceAll("\\\\\"", "双引号")
						.replaceAll("\\/\\/.*", "")
						.replaceAll("'\\{'", "'花括号'")
						.replaceAll("'\\}'", "'花括号'");
					return clineSpecialChar(tempLine);
				}
			else{
				return line;
			}
		}
	}
	
	private String clineSpecialChar(String line){
		char[] chars = line.toCharArray();
		boolean isStrEnd = true;
		char preChar = ' ';
		for(int i = 0; i<chars.length ; i++){
			char c = chars[i];
			//检查到双引号
			if(c == '"' && preChar!='\\'  && isStrEnd){
				isStrEnd = false;
				continue;
			}
			
			if(!isStrEnd){
				//检查到双引号闭合条件当前字符是双引号并且前一个不是\
				if(c == '"' && preChar!='\\'){
					isStrEnd = true;
				}
				//TODO:需要转义的特殊字符
				if("{}*/,".contains(c+"")){
					chars[i] = '#';
				}
				//对\"进行转义
				if(c == '"' && preChar=='\\'){
					chars[i] = '#';
					chars[i-1] = '#';
				}
				
			}
			preChar = c;
		}
		return String.valueOf(chars);
	}
	/**
	 *  判断一行代码是不是代码段的开始
	 * @author hq
	 * @param 
	 * @return
	 * */
	private boolean isCodeBlockLineStart(String line){
		if (line.matches("^\\s*if\\s*\\(.*") 
				|| line.matches("^\\s*\\}*\\s*else.*")
				|| line.matches("^\\s*\\}*\\s*else\\s+if\\s*\\(.*")
				|| line.matches("^^\\s*while\\s*\\(.*")
				|| line.matches("^^\\s*switch\\s*\\(.*")
				|| line.matches("^\\s*\\}*\\s*catch\\s*\\(.*Exception.*")
				|| line.matches("^\\s*try\\s*\\{\\s*")
				|| line.matches("^\\s*for\\s*\\(.*\\{\\s*")
				|| line.matches("^\\s*.*\\s*new.*\\{\\s*")
			) 
		{
			return true;
		}else{
			return false;
		}
	}
	/**
	 *  取得最后一个没有初始化完的代码段
	 * @author hq
	 * @param 
	 * @return
	 * */
	private JavaCodeBlock getTheLastUnEndCodeBlock(List<JavaCodeBlock> javaCodeBlockList){
		if(javaCodeBlockList.size() > 0 && !javaCodeBlockList.get(javaCodeBlockList.size() - 1).isInitFinished()){
			JavaCodeBlock codeBlock = javaCodeBlockList.get(javaCodeBlockList.size() - 1);
			if(codeBlock.getJavaCodeBlockList().size()  > 0 ){
				if (getTheLastUnEndCodeBlock(codeBlock.getJavaCodeBlockList()) == null){
					return codeBlock;
				}else{
					return getTheLastUnEndCodeBlock(codeBlock.getJavaCodeBlockList());
				}
			}else if(!codeBlock.isInitFinished()){
				return codeBlock;
			}
		}
		return null;
	}
	/**
	 *  导出格式化完成java源码文件
	 * @author hq
	 * @param 
	 * @return
	 * */
	public Path exportFormateJavaFile(String filePath){
		
		Path javaSourceCodeFilePath = FileSystems.getDefault().getPath(filePath);
		Charset charset = Charset.forName("utf-8");
		BufferedReader javaSourceCodeFileReader = null;
		BufferedReader handleAnnotationReader = null;
		BufferedReader handleBraceFileFileReader = null;
		BufferedReader handlealoneLineFileFileReader = null;
		
		BufferedWriter FormateJavaFileWrite = null;
		BufferedWriter handleAnnotationReaderWriter = null;
		BufferedWriter handleBraceFileWriter = null;
		BufferedWriter handlealoneLineFileWriter = null;
		
		String path = CheckOperateLog.class.getResource("/").getPath();
		Path FormateJavaFileWriteFilePath = FileSystems.getDefault().getPath(path.substring(1)+"exportFormateJavaFile.txt");
		Path handleAnnotationFilePath = FileSystems.getDefault().getPath(path.substring(1)+"tempFile1.txt");
		Path handleBraceFilePath = FileSystems.getDefault().getPath(path.substring(1)+"tempFile2.txt");
		Path handlealoneLineFilePath = FileSystems.getDefault().getPath(path.substring(1)+"tempFile3.txt");
		
		whenFileNotExistCreate(FormateJavaFileWriteFilePath);
		whenFileNotExistCreate(handleAnnotationFilePath);
		whenFileNotExistCreate(handleBraceFilePath);
		whenFileNotExistCreate(handlealoneLineFilePath);
		try {
			javaSourceCodeFileReader = Files.newBufferedReader(javaSourceCodeFilePath, charset);
			handleAnnotationReader = Files.newBufferedReader(handleAnnotationFilePath, charset);
			handleBraceFileFileReader = Files.newBufferedReader(handleBraceFilePath, charset);
			handlealoneLineFileFileReader = Files.newBufferedReader(handlealoneLineFilePath, charset);
			
			FormateJavaFileWrite = Files.newBufferedWriter(FormateJavaFileWriteFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
			handleAnnotationReaderWriter = Files.newBufferedWriter(handleAnnotationFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
			handleBraceFileWriter = Files.newBufferedWriter(handleBraceFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
			handlealoneLineFileWriter = Files.newBufferedWriter(handlealoneLineFilePath, charset, StandardOpenOption.TRUNCATE_EXISTING);
			
			List<String> lineBuffer = new ArrayList<String>();
			String line = "";
			boolean isFindAnnotation = false;
			
			/**************把"* /"字符单独成行*******************/
//System.out.println("start make \"*/\" as a alone line -------------------------------------------");
logger.debug("start make \"*/\" as a alone line -------------------------------------------"); 
			while ((line = javaSourceCodeFileReader.readLine()) != null) {
				if(line != null || !"".equals(line)){
					if(line.matches(".*\\*\\/.*.*")){
						if(line.matches(".*\\*\\/.*\\w+.*")){
							handleAnnotationReaderWriter.write(line.replaceAll("\\*\\/", "*/\r\n"));
						}else{
							handleAnnotationReaderWriter.write(line + "\r\n");
						}
					}else{
						handleAnnotationReaderWriter.write(line + "\r\n");
					}
				}
			}
			closeReader(javaSourceCodeFileReader);
	    	closeWriter(handleAnnotationReaderWriter);
//System.out.println(" make \"*/\" as a alone line end. export temp1");
	    	logger.debug(" make \"*/\" as a alone line end. export temp1");
			/**********************使得}符号单独成行************************/
//System.out.println("start make \"}\" as a alone line -------------------------------------------");
	    	 logger.debug("start make \"}\" as a alone line -------------------------------------------");
			line = "";
			while ((line = handleAnnotationReader.readLine()) != null) {
//debugLine(line, "new TypeToken<List<String>>(){}.getType());");
				if (line.matches("^\\s*\\/\\*.*\\*\\/\\s*$")){
					continue;
				}
				if (line.matches("^\\s*\\/\\*.*$") || isFindAnnotation) {
					if (line.matches("^\\s*\\/\\*.*$")) {
						isFindAnnotation = true;
						continue;
					}
					if (line.matches("^.*\\*\\/.*")) {
						isFindAnnotation = false;
						continue;
					}
					if(isFindAnnotation){
						continue;
					}

				}
				if("".equals(cleanLine(line))){
					continue;
				}
				line = cleanLine(line);
				if(line != null || !"".equals(line)){
					if(line.contains("{") || line.contains("}")){
						/*     adfdfa}afdafd  
						 * --------->
						 * adfdfa
						 * }
						 * afdafd
						 *        
						 *        
						if(line.matches(".*[^\\s]+.*\\}[\\w]+.*") ){
							line = line.replaceAll("\\}", "\r\n}\r\n");
						}
						     adfdfa}         
						 * ----------------->
						 * adfdfa
						 * }
						 * 
						 * 
						 * 
						if(line.matches(".*[^\\s]+.*\\}\\s*") ){
							line = line.replaceAll("\\}", "\r\n}");
						}
						     adfdfa}         
						 * ----------------->
						 * }
						 * adfdfa
						 * 
						 * 
						if(line.matches("\\s*\\}[^\\s]+.*") ){
							line = line.replaceAll("\\}", "}\r\n");
						}*/
						if("}".equals(line.trim())){
							line = line + "\r\n";
						}
						if("{".equals(line.trim())){
							line = line + "\r\n";
						}
						
						if(line.matches(".*\\{.*") || line.matches(".*\\}.*")){
							line = line.replaceAll("\\{", "{\r\n").replaceAll("\\}", "\r\n}\r\n");
						}
						
						
						handleBraceFileWriter.write(line + "\r\n");
					}else{
						handleBraceFileWriter.write(line + "\r\n");
					}
				}
				
			}
			closeReader(handleAnnotationReader);
	    	closeWriter(handleBraceFileWriter);
	    	System.out.println("make \"}\" as a alone line end.export temp1");
	    	logger.debug("make \"}\" as a alone line end.export temp1");
			line = "";
			System.out.println("start make one whole code in a line -------------------------------------------");
			logger.debug("start make one whole code in a line -------------------------------------------");
			while ((line = handleBraceFileFileReader.readLine()) != null) {
				if (line != null || !"".equals(line)) {
					if (null != line && !"".equals(line.replaceAll("\\s", ""))) {
						String cleanStr = cleanLine(line);
//debugLine(line, "String parameterName = emailTemplateService.getEmailParameterNamesByIds(new String[]{");
						if (!"".equals(cleanStr)) {
							if ((cleanStr.matches(".*;\\s*$") || cleanStr.matches("^\\s*.*\\{\\s*$"))&& lineBuffer.size() == 0) {
								handlealoneLineFileWriter.write(cleanStr + "\r\n");
							}else if(cleanStr.contains("}")){
								/**
								 * new String[]{urlid });
								 * }前是单独的语句
								 * **/
								if(lineBuffer.size() == 0){
									handlealoneLineFileWriter.write(cleanStr + "\r\n");
								}else{
								//}前不是单独的语句
									lineBuffer.add(cleanStr.replaceAll("\\}", "\r\n}\r\n"));
								}
								
							}else if(cleanStr.matches(".*" + UNNEEDOPERALOG + ".*")){
								handlealoneLineFileWriter.write(cleanStr + "\r\n");
							}
							else {
								if (cleanStr.matches(".*;\\s*$") || cleanStr.matches("^\\s*.*\\{\\s*$")) {
									lineBuffer.add(cleanStr + " ");
									handlealoneLineFileWriter.write(lingBufferToString(lineBuffer) + "\r\n");
									lineBuffer.clear();
								} else {
									lineBuffer.add(cleanStr + " ");
								}

							}
						}

					}

				}
			}
			 closeReader(handleBraceFileFileReader);
	    	 closeWriter(handlealoneLineFileWriter);
			System.out.println("make one whole code in a line end.export temp3");
			logger.debug("make one whole code in a line end.export temp3");
			
			System.out.println("start handle except Brace code -------------------------------------------");
			logger.debug("start handle except Brace code -------------------------------------------");
			lineBuffer.clear();
			boolean isFindArrayEnd = true;
			boolean isFindInterClassEnd = true;
			String preLine = "";
			while ((line = handlealoneLineFileFileReader.readLine()) != null) {
				//debugLine(line, "String[] thisLine = {");
				/**
				 *   String[] methodArray = {
				 * String methodArray[] = {
				 * */
				if(line.matches("^\\s*\\w+\\s*\\[\\s*\\]\\s*\\w+\\s*\\=\\s*\\{\\s*$")
						|| line.matches("^\\s*\\w+\\s*\\w+\\s*\\[\\s*\\]\\s*\\=\\s*\\{\\s*$")){
					isFindArrayEnd = false;
					lineBuffer.add(line);
					preLine = line;
					continue;
				}
				if(!isFindArrayEnd){
					lineBuffer.add(line);
					/**
					 * "-1940022389","-1940022389","-1940022389","-1940022389" ,"-1940022389", "-1940022389"};
					 *         	 String[] thisLine = {
					 *vo.getDeviceName(), vo.getIpAddr(), vo.getEquipmentTypeName(),vo.getEquipmentModelName(), vo.getEnable() == 0 ? "OFF" : "ON" 
					 *}
					 *;
					 * */
					if(line.matches("^\\s*.*\\}\\s*\\;\\s*$")
							||(line.matches("^\\s*\\;\\s*$") &&  preLine.trim().endsWith("}"))
							){
						isFindArrayEnd = true;
						FormateJavaFileWrite.write(lingBufferToString(lineBuffer) + "\r\n");
						lineBuffer.clear();
					}
				}else{
					/**
					 * indicatorList = JsonUtils.fromJson(topoIndicator, new TypeToken<List<ReportIndicator>>() {
					 * String parameterName = emailTemplateService.getEmailParameterNamesByIds(new String[]{urlid });
					 * */
					if(line.matches("^.*new\\s*[A-Z]\\w*.*[\\(\\[]\\s*[\\)\\]]\\s*\\{\\s*$") ){
						isFindInterClassEnd = false;
						lineBuffer.add(line);
						preLine = line;
						continue;
					}
					if(!isFindInterClassEnd){
						lineBuffer.add(line);
						/**
						 * }
						 * );
						 * 或
						 * }
						 *.getType());
						 *或
						 *}.getType());
						 * */
						if( ((line.matches("^\\s*\\).*\\;\\s*$") || line.matches("^\\s*\\)?\\s*\\..*\\(\\s*\\).*\\;\\s*$"))
								&& preLine.trim().endsWith("}"))
								|| line.matches("^\\s*\\}\\s*\\.\\)?\\s*\\..*\\(\\s*\\).*\\;\\s*$")
								){
							isFindInterClassEnd = true;
							FormateJavaFileWrite.write(lingBufferToString(lineBuffer) + "\r\n");
							lineBuffer.clear();
						}
					}else{
						FormateJavaFileWrite.write(line + "\r\n");
					}
				}
				preLine = line;
			}
			System.out.println("handle except Brace code end.");
			logger.debug("handle except Brace code end.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			 closeReader(handlealoneLineFileFileReader);
	    	 closeWriter(FormateJavaFileWrite);
		}

		return FormateJavaFileWriteFilePath;
	}

	public static void whenFileNotExistCreate(Path filePath) {

		if (Files.notExists(filePath,
				new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
			try {
				Files.createFile(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeReader(BufferedReader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeWriter(BufferedWriter writer) {
		try {
			if (writer != null) {
				writer.flush();
				writer.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/**
	 *调试代码用，如果一行包含需要的字符串，则记入断点断点 
	 * */
	private void debugLine(String line, String fingStr){
		System.out.println(line);
		if(line.contains(fingStr)){
			System.out.print("");
		}
	}
}
