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
<title>Willing customer Management</title>
<link rel="stylesheet" href="/ls/css/common.css">
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
		<div class="container" id="applyCustomerModeContainer">
				<div class="row">
					<div class="app-wrapper ui-corner-top">
							<div class="blue module ui-corner-top clearfix">
								<h2>�����б�</h2>
							</div>
							<div class="content">
								<div class="row">
									<table class="infoTable">
										<thead>
											<tr>
												<th style="text-align: center">��˾���</th>
												<th style="text-align: center">��˾����</th>
												<th style="text-align: center">Ժ������</th>
												<th style="text-align: center">������</th>
												<th style="text-align: center">״̬</th>
												<th style="text-align: center">��������</th>
												<th style="text-align: center">����</th>
											</tr>
										</thead>
										<tbody data-bind="foreach : applyingList">
											<tr>
												<td style="text-align: center" data-bind="text : companyId"></td>
												<td style="text-align: center" data-bind="text : companyName"></td>
												<td style="text-align: center" data-bind="text : bossName"></td>
												<td style="text-align: center" data-bind="text : applyerName"></td>
												<td style="text-align: center">
													<span data-bind="visible: status == 10 ">
														��������
													</span>
													<span data-bind="visible: status == 20 ">
														���ͨ��
													</span>
													<span data-bind="visible: status == 30 ">
														δͨ�����
													</span>
												</td>
												<td style="text-align: center" data-bind="text : applyingDate"></td>
												<td style="text-align: center">
													<a title="���" class="tiny blue button" data-bind="click : $root.checkApplyingCustomer" style="margin-left : 5px;" href="#">���</a>
													<a title="��׼" class="tiny white button" data-bind="click : $root.approveCustomer" style="margin-left : 5px;" href="#">��׼</a>
													<a title="�ܾ�" class="tiny red button" data-bind="click : $root.rejectCustomer" style="margin-left : 5px;" href="#">�ܾ�</a>
												</td>
											</tr>
										</tbody>
									</table>
									</div>
									<br>
									<div id="checkDuplicationDialog" data-bind="with : willingCustomerCheckResult" style="display : none;" title="�����">
										<label class="green label"> ��ʾ �� ��������ǹ�˾����б�</label>
										<div class="row">
											<label>
												�������: 
											</label>
											<textarea data-bind="text:bossNameResult"></textarea>
										</div>
										<div class="row">
											<label>
												Ժ���ֻ�������:
											</label>
											<textarea data-bind="text:bossMobileResult"></textarea>
										</div>
										<div class="row">
											<label>
												��˾���Ƽ��:
											</label>
											<textarea data-bind="text:companyNameResult"></textarea>
										</div>
									</div>
							</div>
						</div>
					</div>
			</div>
	</section>

	<s:include value="/jsps/common/footer.jsp" />
	<script>
		$(document).ready( function() {
					var WillCustomerCheckResult = function(){
						var self = this;
						self.bossNameResult = '';
						self.bossMobileResult = '';
						self.companyNameResult = '';
					};
					
					var ApplyCustomerMode = function() {
						var self = this;
						self.applyingList = ko.observableArray([]);
						self.willingCustomerCheckResult = ko.observable(new WillCustomerCheckResult());
						
						self.getAllApplyingList = function() {
							$.ajax({
								url : 'getAllApplyingList.ls',
								success : function(data) {
									self.applyingList(data);
								}
							});
						};
						self.getAllApplyingList();
						
						self.checkApplyingCustomer = function(item, event) {
							$.ajax({
								url : 'checkApplyingCustomer.ls',
								method : 'POST',
								data : {
										applyingCustomerJson : JSON.stringify(item)
								},
								success : function(data) {
									
									handleStanderdResponse(data);
									
									if(isOK(data)) {
										self.willingCustomerCheckResult(data.object);
										$('#checkDuplicationDialog').dialog({
											modal : true,
											width : 500,
											height : 500,
											open : function(e) {
												changeButtonStyleForPopup(e);
											},
											
											buttons : {
												
												'�رմ���' : function() {
													closeDialog('checkDuplicationDialog');
												}
											}
										});
									}
								}
							});
						};
						
						self.approveCustomer = function(item, event) {
							$.ajax({
								url : 'approveCustomer.ls',
								method : 'POST',
								data : {
										applyingCustomerJson : JSON.stringify(item)
								},
								success : function(data) {
									
									handleStanderdResponse(data);
									self.getAllApplyingList();
								}
							});
						};
						
						self.rejectCustomer = function(item, event) {
							$.ajax({
								url : 'rejectCustomer.ls',
								method : 'POST',
								data : {
										applyingCustomerJson : JSON.stringify(item)
								},
								success : function(data) {
									handleStanderdResponse(data);
									self.getAllApplyingList();
								}
							});
						};
					};
					
					var applyCustomerMode = new ApplyCustomerMode();
					var $applyCustomerModeContainer = $("#applyCustomerModeContainer")[0];
					ko.applyBindings(applyCustomerMode, applyCustomerModeContainer);
					
				});
	</script>
</body>
</html>
