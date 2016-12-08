package cn.com.hq.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class FileUtils {
	
	public static BufferedWriter getFileWriter(String path){
		BufferedWriter write = null;
		Path filePath = FileSystems.getDefault().getPath(path);
		whenFileNotExistCreate(filePath);
		try {
			write = new BufferedWriter(new FileWriter(filePath.toFile().getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return write;
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
}
