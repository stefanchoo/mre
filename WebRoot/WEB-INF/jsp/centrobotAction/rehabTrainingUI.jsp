<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/centrobotAction/public/centrobotcss.jspf"%>
<title>Centrobot上肢康复训练系统 - 康复训练</title>
<script type="text/javascript">
	$(function() {
		$("#cent_rehabTraining").addClass("navi_checked");
	});
</script>
<style type="text/css">
.controlArea {
	width: 780px;
	height: 800px;
	margin: 16px 8px;
	border: 1px solid #CCC;
	float: left;
}
</style>
<script type="text/javascript">
	$(window).bind('beforeunload', function(e) {
			return '请确保训练计划已经完成！';
		}
	);
</script>
</head>
<!--  SSH + jFreeChart画图 -->
<!-- SSH + highcharts + jQuery -->
<body>

	<!-- 首页 -->
	<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf"%>

	<!-- 设置居中 放大缩小时，始终处于中间位置-->
	<div class="bodyPage">
		<!-- bannerPage -->
		<%@ include
			file="/WEB-INF/jsp/centrobotAction/public/centrobotBannerPage.jspf"%>
		<div class="cent_makePlansPage">
			<!-- patientsRehabList frame page -->
			<div class="iframeBox300">
				<iframe name="patientsRehabList" frameborder="0" height="100%"
					width="100%" scrolling="auto"
					src="http://www.nofate.net.cn/mre/centrobot_cent_patientsRehabList.action?equipmentId=${equipmentId}"></iframe>
			</div>
			<div class="controlArea">
				<iframe name="commandArea" frameborder="0" height="100%"
					width="100%" scrolling="no"
					src="http://www.nofate.net.cn/mre/centrobot_cent_commandArea.action?equipmentId=${equipmentId}&rehabPlanId=${rehabPlanId}">
				</iframe>
			</div>
		</div>
	</div>
</body>
</html>
