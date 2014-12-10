<header id="brand" data-bind="with : user">
	<div class="row">
		<div class="appname hide-on-phones"></div>
		<address>
			<span> 
				<div class="row">
					<b data-bind="text : name"></b><label class="label green">1</label>
					<div data-bind="foreach : roles">
						<b data-bind="text : description"></b>
					</div>
				</div>
				<div class="row">
					<a href="/ls/logout"><i class="icon-power-off small"></i></a>
				</div>
			</span>
		</address>
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