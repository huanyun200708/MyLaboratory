package cn.com.hq.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.apache.struts2.ServletActionContext;

public class FileUploadAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private File file;
	private String fileFileName;
	private String fileFileContentType;

	private String message = "你已成功上传文件";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}

	@Override
	public String execute() throws Exception {
		return null;
	}

	public String upload() {

		String path = ServletActionContext.getServletContext().getRealPath("/");

		try {
			File f = this.getFile();
			if (this.getFileFileName().endsWith(".exe")) {
				message = "对不起,你上传的文件格式不允许!!!";
				return ERROR;
			}
			
			Path newfile = FileSystems.getDefault().getPath(path + "/upload/" + this.getFileFileName());
			if (!Files.exists(newfile.toAbsolutePath(), LinkOption.NOFOLLOW_LINKS)) {
				Files.createDirectories(newfile.getParent().toAbsolutePath());
				Files.createFile(newfile.toAbsolutePath());
			}

			FileInputStream inputStream = new FileInputStream(f);
			FileOutputStream outputStream = new FileOutputStream(path
					+ "/upload/" + this.getFileFileName());
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}
			inputStream.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			message = "对不起,文件上传失败了!!!!";
			responseWriter(message);
		}
		//必须有，不然前台接受的不是JS对象，而是html标签
		this.getResponse().setContentType("text/html;charset=UTF-8");
		//信息必须以JSON格式字符串型返回，不然前台会报错
		responseWriter("{\"message\":\"文件上传成功！\",\"imgDir\":\"upload/"+this.getFileFileName()+"\"}");
		return null;

	}

	

	
}
