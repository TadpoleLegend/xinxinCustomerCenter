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
		<div class="container">
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
												<td style="text-align: center" data-bind="text : applyingDate"></td>
												<td style="text-align: center">
													<a title="��׼" data-bind="click : $root.checkApplyingCustomer" style="margin-left : 10px;" href="#"><i class="icon-user small icon-blue"></i>��׼</a>
													<a title="��׼" data-bind="click : $root.approveCustomer" style="margin-left : 10px;" href="#"><i class="icon-user small icon-blue"></i>��׼</a>
													<a title="�ܾ�" data-bind="click : $root.rejectCustomer" style="margin-left : 10px;" href="#"><i class="icon-pencil small icon-blue"></i>�ܾ�</a>
												</td>
											</tr>
										</tbody>
									</table>
									</div>
									<br>
							</div>
						</div>
					</div>
			</div>
	</section>

	<s:include value="/jsps/common/footer.jsp" />
	<script>
		$(document).ready( function() {
			
					var ApplyCustomerMode = function() {
						var self = this;
						self.applyingList = ko.observableArray([]);
						
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
					
					var model = new ApplyCustomerMode();
					ko.applyBindings(model);
					
				});
	</script>
</body>
</html>