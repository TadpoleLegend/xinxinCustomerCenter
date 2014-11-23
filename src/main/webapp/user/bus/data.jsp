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
		<div id="container">

			<div style="color: red">
				<s:fielderror />
			</div>
			
			<div class="row">
				<div id="searchWrapper" class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>����</h2>
					</div>
					<div class="content">

						<div class="row">
							<div class="three columns">
								<label>ʡ/ֱϽ��</label> <select
									data-bind="options: provinces, optionsCaption: 'ȫ��', optionsText: 'name', optionsValue: 'id', value: selectedProvince, valueAllowUnset: true"></select>
							</div>
							<div class="three columns">
								<label>��</label> <select
									data-bind="options: cities, optionsCaption: 'ȫ��', optionsText: 'name', optionsValue: 'id', value: selectedCity, valueAllowUnset: true"></select>
							</div>

							<div class="three columns"></div>
							<div class="three columns"></div>
						</div>
						<hr>
						<div class="row">
							<div class="three columns">
								<label>��˾����</label> <input type="text" class="addon-postfix"
									data-bind="value : seachCompany" />
							</div>
							<div class="three columns">
								<label>��ϵ��</label> <input type="text" class="addon-postfix"
									data-bind="value : searchContactor" />
							</div>

							<div class="three columns">
								<label>������</label> <input type="text" class="addon-postfix"
									data-bind="value : searchDistinct" />
							</div>
							<div class="three columns">
								<label>�Ǽ�</label>
								<div id="starInput"
									data-bind="attr: { 'starInput' : starInput }"></div>
								<input type="checkbox" data-bind="checked : allStar">
								���������Ǽ�
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="six columns centered">
								<a class="small blue button" href="#"
									data-bind="click : searchCompany"> �������������Ŀͻ� </a>
							</div>
						</div>
					</div>
				</div>

				<div class="app-wrapper ui-corner-top" id="companyList">
					<div class="blue module ui-corner-top clearfix">
						<h2>�ͻ��б�</h2>
					</div>
					<div class="content">
						<div class="row">
							���ҵ�<label class="green label" data-bind="text: totalPagesCount"></label>
							�� �� <label class="yellow label"
								data-bind="text: totalCompanyCount"></label>���ͻ�
						</div>
						<br>
						<ul class="smartlist nice" data-bind="foreach: companyList">
							<li>
								<div class="row">
									<label class="input-checkbox">
										<div class="row">
											<div class="four columns text-center">
												<a style="margin-left: 20px;"
													data-bind="click:$root.showDetail"><h5>
														<b data-bind="text : name"></b>
													</h5></a>
											</div>
											<div class="one columns">
												<b data-bind="text : contractor"></b>
											</div>
											<div class="three columns">
												<label class="input-checkbox"> <img
													style="margin-left: 45px" alt="�绰����"
													data-bind="attr: { 'src' : phone_src }"></label>
											</div>
											<div class="two columns">
												<div class="star listStar"
													data-bind="attr : {'star' : star, 'id': id}"></div>
											</div>
											<div class="two columns">
												<div class="row">
													<a class="small blue button"
														data-bind="click : $root.trackCustomer" href="#">�鿴</a>
												</div>
											</div>
										</div>
									</label>
								</div>
							</li>
						</ul>
						<div class="row">
							<div class="three columns"></div>
							<div class="six columns">
								<a href="#" class="small blue button"
									data-bind="click : lastPage, disable : currentIndex() > 1">��һ���ͻ�</a>
								<label class="label yellow" data-bind="text : currentIndex"></label>
								<a href="#" class="small blue button"
									data-bind="click : nextPage">��һ���ͻ�</a>
							</div>
							<div class="three columns"></div>
						</div>
					</div>
				</div>
				<div id="selectedCompany" class="row"
					data-bind="with : selectedCompany" style="display: none;">
					<div class="app-wrapper ui-corner-top">
						<div class="gray module ui-corner-top clearfix">
							<h2>
								��ϸ��Ϣ<span class="subheader line" data-bind="text : name"></span>
							</h2>
							<h2 class="right">
								<a class="small blue button"
									data-bind="click : $root.backToCustomerList" href="#">���ؿͻ��б�</a>
							</h2>
						</div>
						<div class="content">
							<div class="row">
								<div class="four columns text-center">
									<i class="icon-user xlarge"></i><b data-bind="text : status"></b>
								</div>
								<div class="two columns">
									<div id="detailStar" class="star"
										data-bind="attr : {'star' : star, 'companyId' : id}"></div>
								</div>
								<div class="four columns">
									<a style="margin-left: 20px;"
										data-bind="click:$root.showDetail"><span
										data-bind="text : detailUrl"></span></a>
								</div>
								<div class="two columns">
									<a class="small green button" href="#">��Ϊ����ͻ�</a>
								</div>
							</div>
							<br>
							<div id="accordion">
								<h4>
									<a href="#">�ͻ�������Ϣ</a>
								</h4>
								<div>
									<div class="row">
										<div class="row">
											<div class='three columns'>
												<label>���� </label><input type="text"
													data-bind="value : name" disabled="disabled">
											</div>
											<div class='one columns'>
												<label>���� </label><input type="text"
													data-bind="value : distinct">
											</div>
											<div class='eight columns'>
												<label>��ַ </label> <input type="text"
													data-bind="value : address">
											</div>
										</div>

										<div class="row">
											<div class='four columns'>
												<label>��ϵ�� </label><input type="text"
													data-bind="value : contractor">
											</div>
											<div class='four columns'>
												<label>��ϵ�绰</label><label class="input-checkbox"> <img
													alt="�绰����" data-bind="attr: { 'src' : phone_src }">
												</label>
											</div>
											<div class='four columns'>
												<label>Email</label> <label>�����ʼ�</label><label
													class="input-checkbox"> <img alt="��������"
													data-bind="attr: { 'src' : email_src }">
												</label>
											</div>
										</div>
									</div>
									<div class="row">
										<label>��˾���</label>
										<textarea name="ex-textarea-4"></textarea>
									</div>
									<br>
									<h4>����ͻ���Ϣ</h4>
									<br>
									<div id="addtionalCompanyInformation"
										data-bind="with : $root.addtion">
										<div class="row">
											<div class="three columns">
												<label>Ժ������</label> <input type="text"
													data-bind="value : bossName" />
											</div>
											<div class="three columns">
												<label>Ժ���ֻ�</label> <input class="number" type="text"
													data-bind="value : bossMobile" />
											</div>
											<div class="three columns">
												<label>Ժ���绰</label> <input class="number" type="text"
													data-bind="value : bossTelephone" />
											</div>
											<div class="three columns">
												<label>Ժ��QQ����΢�ź�</label> <input type="text"
													data-bind="value : bossQQorWechat" />
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>�����</label> <input class="number" type="text"
													data-bind="value : branchCount" />
											</div>
											<div class="three columns">
												<label>�곤����</label> <input class="number" type="text"
													data-bind="value : branchManagerCount" />
											</div>
											<div class="three columns">
												<label>��������</label> <input class="number" type="text"
													data-bind="value : branchConsultantCount" />
											</div>
											<div class="three columns">
												<label>��λ��</label> <input class="number" type="text"
													data-bind="value : bedCount" />
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>��ģ��ƽ����</label> <input class="number" type="text"
													data-bind="value : acreage" />
											</div>
											<div class="three columns">
												<label>ȥ��ҵ������</label> <input class="number" type="text"
													data-bind="value : lastYearIncome" />
											</div>
											<div class="three columns">
												<label>��ƽ��������</label> <input class="number" type="text"
													data-bind="value : thisYearMonthlyIncome" />
											</div>
											<div class="three columns">
												<label>Ժ�������Ŷ����ꣿ</label> <input class="number" type="text"
													data-bind="value : bossAge" />
											</div>
										</div>
										<div class="row">
											<a class="small blue button"
												data-bind="click : $root.saveAddition">����</a>

										</div>
									</div>
								</div>

								<h4>
									<a href="#">�������</a>
								</h4>
								<div>
									<div class="row">
										<div class="four columns">
											<div class="app-wrapper ui-corner-top">
												<div class="blue module ui-corner-top clearfix">
													<h2>Ա������</h2>
												</div>
												<div class="content">
													<div data-bind="foreach : $root.allProblemsConstantA">
														<label class="input-checkbox" for="employeeProblem">
															<input class="icheckbox" type="checkbox" name="employeeProblem" data-bind="value : name, click : $root.updateProblemItem, checked : $root.problemsTheCompanyHas"/> <span
															data-bind="text : name"></span>
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="four columns">
											<div class="app-wrapper ui-corner-top">
												<div class="blue module ui-corner-top clearfix">
													<h2>�˿�����</h2>
												</div>
												<div class="content">
													<div data-bind="foreach : $root.allProblemsConstantB">
														<label class="input-checkbox" for="consumerProblem">
															<input type="checkbox" name="consumerProblem" class="icheckbox" data-bind="value : name, click : $root.updateProblemItem, checked : $root.problemsTheCompanyHas"/> <span
															data-bind="text : name"></span>
														</label>
													</div>
												</div>
											</div>

										</div>
										<div class="four columns">
											<div class="app-wrapper ui-corner-top">
												<div class="blue module ui-corner-top clearfix">
													<h2>��������</h2>
												</div>
												<div class="content">
													<div data-bind="foreach : $root.allProblemsConstantC">
														<label class="input-checkbox" for="otherProblems">
															<input class="icheckbox" type="checkbox"
															name="otherProblems" data-bind="value : name, click : $root.updateProblemItem, checked : $root.problemsTheCompanyHas"/> <span
															data-bind="text : name"></span>
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>

								</div>

								<h3>
									<a href="#">�绰����</a>
								</h3>
								<div>
									<div class="row">
										<div class="right">
											<a class="small blue button" data-bind="click : $root.openPhoneCallDialog">�����µ�ͨ����¼</a>
										</div>
									</div>
									<div class="row">
										<ul class="smartlist nice">
											<label class="input-checkbox">
												<li>
													<div class="row">
														<div class="three columns text-center">
															<label>��������</label> <input type="text"
																disabled="disabled" value="2013��4��25��" />
														</div>
														<div class="three columns">
															<label>Լ���´ε绰ʱ��</label> <input type="text"
																disabled="disabled" value="2013��4��26��" />
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
															<label>��������</label> <input type="text"
																disabled="disabled" value="2013��4��25��" />
														</div>
														<div class="three columns">
															<label>Լ���´ε绰ʱ��</label> <input type="text"
																disabled="disabled" value="2013��4��26��" />
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
															<label>��������</label> <input type="text"
																disabled="disabled" value="2013��4��25��" />
														</div>
														<div class="three columns">
															<label>Լ���´ε绰ʱ��</label> <input type="text"
																disabled="disabled" value="2013��4��26��" />
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
															<label>��������</label> <input type="text"
																disabled="disabled" value="2013��4��25��" />
														</div>
														<div class="three columns">
															<label>Լ���´ε绰ʱ��</label> <input type="text"
																disabled="disabled" value="2013��4��26��" />
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
															<label>��������</label> <input type="text"
																disabled="disabled" value="2013��4��25��" />
														</div>
														<div class="three columns">
															<label>Լ���´ε绰ʱ��</label> <input type="text"
																disabled="disabled" value="2013��4��26��" />
														</div>
														<div class="six columns">
															<textarea name="ex-textarea-5"></textarea>
														</div>
													</div>
											</li>
											</label>
											</ul>
									</div>
								</div>

							</div>
							<div id="phoneCallDialog" title="�绰��¼����" style="display:none;" data-bind="with : $root.phoneCall">
									<form id="problemForm">
										<div class="row">
											<label class="label"> �绰��¼��� ��</label> <span
												data-bind="text : id"></span>
										</div>
										<br>
										<hr>
										<div class="row">
											<label class="required">�绰����</label>
											<textarea name="descriptionArea" data-bind="value : description" class="required" style="width: 598px; height: 287px;"></textarea>
										</div>
										<div class="row">
											<label>�´ι�ͨ���ڼ�ʱ��</label>
											 <input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1, onClose : $root.nextDateOnClose}, value : nextDate" class="required">
										</div>
									</form>
									<div class="row">
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
					//$('#addtionalCompanyInformation').validate({});
					$("#searchWrapper").accordion({
						collapsible: true
					});
	
					$(".expand").click(function() {
						$("#searchWrapper").accordion('option', 'ative', 0);
					});
	
					var Problem = function(id, name) {
						var self = this;
						
						self.id= id;
						self.name = name;
						self.checked = ko.observable(false);
						self.selectedItem = ko.observable('');
						
					};
					
					var Company = function(id, name, contractor, email, email_src, phone, phone_src, star, address, distinct, problems, detailUrl, status) {
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
						self.status = status;
					};
					
					var CompanyAddtion = function() {
						 var self = this;
						 self.id='';
						 self.bossName='';
						 self.bossMobile='';
						 self.bossTelephone='';
						 self.bossQQorWechat='';
						 self.branchCount='';
						 self.branchManagerCount='';
						 self.branchConsultantCount='';
						 self.bedCount='';
						 self.acreage='';
						 self.lastYearIncome='';
						 self.thisYearMonthlyIncome='';
						 self.bossAge='';
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
					
					var PhoneCallHistory = function() {
							this.id = null;
							this.nextDate = '';
							this.description = '';
					}
					
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
						self.allProblemsConstantA = [];
						self.allProblemsConstantB = [];
						self.allProblemsConstantC = [];
						self.provinces = ko.observableArray([]);
						self.selectedProvince =  ko.observable('');
						self.selectedCompany = ko.observable(new Company());
						self.addtion =  ko.observable(new CompanyAddtion());
						self.problemsTheCompanyHas = ko.observableArray([]);
						self.phoneCall = ko.observable(new PhoneCallHistory());
						
						self.nextDateOnClose = function(item, event) {
							
						};
						self.openPhoneCallDialog = function(item, event) {
							$('#phoneCallDialog').dialog({
								modal : true,
								width : 640,
								height : 580
							})
						};
						self.updateProblemItem = function(item, event) {
							
							var checkedFlag = $(event.target).is(':checked');
							$.ajax({
								url : '/ls/user/checkOrUncheckProblem.ls',
								method : 'POST',
								data : {
										companyJson : JSON.stringify(self.selectedCompany()), 
										problemJson : JSON.stringify(item),
										checkFlag : checkedFlag},
								success : function(data) {
									
									if (data && data.message) {
										
										var showToUser = '';
										
										if (checkedFlag) {
											showToUser = ( "Ϊ <label class=\"label green\">" + self.selectedCompany().name + "</label>" + " ������������: <b>" + item.name + "</b>��");
										} else {
											showToUser = "Ϊ <label class=\"label green\">" + self.selectedCompany().name + "</label>" + " ȥ����������:<b>" + item.name + "</b>��";
										}
										
										success(showToUser);
										
									} else {
										fail();
									}
								}
							});
							
							return true;
						};
						
						self.changeStarLevel = function(companyId, score) {
							$.ajax({
								url : '/ls/user/changeStarLevel.ls',
								method : 'POST',
								data : {star : score, company_id: companyId},
								success : function(data) {
									
									if (data && data.message) {
										var showToUser = "<label class=\"label green\">" + self.selectedCompany().name + "</label>" + "�ѱ���Ϊ <b>" + score + "</b>�ǡ�";
										success(showToUser);
										
									} else {
										fail();
									}
								}
							});
						};
						
						
						self.saveAddition = function(item, event) {
							$.ajax({
								url : '/ls/user/saveAddtionalCompanyInformation.ls',
								method : 'POST',
								data : {additionalCompanyInformation : JSON.stringify(item), company_id: self.selectedCompany().id},
								success : function(data) {
									
									if (data && data.id) {
										self.addtion(data);	
										success("����ɹ���");
									}
								}
							});
						};
						
						self.toggleListAndDetail = function() {
							$( "#selectedCompany" ).slideToggle();
							$( "#companyList" ).slideToggle();
						};
						
						self.backToCustomerList = function() {
							self.toggleListAndDetail();
							
							self.searchCompany();
						};
						
						
						
						self.trackCustomer = function(item, event) {
							
							console.debug(ko.toJS(item));
							
							self.selectedCompany(item);
							
							//var problems = ko.toJS(item.problems);
							
							self.addtion(new CompanyAddtion());
							
							self.toggleListAndDetail();
							
							$('#accordion').accordion({ heightStyle: "content"});
							
							$('#nextScheduleDate').datepicker();
							
							self.loadCompanyAdditionalInformation();
							self.loadCompanyProblems();
						
							$('#detailStar').raty({
									score : function() {
										return $(this).attr('star');
									},
								  	click: function(score, evt) {
									    var companyId = $(this).attr('companyId');
									    self.changeStarLevel(companyId, score);
									    
									},
									number : 5
							});
							success('<label class="green label">' + item.name + "</label> �ѳɹ�����");
						};
						
						self.loadCompanyProblems = function() {
							$.ajax({
								url : '/ls/loadCompanyProblems.ls',
								data : {companyId : self.selectedCompany().id},
								success : function(data) {
									if (data) {
										var selectedIds = [];
										$.each(data, function(i, n) {
											selectedIds.push(n.name);
										});
										
										self.problemsTheCompanyHas(selectedIds);
									} else {
										fail('���ؿͻ�����ѡ��ʧ��');
									}
								}
							});
							
						};
						
						self.loadCompanyAdditionalInformation = function() {
							
							$.ajax({
								url : '/ls/loadAddtionalCompanyInformation.ls',
								data : {companyId : self.selectedCompany().id},
								success : function(data) {
									
									if (data) {
										self.addtion(data);
									}
								}
							});
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
							
							self.loadProblemConstants('Ա������');
							self.loadProblemConstants('�˿�����');
							self.loadProblemConstants('��������');
							
							$.ajax({
								url : '/ls/findAllProvinces.ls',
								success : function(data) {
									
									$.each(data, function(index, value) {

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
						
						self.loadProblemConstants = function(type) {
							
							$.ajax({
								url : '/ls/findAllProblems.ls',
								data : {type : type},
								success : function(data) {
									if (data) {
										if (type == 'Ա������') {
											self.allProblemsConstantA = data;
										} else if ( type == '�˿�����'){
											self.allProblemsConstantB = data;
										} else {
											self.allProblemsConstantC = data;
										}
									}
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
						
						self.showDetail = function(item, event) {
							window.open(item.detailUrl, '_blank');
						};
						
						self.searchCompany = function() {
							$("#searchWrapper").accordion({
								active: false
							});
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
									success("�ͻ��б��Ѽ��ء�");
									
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
								
								var company = new Company(value.id, value.name, value.contactor, value.email, new_email_src, value.phone, new_phone_src, value.star, value.address, value.area, problems, value.fEurl, value.status);

								self.companyList.push(company);
								
								$('.star').raty({
							        score : function() {
							                return $(this).attr('star');
							        },
							        number : 5,
							        readOnly   : true
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
							
							return true;
						};
						
					};
					
					var companyModel = new CompanyModel();
					companyModel.init();
					
					var $container = $("#container")[0];
					ko.applyBindings(companyModel, $container);
					
				});
	</script>
</body>
</html>
