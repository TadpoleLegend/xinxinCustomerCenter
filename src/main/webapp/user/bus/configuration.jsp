<%@ page contentType="text/html;charset=gb2312"%>
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
<title>Configuration</title>

<s:include value="/jsps/common/head.jsp" />
</head>
<body>
	<header id="brand">
		<div class="container">
			<div class="row">
				<div class="appname hide-on-phones"></div>
			</div>
		</div>
	</header>
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container">
			<div id="problemDialog" style="display: none;" title="�����ǩ" data-bind="with : selectedProblem">

				<form id="problemForm">
					<div class="row">
						<label class="label"> ������ ��</label> <span data-bind="text : id"></span>
					</div>
					<br>
					<hr>
					<div class="row">
						<label> �������</label> <input id="categoryInput" type="text" data-bind="value : category" class="required">
					</div>
					<div class="row">
						<label> ��������</label> <input type="text" data-bind="value : name" class="required">
					</div>
				</form>
				<div class="row">
					<a href="#" class="small blue button"
							data-bind="click : $root.saveProblem" title="">�����¼</a>
					<a href="#" class="small blue button"
							data-bind="click : $root.closeDialog" title="">�رմ���</a>		
				</div>
			</div>
			<div class="row">

				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>��˾��������</h2>
						<h2 class="right"> <a class=" tiny green button" href="#" data-bind="click : $root.openProblemDialog">�����������ǩ</a></h2>
					</div>
					<div class="content">
						<table class="display compact" id="problemListTable">
							<thead>
								<tr>
									<th class="text-center">���</th>
									<th class="text-center">���</th>
									<th class="text-center">����</th>
									<th class="text-center">�޸�/ɾ��</th>
								</tr>
							</thead>
							<tbody data-bind="foreach: problems">
								<tr>
									<td class="text-center"><span data-bind="text: id"></span></td>
									<td class="text-center"><span data-bind="text: category"></span></td>
									<td class="text-center"><span data-bind="text: name"></span></td>
									<td class="text-center"><a class=" tiny green button"
										href="#" data-bind="click : $root.openProblemDialogToEdit">�༭</a> <a
										class=" tiny red button" href="#"
										data-bind="click : $root.deleteProblem">ɾ��</a></td>
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
					var problemCategoryList = ['Ա������', '�˿�����', '��������'];
					
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
					};

					var ConfigurationModel = function() {

						var self = this;

						self.newProblenName = ko.observable();
						self.problems = ko.observableArray([]);
						self.newType = ko.observable();

						self.selectedProblem = ko.observable(new Problem());

						self.categoryList = [];
						
						self.findAllProblems = function() {
							$.ajax({
								url : '/ls/getAllProblems.ls',
								success : function(data) {
									self.problems(data);
								}
							});
						};

						self.deleteProblem = function(item, event) {
							
							if (window.confirm('��ȷ��Ҫɾ���������ѡ����')) {
								$.ajax({
									url : '/ls/deleteProblem.ls',
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
							
							if ($('#problemForm').valid()) {
								
								$.ajax({
									url : '/ls/saveProblem.ls',
									data : {
										problem : JSON.stringify(self.selectedProblem())
									},
									success : function(data) {
										
										if (data) {
											self.findAllProblems();
											
											success('�ѳɹ��洢');
											
											self.closeDialog();
											
										} else {
											fail('�洢ʧ��');
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
							
							$('#categoryInput').autocomplete({source : problemCategoryList, minLength: 0, 
								select: function( event, ui ) {
										self.selectedProblem().category = (ui.item.label);	
							}});
							
							$('#categoryInput').autocomplete("search", "");
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
					ko.applyBindings(configurationModel);
					
				});
	</script>
</body>
</html>
