<%@ page contentType="text/html;charset=gbk"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>	<html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>	<html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]> <html class="no-js ie9" lang="en"> <!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta name="viewport" content="width=device-width" />
<title>登陆</title>
<%@ include file="jsps/common/head.jsp"%>
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
	<section class="mainbg" style="background-image:url('images/login_bg.jpg')">
		<div class="container">
			<div class="content">
				<div class="row">
					<div class="four columns">
						<div class="app-wrapper ui-corner-top">
							<div class="blue module ui-corner-top clearfix">
								<h2>输入用户名和密码</h2>
							</div>
							<div class="content">

								<form action="j_spring_security_check" method="POST">
									<div class="row">
										<label for="username" class="required">用户名</label> <input type="text" id="username" name="j_username" autocorrect="off" autocapitalize="off" />
									</div>
									<label for="password" class="required custdrop">密码</label>
									<div class="row">
										<input type="password" id="password" name="j_password" autocorrect="off" autocapitalize="off" />
									</div>
									<br>
									<div class="row">
										<button type="submit" class="nice radius medium blue button" value="Log In">登陆</button>
										<a class="active tertiary" data-bind="click : forgetPassword">忘记密码?</a> <label for="checkbox1" class="right"> </label>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="eight columns" >

						<div class="app-wrapper ui-corner-top" style="height:422px; background-image:url('images/login_bg.jpg')">
							<div class="blue module ui-corner-top clearfix">
								<h2>系统公告</h2>
							</div>
							<div class="content">
								<h4></h4>
								<hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr>
								<p></p>
								<p></p>
								<p></p>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="jsps/common/footer.jsp"%>
</body>
</html>
