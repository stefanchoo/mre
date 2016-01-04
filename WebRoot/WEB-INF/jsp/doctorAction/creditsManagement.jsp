<%@page import="com.mre.domain.User"%>
<%@page import="com.mre.util.Formatter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#creditsManagement").addClass('li_checked');
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
    	<div class="centerTitle">我的账户 &gt; 积分管理</div>
        <!-- 下载时需要的积分 -->
        <div class="creditsArea">
        您好！ ${user.trueName}<br/>
        您目前的积分数为 <span style="color:#F00">${user.credits}</span> 分
        <br />
        <br />
        <a href="#01">如何获取积分？</a> 
        </div>
        <div class="getCredits">
        <a name="01"></a>
            网站积分是根据您的在线时间，发帖量、回复量以及对本网站的贡献进行累加的。<br/><br />
            在线时间：+ 10分/小时<br/><br />
            发帖量： + 10分/贴<br/><br />
            回复量： + 1分/次<br /><br />
            网站建议: +20分/采纳 <br /><br />
        </div>
    </div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
