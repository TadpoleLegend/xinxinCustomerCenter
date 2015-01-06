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
		<div class="container" id="singleGrabModelContainer">
			<div class="row">
			<div style="color: red">
				<s:fielderror />
			</div>
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>单页采集</h2>
						<h2 class="right">
						</h2>
					</div>
					<div class="content">
						<form id="urlInputForm">
							<div class="row">
								<div class="nine columns">
									<input id="urlInput" type="text" placeholder="请输入公司详细页面的链接地址" required data-bind="value : targetDetailUrl">
								</div>
								<div class="three columns">
									<a class="small blue button" href="#" data-bind="click : grabSinglePage">开始抓取</a>
								</div>
							</div>
						</form>
						<br>
						<div class="panel" data-bind="with : grabedNewCompany">
							<div class="row">
								<div class="four columns">
									<label class="right">编号</label>
								</div>
								<div class="eight columns">
									<span data-bind="text : id"></span>
								</div>
							</div>
							<div class="row">
								<div class="four columns">
									<label class="right">公司名称：</label>
								</div>
								<div class="eight columns">
									<span data-bind="text : name"></span>
								</div>
							</div>
							<div class="row">
								<div class="four columns">
									<label class="right">地址：</label>
								</div>
								<div class="eight columns">
									<span data-bind="text : address"></span>
								</div>
							</div>
							<div class="row">
								<div class="four columns">
									<label class="right">联系人：</label>
								</div>
								<div class="eight columns">
									<span data-bind="text : contactor"></span>
								</div>
							</div>
							<div class="row">
								<div class="four columns">
									<label class="right">联系手机：</label>
								</div>
								<div class="eight columns">
									<img alt="电话号码" data-bind="attr: { 'src' : mobilePhoneSrc }, visible : mobilePhoneSrc != '' ">
								</div>
							</div>
							<div class="row">
								<div class="four columns">
									<label class="right">简介：</label>
								</div>
								<div class="eight columns">
									<span data-bind="text : description"></span>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		</div>
	</section>
	<s:include value="/jsps/common/footer.jsp" />
	<script>
		$(document).ready(function() {
			
			var Company = function() {
				
				var self = this;
				
				self.id = '';
				self.name = '';
				self.contactor = '';
				self.email = '';
				self.emailSrc = '';
				self.phone = '';
				self.phoneSrc = '';
				self.mobilePhone = '';
				self.mobilePhoneSrc = '';
				self.star = '';
				self.address = '';
				self.distinct = '';
				self.oteUrl = '';
				self.provinceId = '';
				self.cityId = '';
				self.description = '';
				self.ganjiUrl = '';
				self.fEurl = '';
			};
			
			var SingleGrabModel = function() {
				var self = this;
				self.url = ko.observable('');
				self.targetDetailUrl = ko.observable('');
				self.companyList = ko.observableArray([]);
				self.totalLength = ko.observable('');
				self.grabedNewCompany = ko.observable(new Company());
				self.grabSinglePage = function(item, event ) {
					
					if(!$('#urlInput').val()) {
						fail("请输入链接地址！");
						$('#urlInput').focus();
						return;
					}
					
					$.ajax({
						data : {
							userInputTargetDetailUrl : self.targetDetailUrl()
						},
						url : "grabSinglePage.ls",
						success: function(data) {
							handleStanderdResponse(data);
							if (data && data.object) {
								self.grabedNewCompany(data.object);
							} else {
								self.grabedNewCompany(new Company());
							}
							
						}
					});
				};
				};

			var singleGrabModel = new SingleGrabModel();
			
			var $singleGrabModelContainer = $('#singleGrabModelContainer')[0];
			ko.applyBindings(singleGrabModel, $singleGrabModelContainer);
		});
		

		function activeCurrentMenuItem() {
			$('#singleGrab').addClass('active');
		}
	</script>
</body>
</html>
