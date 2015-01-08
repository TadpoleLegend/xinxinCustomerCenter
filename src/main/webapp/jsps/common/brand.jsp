<%@ page contentType="text/html;charset=gbk"%>
<header id="brand" data-bind="with : user">
	<div class="row">
		<div class="appname hide-on-phones">欣心客户数据中心</div>
		<address>
			<span>
				<div class="row">
					<b data-bind="text : name"></b> &nbsp; <a title="安全退出系统" href="/ls/logout"><i class="icon-power-off small"></i></a> &nbsp;
					<a title="修改密码" href="#" data-bind="click : $root.openResetMyPasswordDialog"><i class="icon-cog small"></i></a>
				</div>
			</span>
		</address>
		<br>
		<br>
	</div>
	<div id="resetPasswordDialogInBrandJsp" style="display: none;">
			<input type="password"  placeholder="请输入旧的密码" required data-bind="value : $root.oldPassword"> 
			<input type="password"  placeholder="请输入新的密码" required data-bind="value : $root.newPassword"> 
	</div>
</header>
<script>
	$(document).ready(function() {

		var UserModel = function() {
			var self = this;
			self.user = ko.observable(new User());
			self.oldPassword = ko.observable('');
			self.newPassword = ko.observable('');
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
			
			self.openResetMyPasswordDialog = function() {
				
				$('#resetPasswordDialogInBrandJsp').dialog({
					modal : true,
					width : 'auto',
					height : 'auto',
					open : function(e) {
						changeButtonStyleForPopup(e);
					},
					
					buttons : {
						'提交' : function() {
							
							$.ajax({
								url : '/ls/resetMyPassword.ls',
								method : 'POST',
								data : {
									oldPassword : self.oldPassword(),
									newPassword : self.newPassword()
								},
								success : function(data) {
									if (isOK(data)) {
										
										success(data.message);
										
										closeDialog('resetPasswordDialogInBrandJsp');
									} else {
										
										fail(data.message);
									}
								}
							});
						},
						'关闭窗口' : function() {
							closeDialog('resetPasswordDialogInBrandJsp');
						}
					}
					
				});
			};
			
		};
		var userModel = new UserModel();
		var $userContainer = $('#brand')[0];
		ko.applyBindings(userModel, $userContainer);
	});
</script>