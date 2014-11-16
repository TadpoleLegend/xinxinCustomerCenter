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
<title>Data Management</title>
<link rel="stylesheet" href="/ls/css/jquery.raty.css">
<link rel="stylesheet" href="/ls/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="/ls/css/flat-ui.css">
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
		<div class="container" id="container">
			<div class="row">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>搜索</h2>
					</div>
					<div class="content">
					
						<div class="row">
							<div class="three columns">
								<label>省/直辖市</label>
								<select data-bind="options: provinces, optionsCaption: '全部', optionsText: 'name', optionsValue: 'id', value: selectedProvince, valueAllowUnset: true"></select>
							</div>
							<div class="three columns">
								<label>市</label>
								<select data-bind="options: cities, optionsCaption: '全部', optionsText: 'name', optionsValue: 'id', value: selectedCity, valueAllowUnset: true"></select>
							</div>
							
							<div class="three columns">
							</div>
							<div class="three columns">
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="three columns">
								<label>公司名称</label>
								<input type="text" class="addon-postfix" data-bind = "value : seachCompany"/>
							</div>
							<div class="three columns">
								<label>联系人</label>
								<input type="text" class="addon-postfix" data-bind = "value : searchContactor"/>
							</div>
							
							<div class="three columns">
								<label>所在区</label>
								<input type="text" class="addon-postfix" data-bind = "value : searchDistinct"/>
							</div>
							<div class="three columns">
								<label>星级</label>
								<div id="starInput" data-bind="attr: { 'starInput' : starInput }"></div>
								<input type="checkbox" data-bind="checked : allStar"> 包含所有星级
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="six columns centered">
								<a class="small blue button" href="#" data-bind="click : searchCompany" > 搜索符合条件的客户 </a>
							</div>
						</div>
					</div>
				</div>

				<div class="app-wrapper ui-corner-top" id="companyList">
					<div class="blue module ui-corner-top clearfix">
						<h2>客户列表</h2>
					</div>
					<div class="content">
						<div class="row">
							共找到<label class="green label" data-bind="text: totalPagesCount"></label> 波 ， <label class="yellow label" data-bind="text: totalCompanyCount"></label>个客户
						</div>
						<br>
						<ul class="smartlist nice" data-bind="foreach: companyList">
							<li>
								<div class="row">
									<label class="input-checkbox">
										<div class="row">
											<div class="four columns text-center">
												<a style="margin-left: 20px;" data-bind="click:$root.showDetail"><h5><b data-bind="text : name"></b></h5></a>
											</div>
											<div class="one columns">
												<b data-bind="text : contractor"></b>
											</div>
											<div class="three columns">
												<label class="input-checkbox"> <img style="margin-left:45px" alt="电话号码" data-bind="attr: { 'src' : phone_src }"></label>
											</div>
											<div class="two columns">
												<div class="star" data-bind="attr: { 'star' : star }"></div>
											</div>
											<div class="two columns">
												<div class="row">
													<a class="small blue button" data-bind="click : $root.trackCustomer" href="#">查看</a>
												</div>
											</div>
										</div>
									</label>
								</div>
							</li>
						</ul>
						<div class="row">
							<div class="three columns">
								
							</div>
							<div class="six columns">
								<a href="#" class="small blue button"  data-bind="click : lastPage, disable : currentIndex() > 1" >上一波客户</a>
								<label class="label yellow" data-bind="text : currentIndex"></label>
								<a href="#" class="small blue button" data-bind="click : nextPage">下一波客户</a>
							</div>
							<div class="three columns">
							</div>
						</div>
					</div>
				</div>
				<div id="selectedCompany" class="row" data-bind="with : selectedCompany" style="display: none;">
					<div class="app-wrapper ui-corner-top">
						<div class="gray module ui-corner-top clearfix">
							<h2>
								详细信息<span class="subheader line" data-bind="text : name"></span>
							</h2>
							<h2 class="right">
								<a class="small blue button" data-bind="click : $root.trackCustomer" href="#">返回客户列表</a>
							</h2>
						</div>
						<div class="content">
							<div class="row">
								<div class="four columns">
								</div>
								<div class="two columns">
									<div id="detailStar" class="star" data-bind="attr: { 'star' : star }"></div>
								</div>
								<div class="four columns">
									<a style="margin-left: 20px;" data-bind="click:$root.showDetail"><span data-bind="text : detailUrl"></span></a>
								</div>
								<div class="two columns">
									<a class="small green button" href="#">成为意向客户</a>
								</div>
							</div>
							<br>
							<div id="accordion">
								<h4>
									<a href="#">客户基本信息</a>
								</h4>
								<div>
								<div class="row">
								<div class="row">
									<div class='three columns'>
										<label>名称 </label><input type="text" data-bind="value : name" disabled="disabled">
									</div>
									<div class='one columns'>
										<label>区域 </label><input type="text" data-bind="value : distinct">
									</div>
									<div class='eight columns'>
										<label>地址 </label> <input type="text" data-bind="value : address">
									</div>
								</div>

								<div class="row">
									<div class='four columns'>
										<label>联系人 </label><input type="text" data-bind="value : contractor">
									</div>
									<div class='four columns'>
										<label>联系电话</label><label class="input-checkbox"> <img alt="电话号码" data-bind="attr: { 'src' : phone_src }">
										</label>
									</div>
									<div class='four columns'>
										<label>Email</label> <label>电子邮件</label><label class="input-checkbox"> <img alt="电子邮箱" data-bind="attr: { 'src' : email_src }">
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<label>公司简介</label>
								<textarea name="ex-textarea-4"></textarea>
							</div>
							<br>
							<h4>补充客户信息</h4>
							<br>
							<div class="row">
								<div class="three columns">
									<label>院长姓名</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>院长手机</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>院长电话</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>院长QQ或者微信号</label>
									<input type="text"/>
								</div>
							</div>
							<div class="row">
								<div class="three columns">
									<label>店个数</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>店长人数</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>顾问人数</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>床位数</label>
									<input type="text"/>
								</div>
							</div>
							<div class="row">
								<div class="three columns">
									<label>规模（平方）</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>去年业绩（万）</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>现平均多少万</label>
									<input type="text"/>
								</div>
								<div class="three columns">
									<label>院长年龄大概多少岁？</label>
									<input type="text"/>
								</div>
							</div>
								
								</div>

								<h4>
									<a href="#">问题归类</a>
								</h4>
								<div>
									<div class="row">
										<div class="four columns">
											<div data-bind="foreach : $root.allProblemsConstant">
												<label class="input-checkbox" for="ex-chx-a"> <input
													type="checkbox" name="ex-checkbox" /> <span
													data-bind="text : name, value : id"></span>
												</label>
											</div>
										</div>
										<div class="four columns">
											<div data-bind="foreach : $root.allProblemsConstant">
												<label class="input-checkbox" for="ex-chx-a"> <input
													type="checkbox" name="ex-checkbox" /> <span
													data-bind="text : name, value : id"></span>
												</label>
											</div>
										</div>
										<div class="four columns">
											<div data-bind="foreach : $root.allProblemsConstant">
												<label class="input-checkbox" for="ex-chx-a"> <input
													type="checkbox" name="ex-checkbox" /> <span
													data-bind="text : name, value : id"></span>
												</label>
											</div>
										</div>
									</div>

								</div>

								<h3>
									<a href="#">电话跟踪</a>
								</h3>
								<div>
									<div class="row">
										<ul class="smartlist nice">
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>跟踪日期</label> <input type="text"
																disabled="disabled" value="2013年4月25日" />
														</div>
														<div class="three columns">
															<label>约定下次电话时间</label> <input type="text"
																disabled="disabled" value="2013年4月26日" />
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
											</li>
											</label>
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>跟踪日期</label> <input type="text"
																disabled="disabled" value="2013年4月25日" />
														</div>
														<div class="three columns">
															<label>约定下次电话时间</label> <input type="text"
																disabled="disabled" value="2013年4月26日" />
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
											</li>
											</label>
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>跟踪日期</label> <input type="text"
																disabled="disabled" value="2013年4月25日" />
														</div>
														<div class="three columns">
															<label>约定下次电话时间</label> <input type="text"
																disabled="disabled" value="2013年4月26日" />
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
											</li>
											</label>
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>跟踪日期</label> <input type="text"
																disabled="disabled" value="2013年4月25日" />
														</div>
														<div class="three columns">
															<label>约定下次电话时间</label> <input type="text"
																disabled="disabled" value="2013年4月26日" />
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
											</li>
											</label>
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>跟踪日期</label> <input type="text"
																disabled="disabled" value="2013年4月25日" />
														</div>
														<div class="three columns">
															<label>约定下次电话时间</label> <input type="text"
																disabled="disabled" value="2013年4月26日" />
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
											</li>
											</label>
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>跟踪日期</label> <input type="text" value="" />
														</div>
														<div class="three columns">
															<label>约定下次电话时间</label> <input type="text" id="nextScheduleDate"/>
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
													<div>
														<a class="small blue button">保存电话记录</a>
													</div>
											</li>
											</label>
										</ul>
									</div>

								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<div id="resouceDetail"></div>
	<s:include value="/jsps/common/footer.jsp" />
	<script src="/ls/js/list.js"></script>
	<script src="/ls/js/jquery.raty.js"></script>
	<script>
		$(document).ready( function() {
					
					var Problem = function(id, name) {
						var self = this;
						
						self.id= id;
						self.name = name;
						self.checked = ko.observable(false);
						self.selectedItem = ko.observable('');
						
					};
					
					var Company = function(id, name, contractor, email, email_src, phone, phone_src, star, address, distinct, problems, detailUrl) {
						var self = this;
						
						self.id = id;
						self.name = name;
						self.contractor = contractor;
						self.email = email;
						self.email_src = email_src;
						self.phone = phone;
						self.phone_src = phone_src;
						self.star = star;
						self.address = address;
						self.distinct = distinct;
						self.problems = ko.observableArray(problems);
						self.detailUrl = detailUrl;
						self.selectedProblem = ko.observable('');
					};

					var Province = function(id, name, cities) {
						this.id = id;
						this.name = name;
						this.cities = cities;
					};
					
					var City = function(id, name) {
						this.id = id;
						this.name = name;
					};
					
					var CompanyModel = function() {
						var self = this;

						self.companyList = ko.observableArray([]);
						self.currentIndex = ko.observable(1);
						self.pageIndexToGo = ko.observable('');
						self.totalPagesCount = ko.observable('1');
						self.totalCompanyCount = ko.observable('0');
						self.starInput = ko.observable(0);
						self.distinctInput = ko.observable();
						self.seachCompany = ko.observable('');
						self.searchContactor =  ko.observable('');
						self.searchDistinct =  ko.observable('');
						self.allStar = ko.observable(true);
						self.allProblemsConstant = ko.observableArray([]);
						self.provinces = ko.observableArray([]);
						self.selectedProvince =  ko.observable('');
						self.selectedCompany = ko.observable(new Company());
						
						self.trackCustomer = function(item, event) {
							self.selectedCompany(item);
							
							$( "#selectedCompany" ).slideToggle();
							$( "#companyList" ).slideToggle();
							
							$('#detailStar').raty({
								  click: function(score, evt) {
									  	self.starInput(score);
									  }
								});
							$('#accordion').accordion({ heightStyle: "content"});
							
							$('#nextScheduleDate').datepicker();
						};
						self.cities = ko.computed(function() {
							var cityOptions;
							$.each(self.provinces(), function(i, n){
								if (n.id == self.selectedProvince()) {
									cityOptions =  n.cities;
								}
							});
							return cityOptions;
						});
						self.selectedCity = ko.observable('');
						
						self.init = function() {
							$('#starInput').raty({
								  click: function(score, evt) {
									  	self.starInput(score);
									  }
								});
							
							self.searchCompany();
							
							$.ajax({
								url : '/ls/findAllProblems.ls',
								success : function(data) {
									
									$.each(data, function(index, value) {

										self.allProblemsConstant.push(value);

									});
								}
							});
							
							$.ajax({
								url : '/ls/findAllProvinces.ls',
								success : function(data) {
									
									$.each(data, function(index, value) {

										//self.provinces.push(value.name);
										var cities = new Array();
										$.each(value.citys, function(i, n){
											var city = new City(n.id, n.name);
											cities.push(city);
										});
										var province = new Province(value.id, value.name, cities);
										self.provinces.push(province);
									});
								}
							});
						};
						
						self.lastPage = function() {
							
							self.currentIndex(self.currentIndex() - 1);
							self.searchCompany();
						};
						
						self.nextPage = function() {
							self.currentIndex(self.currentIndex() + 1);
							self.searchCompany();
						};
						
						self.detail = function(item, event) {
							$('.'+item.id).toggle('blind', {}, 200);
							//var $this = $(event.target);
							//$this.parent().parent().find('.companydetail').toggle('blind', {}, 200);
						};

						self.showDetail = function(item, event) {
							window.open(item.detailUrl, '_blank');
						};
						
						self.searchCompany = function() {
							
							$.ajax({
								url : '/ls/user/loadCompanyInPage.ls',
								data : {pageNumber : self.currentIndex(), 
										starInput : self.starInput(), 
										searchDistinct: self.searchDistinct(),
										seachCompany : self.seachCompany(), 
										searchContactor : self.searchContactor(),
										allStar : self.allStar(),
										cityId : self.selectedCity(),
										provinceId : self.selectedProvince()
										},
								success : function(data) {
									self.fillCompany(data);
								}
							});
						};
						
						self.fillCompany = function(data) {
							
							self.companyList.removeAll();

							$.each(data.elements, function(index, value) {
								var new_phone_src = "/ls/img/" + value.phoneSrc;
								var new_email_src = "/ls/img/" + value.emailSrc;
								
								var problems = new Array();
								
								$.each(value.problems, function(i, n) {
									problems.push(n.name);
								});
								
								var company = new Company(value.id, value.name, value.contactor, value.email, new_email_src, value.phone, new_phone_src, value.star, value.address, value.area, problems, value.fEurl);

								self.companyList.push(company);

								$('.star').raty({
									score : function() {
										return $(this).attr('star');
									}
								});
							});
							
							self.totalCompanyCount(data.total);
							self.totalPagesCount(data.totalPages);
						};
						
						self.findProblemInItem = function(array, id) {
							var found = false;
							$.each(array, function(i, v) {
								if (v.id == id) {
									found = true;
								}
							});
							
							return found;
						};
						
						self.addProblem = function(item, event) {
							console.debug(item);
							console.debug(item.selectedProblem());
						};
						
						self.updateProblem = function(item, event) {
							
						//	self.companyList()[0].problems().push(item);
							
						//	if (event.stopPropagation) {event.stopPropagation();}
							
						//	if ($(event.target).is(':selected')) {
						//		$(event.target).removeAttr('checked');
						//		
						//	} else {
						//		$(event.target).attr('checked', 'checked');
						//	}
							
							return true;
						};
						
					};
					
					var model = new CompanyModel();
					model.init();
					var $container = $("#container")[0];
					ko.applyBindings(model, $container);

				});
	</script>
</body>
</html>
