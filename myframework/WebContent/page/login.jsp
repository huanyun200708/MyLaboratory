<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>!!!My FrameWork!!!</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/NewFile.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/crypto/core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/crypto/crypto-js.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/crypto/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jsUtils.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
<script type="text/javascript">
		var name = "${myname}";
		var user = {
				"name" : "n1",
				"description" : "d1"
			};
		var CTX_PATH = '<%=request.getContextPath()%>';
	
</script>

<style type="text/css">

span {
	display:block;
	width:298px;
	height:20px;
	margin-top: 6px;
	margin-left: 5px;
	font-weight: bold;
	float:left;
}

div{
	margin-left: auto;
	margin-right: auto;
}
textarea{
	 margin-top: 5px;
	 float:left;
}

</style>
</head>

<body >

	<div align="center">
		<s:form action="login_login" method="post">
			<s:textfield name="userName" id="name" label="用户名" key="user" class="textclor" size="49"></s:textfield>
			<s:textarea id="description" name="description" label="描述" cols="51" rows="5"></s:textarea>
			<%-- <s:password name="passWord" label="密码" key="password"></s:password> --%>
			<%-- <s:submit key="login" value="提交" align="center"></s:submit> --%>
		</s:form>
	</div>
	
	<div style="width: 500px;height: 400px;background-color:#e5e8e8;border: 1px; border-color:black; border-style:solid;">
		<div align="center">
			<span style="width:auto;">HTML方式赋值:</span>
			<textarea rows="5" cols="40" id="textarea1" style="margin-left: 5px;"></textarea>
		</div>
		<div align="center">
			<span style="width:auto;">VAL方式赋值:</span>
			<textarea rows="5" cols="40" id="textarea2" style="margin-left: 13px;"></textarea>
		</div>
		<div style="width: 430px;height: 30px;float: left;">
			<span style="width:auto;">HTML方式赋值:</span>
			<span style="border:1px solid #00f; " id="span1"></span>
		</div>
		<div style="width: 430px;height: 90px;float: left;">
			<span style="width:auto;">HTML方式赋值:</span>
			<div style="border:1px solid #00f;float:left;width: 298px;height: 80px;margin-left: 5px;" id="div1"></div>
		</div>
		<div align="center" style="width: 100%;float: left;">
			<input type="button" value="ajax前台输入查询" onclick="f1()" />
			<input type="button" value="ajax后台回传查询" onclick="f2()" />
			<input type="button" value="保存用户信息" onclick="saveAccount()" />
		</div>
	</div>
	

</body>
</html>
