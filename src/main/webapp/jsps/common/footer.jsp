<!-- Footer -->
<footer role="footer">
		<div class="container">
			<div class="row">
				<div class="eight columns">
					<p>Copyright &copy; 2009 - 2013 XinXin Enterprises. All rights reserved.</p>
					<address>
						<a rel="external" class="display-inline" title="www.baidu.com" href="www.baidu.com">Privacy Policy <i class="icon-external-link"></i></a>
						<a rel="external" title="www.baidu.com" href="www.baidu.com">Terms and Conditions <i class="icon-external-link"></i></a>
						<a rel="external" title="www.baidu.com" href="www.baidu.com">www.baidu.com <i class="icon-external-link"></i></a>
					</address>
				</div>
				<div class="four columns"><h6 class="logo smart">Powered by SMART</h6></div>
			</div>
		</div>
	</footer>
	<div id="commonErrorMessageDialog" title=""></div>
	<script>

	$(document).ajaxStart(function() {
		Common.prototype.loadAjaxLoader("LOADING....");
	}).ajaxStop(function() {
		Common.prototype.closeAjaxLoader();
	});
	
	$(document).ajaxError(
		function ajaxRequestFailHandler(event, xmlhttprequest, ajaxoptions, errorthrown) {
		
		var htmlResponseText = xmlhttprequest.responseText;
		$('#commonErrorMessageDialog').html(htmlResponseText);
		
		$('#commonErrorMessageDialog').dialog({
			modal : true,
			width : 700,
			height : 700,
			buttons : {
				
				'Close Window' : function() {
					closeDialog('commonErrorMessageDialog');
				}
			}
		});
		
		fail(errorthrown);
	});
</script>