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
			message : '��Ĳ����Ѿ�<b>�ɹ�</b>ִ�У�',
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
			message : '��Ĳ����Ѿ�<b>�ɹ�</b>ִ�У�',
			showCloseButton : true,
			type : 'error'
		});
	}
	
}