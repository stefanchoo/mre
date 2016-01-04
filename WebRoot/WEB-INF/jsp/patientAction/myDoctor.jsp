<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#myDoctor").addClass('li_checked');
	});
</script>
<title>我的账户</title>
</head>

<body>
	<!-- 首页 -->
	<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf"%>
	<!-- 设置居中 放大缩小时，始终处于中间位置-->
	<div class="bodyPage">
		<!-- Banner 背景图与搜索框-->
		<!-- 搜素框设有 康复设备 合作医院 康复专家 康复论坛 用以搜索时的筛选 -->
		<%@ include file="/WEB-INF/jsp/public/bannerPage.jspf" %>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/patientLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
    	<div class="centerTitle">医疗状况 &gt; 我的医生</div>
    	<s:if test="%{#session.user.doctor}">
        <div class="myDoctor">
        <div class="userPic"><img src="${pageContext.request.contextPath}/style/images/userIcons/${user.doctor.icon}"/></div>
        <div class="myDoctorInfo">
        <span style="font-weight:700;">${user.doctor.trueName}</span><br />
        	单位： ${user.doctor.hospital.name}<br />
        	职称： ${user.doctor.title}<br />
        	科室： ${user.doctor.department}<br /> 
       		擅长： ${user.doctor.goodAt}
       		 </div>
        	</div>
        <div class="connectDoc"><s:a action="patient_sendMessageUI?receiver=doctor">联系医生</s:a></div>
        <script type="text/javascript">
        	$(".connectDoc > a").mouseleave(function(){
        		$(this).css("background-color","#666");
        	});
        	$(".connectDoc > a").mouseover(function(){
        		$(this).css("background-color","#00BD00");
        	});
        </script>
        </s:if>	
        <s:else>
        	抱歉，你还没有关联的医生哟！
        </s:else>
   			 </div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
