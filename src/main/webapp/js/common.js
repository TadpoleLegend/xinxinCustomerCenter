function Common() {
	
};

Common.prototype.loadAjaxLoader = function(message) {
	if (message == null || message == 'undefined' || message == '') {
		message = "loading";
	}
	$.loader({
		className : "black-with-image",
		content : "<div class='load-text'>" + message + "</div>"
	});
};

Common.prototype.closeAjaxLoader = function() {
	$.loader("close");
};


function success(message) {
	
	if (message) {
		Messenger().post({
			message : message,
			showCloseButton : true
		});
	} else {
		Messenger().post({
			message : '你的操作已经<b>成功</b>执行！',
			showCloseButton : true
		});
	}
	
}

function fail(message) {
	
	if (message) {
		Messenger().post({
			message : message,
			showCloseButton : true,
			type : 'error'
		});
	} else {
		Messenger().post({
			message : '你的操作已经<b>成功</b>执行！',
			showCloseButton : true,
			type : 'error'
		});
	}
	
}

function changeButtonStyleForPopup(event) {
	
	var popupWidget = $(event.target).parents(".ui-dialog.ui-widget");
	var buttonsOnWidget = popupWidget.find(".ui-dialog-buttonpane button");
	var firstButton = buttonsOnWidget[0];
	var secondButton = buttonsOnWidget[1];
	$(firstButton).addClass("primary");
	$(secondButton).addClass("secondary");
	$(".ui-widget-overlay").hide().fadeTo(800, 0.8);
}

function isOK (response) {
	if (response && response.type =='SUCCESS') {
		return true;
	} else {
		return false;
	}
}

function labelIt(message) {
	return '<label class="label green">' + message + '</label>';
}

function bIt(message) {
	return '<b>' + message + '</b>';
}