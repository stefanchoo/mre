<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#receiveBox").addClass('li_checked');
	});
</script>
<title>站内信</title>
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
					<div class="centerTitle">站内信息 &gt; 收件箱</div>
					<div class="messages">
						<ul>
							<s:iterator value="%{recordList}">
							<s:a action="patient_receiveMessageShowUI?message.id=%{id}">
							<s:if test="%{readByReceiver}">
								<li style="display:block;width:800px; height:30px; border-bottom:1px #DDD dashed;">
							</s:if>
							<s:else>
								<li style="display:block; width:800px; height:30px; font-weight:bold; border-bottom:1px #DDD dashed;">
							</s:else>
							<span style="width:100px;height:30px;display:block; text-align:center; float:left;">${fromUser.trueName}</span>
							<span style="width:500px;height:30px;display:block; float:left;">${title}</span>
							<span style="width:180px;height:30px;display:block; float:right;"><s:date name="postTime" format="yyyy-MM-dd HH:mm:ss"/></span>
							</li>
							</s:a>
							</s:iterator>
						</ul>	
						<!-- 分页信息 -->
						<%@ include file="/WEB-INF/jsp/public/turnPage.jspf"%>
						<s:form id="pageForm" action="patient_receiveBoxList"
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
