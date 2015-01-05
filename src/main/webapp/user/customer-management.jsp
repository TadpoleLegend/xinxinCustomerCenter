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
<title>���Ĺ˿���������</title>
<link rel="stylesheet" href="/ls/css/jquery.raty.css">

<link rel="stylesheet" href="/ls/css/bwizard.css">
<link rel="stylesheet" href="/ls/css/common.css">

<s:include value="/jsps/common/head.jsp" />
</head>
<body>
	<s:include value="/jsps/common/brand.jsp" />
	<s:include value="/jsps/common/menu.jsp" />
	
	<div id="searchboxDialog" style="display : none;">
				<form class="form-wrapper">
					<input type="text" id="search" placeholder="���빫˾���....." required> <input type="button" value="����" id="submit">
				</form>
	</div>
	<section class="mainbg">
		<div id="container">

			<div style="color: red">
				<s:fielderror />
			</div>
			
			<div class="row">
				<div id="searchWrapper" class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>��ѯ�˿���Ϣ</h2>
					</div>
					<div class="content">
						<div class="row">
							<div class="three columns">
								<label>�˿ͱ��</label> 
								<input type="text" data-bind="value: searchId">
							</div>
							<div class="three columns">
								<label>����</label>
								<select data-bind="options: $root.starLevelOperators,
                      										optionsText: 'optionText',
                       									    value: $root.starLevelOperator,
                       									    optionsValue : 'optionValue',
                       									    selectedOption : $root.starLevelOperator,
                       									    optionsCaption: '��ѡ��...'">
                       			</select>
							</div>
							<div class="three columns">
								<br>
								<div id="starInput" data-bind="attr: { 'starInput' : starInput }"></div>
							</div>
						</div>
						<div class="row">
							<div class="three columns">
								<label>�˿�����</label>
								<select data-bind="options: $root.problemCategories,
                      										optionsText: 'name',
                       									    value: $root.selectedProblemCategory,
                       									    optionsValue : 'name',
                       									    selectedOption : $root.selectedProblemCategory,
                       									    optionsCaption: '��ѡ��...'">
                       			</select>
							</div>
							<div class="three columns">
								<label>�˿�״̬</label>
								<select data-bind="options: $root.allSteps,
                      										optionsText: 'name',
                       									    value: $root.customerStatus,
                       									    optionsValue : 'id',
                       									    selectedOption : $root.customerStatus,
                       									    optionsCaption: '��ѡ��...'">
                       			</select>
							</div>
							
						</div>
						<div class="row">
							<div class="three columns">
								<label>ʡ��ֱϽ��</label>
								<select data-bind="options: provinces, optionsCaption: 'ȫ��', optionsText: 'name', optionsValue: 'id', value: selectedProvince, valueAllowUnset: true"></select>
							</div>
							<div class="three columns">
								<label>��</label> 
								<select data-bind="options: cities, optionsCaption: 'ȫ��', optionsText: 'name', optionsValue: 'id', value: selectedCity, valueAllowUnset: true"></select>
							</div>
							<div class="three columns">
								<label>��˾����</label> 
								<input type="text" class="addon-postfix" data-bind="value : seachCompany" />
							</div>
							<div class="three columns">
								<label>��ϵ��</label> <input type="text" class="addon-postfix" data-bind="value : searchContactor" />
							</div>
						</div>
						<div class="row" data-bind="with : advanceSearch">
							<div class="row">
								<div class="three columns">
									<label>Լ����ϵ��ʼ����</label> <input id="appointStartDateInput" type="text" data-bind="datepicker : {dateFormat : 'yy-mm-dd'}, value : appointStartDate" />
								</div>
								<div class="three columns">
									<label>Լ����ϵ��������</label> <input type="text" data-bind="datepicker : {dateFormat : 'yy-mm-dd'}, value : appointEndDate" />
								</div>
							</div>
							
							<div class="row">
								<!-- 
								<div class="three columns">
													<label>�ж�����</label> 
													<select data-bind="options: $root.birthdayTypes,
                      										optionsText: 'optionText',
                       									    value: birthdayType,
                       									    optionsValue : 'optionValue',
                       									    selectedOption : birthdayType,
                       									    optionsCaption: '��ѡ��...'">
													</select>
								</div>
								<div class="three columns">
										<label>�ж����</label> 
										<input type="text" data-bind="value : selectedMovingYear" />
										<!-- <label>����</label> <input type="text" data-bind="datepicker : {dateFormat : 'mm-dd'}, value : birthDayValue" />
								</div>
								 
								  -->
								<div class="three columns">
										<label>�ж��·�</label> 
													<select data-bind="options: $root.monthArray,
                       									    selectedOption : selectedMovingMonth,
                       									    value : selectedMovingMonth,
                       									    optionsCaption: '��ѡ��...'">
									</select>
								</div>
							</div>
							<div class="row">
							
								<div class="three columns">
									<label>��ѵԤԼ</label>
												<select data-bind="options: $root.phases,
                      											   optionsText: 'name',
                       											   value: phase,
                       											   optionsValue : 'id',
                       											   selectedOption : phase,
                       											   optionsCaption: '��ѡ��...'">
                       							</select>
									
								</div>
								<div class="three columns">
									
								</div>
							</div>
						</div>
						<hr>
						<div class="row">
							<div class="six columns centered">
								<a class="small blue button" href="#" data-bind="click : searchCompanyForConditions"> �������������Ŀͻ� </a>
								<a class="small blue button" href="#" data-bind="click : clearAllConditions"> ��������������� </a>
							</div>
						</div>
					</div>
				</div>

				<div class="app-wrapper ui-corner-top" id="companyList">
					<div class="blue module ui-corner-top clearfix">
						<h2>�˿��б�</h2>
						<h2 class="right"><a class="small white button" data-bind="click : $root.openManageCompanyDialog">¼���µĹ˿�����</a></h2>
					</div>
					<div class="content">
						<div class="row">
							���ҵ�<label class="green label" data-bind="text: totalPagesCount"></label>
							�� �� 	<label class="yellow label" data-bind="text: totalCompanyCount"></label>���˿ͣ���ǰ��<label data-bind="text : currentIndex" class="label ">6</label>ҳ
						</div>
						<br>
						<ul class="smartlist nice" data-bind="foreach: companyList">
							<li>
								<div class="row">
											<div class="four columns text-center">
												<a style="margin-left: 20px;" data-bind="click:$root.showDetail.bind($data, 'anything')">
														<h6><span data-bind="text : name"></span></h6>
													</a>
											</div>
											<div class="one columns" style="top:35%">
													<h7><span data-bind="text : contactor"></span></h7>
											</div>
											<div class="three columns">
												<label class="input-checkbox"> 
													<img style="margin-left: 45px" alt="�绰����" data-bind="attr: { 'src' : phoneSrc }, visible : mobilePhoneSrc == '' ">
													<img style="margin-left: 45px" alt="�ֻ�����" data-bind="attr: { 'src' : mobilePhoneSrc }, visible : mobilePhoneSrc != '' ">
													<span data-bind="text : mobilePhone, visible : mobilePhone != '' "></span>
													<span data-bind="text : phone, visible : mobilePhone == '' && phone != '' "></span>
												</label>
											</div>
											<div class="two columns" style="top:35%">
												<div class="star listStar" data-bind="attr : {'star' : star, 'id': id}"></div>
											</div>
											<div class="two columns" style="top:35%">
												<div class="row">
													<a class="small green button" data-bind="click : $root.trackCustomer, css: { red : id == $root.selectedId()}" href="#">�鿴����</a>
												</div>
											</div>
								</div>
							</li>
						</ul>
						<div data-bind="visible : companyList().length == 0">
							<h4 class="text-center">û�в�ѯ�����������Ĺ˿���Ϣ��</h4>
							<hr>
						</div>
						<div class="row" id="companyPagenavigation"></div>
					</div>
				</div>
				<div id="selectedCompany" class="row" data-bind="with : selectedCompany" style="display: none;">
					<div class="app-wrapper ui-corner-top">
						<div class="gray module ui-corner-top clearfix">
							<h2>
								��ϸ��Ϣ<span class="subheader line" data-bind="text : name"></span>
							</h2>
							<h2 style="margin-left : 200px">
								<div id="detailStar" class="star" data-bind="attr : {'star' : star, 'companyId' : id}"></div>
							</h2>
							<h2 class="right">
								<a class="small blue button" data-bind="click : $root.backToCustomerList" href="#">���ؿͻ��б�</a>
							</h2>
						</div>
						<div class="content app-wrapper">
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
									<a href="#">�ͻ�������Ϣ</a> 
								</h4>
								<div class="content">
									<div class="row">
										<div class="three columns">
											<label>�ͻ����:</label>  <span  data-bind="text : id" ></span>
										</div>
										<div class="three columns">
											<a data-bind="attr : {url : oteUrl}, click : $root.showDetail.bind($data, 'ote') "><span data-bind="text : oteUrl"></span></a>
										</div>
										<div class="three columns">
											<a data-bind="attr : {href : oteUrl}, click : $root.showDetail.bind($data, 'fe') "><span data-bind="text : fEurl"></span></a>
										</div>
										<div class="three columns">
											<a data-bind="attr : {href : oteUrl}, click : $root.showDetail.bind($data, 'ganji') "><span data-bind="text : ganjiUrl"></span></a>
										</div>
									</div>
									<hr>
									<div class="row">
										<div class="row">
											<div class='three columns'>
												<label>���� </label><input type="text"
													data-bind="value : name" disabled="disabled">
											</div>
											<div class='two columns'>
												<label>���� </label><input type="text"
													data-bind="value : distinct">
											</div>
											<div class='seven columns'>
												<label>��ַ </label> <input type="text"
													data-bind="value : address">
											</div>
										</div>

										<div class="row">
												
										
										</div>
										<div class="row">
											<div class='three columns'>
												<label>��ϵ�� </label><input type="text"
													data-bind="value : contactor">
											</div>
											<div class='three columns'>
												<label>�ֻ�</label>
												<label class="input-checkbox" data-bind="visible : !mobilePhone"> 
													<img alt="�绰����" data-bind="attr: { 'src' : mobilePhoneSrc }  ">
												</label>
												<input type="text" data-bind="value : mobilePhone, visible : mobilePhone " />
											</div>
											<div class='three columns'>
												<label>�̶��绰</label>
												<label class="input-checkbox" data-bind="visible : !phone"> 
													<img alt="�绰����" data-bind="attr: { 'src' : phoneSrc }">
												</label>
												<input type="text" data-bind="value : phone, visible : phone" />
											</div>
											
											<div class='three columns' >
												<label>�����ʼ�</label>
												<label class="input-checkbox" data-bind="visible : email == ''"> 
													<img alt="��������" data-bind="attr: { 'src' : emailSrc }">
												</label>
												<input type="text" data-bind="value : email, visible : email" />
											</div>
										</div>
									</div>
									<div class="row">
										<label>��˾���</label>
										<textarea data-bind="value : description"></textarea>
									</div>
									<br>
									<h4 class="text-success">����ͻ���Ϣ</h4>
									<hr>
									<div id="addtionalCompanyInformation"
										data-bind="with : $root.addtion">
										<div class="row">
											<div class="three columns">
												<label>�ͻ�����</label>
												<select data-bind="options: $root.companyTypes,
                      											   optionsText: 'optionText',
                       											   value: companyLevel,
                       											   optionsValue : 'optionValue',
                       											   selectedOption : companyLevel,
                       											   optionsCaption: 'ѡ��ͻ�����'"
                       									class="required">
                       							</select>
											</div>
										</div>
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
										<br>
										<h4 class="text-success">�ж��˿�</h4>
										<hr>
										<div class="row">
											<div class="three columns">
												<label>Ժ������</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : bossBirthday" />
											</div>
											<div class="three columns">
												<label>��������</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : firstKidBirthday" />
											</div>
											<div class="three columns">
												<label>��������</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : loverBirthday" />
											</div>
											<div class="three columns">
											</div>
										</div>
										<div class="row">
											<div class="three columns">
												<label>��˾����</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : companyAnniversary" />
											</div>
											<div class="three columns">
												<label>��������</label> <input  type="text" data-bind="datepicker : {dateFormat : 'mm-dd'},value : merryAnniversary" />
											</div>
											<div class="three columns">
												
											</div>
										</div>
										<div class="row">
											<div class="six columns">
												<label>��ע</label> 
												<textarea rows="1" data-bind=" value : comments"></textarea>
											</div>
										</div>
										<div class="row">
											<a class="small blue button" data-bind="click : $root.saveAddition">����ͻ���Ϣ</a>
										</div>
									</div>
								</div>

								<h4>
									<a href="#">�������</a>
								</h4>
								<div class="app-wrapper content">
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
															data-bind="text : name" ></span>
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
															data-bind="text : name" ></span>
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
								<div class="app-wrapper content">
									<div class="row">
										<div class="right">
											<a class="small blue button" data-bind="click : $root.addHistory">�����µ�ͨ����¼</a>
										</div>
									</div>
									<div class="row">
										<table class="display compact" id="phoneCallHistoryListTable">
											<thead>
												<tr>
													<th class="text-center">��¼���</th>
													<th class="text-center">����ʱ��</th>
													<th class="text-center">�´θ���ʱ��</th>
													<th class="text-center">��ͨ����</th>
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
									<a href="#">��ѵ��¼</a>
								</h3>
								<div class="app-wrapper content">
									<div class="row">
										<div class="right">
											<a class="small blue button" data-bind="click : $root.addLearningHistory">�����µ���ѵ��¼</a>
										</div>
									</div>
									<div>
										<table class="display compact" id="learningHistoryListTable">
											<thead>
												<tr>
													<th class="text-center">��¼���</th>
													<th class="text-center">����</th>
													<th class="text-center">��ʼʱ��</th>
													<th class="text-center">����ʱ��</th>
													<th class="text-center">�в�����</th>
													<th class="text-center">�߲�����</th>
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
							<div id="phoneCallDialog" title="�绰��¼����" style="display:none;" data-bind="with : $root.phoneCall">
									<form id="historyForm">
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
							</div>
							<div id="selectedCompanyDialog" title="��˾����" style="display:none;" data-bind="with : $root.newCompany">
							
									<form id="companyManageForm">
										<div class="row">
											<label class="label"> ��˾��� ��</label> <br> <span data-bind="text : id"></span>
											</div>
										<br>
										<hr>
										<div class="row">
											<div class="four columns">
												<label>ʡ/ֱϽ��</label> 
												<select class="required" data-bind="options: $root.provinces, optionsCaption: '��ѡ��...', optionsText: 'name', optionsValue: 'id', value: $root.selectedProvinceInDialog, valueAllowUnset: true"></select>
											</div>
											<div class="four columns">
												<label>��/��</label> 
												<select data-bind="options: $root.citiesForDialog, optionsCaption: '��ѡ��...', optionsText: 'name', optionsValue: 'id', value: cityId, valueAllowUnset: true"></select>
											</div>
											<div class='four columns'>
												<label>���� </label><input type="text" data-bind="value : name">
											</div>
											
										</div>
										
										<div class="row">
											<div class='four columns'>
												<label>��ϵ�� </label>
												<input type="text" data-bind="value : contactor">
											</div>
											<div class='four columns'>
												<label>�ֻ�</label>
												<input type="text" data-bind="value : mobilePhone">
											</div>
											<div class='four columns'>
												<label>�̶��绰</label>
												<input type="text" data-bind="value : phone">
											</div>
										</div>
										<div class="row">
											<div class='six columns'>
												<label>�����ʼ�</label>
												<input type="text" data-bind="value : email">
											</div>
										</div>
										<div class="row">
											<label>��ַ </label> 
											<input type="text" data-bind="value : address">
										</div>
										<div class="row">
											<label>��˾���</label> 
											<textarea data-bind="value : description"></textarea>
										</div>
									</form>
							</div>
							<div id="learningHistoryDialog" title="��ѵ��¼����" style="display:none;" data-bind="with : $root.learningHistory">
									<form id="learningHistoryForm">
										<div class="row">
											<div class="four columns">
												<label>ѡ����ѵ״̬</label>
												<select data-bind="options: $root.traningStatus,
                      											   optionsText: 'optionText',
                       											   value: status,
                       											   optionsValue : 'optionValue',
                       											   selectedOption : status,
                       											   optionsCaption: 'ѡ��״̬'"
                       									class="required">
                       							</select>
											</div>
										</div>
										<div class="row">
											<div class="four columns">
												<label>��������</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : signUpDate">
											</div>
											<div class="four columns">
												<label>�׸�����</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : firstPayDate">
											
											</div>
											<div class="four columns">
												<label>�׸����</label>
												<input class="number" type="text" data-bind="value : firstPayAmount" />
											</div>
										</div>
										<div class="row">
											<div class="four columns">
												<label>�ܿ�</label>
												<input class="number" type="text" data-bind="value : totalAmount" /></div>
											<div class="four columns">
												<label>Ƿ��</label>
												<input class="number" type="text" data-bind="value : debtAmount" /></div>
											<div class="four columns">
												<label>��������</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : payDebtDate">
											</div>
											
										</div>
										<div class="row">
											<div class="four columns">
												<label>��������</label>
												<input class="number" type="text" data-bind="value : currentMonthPolicy" />
											</div>
										</div>
										<div class="row">
											<div class="four columns">
												<label>����</label>
												<select data-bind="options: $root.phases,
                      											   optionsText: 'name',
                       											   value: phase.id,
                       											   optionsValue : 'id',
                       											   selectedOption : phase.id,
                       											   optionsCaption: 'ѡ������'"
                       									class="required">
                       							</select>
											</div>
											<div class="four columns">
												<label>�в�����</label>
												<input class="number" type="text" data-bind="value : middleLevelManagerCount" />
											</div>
											<div class="four columns">
												<label>�߲�����</label>
												<input class="number" type="text" data-bind="value : highLevelManagerCount" />
											</div>
										</div>
										
										<div class="row">
											<div class="six columns">
												<label>��ʼ����</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : startDate">
											</div>
											<div class="six columns">
												<label>��������</label>
												<input type="text" data-bind="datepicker : {showSecond : true, dateFormat : 'yy-mm-dd',stepHour : 1,stepMinute : 1,stepSecond : 1}, value : endDate">
											</div>
										</div>
										<div class="row">
											<label>��ע</label>
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
	<script src="/ls/js/jquery.pagination.js"></script>
	<script>
		$(document).bind('keypress.alt_1', function() {
			
			$('#searchboxDialog').dialog({
				modal : true,
				dialogClass : 'noTitle',
				height : 'auto',
				width : 'auto',
				maxHeight: 'auto',
				maxWidth:'auto',
				minHeight:'auto',
				minWidth:'auto',
				position : ['center', 200]
			});
		});
	
		$(document).ready( function() {
					
					$("#searchWrapper").accordion({
						collapsible: true
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
					
					var SearchConditions = function() {
						this.id = '';
					};
					
					var Problem = function(id, name) {
						var self = this;
						
						self.id= id;
						self.name = name;
						self.checked = ko.observable(false);
						self.selectedItem = ko.observable('');
						
					};
					
					var Company = function(id, name, contactor, email, emailSrc, phone, phoneSrc, star, address, distinct, problems, oteUrl, status, mobilePhone, mobilePhoneSrc,description, ganjiUrl, fEurl) {
						var self = this;
						
						self.id = id;
						self.name = name;
						self.contactor = contactor;
						self.email = email;
						self.emailSrc = emailSrc;
						self.phone = phone;
						self.phoneSrc = phoneSrc;
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
						self.ganjiUrl = ganjiUrl;
						self.fEurl = fEurl;
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
						 self.status ='';
							
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
						this.signUpDate = '';
						this.firstPayDate = '';
						this.firstPayAmount = '';
						this.totalAmount = '';
						this.debtAmount = '';
						this.payDebtDate = '';
						this.currentMonthPolicy = '';
						this.status = '';
					};
					
					var AdvanceSearch = function() {
						this.appointStartDate = '';
						this.appointEndDate = '';
						this.birthdayType = '';
						this.birthDayValue =  '';
						this.phase ='';
						this.selectedMovingMonth = '';
						this.selectedMovingYear = '';
					};
					
					var CompanyModel = function() {
						
						var self = this;
						self.advanceSearch = ko.observable(new AdvanceSearch());
						self.birthdayTypes = ko.observableArray([]);
						self.companyTypes = ko.observableArray([]);
						self.problemCategories = ko.observableArray([]);
						self.starLevelOperators = ko.observableArray([]);
						self.starLevelOperator = ko.observable('');
						self.companyList = ko.observableArray([]);
						self.currentIndex = ko.observable(1);
						self.pageIndexToGo = ko.observable('');
						self.selectedProblemCategory = ko.observable('');
						self.totalPagesCount = ko.observable('1');
						self.totalCompanyCount = ko.observable('0');
						self.starInput = ko.observable(0);
						self.distinctInput = ko.observable();
						self.seachCompany = ko.observable('');
						self.searchContactor =  ko.observable('');
						self.searchId = ko.observable('');
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
						self.monthArray = ['1','2','3','4','5','6','7','8','9','10','11','12'];
						
						self.selectedProvinceInDialog = ko.observable(new Province());
						self.searchConditions = ko.observable(new SearchConditions());
						self.customerStatus = ko.observable('');
						
						self.selectedId = ko.observable('');
						self.traningStatus = ko.observableArray([]);
						self.clearAllConditions = function() {
							
							self.starInput(0); 
							self.seachCompany(''); 
							self.searchContactor('');
							self.selectedCity('');
							self.selectedProvince('');
							self.searchId('');
							self.selectedProblemCategory('');
							self.customerStatus('');
							self.starLevelOperator('');
							self.advanceSearch(new AdvanceSearch());
							
						};
						
						self.cities = ko.computed(function() {
							var cityOptions;
							if (self.selectedProvince()) {
								
								$.each(self.provinces(), function(i, n){
									if ( n.id == self.selectedProvince()) {
										cityOptions = n.cities;
										console.debug(cityOptions);
									}
								});
							} else {
								
								self.selectedCity(null);
								
							}
							
							return cityOptions;
						});
						
						self.citiesForDialog = ko.computed(function() {
							var cityOptions;
							
							if (self.selectedProvinceInDialog()) {
								$.each(self.provinces(), function(i, n){
									if ( n.id == self.selectedProvinceInDialog()) {
										cityOptions = n.cities;
									}
								});
							} 
							
							return cityOptions;
						});
						
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
									'�����¼' : function() {
										self.saveCompany();
									},
									'�رմ���' : function() {
										self.closeDialog('selectedCompanyDialog');
									}
								},
								close : function() {
									$('#wizard').show();
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
										fail('����ѧϰ��¼��ʧ��.');
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
										
									} 
									
									handleStanderdResponse(data);
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
										fail('���ص绰��¼��ʧ��.');
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
							if(window.confirm('�����Ҫɾ��������¼��')) {
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
							if(window.confirm('�����Ҫɾ��������¼��')) {
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
											showToUser = (  self.selectedCompany().name + " ������������: <b>" + item.name + "</b>��");
										} else {
											showToUser =  self.selectedCompany().name + " ȥ����������:<b>" + item.name + "</b>��";
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
										var showToUser =  self.selectedCompany().name + "�ѱ���Ϊ <b>" + score + "</b>�ǡ�";
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
						
						
						self.searchCompanyForConditions = function() {
							self.currentIndex(1);
							self.searchCompany();
							
							if ($('#companyList').is(':hidden')) {
								self.toggleListAndDetail();
							}
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
									'�����¼' : function() {
										self.savePhoneCallHistory();
									},
									'�رմ���' : function() {
										self.closeDialog('phoneCallDialog');
									}
								},
								close : function() {
									$('#wizard').show();
								}
							});
							
							$('#learningHistoryDialog').dialog({
								autoOpen : false,
								modal : true,
								width : 743,
								height : 1040,
								open : function(e) {
									changeButtonStyleForPopup(e);
								},
								
								buttons : {
									'�����¼' : function() {
										self.saveLearningHistory();
									},
									'�رմ���' : function() {
										self.closeDialog('learningHistoryDialog');
									}
								},
								close : function() {
									$('#wizard').show();
								}
							});
							
							self.selectedId(item.id);
							
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
						
						self.selectedCity = ko.observable('');
						
						self.init = function() {
							
							 $('#starInput').raty({
								 
						           click: function(score, evt) {
						                self.starInput(score);
						           }
							 
						     });
							
							self.loadProblemConstants('Ա������');
							self.loadProblemConstants('�˿�����');
							self.loadProblemConstants('��������');
							
							$.ajax({
								url : 'findAllProvinces.ls',
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
								url : '/ls/findDropDownDataSouce.ls',
								data : {identityType : 'company_type'},
								success : function(data) {
									self.companyTypes(data);
								}
							});
							
							$.ajax({
								url : '/ls/findDropDownDataSouce.ls',
								data : {identityType : 'star_level_condition'},
								success : function(data) {
									self.starLevelOperators(data);
								}
							});
							$.ajax({
								url : '/ls/findDropDownDataSouce.ls',
								data : {identityType : 'birthday_type'},
								success : function(data) {
									self.birthdayTypes(data);
								}
							});
							$.ajax({
								url : '/ls/findDropDownDataSouce.ls',
								data : {identityType : 'traning_status'},
								success : function(data) {
									self.traningStatus(data);
								}
							});
							
							$.ajax({
								url : '/ls/findProblemCategories.ls',
								success : function(data) {
									self.problemCategories(data);
								}
							});
							
							
						};
						
						self.loadProblemConstants = function(type) {
							
							$.ajax({
								url : 'findAllProblems.ls',
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
						
						self.showDetail = function(type, item, event) {
							
							if ( type === 'ote') {
								window.open(item.oteUrl, '_blank');	
								return;
							} else if ( type === 'fe') {
								window.open(item.fEurl, '_blank');	
								return;
							} else if ( type === 'ganji') {
								window.open(item.ganjiUrl, '_blank');	
								return;
							} else {
								
								if (item.oteUrl) {
									window.open(item.oteUrl, '_blank');	
								} else if (item.fEurl) {
									window.open(item.fEurl, '_blank');	
								} else if (item.ganjiUrl) {
									window.open(item.ganjiUrl, '_blank');	
								} else {
									fail("�����˾û���κ�������Դ������ʾ��");
								}
							}
						};
						
						self.searchCompany = function() {
							$("#searchWrapper").accordion({
								active: false
							});
							$.ajax({
								url : '/ls/user/loadCompanyInPage.ls',
								data : {pageNumber : self.currentIndex(), 
										starInput : self.starInput(), 
										seachCompany : self.seachCompany(), 
										searchContactor : self.searchContactor(),
										cityId : self.selectedCity(),
										provinceId : self.selectedProvince(),
										starLevelOperator : self.starLevelOperator(),
										searchId : self.searchId(),
										customerStatus : self.customerStatus(),
										selectedProblemCategory : self.selectedProblemCategory(),
										advanceSearch : JSON.stringify(self.advanceSearch())
										},
								success : function(data) {
									self.fillCompany(data);
									
										$('#companyPagenavigation').pagination(
												companyModel.totalCompanyCount(), {
												num_edge_entries: 1, //��Եҳ��
												num_display_entries: 15, //����ҳ��
												callback: self.pageselectCallback,
												items_per_page: 10, //ÿҳ��ʾ1��
												prev_text: "��һҳ",
												next_text: "��һҳ",
												current_page : self.currentIndex() - 1,
												load_first_page : false
											});
								},
								
								error : function() {
									
								}
							});
						};
						
						self.pageselectCallback = function(page_index, jq){
							self.currentIndex(page_index + 1);
							self.searchCompany();
							return false;
						};
						
						self.fillCompany = function(data) {
							
							self.companyList.removeAll();

							$.each(data.elements, function(index, value) {
								
								var new_phoneSrc =  value.phoneSrc;
								var new_emailSrc =  value.emailSrc;
								
								var problems = new Array();
								
								$.each(value.problems, function(i, n) {
									problems.push(n.name);
								});
								
								var company = new Company(value.id, value.name, value.contactor, value.email, new_emailSrc, value.phone, new_phoneSrc, value.star, value.address, value.area, problems, value.oteUrl, value.status, value.mobilePhone, value.mobilePhoneSrc, value.description, value.ganjiUrl, value.fEurl);

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
					
					var today = new Date();
					var tomorrow = new Date();
					tomorrow.setDate(today.getDate() + 1);
					
					var todayStanderdString = $.datepicker.formatDate('yy-mm-dd', today);
					var tomorrowStanderdString = $.datepicker.formatDate('yy-mm-dd', tomorrow);
					
					var advanceSearch = new AdvanceSearch();
					advanceSearch.appointStartDate = todayStanderdString;
					advanceSearch.appointEndDate = tomorrowStanderdString;
					companyModel.advanceSearch(advanceSearch);
					
					companyModel.searchCompany();
					companyModel.advanceSearch(new AdvanceSearch());
					
					success("���¹˿���Ҫ�ڽ���������١�");
					
					$('#submit').click(function() {
						companyModel.searchId($('#search').val());
						companyModel.searchCompany();
						$('#searchboxDialog').dialog("close");
					});
					
					
				});
		
		function activeCurrentMenuItem() {
			$('#customerCenter').addClass('active');
		}
	</script>
</body>
</html>
