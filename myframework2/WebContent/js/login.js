function submitForm(){
	user.name = $("#name").val();
	user.description = $("#message").val();
	$('#ff').form('submit',
		{
			url: CTX_PATH + "/hq/savaOrUpdateAccount_login.do",
			dataType : "json",
			onSubmit: function(param){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.progress('close');	// hide progress bar
				}
				param.user=JSON.stringify(user);
				param.description = $("#message").val();
				return isValid;	// return false will stop the form
								// submission
			},
			success: function(data){
				$.messager.progress('close');	// hide progress bar
												// while submit
												// successfully
				var data = eval('(' + data + ')'); // change the JSON
													// string to
													// javascript object
				if (data.success){
					alert(data.message)
				}
			}
	});
}
function clearForm(){
	$('#ff').form('clear');
}
function f1() {
	user.name = $("#name").val();
	user.description = $("#description").val();
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : CTX_PATH + "/hq/queryDataFromDBByName_login.do",
		dataType : "json",
		data : {
			"user" : $.encode(user),
			"name" : user.name,
			"description" : user.description
		},
		success : function(data, textStatus, jqXHR) {
			if(data.isSuccess == false){
				alert("!error:"+data.message);
			}else{
				
				$("#span1").html("");
				$("#textarea1").html("");
				$("#textarea2").val("");
				$("#div1").html("");
				var d = data.description;
				//alert(d);
				$("#span1").html(d);
				$("#textarea1").html(d);
				//$("#textarea2").val(escapeHtml(d));
				$("#textarea2").val(d);
				$("#div1").html(d);
			}
			
		},
		complete : function(XHR, TS) {
			XHR = null;
		}
	});
}
function f2() {
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : CTX_PATH + "/hq/queryDataFromDBByName_login.do",
		dataType : "json",
		data : {
			"name" : $("#name").val()
		},
		success : function(data, textStatus, jqXHR) {
			if(data.isSuccess == false){
				alert("!error:"+data.message);
			}else{
				user.name = data.name;
				user.description = data.description;
				$.ajax({
					async : false,
					cache : false,
					type : "POST",
					url : CTX_PATH + "/hq/queryData_login.do",
					dataType : "json",
					data : {
						"user" : $.encode(user),
						"name" : user.name,
						"description" : user.description
					},
					success : function(data, textStatus, jqXHR) {
						$("#span1").html("");
						$("#textarea1").html("");
						$("#textarea2").val("");
						$("#div1").html("");
						var d = data.description;
						alert(d);
						$("#span1").html(d);
						$("#textarea1").html(d);
						$("#textarea2").val(escapeHtml(d));
						$("#div1").html(d);
					},
					complete : function(XHR, TS) {
						XHR = null;
					}
				});
			}
		},
		complete : function(XHR, TS) {
			XHR = null;
		}
	});
}
function saveAccount() {
	user.name = $("#name").val();
	user.description = $("#description").val();
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : CTX_PATH + "/hq/savaOrUpdateAccount_login.do",
		dataType : "json",
		data : {
			"user" : JSON.stringify(user),
			"name" : user.name,
			"description" : user.description
		},
		success : function(data, textStatus, jqXHR) {
			if(data.isSuccess){
				alert(data.message);
			}else{
				alert("!error:"+data.message);
			}
		},
		complete : function(XHR, TS) {
			XHR = null;
		}
	});
}

function connect () {
	var host;
	if (window.location.protocol == 'http:') {
			host = 'ws://' + window.location.host + '/myframework2/forwardWebSocket';
		} else {
			host = 'wss://' + window.location.host + '/myframework2/forwardWebSocket';
		}
		if ('WebSocket' in window) {
			ws = new WebSocket(host);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(host);
		} else {
			return;
		}
		
		ws.onopen = function() {

		};

		ws.onclose = function() {
			
		};
		
		ws.onmessage = function(message) {
//收到消息后做出处理的方法
			//handleMsg(JSON.parse(message.data));
			alert(JSON.parse(message.data).name + " 在 " +  JSON.parse(message.data).address+ " 等待上车");
		};
	}
function sendWebsocketMsg(){
	user.name = $("#name").val();
	user.description = $("#message").val();
	ws.send(JSON.stringify(user).replace(/\\n/g,"").replace(/\\t/g,""));
}