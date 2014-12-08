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
			message : '<label class=\"text-success\"> ��Ĳ����Ѿ�<b>�ɹ�</b>ִ�У�</label> <i class="icon-smile small icon-green"></i>',
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
			message : '<label class=\"text-error\"> ����<b>ʧ��</b></label> <i class=\"icon-frown small icon-red\"></i>',
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

function handleStanderdResponse(data) {
	if (isOK (data)) {
		success(data.message);
	} else {
		fail(data.message);
	}
}

function hasRole(user, roleName) {
	
	var hasRoleFlag = false;
	
	if (user && user.roles) {
		
		$.each(user.roles, function(i , n) {
			if (n.name == roleName) {
				hasRoleFlag = true;
			}
		});
	}
	
	return hasRoleFlag;
}