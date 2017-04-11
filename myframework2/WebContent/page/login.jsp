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
	<meta charset="UTF-8">
	<title>Validate Form on Submit - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/EasyUI/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/EasyUI/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/EasyUI/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/EasyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		var name = "${myname}";
		var user = {
				"name" : "n1",
				"description" : "d1"
			};
		var CTX_PATH = '<%=request.getContextPath()%>';
		$(document).ready(connect ());
	</script>
</head>

<body >
		<!-- <div align="center" style="width: 100%;float: left;">
			<input type="button" value="ajax前台输入查询" onclick="f1()" />
			<input type="button" value="ajax后台回传查询" onclick="f2()" />
			<input type="button" value="保存用户信息" onclick="saveAccount()" />
		</div> -->
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="基本信息" style="width:100%;max-width:400px;padding:30px 60px;">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" id='name' style="width:100%" data-options="label:'姓名:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="email" style="width:100%" data-options="label:'邮箱:',required:true,validType:'email'">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="subject" style="width:100%" data-options="label:'Subject:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="message" id="message" style="width:100%;height:60px" data-options="label:'Message:',multiline:true">
			</div>
			<div style="margin-bottom:20px">
				<select class="easyui-combobox" name="language" label="Language" style="width:100%"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
			</div>
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="sendWebsocketMsg()" style="width:80px">Submit</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">Clear</a>
		</div>
	</div>
	<script>
		
	</script>
</body>
</html>
