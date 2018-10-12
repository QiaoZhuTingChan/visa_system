/**
 * 
 */
function insertContent(content) {
	var textbox = $("textarea")[0];
	/*var $textbox = $("textarea[name=inputFormula]");
	var value = $textbox.attr("value");
	value += content;
	$textbox.attr("value", value);*/
	
//	var insertStr = content || "";
//	if (document.selection) {
//		textbox.focus();
//		var r = document.selection.createRange();
//		r.text = insertStr;
//		textbox.focus();
//
//	} else if (textbox.selectionStart || textbox.selectionStart == '0') {
//		var startPos = textbox.selectionStart;
//		var endPos = textbox.selectionEnd;
//		textbox.value = textbox.value.substring(0, startPos) + insertStr
//				+ textbox.value.substring(endPos, textbox.value.length);
//		textbox.selectionStart = startPos + insertStr.length;
//		textbox.selectionEnd = startPos + insertStr.length;
//	} else {
//		textbox.value += insertStr;
//	}
	textbox.value = textbox.value + content;
}

function backContent() {
	var textbox = $("textarea")[0];
	textbox.value = textbox.value.substring(0,textbox.value.length - 1);
//	var $textbox = $("textarea[name=inputFormula]");
//	$textbox.attr("value").substring(0,textbox.value.length - 1);
}