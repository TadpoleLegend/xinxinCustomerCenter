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
		<div class="container" id="grabModelContainer">
			<div class="row">
				<div class="three columns">
					<div class="app-wrapper ui-corner-top">
						<div class="blue module ui-corner-top clearfix">
							<h2>我的区域</h2>
						</div>
						<div>
							
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
				<div class="nine columns">
					<div class="app-wrapper ui-corner-top">
						<div class="blue module ui-corner-top clearfix">
							<h2>数据资源</h2>
							<h2 class="right">
								<a class="small white button" href="#" data-bind="click : searchUrlResources.bind($data, '138')">搜索138美容网</a>
								<a class="small white button" href="#" data-bind="click : searchUrlResources.bind($data, '58')">搜索58同城</a>
								<a class="small white button" href="#" data-bind="click : searchUrlResources.bind($data, 'gj')">搜索赶集网</a>
							</h2>
						</div>
						<div class="content">
							<div class="row">
								<div class="nine columns">
									（最多显示500条资源记录，采集过程可能需要很长时间，请耐心等待....）
								</div>
								<div class="three columns">
									<a class="small blue button" href="#" data-bind="click : grabSelected">全部抓取</a>
								</div>
							</div>
							<table class="infoTable">
									<thead>
										<tr>
											<th class="text-center">公司名</th>
											<th class="text-center">资源编号</th>
											<th class="text-center">链接</th>
										</tr>
									</thead>
									<tbody data-bind="foreach: otePreviewList">
										<tr>
											<td class="text-center"><span data-bind="text: name"></span></td>
											<td class="text-center"><span data-bind="text: companyId"></span></td>
											<td class="text-center"><span data-bind="text: url"></span></td>
										</tr>

									</tbody>
								</table>
								<br>
						</div>
					</div>
				</div>
				
				</div>
			</div>
			
			<div class="row" style="display : none;">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>抓取结果</h2>
					</div>
					<div class="content">
						<ul class="smartlist nice" data-bind="foreach: companyList">
							<li><label class="input-checkbox">
									<div class="row">
										<div class="one columns"></div>
										<div class="four columns text-center">
											<h5>
												<b data-bind="text : name"></b>
											</h5>
										</div>
										<div class="one columns">
											<b data-bind="text : distinct"></b>
										</div>
										<div class="six columns">
											<a data-bind="text : detailUrl, attr: { 'href' : detailUrl }"></a>
										</div>
									</div>
							</label></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	<s:include value="/jsps/common/footer.jsp" />
	<script src="/ls/js/jstree.min.js"></script>
	<script>
		$(document).ready(function() {
			
			var Company = function(name, distinct, detailUrl) {
				self.name = name;
				self.detailUrl = detailUrl;
				self.distinct = distinct;

			};
			
			var City = function(name, url) {
				self.name = name;
				self.url = url;

			};
			
			var GrabModel = function() {
				var self = this;
				self.url = ko.observable("");
				self.companyList = ko.observableArray([]);
				self.selectedURLs = ko.observableArray([]);
				self.lastPublishDate = ko.observable('');
				self.userCities = ko.observableArray([]);
				self.otePreviewList = ko.observableArray([]);
				self.selectedCities = ko.observableArray([]);
				self.totalLength = ko.observable('');
				self.datasourceType = ko.observable('');
				
				self.searchUrlResources = function(type, item, event ) {
					
					var url = '';
					if (type == '58') {
						url = "load58PreviewList.ls";
					} else if (type == '138') {
						url = "load138PreviewList.ls";
					} else if (type =='gj') {
						url = "loadGanjiPreviewList.ls";
					} else {
						fail('操作错误');
						return;
					}
					
					var selectedIds = $.jstree.reference('#userCityTree').get_selected();
					$.ajax({
						data : {
							selectedIds : JSON.stringify(selectedIds)
						},
						url : url,
						success: function(data) {
							if(data) {
								self.otePreviewList(data);
								self.totalLength(self.otePreviewList().length);
							} else {
								success("没有发现资源数据");
							}
							
							self.datasourceType(type);
						}
					});
				};
				self.filterUrlsWithCity = function() {
					$.ajax({
						data : {
							cityIdsHtml : JSON.stringify(self.selectedCities())
						},
						url : 'load138PreviewList.ls',
						success: function(data) {
							self.otePreviewList(data);
							self.totalLength(self.otePreviewList().length);
						}
					});
				};
				self.createJstree = function() {
					
					$('#userCityTree').jstree({
							plugins : ["checkbox"], "checkbox" : {
							      "keep_selected_style" : false
						    },
					});
				};
					
				self.initCities = function(city) {
					
					$.ajax({
						url : '/ls/user/findAllProvinces.ls',
						success: function(data) {
							self.userCities(data);
							self.createJstree();
						}
					});
					
				};
				
				self.grab = function() {
						$.ajax({url : '/ls/grab/grabCompanyIndexPage.ls',
								data : {url : encodeURIComponent(self.url())},
								success: function(data) {
									self.companyList.removeAll();

									$.each(data, function(index, value) {
										var company = new Company( value.name, value.distinct, value.fEurl );

										self.companyList.push(company);

										});
								}
								});
					
					};
					
				self.grabSelected = function() {
						var selectedIds = $.jstree.reference('#userCityTree').get_selected();
						
						$.ajax({
							data : {
								selectedIds : JSON.stringify(selectedIds),
								datasourceType : self.datasourceType()
							},
							url : 'grabSelectedCities.ls',
							success: function(data) {
								handleStanderdResponse(data);
							}
						});
					};

				};

			var grabModel = new GrabModel();
			grabModel.initCities();
			
			var $grabModelContainer = $('#grabModelContainer')[0];
			ko.applyBindings(grabModel, $grabModelContainer);
		});
		

		function activeCurrentMenuItem() {
			$('#dataCollection').addClass('active');
		}
	</script>
</body>
</html>
