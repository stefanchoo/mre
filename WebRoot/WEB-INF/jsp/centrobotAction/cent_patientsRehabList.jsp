<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery-1.8.3.min.js"></script>
<title>训练计划列表</title>
<!-- html {overflow-x:hidden;} 这样设置之后就不会出现横向的滚动条-->
<!-- 使用两个不同名称的相同class 可以轻松实现 -->
<style type="text/css">
@import url("${pageContext.request.contextPath}/style/css/init.css");

html {
	overflow-x: hidden;
}

.cent_patientsRehabList {
	width: 300px;
	height: auto;
}

ol,.cent_patientTitle,.cent_patientInfoON {
	margin-top: 0px;
	width: 300px;
	height: 40px;
	font-size: 16px;
	display: block;
	border-bottom: 1px dashed #CCC;
	cursor: pointer;
}

.cent_patientTitle {
	margin-top: 10px;
	border-bottom: 1px solid #CCC;
	cursor: none;
}

.cent_patientInfoON,.cent_patientInfo {
	color: #090;
	background: #CCC;
}

ol li,ul li {
	width: 100px;
	height: 40px;
	display: block;
	float: left;
	line-height: 40px;
	text-align: center;
}

a:hover {
	color: #090;
}
</style>

<script type="text/javascript">
	$(function() {
		$('ol').mouseleave(function() {
			$(this).removeClass("cent_patientInfo");
		});

		$('ol').mousemove(function() {
			$(this).addClass("cent_patientInfo");
		});

		$("a").click(function() {
			$(this).siblings("a").find("ol").removeClass("cent_patientInfoON");
			$(this).find("ol").addClass("cent_patientInfoON");
		});
	});
</script>
</head>
<body>
	<div class="cent_patientsRehabList">
		<ul class="cent_patientTitle">
			<li>姓名</li>
			<li>时间</li>
			<li>类型</li>
		</ul>
		<s:iterator value="rehabPlansList" status="status">
		<s:a action="centrobot_cent_commandArea?rehabPlanId=%{id}&equipmentId=%{#equipmentId}" target="commandArea">
		<s:if test="%{#status.first}" >
		<ol class="cent_patientInfoON">
		</s:if>
		<s:else>
		<ol>
		</s:else>
			<li>${patient.trueName}</li>
			<li><s:date name="time" format="HH:mm" /></li>
			<s:if test="%{null != centrobotRehabPlan.subTrainingType}">
			<li>${centrobotRehabPlan.subTrainingType}</li>
			</s:if>
			<s:else>
			<li>${centrobotRehabPlan.trainingType}</li>
			</s:else>
		</ol>
		</s:a>
		</s:iterator>
	</div>
</body>
</html>
