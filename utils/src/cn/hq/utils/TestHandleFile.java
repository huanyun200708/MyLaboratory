package cn.hq.utils;

public class TestHandleFile {

	public static void main(String[] args) {

		//FileUtil.copeFileThroughRegex("D:/java_temp/source.txt", "D:/java_temp/with_parameter.txt","^/.*\\.do\\?.*");
		//FileUtil.copeFileThroughRegex("D:/code file/java/a.txt", "D:/code file/java/result.txt","^Revision.*\\d$");
		FileUtil.copeFileThroughRegex("D:/code file/java/a.txt", "D:/code file/java/result.txt","^Author.*\\w$");
		//FileUtil.exportDifferentFormFiles("D:/Users/all.txt", "D:/Users/c.txt","D:/Users/d.txt");
		//System.out.println(FileUtil.selectHigeValueLine2("/authority/hasFun.do"));
		/*FileUtil.changeFileAllLine(
				"E:/工作/项目/03 ACROSSPM/安全SVN/05.�?发输出件/全量排查20160325/20160325-20160414排查结果/反射型XSS扫描结果/${.txt",
				"E:/工作/项目/03 ACROSSPM/安全SVN/05.�?发输出件/全量排查20160325/20160325-20160414排查结果/反射型XSS扫描结果/result.txt",
				"pageContext.request.contextPath");*/
	}
}
