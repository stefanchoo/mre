<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/centrobotAction/public/centrobotcss.jspf"%>
<title>Centrobot上肢康复训练系统 - 计划定制</title>
<script type="text/javascript">
	$(function() {
		$("#cent_makePlans").addClass("navi_checked");
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
		<div class="cent_makePlansPage">
		<!-- patient frame page -->
		<%@ include
			file="/WEB-INF/jsp/centrobotAction/public/centrobotPatientPage.jspf"%>
			<div class="cent_planContentPage">
				<span
					style="display:block; float:left; width:800px; font-size:18px;">计划内容</span>
				<div class="iframeBoxw802">
				<!-- 两种情况获取patientId的操作时不一样的 -->
				<s:if test="%{#model != null}">
					<iframe name="planContent" frameborder="0" height="100%"
						width="100%" scrolling="no"
						src="http://www.nofate.net.cn/mre/centrobot_cent_planContent.action?id=${model.id}&patientId=${model.rehabPlan.patient.id}"></iframe>
				</s:if>
				<s:else>
				<iframe name="planContent" frameborder="0" height="100%"
						width="100%" scrolling="no" 
						src="http://www.nofate.net.cn/mre/centrobot_cent_planContent.action?patientId=${patientId}"></iframe>
				</s:else>		
				</div>
			</div>
		</div>
	</div>
</body>
</html>
