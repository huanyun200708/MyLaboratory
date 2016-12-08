package cn.com.hq.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Record {
	private Date changeTime;
	private String authorName;
	private  List<OperateFile> OperateFiles= new ArrayList<OperateFile>();
	private boolean isfinish = true;
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public List<OperateFile> getOperateFiles() {
		return OperateFiles;
	}
	public void setOperateFiles(List<OperateFile> operateFiles) {
		OperateFiles = operateFiles;
	}
	public boolean isIsfinish() {
		return isfinish;
	}
	public void setIsfinish(boolean isfinish) {
		this.isfinish = isfinish;
	}
	
	
}
