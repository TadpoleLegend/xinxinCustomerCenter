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
<s:include value="/jsps/common/head.jsp" />
</head>
<body>
	<s:include value="/jsps/common/brand.jsp" />
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container" id="configurationModelContainer">
			<div id="problemDialog" style="display: none;" title="问题标签" data-bind="with : selectedProblem">

				<form id="problemForm">
					<div class="row">
						<label class="label"> 问题编号 ：</label> <span data-bind="text : id"></span>
					</div>
					<br>
					<hr>
					<div class="row">
						<label class="required"> 问题类别</label> 
						<select data-bind="options: $root.problemCategories,
                      											   optionsText: 'optionText',
                       											   value: categoryCode,
                       											   optionsValue : 'optionValue',
                       											   selectedOption : categoryCode,
                       											   optionsCaption: '全部'"
                       									class="required">
                       	</select>
					</div>
					<div class="row">
						<label> 问题描述</label> <input type="text" data-bind="value : name" class="required">
					</div>
				</form>
				<div class="row">
					<a href="#" class="small blue button"
							data-bind="click : $root.saveProblem" title="">保存记录</a>
					<a href="#" class="small blue button"
							data-bind="click : $root.closeDialog" title="">关闭窗口</a>		
				</div>
			</div>
			<div class="row">

				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>公司问题配置</h2>
						<h2 class="right"> <a class=" tiny green button" href="#" data-bind="click : $root.openProblemDialog">创建新问题标签</a></h2>
					</div>
					<div class="content">
						<table class="display compact" id="problemListTable">
							<thead>
								<tr>
									<th class="text-center">编号</th>
									<th class="text-center">类别</th>
									<th class="text-center">内容</th>
									<th class="text-center">修改/删除</th>
								</tr>
							</thead>
							<tbody data-bind="foreach: problems">
								<tr>
									<td class="text-center"><span data-bind="text: id"></span></td>
									<td class="text-center"><span data-bind="text: category"></span></td>
									<td class="text-center"><span data-bind="text: name"></span></td>
									<td class="text-center"><a class=" tiny green button"
										href="#" data-bind="click : $root.openProblemDialogToEdit">编辑</a> <a
										class=" tiny red button" href="#"
										data-bind="click : $root.deleteProblem">删除</a></td>
								</tr>
							</tbody>
						</table>
						<br>
					</div>
				</div>
			</div>
		</div>
	</section>
	<s:include value="/jsps/common/footer.jsp" />
	<script src="/ls/js/User.js"></script>
	<script>
		$(document).ready( function() {
					
					$('#problemListTable').dataTable({
						"paging" : false,
						"ordering" : false,
						"info" : false,
						"searching" : false
					});

					var Problem = function(id, name, category) {
						var self = this;

						self.id = id;
						self.name = name;
						self.category = category;
						self.categoryCode = '';
					};

					var ConfigurationModel = function() {

						var self = this;

						self.newProblenName = ko.observable();
						self.problems = ko.observableArray([]);
						self.newType = ko.observable();
						self.problemCategories = ko.observableArray([]);
						self.selectedProblem = ko.observable(new Problem());

						self.categoryList = [];
						
						self.findAllProblems = function() {
							$.ajax({
								url : 'getAllProblems.ls',
								success : function(data) {
									self.problems(data);
								}
							});
						};
						
						$.ajax({
							url : '/ls/findDropDownDataSouce.ls',
							data : {identityType : 'problem_category'},
							success : function(data) {
								self.problemCategories(data);
							}
						});
						
						self.deleteProblem = function(item, event) {
							
							if (window.confirm('你确定要删除这个问题选项吗？')) {
								$.ajax({
									url : 'deleteProblem.ls',
									data : {
										problem : JSON.stringify(item)
									},
									success : function(data) {
										if (data && data.type =='SUCCESS') {
											self.findAllProblems();
											success();
										} else {
											fail();
										}
									}
								});
							}
						};

						self.saveProblem = function() {
								console.debug(self.selectedProblem());
							if ($('#problemForm').valid()) {
								
								$.ajax({
									url : 'saveProblem.ls',
									method : 'POST',
									data : {
										problem : JSON.stringify(self.selectedProblem())
									},
									success : function(data) {
										
										if (data) {
											self.findAllProblems();
											
											success('已成功存储');
											
											self.closeDialog();
											
										} else {
											fail('存储失败');
										}
									}
								});
							}
						};

						self.openProblemDialog = function() {
							$('#problemDialog').dialog({
								modal : true,
								width : 640,
								height : 580,
								open : function(e) {
									changeButtonStyleForPopup(e);
								}
							});
						
						};
						
						self.closeDialog = function() {
							$('#problemDialog').dialog("close");
							self.selectedProblem(new Problem());
						};
						
						self.openProblemDialogToEdit = function(item, event) {
							self.selectedProblem(item);
							self.openProblemDialog();
						}
					};

					var configurationModel = new ConfigurationModel();
					configurationModel.findAllProblems();
					
					var $configurationModelContainer = $("#configurationModelContainer")[0];
					ko.applyBindings(configurationModel, configurationModelContainer);
					
				});
		function activeCurrentMenuItem() {
			$('#systemConfiguration').addClass('active');
		}
	</script>
</body>
</html>
