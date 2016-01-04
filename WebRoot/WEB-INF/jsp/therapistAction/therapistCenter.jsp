<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<title>用户中心</title>
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
			<%@ include file="/WEB-INF/jsp/public/therapistLeftSidePage.jspf"%>
			<div class="rightSideTemplate">
				<div class="rightSide">
					<div class="welcomeInfo">
						<div class="userPic">
							<img
								src="${pageContext.request.contextPath}/style/images/userIcons/${user.icon}" />
						</div>
						<div class="userInfo">
							您好！ ${user.trueName}<br /> 今日计划<span style="color:#C30;"
								id="todayRehabPlanCount"><s:a
									action="therapist_todayRehabPlanUI">【 ${todayRehabPlanCount} 】</s:a></span><br />
							<s:if test="%{#session.user.lastLoginTime != null}">     
            上次登陆时间: <span style="font-size:16px; color:#000;"> 
            <s:date name="%{#session.user.lastLoginTime}" format="yyyy-MM-dd HH:mm:ss" />
								</span>
							</s:if>

							<s:else>
            	这是您的第一次登录
          </s:else>



						</div>
					</div>
					<div class="instruction">
						<span class="wltitle">尊敬的大夫,您好！</span>
						<p>欢迎您注册使用MRE康复设备的医疗网络平台, MRE将向你提供以下服务：</p>
						<p>1. 在线浏览康复设备、合作医院和康复专家的信息</p>
						<p>2. 在线浏览并下载康复评定量表，帮助您制定工作需要的评定量表</p>
						<p>3. 在站内康复论坛中浏览、回复和发帖，了解和分享您的康复知识</p>
						<p style="color:#F00;">*4.
							在设备管理中，可以调出康复设备的设置和控制页面，直接使用MRE进行设备控制，训练结果将直接保存到系统中，方便您查看</p>
						<p />
						<p />	
						<p />
						<p style="color:#F00;">提示：
							本网站旨在帮助更多患者大众了解康复知识，帮助康复医生提高工作效率，实时保存康复设备的训练效果，形成康复处方的大数据，推动康复事业的数字化发展。</p>
						<p />
						<p />
						<p />
						<p />
						<span class="registerTime"><s:date
								name="%{#session.user.registerTime}"
								format="yyyy-MM-dd HH:mm:ss" /></span>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
