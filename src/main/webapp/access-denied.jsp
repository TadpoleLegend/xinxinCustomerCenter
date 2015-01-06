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
<title>没有权限</title>
<link rel="stylesheet" href="/ls/css/common.css">
<!-- Required CSS Files -->
<link rel="stylesheet" href="/ls/css/styles.css">
<link rel="stylesheet" href="/ls/css/style-theme.css" media="print" />
<link rel="stylesheet" href="/ls/css/messenger.css">
<link rel="stylesheet" href="/ls/css/messenger-theme-future.css">

</head>
<body>
	<header id="brand">
		<div class="container">
			<div class="row">
				<div class="appname hide-on-phones">欣心客户数据中心</div>
			</div>
		</div>
	</header>
<nav class="site-nav" id="nav">
	<div class="row">
		<ul id="dropdown">
			<li></li>
		</ul>
	</div>
</nav>
	<section class="mainbg">
		<div class="container">
				<div class="row">
					<h4 class="text-center">抱歉！你没有权限访问这个页面！你可以 <a href="javascript:history.go(-1);">返回上一页</a></h4>
					
					<hr>
				</div>
		</div>		
	</section>

	<!-- Footer -->


<footer role="footer">
		<div class="container">
			<div class="row">
				<div class="eight columns">
					<p>Copyright &copy; 2009 - 2013 XinXin Enterprises. All rights reserved.</p>
					<address>
						<a rel="external" class="display-inline" title="www.baidu.com" href="www.baidu.com">Privacy Policy <i class="icon-external-link"></i></a>
						<a rel="external" title="www.baidu.com" href="www.baidu.com">Terms and Conditions <i class="icon-external-link"></i></a>
						<a rel="external" title="www.baidu.com" href="www.baidu.com">www.baidu.com <i class="icon-external-link"></i></a>
					</address>
				</div>
				<div class="four columns"><h6 class="logo smart">Powered by SMART</h6></div>
			</div>
		</div>
	</footer>
	
	<script>

	$(document).ajaxStart(function() {
		Common.prototype.loadAjaxLoader("LOADING....");
	}).ajaxStop(function() {
		Common.prototype.closeAjaxLoader();
	});
	
</script>
</body>
</html>
