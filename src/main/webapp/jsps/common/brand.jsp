<%@ page contentType="text/html;charset=utf8"%>
<header id="brand" data-bind="with : user">
	<div class="row">
		<div class="appname hide-on-phones">欣心客户数据中心</div>
		<address>
			<span> 
				<div class="row">
					<b data-bind="text : name"></b> &nbsp; <a href="/ls/logout"><i class="icon-power-off small"></i></a>
				</div>
			</span>
		</address>
		<br><br>
	</div>
</header>
<script>
	$(document).ready(function() {

		var UserModel = function() {
			var self = this;
			self.user = ko.observable(new User());
			self.loadMe = function() {
				$.ajax({
					url : '/ls/loadMe.ls',
					success : function(data) {
						
						if (data && data.id) {
							self.user(data);
						}
					}
				});
			};
			self.loadMe();
		};
		var userModel = new UserModel();
		var $userContainer = $('#brand')[0];
		ko.applyBindings(userModel, $userContainer);
	});
</script>