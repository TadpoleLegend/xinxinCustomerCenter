<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>	<html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>	<html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]> <html class="no-js ie9" lang="en"> <!-->
<html lang="en">
<!--<![endif]-->
<head>
<!-- Set the viewport width to device width for mobile -->
<meta name="viewport" content="width=device-width" />
<title>欣心顾客数据中心</title>
<link rel="stylesheet" href="/ls/css/common.css">
<s:include value="/jsps/common/head.jsp" />

</head>
<body>
	<%@ include file="/jsps/common/brand.jsp" %>
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container" id ="userModeContainer">
			<div id="resetPasswordDialog" style="display : none;">
				<form class="form-wrapper">
					<input type="text" id="search" placeholder="请输入新的密码" required data-bind="value : newPasswordToReset"> 
					<a type="password" class="small blue button" data-bind="click : resetPassword"> <i class="icon-circle-arrow-right"></i>提交</a>
				</form>
			</div>
			<div class="content" id="userRolesManagementDialog" style="display : none;">
				<div data-bind="foreach : allRoles">
					<label class="input-checkbox" for="employeeProblem">
						<input class="icheckbox" type="checkbox" name="userRoleInput" data-bind="value : name, click : $root.updateUserRole, checked : $root.selectedUserRoles"/> <span
															data-bind="text : description"></span>
					</label>
				</div>
			</div>
			<div class="row">
				<div class="row">
					<div class="app-wrapper ui-corner-top">
							<div class="blue module ui-corner-top clearfix">
								<h2>用户列表</h2>
								<h2 class="right">
									<a class="small white button" data-bind="click : $root.openUserManagementDialog">创建新用户</a>
								</h2>
							</div>
							<div class="content">
								<div class="row">
									<table class="infoTable">
										<thead>
											<tr>
												<th>编号</th>
												<th>姓名</th>
												<th>账号</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody data-bind="foreach : users">
											<tr>
												<td style="text-align: center" data-bind="text : id"></td>
												<td style="text-align: center" data-bind="text : name"></td>
												<td style="text-align: center" data-bind="text : username"></td>
												<td style="text-align: center">
												
													<span data-bind="visible : active">激活</span>	
													<span data-bind="visible : !active">关闭</span>											
												</td>
												<td style="text-align: center">
													<a title="分配角色" data-bind="click : $root.openAssignRolesDialog" style="margin-left : 10px;" href="#"><i class="icon-user small icon-blue"></i></a>
													<a title="重置密码" data-bind="click : $root.openResetPasswordDialog" style="margin-left : 10px;" href="#"><i class="icon-pencil small icon-orange"></i></a>
													<a title="关闭用户" data-bind="click : $root.disactiveUser" style="margin-left : 10px;" href="#"><i class="icon-trash small icon-red"></i></a>
												</td>
											</tr>
										</tbody>
									</table>
									</div>
									<br>
							</div>
						</div>
					</div>
				<div id="userManagementDialog" class="content" title="用户管理" style="display: none;" data-bind="with : selectedUser">
					<div class="row">
							<label>姓名</label>
							<input id="userNameInput" type="text" class="addon-postfix" placeholder="请输入姓名" data-bind="value : name" />
					</div>
					<div class="row">
							<label>用户名</label>
							<input type="text" class="addon-postfix" placeholder="请输入用户名" data-bind="value : username" />
					</div>
					<div class="row">
							<label>密码</label>
							<input type="password" class="addon-postfix" placeholder="请输入密码" data-bind="value : password" />
					</div>
				</div>
			</div>
			</div>
	</section>

	<s:include value="/jsps/common/footer.jsp" />
	<script src="/ls/js/User.js"></script>
	<script>
		$(document).ready( function() {
			var Role = function() {
				var self = this;
				self.id = '';
				self.name = '';
				self.description = '';
			};
					var UserModel = function() {
						var self = this;
						self.userName = ko.observable('');
						self.users = ko.observableArray([]);
						self.selectedUser = ko.observable(new User());
						self.newPasswordToReset = ko.observable('');
						self.allRoles = ko.observable([]);
						
						$.ajax({
							url : 'getAllRoles.ls',
							success : function(data) {
								self.allRoles(data);
							}
						});
						
						self.updateUserRole = function(item, event) {
							$.ajax({
								url : 'updateUserRole.ls',
								method : 'POST',
								data : {
										roleJson : JSON.stringify(item),
										checkedOrNot : $(event.target).is(':checked'),
										userJson : JSON.stringify(self.selectedUser())
								},
								success : function(data) {
									
									if (isOK(data)) {
										
										success();
										self.loadAllUsers();
										
									} else {
										fail();
									}
								}
							});
							return true;
						};
						self.selectedUserRoles = ko.observableArray([]);
						
						self.openAssignRolesDialog = function(item, event) {
							$('#userRolesManagementDialog').dialog({
								modal : true,
								width : 500,
								height : 300,
								open : function(e) {
									changeButtonStyleForPopup(e);
								},
								
								buttons : {
									'关闭窗口' : function() {
										self.closeDialog('userRolesManagementDialog');
									}
								}
							});
							
							self.selectedUser(item);
							self.selectedUserRoles([]);
							$.each(item.roles, function(index, role) {
								self.selectedUserRoles.push(role.name);
							});
						};
						
						self.assignRoles = function(item, event) {
							
						};
						
						self.disactiveUser = function(item, event) {
							if (window.confirm('你真的确定要关闭这个用户吗？')) {
								
								$.ajax({
									url : 'disactiveUser.ls',
									method : 'POST',
									data : {
											userJson : JSON.stringify(item)
									},
									success : function(data) {
										
										if (isOK(data)) {
											
											success("Good Job!");
											self.loadAllUsers();
											
										} else {
											fail("Bad.");
										}
									}
								});
							}
						};
						
						self.resetPassword = function() {
							$.ajax({                        
								  url: 'resetPassword.ls',
								  data: { newPasswordToReset : self.newPasswordToReset() , userJson : JSON.stringify(self.selectedUser()) },
								  type : 'POST',
								  success: function(data) {  
									  
									  handleStanderdResponse(data);
									  self.selectedUser(new User());
									  self.loadAllUsers();
									  self.closeDialog('resetPasswordDialog');
								  }
								});
						};
						
						self.openResetPasswordDialog = function(item, event) {
							self.selectedUser(item);
							
							$('#resetPasswordDialog').dialog({
								modal : true,
								dialogClass : 'noTitle',
								height : 'auto',
								width : 'auto',
								maxHeight: 'auto',
								maxWidth:'auto',
								minHeight:'auto',
								minWidth:'auto',
								position : ['center', 200]
							});
						};
						
						self.closeDialog = function(id) {
							$('#' + id).dialog("close");
						};
						self.openUserManagementDialog = function() {
							self.selectedUser(new User());
							
							$('#userManagementDialog').dialog({
								modal : true,
								width : 500,
								height : 300,
								open : function(e) {
									changeButtonStyleForPopup(e);
								},
								
								buttons : {
									'保存用户' : function() {
										self.saveUser();
									},
									'关闭窗口' : function() {
										self.closeDialog('userManagementDialog');
									}
								}
							});
						};
						
						self.searchUser = function() {
							$.ajax({	url : 'ajaxFindUser.ls',
										data : {
											userName : self.userName()
										},
										success : function(data) {
											self.users(data);
										}
									});
						};
						
						self.loadUserAccouts = function() {
							//apply country auto-complete 
							$.ajax({                        
								  url: 'getAllUserAccounts.ls',
								  async: false,      
								  success: function(data) {   
									  $("#userNameInput").autocomplete({ source: data, minLength: 2 });
								  }
								});
						};
						
						self.loadAllUsers = function() {
							//load all
							$.ajax({                        
								  url: 'loadAllUsers.ls',
								  success: function(data) {   
									  	self.users(data);

								  }
								});
						};
						
						self.saveUser = function() {
							var currentUser = self.selectedUser();
							if (!currentUser.name) {
								fail("请输入用户姓名");
								return;
							}
							if (!currentUser.username) {
								fail("请输入用户账号");
								return;
							}
							if (!currentUser.password) {
								fail("请输入密码");
								return;
							}
							$.ajax({                        
								  url: 'createUser.ls',
								  data: { userJson : JSON.stringify(currentUser) },
								  type : 'POST',
								  success: function(data) {  
									  
									  handleStanderdResponse(data);
									  self.selectedUser(new User());
									  self.loadAllUsers();
									  self.closeDialog('userManagementDialog');
								  }
								});
						};
					};
					var model = new UserModel();
					model.loadAllUsers();
					
					var $userModeContainer = $("#userModeContainer")[0];
					ko.applyBindings(model, userModeContainer);
					
				});
		
		function activeCurrentMenuItem() {
			$('#userManager').addClass('active');
		}
	</script>
</body>
</html>
