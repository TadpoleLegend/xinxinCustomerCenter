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
<link rel="stylesheet" href="/ls/css/flat-ui.css">
<link rel="stylesheet" href="/ls/css/bwizard.css">

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

							<div class="three columns"></div>
							<div class="three columns"></div>
						</div>
						<hr>
						<div class="row">
							<div class="three columns">
								<label>公司名称</label> 
								<input type="text" class="addon-postfix" data-bind="value : seachCompany" />
							</div>
							<div class="three columns">
								<label>联系人</label> <input type="text" class="addon-postfix" data-bind="value : searchContactor" />
							</div>

							<div class="three columns">
								<label>所在区</label> <input type="text" class="addon-postfix"
									data-bind="value : searchDistinct" />
							</div>
							<div class="three columns">
								<label>星级</label>
								<div id="starInput" data-bind="attr: { 'starInput' : starInput }"></div>
								<input type="checkbox" data-bind="checked : allStar">
								包含所有星级
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="six columns centered">
								<a class="small blue button" href="#"
									data-bind="click : searchCompany"> 搜索符合条件的客户 </a>
							</div>
						</div>
					</div>
				</div>

				<div class="app-wrapper ui-corner-top" id="companyList">
					<div class="blue module ui-corner-top clearfix">
						<h2>客户列表</h2>
						<h2 class="right"><a class="small white button" data-bind="click : $root.openManageCompanyDialog">录入新的顾客资料</a></h2>
					</div>
					<div class="content">
						<div class="row">
							共找到<label class="green label" data-bind="text: totalPagesCount"></label>
							波 ， <label class="yellow label"
								data-bind="text: totalCompanyCount"></label>个客户
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
												<b data-bind="text : contactor"></b>
											</div>
											<div class="three columns">
												<label class="input-checkbox"> 
													<img style="margin-left: 45px" alt="电话号码" data-bind="attr: { 'src' : phone_src }, visible : mobilePhoneSrc == '' ">
													<img style="margin-left: 45px" alt="手机号码" data-bind="attr: { 'src' : mobilePhoneSrc }, visible : mobilePhoneSrc != '' ">
													<span data-bind="text : mobilePhone, visible : mobilePhone != '' "></span>
													<span data-bind="text : phone, visible : mobilePhone == '' && phone != '' "></span>
												</label>
											</div>
											<div class="two columns">
												<div class="star listStar" data-bind="attr : {'star' : star, 'id': id}"></div>
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
							<div class="three columns"></div>
							<div class="six columns">
								<a href="#" class="small blue button"
									data-bind="click : lastPage, disable : currentIndex() > 1">上一波客户</a>
								<label class="label yellow" data-bind="text : currentIndex"></label>
								<a href="#" class="small blue button"
									data-bind="click : nextPage">下一波客户</a>
							</div>
							<div class="three columns"></div>
						</div>
					</div>
				</div>
				<div id="selectedCompany" class="row" data-bind="with : selectedCompany" style="display: none;">
					<div class="app-wrapper ui-corner-top">
						<div class="gray module ui-corner-top clearfix">
							<h2>
								详细信息<span class="subheader line" data-bind="text : name"></span>
							</h2>
							<h2 style="margin-left : 200px">
								<div id="detailStar" class="star" data-bind="attr : {'star' : star, 'companyId' : id}"></div>
							</h2>
							<h2 class="right">
								<a class="small blue button" data-bind="click : $root.backToCustomerList" href="#">返回客户列表</a>
							</h2>
						</div>
						<div class="content">
							<div class="row">
								<div id="wizard">
									<ol class="bwizard-steps clearfix clickable" role="tablist" data-bind="foreach : $root.allSteps">
										<li role="tab"  aria-selected="false" data-bind="css : {active : id == $root.selectedCompany().status}, attr : {id :  'stepLink' + id }">
											<span class="label green" data-bind="text:orderNumber"></span>
											<a class="hidden-phone" data-bind="click : $root.changeCompanyStatus"><span data-bind="text : name"></span></a>
										</li>
									</ol>
								</div>
							</div>
							<br>
							<div id="accordion">
								<h4>
									<a href="#">客户基本信息</a> 
								</h4>
								<div class="content">
									<div class="row">
										<div class="row">
											<div class='three columns'>
												<label>名称 </label><input type="text"
													data-bind="value : name" disabled="disabled">
											</div>
											<div class='one columns'>
												<label>区域 </label><input type="text"
													data-bind="value : distinct">
											</div>
											<div class='four columns'>
												<label>地址 </label> <input type="text"
													data-bind="value : address">
											</div>
											<div class="four columns">
												<br>
												<a style="margin-left: 20px;" data-bind="click:$root.showDetail"><span data-bind="text : oteUrl"></span></a>
											</div>
										</div>

										<div class="row">
											<div class='three columns'>
												<label>联系人 </label><input type="text"
													data-bind="value : contactor">
											</div>
											<div class='three columns'>
												<label>手机</label><label class="input-checkbox"> 
													<img alt="电话号码" data-bind="attr: { 'src' : mobilePhoneSrc }">
												</label>
											</div>
											<div class='three columns'>
												<label>固定电话</label><label class="input-checkbox"> 
													<img alt="电话号码" data-bind="attr: { 'src' : phone_src }">
												</label>
											</div>
											<div class='three columns'>
												<label>电子邮件</label>
													<label class="input-checkbox"> 
													<img alt="电子邮箱" data-bind="attr: { 'src' : email_src }">
												</label>
											</div>
										</div>
									</div>
									<div class="row">
										<label>公司简介</label>
										<textarea data-bind="value : description"></textarea>
									</div>
									<br>
									<h4 class="text-success">补充客户信息</h4>
									<hr>
									<div id="addtionalCompanyInformation"
										data-bind="with : $root.addtion">
										<div class="row">
											<div class="three columns">
												<label>客户评级</label>
												<select data-bind="options: $root.companyTypes,
                      											   optionsText: 'optionText',
                       											   value: companyLevel,
                       											   optionsValue : 'optionValue',
                       											   selectedOption : companyLevel,
                       											   optionsCaption: '选择客户级别'"
                       									class="required">
                       							</select>
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>院长姓名</label> <input type="text"
													data-bind="value : bossName" />
											</div>
											<div class="three columns">
												<label>院长手机</label> <input class="number" type="text"
													data-bind="value : bossMobile" />
											</div>
											<div class="three columns">
												<label>院长电话</label> <input class="number" type="text"
													data-bind="value : bossTelephone" />
											</div>
											<div class="three columns">
												<label>院长QQ或者微信号</label> <input type="text"
													data-bind="value : bossQQorWechat" />
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>店个数</label> <input class="number" type="text"
													data-bind="value : branchCount" />
											</div>
											<div class="three columns">
												<label>店长人数</label> <input class="number" type="text"
													data-bind="value : branchManagerCount" />
											</div>
											<div class="three columns">
												<label>顾问人数</label> <input class="number" type="text"
													data-bind="value : branchConsultantCount" />
											</div>
											<div class="three columns">
												<label>床位数</label> <input class="number" type="text"
													data-bind="value : bedCount" />
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>规模（平方）</label> <input class="number" type="text"
													data-bind="value : acreage" />
											</div>
											<div class="three columns">
												<label>去年业绩（万）</label> <input class="number" type="text"
													data-bind="value : lastYearIncome" />
											</div>
											<div class="three columns">
												<label>现平均多少万</label> <input class="number" type="text"
													data-bind="value : thisYearMonthlyIncome" />
											</div>
											<div class="three columns">
												<label>院长年龄大概多少岁？</label> <input class="number" type="text"
													data-bind="value : bossAge" />
											</div>
										</div>
										<br>
										<h4 class="text-success">感动顾客</h4>
										<hr>
										<div class="row">
											<div class="three columns">
												<label>老大生日</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : firstKidBirthday" />
											</div>
											<div class="three columns">
												<label>老二生日</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : secondKidBirthday" />
											</div>
											<div class="three columns">
												<label>老三生日</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : thirdKidBirthday" />
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>院长生日</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : bossBirthday" />
											</div>
											<div class="three columns">
												<label>公司年庆</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : companyAnniversary" />
											</div>
											<div class="three columns">
												<label>结婚纪念日</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : merryAnniversary" />
											</div>
											<div class="three columns">
												<label>老公生日</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : loverBirthday" />
											</div>
										</div>
										<div class="row">
											<div class="six columns">
												<label>备注</label> 
												<textarea rows="2" data-bind=" value : comments"></textarea>
											</div>
										</div>
										<div class="row">
											<a class="small blue button" data-bind="click : $root.saveAddition">保存客户信息</a>
										</div>
									</div>
								</div>

								<h4>
									<a href="#">问题归类</a>
								</h4>
								<div>
									<div class="row">
										<div class="four columns">
											<div class="app-wrapper ui-corner-top">
												<div class="blue module ui-corner-top clearfix">
													<h2>员工问题</h2>
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
													<h2>顾客问题</h2>
												</div>
												<div class="content">
													<div data-bind="foreach : $root.allProblemsConstantB">
														<label class="input-checkbox" for="consumerProblem">
															<input type="checkbox" name="consumerProblem" class="icheckbox" data-bind="value : name, click : $root.updateProblemItem, checked : $root.problemsTheCompanyHas"/> <span
															data-bind="text : name" ></span>
														</label>
													</div>
												</div>
											</div>

										</div>
										<div class="four columns">
											<div class="app-wrapper ui-corner-top">
												<div class="blue module ui-corner-top clearfix">
													<h2>其他问题</h2>
												</div>
												<div class="content">
													<div data-bind="foreach : $root.allProblemsConstantC">
														<label class="input-checkbox" for="otherProblems">
															<input class="icheckbox" type="checkbox"
															name="otherProblems" data-bind="value : name, click : $root.updateProblemItem, checked : $root.problemsTheCompanyHas"/> <span
															data-bind="text : name" ></span>
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>

								</div>

								<h3>
									<a href="#">电话跟踪</a>
								</h3>
								<div>
									<div class="row">
										<div class="right">
											<a class="small blue button" data-bind="click : $root.addHistory">创建新的通话记录</a>
										</div>
									</div>
									<div class="row">
										<table class="display compact" id="phoneCallHistoryListTable">
											<thead>
												<tr>
													<th class="text-center">记录编号</th>
													<th class="text-center">跟踪时间</th>
													<th class="text-center">下次跟踪时间</th>
													<th class="text-center">沟通内容</th>
													<th class="text-center"></th>
												</tr>
											</thead>
											<tbody data-bind="foreach: $root.historyRecords">
												<tr>
													<td class="text-center"><span class="text-success" data-bind="text: id"></span></td>
													<td class="text-center"><span data-bind="text: createDate"></span></td>
													<td class="text-center"><span data-bind="text: nextDate"></span></td>
													<td class="text-center"><span data-bind="text: description"></span></td>
													<td class="text-center">
														<a href="#" data-bind="click : $root.editPhoneCallHistory"><i class="icon-pencil small icon-blue"></i></a> 
														<a href="#" style="margin-left : 10px;" data-bind="click : $root.removePhoneCallHistory"><i class="icon-trash small icon-red"></i></a>
													</td>	
												</tr>
											</tbody>
										</table>
										<br>
									</div>
								</div>
								<h3>
									<a href="#">培训记录</a>
								</h3>
								<div>
									<div class="row">
										<div class="right">
											<a class="small blue button" data-bind="click : $root.addLearningHistory">创建新的培训记录</a>
										</div>
									</div>
									<div>
										<table class="display compact" id="learningHistoryListTable">
											<thead>
												<tr>
													<th class="text-center">记录编号</th>
													<th class="text-center">期数</th>
													<th class="text-center">开始时间</th>
													<th class="text-center">结束时间</th>
													<th class="text-center">中层人数</th>
													<th class="text-center">高层人数</th>
													<th class="text-center"></th>
												</tr>
											</thead>
											<tbody data-bind="foreach: $root.learningHistoryRecords">
												<tr>
													<td class="text-center"><span class="text-success" data-bind="text: id"></span></td>
													<td class="text-center"><span data-bind="text: phase.name"></span></td>
													<td class="text-center"><span data-bind="text: startDate"></span></td>
													<td class="text-center"><span data-bind="text: endDate"></span></td>
													<td class="text-center"><span data-bind="text: middleLevelManagerCount"></span></td>
													<td class="text-center"><span data-bind="text: highLevelManagerCount"></span></td>
													<td class="text-center"><a href="#" data-bind="click : $root.editLearningHistory"><i class="icon-pencil small icon-blue"></i></a>
																			<a href="#" style="margin-left : 10px;" data-bind="click : $root.removeLearningHistory"><i class="icon-trash small icon-red"></i></a>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div>
								</div>
							</div>
							<div id="phoneCallDialog" title="电话记录管理" style="display:none;" data-bind="with : $root.phoneCall">
									<form id="historyForm">
										<div class="row">
											<label class="label"> 电话记录编号 ：</label> <span
												data-bind="text : id"></span>
										</div>
										<br>
										<hr>
										<div class="row">
											<label class="required">电话内容</label>
											<textarea name="descriptionArea" data-bind="value : description" class="required" style="width: 598px; height: 287px;"></textarea>
										</div>
										<div class="row">
											<label>下次沟通日期及时间</label>
											 <input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1, onClose : $root.nextDateOnClose}, value : nextDate" class="required">
										</div>
									</form>
							</div>
							<div id="selectedCompanyDialog" title="公司管理" style="display:none;" data-bind="with : $root.newCompany">
							
									<form id="companyManageForm">
										<div class="row">
											<label class="label"> 公司编号 ：</label> <br> <span data-bind="text : id"></span>
											</div>
										<br>
										<hr>
										<div class="row">
											<div class="four columns">
												<label>省/直辖市</label> 
												<select class="required" data-bind="options: $root.provinces, optionsCaption: '请选择...', optionsText: 'name', optionsValue: 'id', value: $root.selectedProvinceInDialog, valueAllowUnset: true"></select>
											</div>
											<div class="four columns">
												<label>市/区</label> 
												<select data-bind="options: $root.citiesForDialog, optionsCaption: '请选择...', optionsText: 'name', optionsValue: 'id', value: cityId, valueAllowUnset: true"></select>
											</div>
											<div class='four columns'>
												<label>名称 </label><input type="text" data-bind="value : name">
											</div>
											
										</div>
										
										<div class="row">
											<div class='four columns'>
												<label>联系人 </label>
												<input type="text" data-bind="value : contactor">
											</div>
											<div class='four columns'>
												<label>手机</label>
												<input type="text" data-bind="value : mobilePhone">
											</div>
											<div class='four columns'>
												<label>固定电话</label>
												<input type="text" data-bind="value : phone">
											</div>
										</div>
										<div class="row">
											<div class='six columns'>
												<label>电子邮件</label>
												<input type="text" data-bind="value : email">
											</div>
										</div>
										<div class="row">
											<label>地址 </label> 
											<input type="text" data-bind="value : address">
										</div>
										<div class="row">
											<label>公司简介</label> 
											<textarea data-bind="value : description"></textarea>
										</div>
									</form>
							</div>
							<div id="learningHistoryDialog" title="培训记录管理" style="display:none;" data-bind="with : $root.learningHistory">
									<form id="learningHistoryForm">
										<div class="row">
											<label class="label"> 培训记录编号 ：</label> <span data-bind="text : id"></span>
										</div>
										<br>
										<hr>
										<div class="row">
											<div class="four columns">
												<label>期数</label>
												<select data-bind="options: $root.phases,
                      											   optionsText: 'name',
                       											   value: phase.id,
                       											   optionsValue : 'id',
                       											   selectedOption : phase.id,
                       											   optionsCaption: '选择期数'"
                       									class="required">
                       							</select>
											</div>
											<div class="four columns">
												<label>中层人数</label>
												<input class="number" type="text" data-bind="value : middleLevelManagerCount" />
											</div>
											<div class="four columns">
												<label>高层人数</label>
												<input class="number" type="text" data-bind="value : highLevelManagerCount" />
											</div>
										</div>
										<div class="row">
											<div class="six columns">
												<label>开始日期</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : startDate">
											</div>
											<div class="six columns">
												<label>结束日期</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : endDate">
											</div>
										</div>
										<div class="row">
											<label>备注</label>
											<textarea name="descriptionArea" data-bind="value : comments" style="width: 628px; height: 198px;"></textarea>
										</div>
									</form>
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
	<script src="/ls/js/User.js"></script>
	<script>
		
		$(document).ready( function() {

					$("#searchWrapper").accordion({
						collapsible: true,
						border: true
					});
	
					$(".expand").click(function() {
						$("#searchWrapper").accordion('option', 'ative', 0);
					});
	
					$('#phoneCallHistoryListTable').dataTable({
						"paging" : false,
						"ordering" : false,
						"info" : false,
						"searching" : false
					});
					$('#learningHistoryListTable').dataTable({
						"paging" : false,
						"ordering" : false,
						"info" : false,
						"searching" : false
					});
					var Problem = function(id, name) {
						var self = this;
						
						self.id= id;
						self.name = name;
						self.checked = ko.observable(false);
						self.selectedItem = ko.observable('');
						
					};
					
					var Company = function(id, name, contactor, email, email_src, phone, phone_src, star, address, distinct, problems, oteUrl, status, mobilePhone, mobilePhoneSrc,description) {
						var self = this;
						
						self.id = id;
						self.name = name;
						self.contactor = contactor;
						self.email = email;
						self.email_src = email_src;
						self.phone = phone;
						self.phone_src = phone_src;
						self.mobilePhone = mobilePhone;
						self.mobilePhoneSrc = mobilePhoneSrc;
						self.star = star;
						self.address = address;
						self.distinct = distinct;
						self.problems = ko.observableArray(problems);
						self.oteUrl = oteUrl;
						self.selectedProblem = ko.observable('');
						self.status = status;
						self.provinceId = '';
						self.cityId = '';
						self.cityOptions = '';
						self.description = description;
					};
					
					var CompanyAddtion = function() {
						
						 var self = this;
						 
						 self.id='';
						 self.companyLevel = '';
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
						 self.firstKidBirthday='';
						 self.secondKidBirthday='';
						 self.thirdKidBirthday='';
						 self.lunarOrSolar='';
						 self.bossBirthday='';
						 self.companyAnniversary='';
						 self.merryAnniversary='';
						 self.loverBirthday='';
						 self.comments = '';
							
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
							this.createDate = '';
							this.description = '';
					};
					var Phase = function() {
						
						this.id = null;
						this.name = '';
					};
					var LearningHistory = function() {
						
						this.id = null;
						this.phase = new Phase();
						this.startDate = '';
						this.endDate = '';
						this.middleLevelManagerCount = '';
						this.highLevelManagerCount = '';
						this.comments = '';
					};
					
					var CompanyModel = function() {
						var self = this;

						self.companyTypes = ko.observableArray([]);
						
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
						self.selectedProvince =  ko.observable(new Province());
						self.selectedCompany = ko.observable(new Company());
						self.newCompany = ko.observable(new Company());
						self.addtion =  ko.observable(new CompanyAddtion());
						self.problemsTheCompanyHas = ko.observableArray([]);
						self.phoneCall = ko.observable(new PhoneCallHistory());
						self.historyRecords = ko.observableArray([]);
						self.allSteps = ko.observableArray([]);
						self.learningHistoryRecords = ko.observableArray([]);
						self.learningHistory = ko.observable(new LearningHistory());
						self.phases = ko.observableArray([]);
						
						self.selectedProvinceInDialog = ko.observable(new Province());
						
						self.cities = ko.computed(function() {
							var cityOptions;
							$.each(self.provinces(), function(i, n){
								if ( n.id == self.selectedProvince()) {
									cityOptions = n.cities;
									console.debug(cityOptions);
								}
							});
							return cityOptions;
						});
						
						self.citiesForDialog = ko.computed(function() {
							var cityOptions;
							$.each(self.provinces(), function(i, n){
								if ( n.id == self.selectedProvinceInDialog()) {
									cityOptions = n.cities;
									console.debug(cityOptions);
								}
							});
							return cityOptions;
						});
						
						//self.newCompany.subscribe(function() {
						//	console.debug(self.selectedProvinceInDialog());
						//});
						
						self.openManageCompanyDialog = function() {
							$('#wizard').hide();
							$('#selectedCompanyDialog').dialog({
								modal : true,
								width : 909,
								height : 730,
								open : function(e) {
									changeButtonStyleForPopup(e);
								},
								
								buttons : {
									'保存记录' : function() {
										self.saveCompany();
									},
									'关闭窗口' : function() {
										self.closeDialog('selectedCompanyDialog');
									}
								}
							});
						};
						
						self.saveCompany = function(item, event) {
							
							if ($('#companyManageForm').valid()) {
								
								self.newCompany().provinceId = self.selectedProvinceInDialog();
								
								$.ajax({
									url : '/ls/saveCompany.ls',
									type : 'POST',
									data : {
											newCompanyJson : JSON.stringify(self.newCompany()),
											mannually : true,
											cityId :  self.newCompany().cityId
									},
									success : function(data) {
										
										if(isOK(data)) {
											
											success(data.message);
											
											self.closeDialog('selectedCompanyDialog');
										} else {
											
											fail();
										}
									}
								});
							}
							
						};
						
						self.loadLearningHistory = function() {
							$.ajax({
								url : '/ls/loadLearningHistory.ls',
								data : {companyId : self.selectedCompany().id},
								success : function(data) {
									if (data) {
										self.learningHistoryRecords(data);
									} else {
										fail('加载学习记录项失败.');
									}
								}
							});
						};
						
						self.saveLearningHistory = function(item, event) {
							
							if ($('#learningHistoryForm').valid()) {
								$.ajax({
									url : '/ls/user/saveLearningHistory.ls',
									method : 'POST',
									data : {
											companyId : JSON.stringify(self.selectedCompany().id), 
											learningJson : JSON.stringify(ko.toJS(self.learningHistory()))
									},
									success : function(data) {
										
										if (isOK(data)) {
											
											success(data.message);
											self.closeDialog('learningHistoryDialog');
											self.loadLearningHistory();
											
										} else {
											fail(data.message);
										}
									}
								});
							}
						};
						
						self.buildRatingAndSections = function() {
							$('#accordion').accordion({ heightStyle: "content"});
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
						};
						
						self.changeCompanyStatus = function(item, event) {
							
							$.ajax({
								url : '/ls/changeCompanyStatus.ls',
								type : 'POST',
								data : {companyId : self.selectedCompany().id,
										statusId : item.id	
								},
								success : function(data) {
									
									if(isOK(data)) {
										
										self.selectedCompany().status = item.name;
										
										$('#wizard .active').removeClass('active');
										
										$('#stepLink' + item.id).attr('class', 'active');
										
										var message = self.selectedCompany().name + '已更改为 ' + bIt(item.name);
										success(message);
										
									} else {
										
										fail();
									}
								}
							});
							
						};
						self.nextDateOnClose = function(item, event) {
							
						};
						
						self.loadPhoneCallHistory = function() {
							$.ajax({
								url : '/ls/loadPhoneCallHistory.ls',
								data : {companyId : self.selectedCompany().id},
								success : function(data) {
									if (data) {
										self.historyRecords(data);
									} else {
										fail('加载电话记录项失败.');
									}
								}
							});
						};
						
						self.savePhoneCallHistory = function(item, event) {
							
							if ($('#historyForm').valid()) {
								
								$.ajax({
									url : '/ls/user/savePhoneCallHistory.ls',
									method : 'POST',
									data : {
											companyId : JSON.stringify(self.selectedCompany().id), 
											phoneCallJson : JSON.stringify(ko.toJS(self.phoneCall()))
									},
									success : function(data) {
										
										if (isOK(data)) {
											
											success();
											self.closeDialog('phoneCallDialog');
											self.loadPhoneCallHistory();
											
										} else {
											fail();
										}
									}
								});
							}
						};
						
						self.closeDialog = function(id) {
							$('#' + id).dialog("close");
							$('#wizard').show();
						};
						
						self.openPhoneCallDialog = function(item, event) {
							
							$('#wizard').hide();
							$('#phoneCallDialog').dialog('open');
						};
						self.openLearningHistoryDialog = function(item, event) {
							
							$('#wizard').hide();
							$('#learningHistoryDialog').dialog('open');
						};
						
						self.addHistory = function() {
							self.phoneCall(new PhoneCallHistory());
							self.openPhoneCallDialog();
						};
						self.editPhoneCallHistory = function(item, event) {
							self.phoneCall(item);
							self.openPhoneCallDialog();
						};
						
						self.addLearningHistory = function() {
							self.learningHistory(new LearningHistory());
							self.openLearningHistoryDialog();
						};
						self.editLearningHistory = function(item, event) {
							self.learningHistory(item);
							self.openLearningHistoryDialog();
						};
						
						
						self.removePhoneCallHistory = function(item, event) {
							if(window.confirm('你真的要删除这条记录吗？')) {
								$.ajax({
									url : '/ls/user/removePhoneCallHistory.ls',
									method : 'POST',
									data : {
										phoneCallId : item.id
									},
									
									success : function(data) {
										
										if (isOK(data)) {
											
											success();
											self.loadPhoneCallHistory();
											
										} else {
											fail();
										}
									}
								});
							}
							
						};
						self.removeLearningHistory = function(item, event) {
							if(window.confirm('你真的要删除这条记录吗？')) {
								$.ajax({
									url : '/ls/user/removeLearningHistory.ls',
									method : 'POST',
									data : {
											learningHistoryId : item.id
									},
									
									success : function(data) {
										
										if (isOK(data)) {
											
											success();
											self.loadLearningHistory();
											
										} else {
											fail();
										}
									}
								});
							}
							
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
											showToUser = (  self.selectedCompany().name + " 增加了问题项: <b>" + item.name + "</b>。");
										} else {
											showToUser =  self.selectedCompany().name + " 去除了问题项:<b>" + item.name + "</b>。";
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
										var showToUser =  self.selectedCompany().name + "已被评为 <b>" + score + "</b>星。";
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
										success("保存成功。");
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
							
							self.selectedCompany(item);
							
							self.addtion(new CompanyAddtion());
							
							self.toggleListAndDetail();
							
							self.loadCompanyAdditionalInformation();
							
							self.loadCompanyProblems();
						
							self.buildRatingAndSections();
							
							self.loadPhoneCallHistory();
							self.loadLearningHistory();
							
							$('#phoneCallDialog').dialog({
								autoOpen : false,
								modal : true,
								width : 640,
								height : 580,
								open : function(e) {
									changeButtonStyleForPopup(e);
								},
								
								buttons : {
									'保存记录' : function() {
										self.savePhoneCallHistory();
									},
									'关闭窗口' : function() {
										self.closeDialog('phoneCallDialog');
									}
								}
							});
							
							$('#learningHistoryDialog').dialog({
								autoOpen : false,
								modal : true,
								width : 643,
								height : 640,
								open : function(e) {
									changeButtonStyleForPopup(e);
								},
								
								buttons : {
									'保存记录' : function() {
										self.saveLearningHistory();
									},
									'关闭窗口' : function() {
										self.closeDialog('learningHistoryDialog');
									}
								}
							});
							
							success( item.name + " 信息已加载!" );
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
										fail('加载客户问题选项失败');
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
						
						self.selectedCity = ko.observable('');
						
						self.init = function() {
							
							 $('#starInput').raty({
								 
						           click: function(score, evt) {
						                self.starInput(score);
						           }
							 
						     });
							 
							self.searchCompany();
							
							self.loadProblemConstants('员工问题');
							self.loadProblemConstants('顾客问题');
							self.loadProblemConstants('其他问题');
							
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
							
							$.ajax({
								url : '/ls/findAllSteps.ls',
								success : function(data) {
									
									self.allSteps(data);
								}
							});
							$.ajax({
								url : '/ls/findAllPhases.ls',
								success : function(data) {
									self.phases(data);
								}
							});
							$.ajax({
								url : '/ls/findAllCompanyTypes.ls',
								success : function(data) {
									self.companyTypes(data);
								}
							});
							
						};
						
						self.loadProblemConstants = function(type) {
							
							$.ajax({
								url : '/ls/findAllProblems.ls',
								data : {type : type},
								success : function(data) {
									if (data) {
										if (type == '员工问题') {
											self.allProblemsConstantA = data;
										} else if ( type == '顾客问题'){
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
							window.open(item.oteUrl, '_blank');
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
									success("客户列表已加载。");
									
								}
							});
						};
						
						self.fillCompany = function(data) {
							
							self.companyList.removeAll();

							$.each(data.elements, function(index, value) {
								//var new_phone_src = "/ls/img/" + value.phoneSrc;
								//var new_email_src = "/ls/img/" + value.emailSrc;
								
								var new_phone_src =  value.phoneSrc;
								var new_email_src =  value.emailSrc;
								
								var problems = new Array();
								
								$.each(value.problems, function(i, n) {
									problems.push(n.name);
								});
								
								var company = new Company(value.id, value.name, value.contactor, value.email, new_email_src, value.phone, new_phone_src, value.star, value.address, value.area, problems, value.fEurl, value.status, value.mobilePhone, value.mobilePhoneSrc, value.description);

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
					};
					
					var companyModel = new CompanyModel();
					companyModel.init();
					
					var $container = $("#container")[0];
					ko.applyBindings(companyModel, $container);
					
				});
	</script>
</body>
</html>
