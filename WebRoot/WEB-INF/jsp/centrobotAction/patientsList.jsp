<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/centrobotAction/public/centrobotcss.jspf"%>
<title>Centrobot上肢康复训练系统 - 患者列表</title>
<script type="text/javascript">
	$(function() {
		$("#cent_patientsList").addClass("navi_checked");
	});
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
		<div class="cent_patientsListPage">

			<!-- patient frame page -->
			<%@ include
				file="/WEB-INF/jsp/centrobotAction/public/centrobotPatientPage.jspf"%>
			<div class="cent_reportsPage">
				<span style="display:block; float:left; font-size:18px;">患者训练报告</span>
				<span
					style="display:block; float:right; font-size:16px; text-align:right; color:#09C; cursor:pointer;"
					onclick="javascript: patientReport.window.print();">打印报告</span>
				<div class="iframeBox800">
					<iframe name="patientReport" frameborder="0" height="100%"
						width="100%" scrolling="auto"
						src="http://www.nofate.net.cn/mre/centrobot_cent_patientReport.action?patientId=${patientId}"></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
