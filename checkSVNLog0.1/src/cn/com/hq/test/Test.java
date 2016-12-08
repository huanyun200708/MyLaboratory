package cn.com.hq.test;

import java.util.Random;

public class Test {
public static void main(String[] args) {
	String line = "             operationLogService.addOperationLog( ALMLogFinals.ALM_CODE, StringUtil.isEmpty(objId) ? \"\" : objId, ObjectLogEnum.ALARM.value, StringUtil.isEmpty(objName)? objId : objName, ActionLogEnum.UNACKNOWLEDGE.value, SecurityUtils.getCurrentUserID(), SecurityUtils.getCurrentUserAccount(), this.getRequest().getRemoteHost(), startTime, TimeUtil.getCurrentTime(), BaseAction.SUCCESS, \"1225107348\"); ";
	System.out.println(line);
	System.out.println(line.matches(".*\\.BaseAction\\.SUCCESS.*"));
}
}
