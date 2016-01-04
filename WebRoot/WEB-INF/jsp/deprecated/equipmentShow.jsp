<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#equipmentsList").addClass('li_checked');
	});
</script>
<title>设备管理</title>
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
			<%@ include file="/WEB-INF/jsp/public/doctorLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
					<div class="centerTitle">设备管理 &gt; 设备列表 &gt;
						${equipmentUsed.name}</div>
					<div class="equipmentContent">
						<div class="equipmentFieldArea">
							<strong> 设备名称<br /> 设备编号<br /> 设备类型<br /> 负责治疗师<br />
								医 院<br /> 状 态<br />
							</strong>
						</div>

						<div class="equipmentFieldPropertyArea">
							${equipmentUsed.name}<br /> ${equipmentUsed.number}<br />
							${equipmentUsed.type}<br /> ${equipmentUsed.therapist.trueName}<br />
							${equipmentUsed.hospital.name}<br />
							<s:if test="%{#equipmentUsed.status == 0}">
									未运转
									</s:if>
							<s:else>
								<span style="color:#090;">运转中</span>
							</s:else>
							<br />
						</div>

						<div class="addRehabPlanContent">
							<strong>简  介</strong><br />
							<br />
							<p style="text-indent:2em;">${equipmentUsed.description}</p>
							<br />
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
