package testPOI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.hq.utils.FileUtil;

public class TestHtml2Word {
	public static void main(String[] args) {
		System.out.println(writeWordFile());
	}
	public static boolean writeWordFile() {  
	       boolean w = false;  
	       String path = "D:/code file/java/";  
	       POIFSFileSystem poifs = new POIFSFileSystem(); 
	       ByteArrayInputStream bais = null;
	       FileOutputStream ostream = null;
	       try {  
	           if (!"".equals(path)) {  
	               // 检查目录是否存在  
	               File fileDir = new File(path);  
	               if (fileDir.exists()) {  
	                   // 生成临时文件名称  
	                    String fileName = "a.doc";  
	                    String content = 
	                    		"<html>"
		                    		+ "<div style=\"text-align: center\">"
		                    		+ "<span style=\"font-size: 28px\">"
		                    			+ "<span style=\"font-family: 黑体\">制度发布通知<br /> <br /> "
		                    				+ "<img id=\"s_lg_img\" src=\"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png\" width=\"270\" height=\"129\">"
		                    			+ "</span>"
			                    		+ "</span>"
			                        + "</div>"
		                        + "</html>";  
	                   String html =FileUtil.readFileAsLine("D:/code file/java/svg.txt"); 
	                  // System.out.println(html);
	                    byte b[] = html.getBytes();  
	                    bais = new ByteArrayInputStream(b);  
	                     
	                    DirectoryEntry directory = poifs.getRoot();  
	                    directory.createDocument("WordDocument", bais);  
	                    ostream = new FileOutputStream(path+ fileName);  
	                    poifs.writeFilesystem(ostream);  
	                    w = true;
	                }  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	      }finally{
	    	  try {
				bais.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
	    	  try {
				ostream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
	    	  try {
				poifs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
	      return w;  
	    }  

}
