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