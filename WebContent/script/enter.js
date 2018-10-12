var controlStep = 0;
/**
 * 为控件设置回车跳转
 * 
 * @returns
 */
function InitEnter() {
	$(':input').addClass('enterIndex');
	$(':input').focus(function() {
		try {
			if ($(this).attr('type') == "textarea")
				return;
			this.select();
		} catch (e) {
		}
	});
	textboxes = $('.enterIndex');
	if ($.browser.mozilla) {
		$(textboxes).bind('keypress', CheckForEnter);
	} else {
		$(textboxes).bind('keydown', CheckForEnter);
	}
	$(textboxes).bind('focus', GetFocus);
}

var controlObject = null;
function GetFocus(event) {
	if ($(this).attr('type') == "text") {
		controlObject = $(this);
		var timer_alert = setTimeout(function() {
			$(controlObject).select();
		}, 200);
	}
}

function CheckForEnter(event) {
	var i = $('.enterIndex').index($(this));
	var n = $('.enterIndex').length;
	if (event.keyCode == 13 && $(this).attr('type') != 'button'
			&& $(this).attr('type') != 'submit'
			&& $(this).attr('type') != 'textarea'
			&& $(this).attr('type') != 'reset') {
		if (i < n - 1) {
			NextDOM($('.enterIndex'), i);
			// var att = $($('.enterIndex').eq(i + 1)[0]).attr('type');
		}
	}

	if (event.keyCode == 38 && controlStep > 0
			&& $(this).attr('type') != 'button'
			&& $(this).attr('type') != 'submit'
			&& $(this).attr('type') != 'textarea'
			&& $(this).attr('type') != 'reset') {
		if (i - controlStep >= 0) {
			NextDOMByStep($('.enterIndex'), i, -1 * controlStep);
			return false;
		}
	}
	if (event.keyCode == 40 && controlStep > 0
			&& $(this).attr('type') != 'button'
			&& $(this).attr('type') != 'submit'
			&& $(this).attr('type') != 'textarea'
			&& $(this).attr('type') != 'reset') {
		if (i < n - 1) {
			NextDOMByStep($('.enterIndex'), i, controlStep);
			return false;
		}
	}
	if (event.keyCode == 37 && controlStep > 0
			&& $(this).attr('type') != 'button'
			&& $(this).attr('type') != 'submit'
			&& $(this).attr('type') != 'textarea'
			&& $(this).attr('type') != 'reset') {
		if (i - controlStep >= 0) {
			NextDOMByStep($('.enterIndex'), i, -1);
			return false;
		}
	}
	if (event.keyCode == 39 && controlStep > 0
			&& $(this).attr('type') != 'button'
			&& $(this).attr('type') != 'submit'
			&& $(this).attr('type') != 'textarea'
			&& $(this).attr('type') != 'reset') {
		if (i < n - 1) {
			NextDOMByStep($('.enterIndex'), i, 1);
			return false;
		}
	}
}
function NextDOM(myjQueryObjects, counter) {
	var att = $(myjQueryObjects.eq(counter + 1)[0]).attr('type');
	if ($(myjQueryObjects.eq(counter + 1)[0]).is(":hidden")
			|| myjQueryObjects.eq(counter + 1)[0].disabled) {
		NextDOM(myjQueryObjects, counter + 1);
	} else {
		myjQueryObjects.eq(counter + 1).focus();
	}
}
function NextDOMByStep(myjQueryObjects, counter, step) {
	if (myjQueryObjects.length <= counter + step || counter + step < 0)
		return;

	var att = $(myjQueryObjects.eq(counter + step)[0]).attr('type');

	if ($(myjQueryObjects.eq(counter + step)[0]).is(":hidden")
			|| myjQueryObjects.eq(counter + step)[0].disabled) {
		NextDOMByStep(myjQueryObjects, counter + step, step);
	} else {
		myjQueryObjects.eq(counter + step).focus();
		if (att == "text") {
			myjQueryObjects.eq(counter + step).select();
		}
	}
}
function ChangeStep(step) {
	controlStep = step;
}

/**
 * 显示信息
 * 
 * @param message
 * @returns
 */
function ShowMessage(message) {
	alert(message);
}

/**
 * iframe窗口开启新的Tab页面，由顶层界面负责开启
 * 
 * @returns
 */
function ShowUrl(url) {
	if (window.parent != undefined) {
		try {
			window.parent.ShowNewUrl();
		} catch (e) {
			window.location.href = url;
		}
	} else {
		window.location.href = url;
	}
}
/**
 * 关闭当前Tab
 * 
 * @returns
 */
function CloseCurrentTab() {
	if (window.parent != null) {
		try {
			window.parent.CloseCurrentTab();
		} catch (e) {
			alert("调用失败");
		}
	} else {
		try {
			window.CloseCurrentTab();
		} catch (e) {
			alert("调用失败");
		}
	}
}
/**
 * 反馈用户数据验证结果是否正确
 * 
 * @param data
 * @param value
 * @returns
 */
function SetCheckValue(data, value) {
	if (window.parent != null) {
		try {
			window.parent.SetReturnCheckValue(data, value);
		} catch (e) {
			alert("调用失败");
		}
	} else {
		alert("调用失败");
	}
}

function NextStep() {
	if (window.parent != null) {
		try {
			window.parent.NextStep();
		} catch (e) {
			alert("调用失败");
		}
	} else {
		alert("调用失败");
	}
}

/**
 * 验证iframe窗口用户数据录入数据是否正确
 * 
 * @param data
 * @returns
 */
function CheckIframeData(data) {
	var iframeWindow = document.getElementsByName(data)[0];
	if (iframeWindow == undefined) {
		alert("调用失败！");
		return;
	} else {
		iframeWindow = iframeWindow.contentWindow;
	}
	iframeWindow.CheckValue(data);
}
/**
 * 调用iframe窗口保存用户数据
 * 
 * @param data
 * @returns
 */
function SaveIframeData(frameName, workflowStatusId) {
	var iframeWindow = document.getElementsByName(frameName)[0];
	if (iframeWindow == undefined) {
		alert("调用失败！");
		return;
	} else {
		iframeWindow = iframeWindow.contentWindow;
	}
	try {
		iframeWindow.SaveData(workflowStatusId);
	} catch (e) {
		alert(e.description);
	}
}
/**
 * 更新调入部门
 * 
 * @param newDepartmentId
 * @returns
 */
function SetNewDepartment(newDepartmentId) {
	if (window.parent != null) {
		try {
			window.parent.SetNewDepartment(newDepartmentId);
		} catch (e) {
			alert("调用失败");
		}
	} else {
		alert("调用失败");
	}
}