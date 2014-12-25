<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>	<html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>	<html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]> <html class="no-js ie9" lang="en"> <!--> <html lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<!-- Set the viewport width to device width for mobile -->
	<meta name="viewport" content="width=device-width" />
	<title>500 </title> 
	<!-- Required CSS Files -->
<link rel="stylesheet" href="/ls/css/styles.css">
<link rel="stylesheet" href="/ls/css/style-theme.css" media="print" />
</head>
<body>
	<header id="brand">
		<div class="container">
			<div class="row">
				<div class="appname hide-on-phones">Application Name</div>
				<address>

				</address>
			</div>
		</div>
	</header>
<section class="mainbg">
	<div class="container">
		<div class="row">
			<div class="six columns centered">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>500:系统错误</h2>
					</div>
						<div class="content">
							<div style="color: red">
								<s:fielderror />
							</div>
							<form class="nice custom">

								<p>你在处理的时候发生了一个系统错误，请检查你的错误<b>重试</b>,或者联系技术人员解决。</p>

								<p>如果错误任然存在，请发送邮件告诉我们错误内容。</p>

								<label for="email">Email Address</label>
								<input type="email" id="email" class="medium input-text" />

								<label for="comment">Comment</label>
								<textarea id="comment" class="short"></textarea>

								<p>
									<input type="submit" class="nice radius medium blue button" value="Send" />
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