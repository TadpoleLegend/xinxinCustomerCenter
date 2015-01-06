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
<title>欣心顾客数据中心</title>
<s:include value="/jsps/common/head.jsp" />

</head>
<body>
	<s:include value="/jsps/common/brand.jsp" />
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container" id="statisticsModelContainer">
			<div class="row">
				<div style="color: red">
					<s:fielderror />
				</div>
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>数据统计 （全国美容行业公司个数排名前20）</h2>
						<h2 class="right"></h2>
					</div>
					<div class="content">
						<canvas id="canvas"></canvas>
					</div>
				</div>

			</div>
		</div>
	</section>
	<s:include value="/jsps/common/footer.jsp" />
	<script src="/ls/js/Chart.min.js"></script>
	<script>
		//var barChartData = {"datasets":[{"data":["1110","233"],"fillColor":"rgba(220,220,220,0.5)","strokeColor":"rgba(220,220,220,0.8)"}],"labels":["Shanghai","BeiJing"]}

		$(document).ready(function() {

			var StatisticsModel = function() {
			};

			var statisticsModel = new StatisticsModel();

			var $statisticsModelContainer = $('#statisticsModelContainer')[0];
			ko.applyBindings(statisticsModel, $statisticsModelContainer);
			
			$.ajax({
				url : 'findTop10CityCompanyCount.ls',
				success : function(data) {
					if (data) {
						var ctx = document.getElementById("canvas").getContext("2d");
						window.myBar = new Chart(ctx).Bar(data, {
							responsive : true
						});
					} 
				}
			});
			
		});

		function activeCurrentMenuItem() {
			$('#statistics').addClass('active');
		}
	</script>
</body>
</html>
