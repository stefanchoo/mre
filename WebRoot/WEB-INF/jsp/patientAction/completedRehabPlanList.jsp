<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#planCompleted").addClass('li_checked');
	});
</script>
<title>训练计划</title>
</head>

<body>
	<!-- 首页 -->
	<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf"%>
	<!-- 设置居中 放大缩小时，始终处于中间位置-->
	<div class="bodyPage">
		<!-- Banner 背景图与搜索框-->
		<!-- 搜素框设有 康复设备 合作医院 康复专家 康复论坛 用以搜索时的筛选 -->
		<%@ include file="/WEB-INF/jsp/public/bannerPage.jspf"%>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/patientLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
					<div class="centerTitle">训练计划 &gt; 完成计划</div>
					<!-- 今日计划 -->
					<!--  今日训练计划的 训练时间 训练模式 使用设备 训练时间 训练师 主治医生-->
					<div class="todayPlan">
						<table width="95%" border="0" align="center" cellpadding="0"
							cellspacing="0" id="plan">
							<tr>
								<th width="15%" scope="col">时间</th>
								<th width="20%" scope="col">训练模式</th>
								<th width="28%" scope="col">训练设备</th>
								<!-- 这里其实还应该包括训练次数 -->
								<th width="12%" scope="col">训练时间</th>
								<th width="12%" scope="col">训练医师</th>
								<th width="13%" scope="col">主治医师</th>
							</tr>
							<!-- 这里循环 -->
							<s:iterator value="%{recordList}">
							<tr style="border-top: 1px solid #999;">
								<td><s:date name="time" format="yyyy-MM-dd HH:mm"/></td>
								<td>${type}</td>
								<td>${equipment.name}</td>
								<td>${exerciseTime}分钟</td>
								<td>${equipment.therapist.trueName}</td>
								<td>${doctor.trueName}</td>
							</tr>
							</s:iterator>
						</table>
						<%@ include file="/WEB-INF/jsp/public/turnPage.jspf"%>
						<s:form id="pageForm" action="patient_completedRehabPlanList"
							method="post"></s:form>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
