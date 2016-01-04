<%@page import="com.mre.domain.User"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf" %>
<title>用户中心</title>
</head>

<body>
<!-- 首页 -->
<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf" %>
<!-- 设置居中 放大缩小时，始终处于中间位置-->
<div class="bodyPage"> 
  <!-- Banner 背景图与搜索框-->
	<!-- 搜素框设有 康复设备 合作医院 康复专家 康复论坛 用以搜索时的筛选 -->
  <%@ include file="/WEB-INF/jsp/public/bannerPage.jspf" %>
  
  <div class="mainPage">
   <%@ include file="/WEB-INF/jsp/public/patientLeftSidePage.jspf"%>
    <!-- 患者中心首页 -->
    <div class="rightSideTemplate">
    <div class="rightSide">
    	<div class="welcomeInfo">
        <div class="userPic">
        	<img src="${pageContext.request.contextPath}/style/images/userIcons/${user.icon}"/>
        </div>
        <div class="userInfo">
            您好！  ${user.trueName}<br/>
            今日计划<span style="color:#C30;" id="todayRehabPlanCount"><s:a action="patient_todayRehabPlanUI" >【 ${todayRehabPlanCount} 】</s:a></span><br/>
      <s:if test="%{#session.user.lastLoginTime != null}">
            上次登录时间：<span style="font-size:16px; color:#000;"><s:date name="%{#session.user.lastLoginTime}" format="yyyy-MM-dd HH:mm:ss"/></span>
       </s:if>
       <s:else>
       	这是您第一次登录
       </s:else>
        </div>
       </div>
       <div class="instruction">
       <span class="wltitle">尊敬的用户,您好！</span>
       <p>欢迎您注册使用MRE康复设备的医疗网络平台, MRE将向你提供以下服务：</p>
       <p>1. 在线浏览康复设备、合作医院和康复专家的信息，为您的康复带来便利</p>
       <p>2. 在线浏览并下载康复评定量表，帮助您进行自我评定</p>
       <p>3. 在站内康复论坛中浏览、回复和发帖，了解和分享您的康复知识</p>
       <p style="color:#F00;">*4. 在得到医生授权后，成为该医生的患者</p>
       <p>5. 完成第四条后，可以享受查看您的医疗状况、训练计划和康复状况等信息,并与医生进行互动</p>
       <p />
       <p />
       <p />
       <p style="color:#F00;">提示： 本网站所有关于医疗处方的建议，仅作为参考，具体请以医生所开处方为准，本站对此不承担法律责任。</p>
       <p />
       <p />
       <p />
       <p />
       <!-- 注册时间 -->
        <span class="registerTime"><s:date name="%{#session.user.registerTime}" format="yyyy-MM-dd HH:mm:ss"/></span>
        </div>
       </div>
    </div>
  </div>
<!-- Footer Page -->
 <%@ include file="/WEB-INF/jsp/public/footerPage.jspf" %>
 </div>
</body>
</html>
