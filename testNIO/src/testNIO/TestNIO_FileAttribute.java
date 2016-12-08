package testNIO;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestNIO_FileAttribute {
	public static void main(String[] args) {
		System.out.println(getFileSize(args[0])/1024/1024 + "M");
	}
	
	public static long getFileSize(String dir){
		 Path filePath = FileSystems.getDefault().getPath(dir);
		 long totalSize = 0L;
		 try {
			 if(Files.isDirectory(filePath)){
				 DirectoryStream<Path> ds = Files.newDirectoryStream(filePath);
				 for (Path p : ds) {   
					 if(Files.isDirectory(p)){
						 totalSize = totalSize + getFileSize(p.toFile().getCanonicalPath());
					 }else{
						 totalSize = totalSize +  Files.size(p);
					 }
					}
			 }else{
				 return Files.size(filePath);
			 }
		} catch (Exception e) {
		}
		 return totalSize/1024/1024;
	}
}
