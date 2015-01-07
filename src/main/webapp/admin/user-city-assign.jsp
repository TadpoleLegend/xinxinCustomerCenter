<%@ page contentType="text/html;charset=utf8"%>
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
<link rel="stylesheet" href="/ls/css/jstree-style.css">
<s:include value="/jsps/common/head.jsp" />

</head>
<body>
	<s:include value="/jsps/common/brand.jsp" />
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container" id ="usercityModelContainer">
			<div class="row">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>搜索</h2>
					</div>
					<div class="content">
						<div class="row">
							<div class="three columns"></div>
							<div class="six columns">
								<div class="row collapse">
									<div class="eight columns">
										<input id="userNameInput" type="text" class="addon-postfix" placeholder="请输入用户姓名" data-bind="value : userName" />
									</div>
									<div class="four columns">
										<button class="small nice blue button postfix" data-bind="click : searchUser">搜索</button>
									</div>
								</div>
							</div>
							<div class="three columns"></div>
							<br>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="six columns">
						<div class="app-wrapper ui-corner-top">
							<div class="blue module ui-corner-top clearfix">
								<h2>用户列表</h2>
							</div>
							<div class="content">
								<div class="row">
									
										<table class="infoTable">
										<thead>
											<tr>
												<th>编号</th>
												<th>姓名</th>
												<th>账号</th>
												<th>操作</th>
											</tr>
										</thead>
											<tbody data-bind="foreach : users">
												<tr>
													<td style="text-align: center" data-bind="text : id"></td>
													<td style="text-align: center" data-bind="text : name"></td>
													<td style="text-align: center" data-bind="text : username"></td>
													<td style="text-align: center">
														<a title="分配城市" data-bind="click : $root.showAssignedCities" style="margin-left : 10px;" href="#"><i class="icon-key small icon-orange"></i></a>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								<br>
							</div>
						</div>
					</div>
					<div class="six columns">
							<div class="app-wrapper ui-corner-top">
							<div class="blue module ui-corner-top clearfix">
								<h2>城市分配</h2>
								<h2 class="right" data-bind="with : selectedUser">
									<span data-bind="text : name"></span>
								</h2>
							</div>
							<div class="content">
								<div class="row">
									<div id="userCityTree">
											<ul data-bind="foreach : userCities">
												<li data-jstree='{"opened":false, "icon":"icon-puzzle-piece small icon-blue"}' data-bind="attr : {id : 'province' + id }"> 
													<span datatype="province" data-bind="text : name"></span>
													<ul data-bind="foreach : citys" > <b data-bind="text : name"></b>
														<li data-bind="attr : {id : 'city' + id }" data-jstree='{"opened":false, "icon":"icon-screenshot small icon-blue"}'>
														<span datatype="city" data-bind="text : name, attr : {id : id}"></span></li>
													</ul>
												</li>
											</ul>
									</div>
									<br>
								</div>
							</div>
						</div>		
					</div>
				</div>
			</div>
			</div>
	</section>

	<s:include value="/jsps/common/footer.jsp" />
	<script src="/ls/js/User.js"></script>
	<script src="/ls/js/jstree.min.js"></script>
	<script>
		$(document).ready( function() {
			
					var UserModel = function() {
						var self = this;
						self.userName = ko.observable('');
						self.users = ko.observableArray([]);
						self.selectedUser = ko.observable(new User());
						self.userCities = ko.observableArray([]);						
						
						self.selectedCities = ko.observableArray([]);
						
						self.loadChina = function() {
							$.ajax({	
								url : 'loadChina.ls',
								success : function(data) {
									
									self.userCities(data);
									self.createJstree();
								}
							});
						};
						self.loadChina();
						
						self.showAssignedCities = function(item) {
							
							self.selectedUser(item);
							
							$.ajax({	
								url : 'showAssignedCities.ls',
								data : {
									userId : item.id
								},
								success : function(data) {
									
									$.jstree.reference('#userCityTree').deselect_all([false]);
									
									if (data) {
										$.each(data, function(index, value) {
											$.jstree.reference('#userCityTree').select_node(value, [true,false]);
										});
									}
								}
							});
						};
						
						self.createJstree = function() {
							
						$('#userCityTree').on('changed.jstree', function (e, data) {
						    var i, j, r = [];
						    for(i = 0, j = data.selected.length; i < j; i++) {
						      r.push(data.instance.get_node(data.selected[i]).text);
						    }
						    self.selectedCities(r);

						    self.assignOrCancelCity();

						  }).jstree({
								plugins : ["checkbox"], "checkbox" : {
								      "keep_selected_style" : false
							    },
							});
						};
						
						self.assignOrCancelCity = function() {
							
							if (!self.selectedUser() || !self.selectedUser().id ) {
								fail("未选择用户!");
								return;
							}
							
							$.ajax({
								url : 'updateUserCity.ls',
								method : 'POST',
								data : {
										selectedCities : JSON.stringify(self.selectedCities()),
										userId : self.selectedUser().id
								},
								success : function(data) {
									handleStanderdResponse(data);
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
							$.ajax({                        
								  url: 'getAllUserAccounts.ls',
								  async: false,      
								  success: function(data) {   
									  $("#userNameInput").autocomplete({ source: data, minLength: 2 });
								  }
								});
						};
						
						self.loadAllUsers = function() {
							$.ajax({                        
								  url: 'loadAllUsers.ls',
								  success: function(data) {   
									  	self.users(data);

								  }
								});
						};
					};
					
					var usercityModel = new UserModel();
					usercityModel.loadUserAccouts();
					usercityModel.loadAllUsers();
					
					var $usercityModelContainer = $("#usercityModelContainer")[0];
					ko.applyBindings(usercityModel, $usercityModelContainer);
				});
		
		function activeCurrentMenuItem() {
			$('#userCityAssign').addClass('active');
		}
	</script>
</body>
</html>
