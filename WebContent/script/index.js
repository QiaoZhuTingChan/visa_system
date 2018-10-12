function ShowNewUrl() {
	var loadPageButton = zk.Widget.$(jq("$loadPageButton"), document);
	zAu.send(new zk.Event(loadPageButton, "onClick", null));
}

function CloseCurrentTab() {
	var closeTabButton = zk.Widget.$(jq("$closeTabButton"), document);
	zAu.send(new zk.Event(closeTabButton, "onClick", null));
}

function InitEnter() {

}

var user = null;
var websocket = null;
var interval = null;
var messageId = null;
var url = null;
var totalTime = 0;
function connectionServer(user, url) {
	totalTime++;
	if (totalTime > 30)
		return;
	this.user = user;
	this.url = url;

	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		if (websocket != null) {
			try {
				websocket.close();
			} catch (e) {

			}
		}

		try {
			websocket = new WebSocket(url);
			websocket.onerror = onError;
			websocket.onopen = onOpen;
			websocket.onmessage = onMessage;
			websocket.onclose = onClose;
			if (interval != null)
				clearInterval(interval);
			interval = setInterval(sendHeartBeatMessage, 1000 * 10);
		} catch (e) {
			connectionServer(user, url);
		}
	} else {
		alert('当前浏览器 Not support websocket')
	}
}

function sendHeartBeatMessage() {
	if (websocket != null) {
		try {
			if (websocket.readyState == 3) {
				connectionServer(user, url);
			}
			var message = {
				type : 'heartBeat',
				content : ''
			}
			var str = JSON.stringify(message);
			websocket.send(str);
		} catch (e) {
			connectionServer(user, url);
		}
	}
}

// 连接发生错误的回调方法
function onError() {
	// connectionServer(user,url);
}

// 连接成功建立的回调方法
function onOpen() {
	var message = {
		type : 'login',
		content : user
	}
	var str = JSON.stringify(message);
	websocket.send(str);

	zAu.send(new zk.Event(zk.Widget.$(this), 'onMessage', null));
}

// 接收到消息的回调方法
function onMessage(event) {
	processMessage(event.data);
}

// 连接关闭的回调方法
function onClose() {
	// connectionServer(user,url);
}

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
	closeWebSocket();
}

// 消息处理
function processMessage(message) {
	var obj = JSON.parse(message);

	switch (obj.type) {
	case "totalMessage":
		document.getElementById('message').innerHTML = obj.content;
		break;
	case "newMessage":
		messageId = obj.messageId;
		document.getElementById("messageContent").innerHTML = obj.content;
		document.getElementById("divMessage").style.display = 'block';
		setTimeout(closeMessageWindow, 1000 * 30);
		break;
	default:
		var iframes = $("iframe");
		for (var i = 0; i < iframes.length; i++) {
			try {
				iframes[i].contentWindow.receiveMessage(message);
			} catch (e) {
			}
		}
	}
}

// 关闭WebSocket连接
function closeWebSocket() {
	websocket.close();
}

function viewMessage() {
	zAu.send(new zk.Event(zk.Widget.$(this), 'onViewMessage', {
		type : "newMessage",
		value : messageId
	}));
	document.getElementById("divMessage").style.display = 'none';
}

function closeMessage() {
	document.getElementById("divMessage").style.display = 'none';
}

function viewAllMessage() {
	zAu.send(new zk.Event(zk.Widget.$(this), 'onViewAllMessage', null));
}

function closeMessageWindow() {
	document.getElementById("divMessage").style.display = 'none';
}