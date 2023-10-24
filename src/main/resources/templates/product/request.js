export function ajaxRequest(method,url,callbackFunction,sendJsonObject={}) {
	let xhr = new XMLHttpRequest();
	xhr.open(method, url, true);
	xhr.setRequestHeader('Content-Type', 'application/json;chraset=UTF-8');
	xhr.onload = function() {
		callbackFunction(JSON.parse(xhr.responseText));
	}
	xhr.send(JSON.stringify(sendJsonObject));
}

