function InitEnter() {
	try {
		$(':input').removeClass('enterIndex');
	} catch (e) {
	}
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