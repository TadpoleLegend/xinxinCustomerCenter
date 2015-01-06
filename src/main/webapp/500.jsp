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
<meta charset="utf-8" />
<!-- Set the viewport width to device width for mobile -->
<meta name="viewport" content="width=device-width" />
<title>500</title>
<!-- Required CSS Files -->
<link rel="stylesheet" href="/ls/css/styles.css">
<link rel="stylesheet" href="/ls/css/style-theme.css" media="print" />
</head>
<body>
	<header id="brand">
		<div class="container">
			<div class="row">
				<address></address>
			</div>
		</div>
	</header>
	<section class="mainbg">
		<div class="container">
			<div class="row">
				<div class="six columns centered">
					<div class="app-wrapper ui-corner-top">
						<div class="blue module ui-corner-top clearfix">
							<h2>服务器处理错误</h2>
						</div>
						<div class="content">
							<div style="color: red">
								<s:fielderror />
							</div>
							<form class="nice custom">
								<p>
									服务器在处理的时候发生了错误，请尝试以下，如果问题任然存在请联系技术人员解决。
								<p>
								
								<p>
									1, 按 F5键刷新页面，重试你的操作。
								</p>
								
								<p>
									2, 退出系统重新登录再重试你的操作。
								</p>
								
								<p>
									3, 仔细思考下是不是有数据错误
								</p>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>