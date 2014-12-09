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
<title>Access denied</title>
<link rel="stylesheet" href="/ls/css/common.css">
<!-- Required CSS Files -->
<link rel="stylesheet" href="/ls/css/styles.css">
<link rel="stylesheet" href="/ls/css/style-theme.css" media="print" />
<link rel="stylesheet" href="/ls/css/messenger.css">
<link rel="stylesheet" href="/ls/css/messenger-theme-future.css">

<!-- Required JS Files -->
<!--  <script src="/ls/js/jquery-2.1.0.js"></script> -->
<script src="/ls/js/jquery-1.10.2.js"></script>
<script src="/ls/js/jquery-ui-1.10.4.custom.js"></script>
<script src="/ls/js/jquery.dataTables.js"></script>
<script src="/ls/js/knockout-3.1.0.js"></script>
<script src="/ls/js/jquery.loader.js"></script>
<script src="/ls/js/messenger.min.js"></script>
<script src="/ls/js/messenger-theme-future.js"></script>
<script src="/ls/js/jquery.validate.js"></script>
<script src="/ls/js/icheck.js"></script>
<script src="/ls/js/common.js"></script>
<script src="/ls/js/knockout-jqueryui.min.js"></script>
<script src="/ls/js/jquery.hotkeys.js"></script>

<script>
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-top',
	    theme: 'future'
	};
	
	function headerDropdown () {
		$(".js-header-dropdown-trigger").click(function() {
		    $(".js-header-dropdown-menu")
		        .show()
		        .position({
		            my: "right top",
		            at: "right bottom",
		            of: $(".js-header-dropdown-trigger")
		        });
		    $(document).one("click", function() {
		        $(".js-header-dropdown-menu").hide();
		    });
		    return false;
		});
	}
	
	$(document).ready(function() {
		
		headerDropdown();
	});
</script>

</head>
<body>
	<header id="brand">
		<div class="container">
			<div class="row">
				<div class="appname hide-on-phones"></div>
			</div>
		</div>
	</header>
<nav class="site-nav" id="nav">
	<div class="row">
		<ul id="dropdown">
			<li><a title="" href="/ls/user/load.ls">Customer Management</a></li>
			<li><a title="" href="/ls/grab/load.ls">Manually Grab</a></li>
			<li><a title="" href="/ls/admin/configuration.ls">Problem Configuration</a></li>
			<li><a title="" href="/ls/admin/loadUser.ls">User Management</a></li>
			<li><a title="" href="/ls/wccheck/loadApproveCustomer.ls">Approve Customer</a></li>
		</ul>
	</div>
</nav>
	<section class="mainbg">
		<div class="container">
				<div class="row">
					<h4 class="text-center">抱歉！你没有权限！</h4>
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
