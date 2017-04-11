<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/crypto/core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/crypto/crypto-js.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery.js"></script>
	<script type="text/javascript">
	var CTX_PATH = '<%= request.getContextPath() %>'; 
		function sendData(){
			/* alert(new Date().getTime());
			var pwd="我的密码";
			var mi=CryptoJS.AES.encrypt("你好，欢迎来到开源中国在线工具，这是一个AES加密测试",pwd);
			alert("你好，欢迎来到开源中国在线工具，这是一个AES加密测试----密文:"+mi);
			var result=CryptoJS.AES.decrypt(mi,pwd).toString(CryptoJS.enc.Utf8);
			alert("解密结果："+result); */
			var encryptStr=CryptoJS.AES.encrypt("你好，欢迎来到开源中国在线工具，这是一个AES加密测试","1234");
			$.ajax({
				async : false,
				cache : false,
				type : "POST",
				url : CTX_PATH + "/decrypt_secureDataTrans.action",
				dataType : "text",
				data : {
					encryptStr : encryptStr
				},
				success : function(data, textStatus, jqXHR) {
					alert(data);
				},
				complete : function(XHR, TS) {
					XHR = null;
				}
			});
		}
		
	</script>
  </head>
  
  <body>
    	<button onclick="sendData()">send data</button>
  </body>
</html>
