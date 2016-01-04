<%@page import="com.mre.domain.User"%>
<%@page import="com.mre.util.Formatter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#messageSetup").addClass('li_checked');
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
		<%@ include file="/WEB-INF/jsp/public/newBannerPage.jspf" %>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/doctorLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
			<div class="rightSide">
    	<div class="centerTitle">我的账户 &gt; 短信通知设置</div>
        <!-- 短信通知设置 -->
        <s:form action="doctor_messageSetup" method="post">
        <input type="hidden" name="id" />
        <div class="messageSetupArea">
        	您好！<strong>${user.trueName}</strong>，在您有训练计划的当天，系统会免费向您发送通知，提醒您准时参加训练。<br/><br/>
            发送时间：每日7:00~18：00. 请选择通知方式：<br/><br/>
            
            <s:checkbox name="inform[0]" fieldValue="1" /> 免费短信通知 &nbsp;&nbsp;
            <s:checkbox name="inform[1]" fieldValue="2" /> 微信公众号通知
            <br/><br />
            手机号码 &nbsp;&nbsp; ${user.telephone }<br/> <br />
            微信号 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${user.weixin}<br/>    
        </div>
        <div class="confirmArea">
        <input type="submit" value="保  存" onclick="javascript: return window.confirm('确定修改吗？');"/>
      </div>
      </s:form>
    </div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
