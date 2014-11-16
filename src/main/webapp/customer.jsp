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
<title>Customer Management</title>

<s:include value="/jsps/common/head.jsp" />

</head>
<body>
	
	<header id="brand">
	<div class="container">
		<div class="row">
			<div class="appname hide-on-phones">Routing Guide</div>
			<address>
			<!-- 
				<span> <strong></strong>欢迎用户， <s:property value="name" /><a href="user/logoff.ls?username=<s:property value="username" />" class="login-secure line">退出登陆</a>
				</span> -->
			</address>
		</div>
	</div>
</header>
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container">
			<div class="row">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>江</h2>
					</div>
					<div class="content">
						<div data-bind="attr : {'class': id}" style="" class="6">
									<div class="row companydetail">
										<div class="app-wrapper ui-corner-top">
											<div class="gray module ui-corner-top clearfix">
												<h2>
													详细信息<span data-bind="text : name" class="subheader line">吴江区盛泽镇欧格兰顿美容养生馆</span>
												</h2>
											</div>
											<div class="content">
												<div data-bind="text : id, visible : false" class="currentId" style="display: none;">6</div>
												<div class="row">
													<div class="six columns">
														<div class="row">
															<div class="two columns"><label>区域 </label><input type="text" data-bind="value : distinct"></div>
															<div class="ten columns"><label>地址 </label> <input type="text" data-bind="value : address"></div>
														</div>
														<div class="row">
															<label>电子邮件</label><label class="input-checkbox"> <img data-bind="attr: { 'src' : email_src }" alt="电子邮箱" src="/ls/img/20140718161333952.jpg">
															</label>
														</div>

													</div>
													<div class="six columns">
														<label>公司简介</label>
														<textarea data-bind="text : name" name="ex-textarea-4" class="tall">吴江区盛泽镇欧格兰顿美容养生馆</textarea>
													</div>
												</div>
												<div class="row">
													<div class="three columns">
														<div class="app-wrapper ui-corner-top">
															<div class="module ui-corner-top clearfix">
																<h2>客户关注点</h2>
															</div>
															<div class="content">
																<ul>
																	<li data-bind="foreach : problems">
																	<div class="row">
																		<div class="ten columns">
																			<label class="btn btn-block btn-lg btn-success">
																			<span data-bind="text : $data">关注点1</span>
																		</label>
																		</div>
																		<div class="two columns">
																			<button class="tiny blue button">删除</button>
																		</div>
																	</div>
																		
																	
																	<div class="row">
																		<div class="ten columns">
																			<label class="btn btn-block btn-lg btn-success">
																			<span data-bind="text : $data">关注点2</span>
																		</label>
																		</div>
																		<div class="two columns">
																			<button class="tiny blue button">删除</button>
																		</div>
																	</div>
																		
																	</li>
																	<li>
																		<div class="row">
																		<div class="ten columns">
																			<select data-bind="options:$root.allProblemsConstant, optionsValue : 'id', optionsText : 'name', value : $parent.selectedProblem"><option value="1">关注点1</option><option value="2">关注点2</option><option value="3">关注点3</option><option value="4">关注点4</option><option value="5">关注点5</option> </select> 
																		</div>
																		<div class="two columns">
																			<a data-bind="click: $root.addProblem" class="tiny blue button" href="#">增加</a>
																		</div>
																		</div>
																	</li>
																</ul>
																<br>
															</div>
														</div>
													</div>
													<div class="three columns">
														<div class="app-wrapper ui-corner-top">
															<div class="module ui-corner-top clearfix">
																<h2>跟踪进度</h2>
															</div>
															<div class="content">
																<label for="ex-chx-a" class="input-checkbox selected"> <input type="checkbox" value="1" id="ex-chx-a" name="ex-checkbox"> 第一次沟通
																</label> <label for="ex-chx-b" class=".selected input-checkbox"> <input type="checkbox" value="2" id="ex-chx-b" name="ex-checkbox"> 第二次沟通
																</label>
															</div>
														</div>
													</div>
													<div class="six columns">
														<div class="app-wrapper ui-corner-top">
															<div class="module ui-corner-top clearfix">
																<h2>跟踪情况(心得体会)</h2>
																<h2 class="right">
																	<a class="nice radius blue tiny button" href="">保存</a>
																</h2>
															</div>
															<div class="content">
																<textarea name="ex-textarea-4" class="tall">革命尚未成功，同志仍需努力！</textarea>
															</div>
														</div>
													</div>
												</div>
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
		});
	</script>
</body>
</html>
