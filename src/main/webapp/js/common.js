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
		
		message = "<label class=\"text-success\">" + message + "</label> <i class=\"icon-smile small icon-green\"></i>";
		
		Messenger().post({
			message : message,
			showCloseButton : true,
			hideAfter: 2
		});
	} else {
		Messenger().post({
			message : '<label class=\"text-success\"> 你的操作已经<b>成功</b>执行！</label> <i class="icon-smile small icon-green"></i>',
			showCloseButton : true,
			hideAfter: 2
		});
	}
	
}

function fail(message) {
	
	if (message) {
		
		message = "<label class=\"text-error\">" + message + "</label> <i class=\"icon-frown small icon-red\"></i>";
		
		Messenger().post({
			message : message,
			showCloseButton : true,
			type : 'error'
		});
	} else {
		Messenger().post({
			message : '<label class=\"text-error\"> 操作<b>失败</b></label> <i class=\"icon-frown small icon-red\"></i>',
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