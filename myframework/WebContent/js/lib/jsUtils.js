/**
*
*重载objTostr
*/
$.encode=function(o){
	return objTostr(o);
}

/**
 * json对象转string
 * @param o json对象
 * @returns
 */
function objTostr(o){
	if(!o && o!=0) return null;
	var r = [];
	if(typeof o =="string"){
		return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").
			replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";
	}
	if(typeof o == "object"){
		if(!o.sort){
			for(var i in o){
				r.push(i+":"+objTostr(o[i]));
			}
			if(!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)){
				r.push("toString:"+o.toString.toString());
			}
			r="{"+r.join()+"}"
		}else{
			for(var i =0;i<o.length;i++){
				r.push(objTostr(o[i]))
			}
			r="["+r.join()+"]"
		}
		return r;
	}
    return o.toString();
}
function escapeHtml(str){
	   if(str != null && str != "" && isNaN(str)){
		   var tmpStr = str;
		   
		   tmpStr = tmpStr.replace(/&amp;/g, "&");
		   tmpStr = tmpStr.replace(/&quot;/g,"\"");
		   tmpStr = tmpStr.replace(/&lt;/g,"<");
		   tmpStr = tmpStr.replace(/&gt;/g, ">");
		   tmpStr = tmpStr.replace(/&apos;&apos;/g, "'");
		   tmpStr = tmpStr.replace(/&apos;/g, "'");
		   tmpStr = tmpStr.replace(/&3D;/g, "=");
		   tmpStr = tmpStr.replace(/&#x2F;/g, "/");
		   tmpStr = tmpStr.replace(/&#x27;/g, "'");
		   tmpStr = tmpStr.replace(/&#x28;/g, "(");
		   tmpStr = tmpStr.replace(/&#x29;/g, ")");
		   tmpStr = tmpStr.replace(/&amp;/g, "&");
		   tmpStr = tmpStr.replace(/&#x5c;/g, "\\");
		   return tmpStr;
	   }
	   
	   return str;
}

function html_encode(str) {
	var s = "";
	if (str.length == 0)
		return "";
	s = str.replace(/&/g, "&amp;");
	s = s.replace(/</g, "&lt;");
	s = s.replace(/>/g, "&gt;");
	s = s.replace(/\'/g, "&#x27;");
	s = s.replace(/\"/g, "&quot;");
	s = s.replace(/\(/g, "&#x28;");
	s = s.replace(/\)/g, "&#x29;");
	s = s.replace(/\//g, "&#x2F;");
	s = s.replace(/\n/g, "<br>");
	return s;
}